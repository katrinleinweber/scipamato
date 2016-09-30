package ch.difty.sipamato.web.pages;

import java.util.Optional;

import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.bean.validation.PropertyValidator;
import org.apache.wicket.devutils.debugbar.DebugBar;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.GenericWebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.panel.EmptyPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import ch.difty.sipamato.web.pages.home.SipamatoHomePage;
import ch.difty.sipamato.web.pages.login.LogoutPage;
import ch.difty.sipamato.web.pages.paper.list.PaperListPage;
import ch.difty.sipamato.web.resources.MainCssResourceReference;
import de.agilecoders.wicket.core.markup.html.bootstrap.common.NotificationPanel;
import de.agilecoders.wicket.core.markup.html.bootstrap.image.GlyphIconType;
import de.agilecoders.wicket.core.markup.html.bootstrap.image.IconType;
import de.agilecoders.wicket.core.markup.html.bootstrap.navbar.Navbar;
import de.agilecoders.wicket.core.markup.html.bootstrap.navbar.NavbarButton;
import de.agilecoders.wicket.core.markup.html.bootstrap.navbar.NavbarComponents;

public abstract class BasePage<T> extends GenericWebPage<T> {

    private static final long serialVersionUID = 1L;

    protected static final String LABEL_TAG = "Label";
    protected static final String LABEL_RECOURCE_TAG = ".label";

    private NotificationPanel feedbackPanel;

    public BasePage(final PageParameters parameters) {
        super(parameters);
    }

    public BasePage(final IModel<T> model) {
        super(model);
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);
        response.render(CssHeaderItem.forReference(MainCssResourceReference.get()));
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        add(newNavbar("navbar"));
        createAndAddFeedbackPanel("feedback");
        createAndAddDebugBar("debug");
    }

    private void createAndAddFeedbackPanel(String label) {
        feedbackPanel = new NotificationPanel(label);
        feedbackPanel.setOutputMarkupId(true);
        add(feedbackPanel);
    }

    private void createAndAddDebugBar(String label) {
        if (getApplication().getDebugSettings().isDevelopmentUtilitiesEnabled()) {
            add(new DebugBar(label).positionBottom());
        } else {
            add(new EmptyPanel(label).setVisible(false));
        }
    }

    private Navbar newNavbar(String markupId) {
        Navbar navbar = new Navbar(markupId);

        navbar.setPosition(Navbar.Position.TOP);
        navbar.setBrandName(new ResourceModel("brandname", "SiPaMaTo"));
        navbar.setInverted(true);

        addPageLink(navbar, SipamatoHomePage.class, new StringResourceModel("menu.home", this, null).getString(), GlyphIconType.home);
        addPageLink(navbar, PaperListPage.class, new StringResourceModel("menu.papers", this, null).getString(), GlyphIconType.list);
        addPageLink(navbar, LogoutPage.class, new StringResourceModel("menu.logout", this, null).getString(), GlyphIconType.edit);

        return navbar;
    }

    private <P extends BasePage<?>> void addPageLink(Navbar navbar, Class<P> pageClass, String label, IconType iconType) {
        NavbarButton<Void> button = new NavbarButton<Void>(pageClass, Model.of(label)).setIconType(iconType);
        navbar.addComponents(NavbarComponents.transform(Navbar.ComponentPosition.LEFT, button));
    }

    protected boolean isSignedIn() {
        return AuthenticatedWebSession.get().isSignedIn();
    }

    protected boolean signIn(String username, String password) {
        return AuthenticatedWebSession.get().signIn(username, password);
    }

    protected void queueFieldAndLabel(FormComponent<?> field, Optional<PropertyValidator<?>> pv) {
        String id = field.getId();
        StringResourceModel labelModel = new StringResourceModel(id + LABEL_RECOURCE_TAG, this, null);
        queue(new Label(id + LABEL_TAG, labelModel));
        field.setLabel(labelModel);
        queue(field);
        if (pv.isPresent()) {
            field.add(pv.get());
        }
    }

}