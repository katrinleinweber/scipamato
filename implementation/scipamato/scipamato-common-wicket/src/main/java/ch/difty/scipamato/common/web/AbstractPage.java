package ch.difty.scipamato.common.web;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.bean.validation.PropertyValidator;
import org.apache.wicket.devutils.debugbar.DebugBar;
import org.apache.wicket.markup.html.GenericWebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.panel.EmptyPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.string.Strings;

import ch.difty.scipamato.common.DateTimeService;
import ch.difty.scipamato.common.config.core.ApplicationProperties;
import ch.difty.scipamato.common.web.component.SerializableSupplier;
import de.agilecoders.wicket.core.markup.html.bootstrap.button.BootstrapAjaxButton;
import de.agilecoders.wicket.core.markup.html.bootstrap.button.BootstrapExternalLink.Target;
import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import de.agilecoders.wicket.core.markup.html.bootstrap.common.NotificationPanel;
import de.agilecoders.wicket.core.markup.html.bootstrap.image.IconType;
import de.agilecoders.wicket.core.markup.html.bootstrap.navbar.Navbar;
import de.agilecoders.wicket.core.markup.html.bootstrap.navbar.NavbarButton;
import de.agilecoders.wicket.core.markup.html.bootstrap.navbar.NavbarComponents;
import de.agilecoders.wicket.core.markup.html.bootstrap.navbar.NavbarExternalLink;

public abstract class AbstractPage<T> extends GenericWebPage<T> {

    private static final long serialVersionUID = 1L;

    protected static final String LABEL_TAG = WicketUtils.LABEL_TAG;
    protected static final String LABEL_RESOURCE_TAG = WicketUtils.LABEL_RESOURCE_TAG;
    protected static final String PANEL_HEADER_RESOURCE_TAG = WicketUtils.PANEL_HEADER_RESOURCE_TAG;
    protected static final String TITLE_RESOURCE_TAG = WicketUtils.TITLE_RESOURCE_TAG;

    @SpringBean
    private DateTimeService dateTimeService;

    private NotificationPanel feedbackPanel;
    private Navbar navbar;

    public AbstractPage(final PageParameters parameters) {
        super(parameters);
    }

    public AbstractPage(final IModel<T> model) {
        super(model);
    }

    protected DateTimeService getDateTimeService() {
        return dateTimeService;
    }

    protected Navbar getNavBar() {
        return navbar;
    }

    public NotificationPanel getFeedbackPanel() {
        return feedbackPanel;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        createAndAddNavBar("navbar");
        createAndAddFeedbackPanel("feedback");
        createAndAddDebugBar("debug");
    }

    private void createAndAddNavBar(String id) {
        navbar = newNavbar(id);
        queue(navbar);
        extendNavBar();
    }

    /**
     * Override if you need to extend the {@link Navbar}
     */
    protected void extendNavBar() {
    }

    private void createAndAddFeedbackPanel(String label) {
        feedbackPanel = new NotificationPanel(label);
        feedbackPanel.setOutputMarkupId(true);
        queue(feedbackPanel);
    }

    private void createAndAddDebugBar(String label) {
        if (getApplication().getDebugSettings().isDevelopmentUtilitiesEnabled()) {
            queue(new DebugBar(label).positionBottom());
        } else {
            queue(new EmptyPanel(label).setVisible(false));
        }
    }

    private Navbar newNavbar(String markupId) {
        Navbar nb = new Navbar(markupId);

        nb.setPosition(Navbar.Position.TOP);
        nb.setBrandName(getBrandName());
        nb.setInverted(true);

        addLinksTo(nb);

        return nb;
    }

    private IModel<String> getBrandName() {
        String brand = getProperties().getBrand();
        if (Strings.isEmpty(brand) || "n.a.".equals(brand))
            brand = new StringResourceModel("brandname", this, null).getString();
        return Model.of(brand);
    }

    protected abstract ApplicationProperties getProperties();

    protected void addLinksTo(Navbar nb) {
    }

    protected <P extends AbstractPage<?>> void addPageLink(Navbar navbar, Class<P> pageClass, String labelResource, IconType iconType, Navbar.ComponentPosition position) {
        final String label = new StringResourceModel(labelResource, this, null).getString();
        NavbarButton<Void> button = new NavbarButton<Void>(pageClass, Model.of(label)).setIconType(iconType);
        navbar.addComponents(NavbarComponents.transform(position, button));
    }

    protected void addExternalLink(final Navbar navbar, final String url, final String label, final IconType iconType, final Navbar.ComponentPosition position) {
        NavbarExternalLink link = new NavbarExternalLink(Model.of(url));
        link.setLabel(Model.of(label));
        link.setTarget(Target.blank);
        if (iconType != null)
            link.setIconType(iconType);
        navbar.addComponents(NavbarComponents.transform(position, link));

    }

    protected void queueFieldAndLabel(FormComponent<?> field) {
        queueFieldAndLabel(field, null);
    }

    protected void queueFieldAndLabel(FormComponent<?> field, PropertyValidator<?> pv) {
        String id = field.getId();
        StringResourceModel labelModel = new StringResourceModel(id + LABEL_RESOURCE_TAG, this, null);
        queue(new Label(id + LABEL_TAG, labelModel));
        field.setLabel(labelModel);
        queue(field);
        if (pv != null) {
            field.add(pv);
        }
    }

    protected void queueResponsePageButton(final String id, SerializableSupplier<AbstractPage<?>> responsePage) {
        BootstrapAjaxButton newButton = new BootstrapAjaxButton(id, new StringResourceModel(id + LABEL_RESOURCE_TAG, this, null), Type.Default) {
            private static final long serialVersionUID = 1L;

            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                super.onSubmit(target, form);
                setResponsePage(responsePage.get());
            }
        };
        queue(newButton);
    }

    protected void queuePanelHeadingFor(String id) {
        queue(new Label(id + LABEL_TAG, new StringResourceModel(id + PANEL_HEADER_RESOURCE_TAG, this, null)));
    }

    protected boolean isSignedIn() {
        return AuthenticatedWebSession.get().isSignedIn();
    }

    protected boolean signIn(String username, String password) {
        return AuthenticatedWebSession.get().signIn(username, password);
    }

    protected void signOutAndInvalidate() {
        AuthenticatedWebSession.get().invalidate();
    }

}