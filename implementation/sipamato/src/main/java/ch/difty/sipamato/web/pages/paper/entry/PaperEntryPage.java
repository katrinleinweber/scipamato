package ch.difty.sipamato.web.pages.paper.entry;

import org.apache.wicket.PageReference;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.wicketstuff.annotation.mount.MountPath;

import ch.difty.sipamato.auth.Roles;
import ch.difty.sipamato.entity.Paper;
import ch.difty.sipamato.service.PaperService;
import ch.difty.sipamato.web.pages.SelfUpdatingPage;
import ch.difty.sipamato.web.panel.paper.EditablePaperPanel;

/**
 * Page to add new papers or modify existing papers. The page implements the {@link SelfUpdatingPage},
 * implying that changes in individual fields will be validated and persisted immediately.
 * <p>
 * There is a number of validations in place (backed by JSR 303 validations on the {@link Paper} entity),
 * that might prevent a save. This is useful if an existing paper is modified in a way that fails validation.
 * The feedback panel on the BasePage immediately indicates the problem that prevents the save.
 * <p>
 * However, if you enter a new paper, you would automatically fail validation on several fields, which is
 * indicated to the user once you have entered the content of the first field. We don't want several
 * validation messages popping up with every field change until you have reached the state where persisting
 * the paper is actually possible.
 * <p>
 * We therefore evaluate if the paper has been persisted already (either previously after loading a saved paper -
 * or once all required fields have been successfully entered in the process of setting up a new paper). The
 * evaluation is based on whether the paper does have an ID (which is assigned by the persistence layer upon
 * saving). If the evaluation indicates the paper has not yet been saved, the feedback messages are restricted
 * to one message at a time. If the paper has been persisted, several feedback messages would be displayed to
 * the user if the come up.
 *
 * In order to fetch fields from PubMed, the default model now contains specific 'n.a.' values for the fields
 * that can't be null. In case PubMed import is enabled, those will be replaced with real values (except for the
 * goals field, which is not available in the PubMed export).
 *
 * In order to be able to jump back to the calling page (search or paper list page), the page accepts the calling
 * page as PageReference and passes it down to the respective panels. Even more, if called from the search page,
 * the page may need to offer a button to exclude a paper from a search, hence the optional searchOrderId.
 *
 * @author u.joss
 */
@MountPath("entry")
@AuthorizeInstantiation({ Roles.USER, Roles.ADMIN })
public class PaperEntryPage extends SelfUpdatingPage<Paper> {

    private static final long serialVersionUID = 1L;

    @SpringBean
    private PaperService service;

    private EditablePaperPanel contentPanel;

    private final PageReference callingPage;
    private final Long searchOrderId;

    /**
     * Instantiates the page with a new paper with a free number and n.a. values on all non-nullable fields.
     * Allows the page to jump back to the calling page and also exclude the page from a search order.
     * @param parameters page parameters
     * @param callingPage page reference to the page that called this page. Can be null.
     * @param searchOrderId id of the search order that found this paper. Offers a button to exclude paper from search order.
     */
    public PaperEntryPage(PageParameters parameters, PageReference callingPage) {
        super(parameters);
        initDefaultModel();
        this.callingPage = callingPage;
        this.searchOrderId = null;
    }

    /**
     * Instantiates the page with the paper passed in as model. Allows the page to jump back to the calling page.
     * @param paperModel model of the paper that shall be displayed.
     * @param callingPage page reference to the page that called this page. Can be null.
     */
    public PaperEntryPage(IModel<Paper> paperModel, PageReference callingPage) {
        this(paperModel, callingPage, null);
    }

    /**
     * Instantiates the page with the paper passed in as model. Allows the page to jump back to the calling page.
     * Allows to exclude the paper from the search order where it was found.
     * @param paperModel model of the paper that shall be displayed.
     * @param callingPage page reference to the page that called this page. Can be null.
     * @param searchOrderId the id of the search order that found this paper. Offers a button to exclude the paper from the search order.
     */
    public PaperEntryPage(IModel<Paper> paperModel, PageReference callingPage, Long searchOrderId) {
        super(paperModel);
        this.callingPage = callingPage;
        this.searchOrderId = searchOrderId;
    }

    /**
     * Sets the n.a. values so the paper could be saved or filled with PubMed information
     */
    private void initDefaultModel() {
        Paper paper = new Paper();
        paper.setNumber(findEmptyNumber());
        paper.setAuthors(Paper.NA_AUTHORS);
        paper.setTitle(Paper.NA_STRING);
        paper.setLocation(Paper.NA_STRING);
        paper.setPublicationYear(Paper.NA_PUBL_YEAR);
        paper.setGoals(Paper.NA_STRING);
        setDefaultModel(Model.of(paper));
    }

    private long findEmptyNumber() {
        return service.findLowestFreeNumberStartingFrom(getApplicationProperties().getMinimumPaperNumberToBeRecycled());
    }

    @Override
    protected void implSpecificOnInitialize() {
        contentPanel = new EditablePaperPanel("contentPanel", getModel(), callingPage, searchOrderId) {
            private static final long serialVersionUID = 1L;

            @Override
            protected void onFormSubmit() {
                PaperEntryPage.this.doUpdate();
            }
        };
        queue(contentPanel);

        adaptFeedbackMessageCountBasedOnId();
    }

    protected void doUpdate() {
        doUpdate(getModelObject());
    }

    protected void doUpdate(Paper paper) {
        try {
            Paper persisted = service.saveOrUpdate(paper);
            if (persisted != null) {
                setModelObject(persisted);
                resetFeedbackMessages();
            } else {
                error(new StringResourceModel("save.error.hint", this, null).setParameters(getNullSafeId(), "").getString());
            }
        } catch (Exception ex) {
            error(new StringResourceModel("save.error.hint", this, null).setParameters(getNullSafeId(), ex.getMessage()).getString());
        }
    }

    private long getNullSafeId() {
        return getModelObject().getId() != null ? getModelObject().getId().longValue() : 0l;
    }

    /**
     * If the id is present, the paper has been persisted. Full feedback messages are enabled.
     * If the paper has not yet been persisted, we restrict the feedback messages to only one item
     * at a time, so the user is not flooded with messages while entering a paper that fails several
     * validations anyhow.
     */
    private void adaptFeedbackMessageCountBasedOnId() {
        if (getNullSafeId() == 0) {
            tuneDownFeedbackMessages();
        } else {
            resetFeedbackMessages();
        }
    }

    @Override
    protected Form<Paper> getForm() {
        return contentPanel.getForm();
    }

}
