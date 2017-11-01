package ch.difty.scipamato.web.panel;

import org.apache.wicket.model.IModel;

import ch.difty.scipamato.ScipamatoPublicSession;
import ch.difty.scipamato.web.AbstractPanel;
import ch.difty.scipamato.web.Mode;

public abstract class BasePanel<T> extends AbstractPanel<T> {

    private static final long serialVersionUID = 1L;

    public BasePanel(final String id) {
        this(id, null, Mode.VIEW);
    }

    public BasePanel(final String id, IModel<T> model) {
        this(id, model, Mode.VIEW);
    }

    public BasePanel(final String id, IModel<T> model, Mode mode) {
        super(id, model, mode);
    }

    protected String getLocalization() {
        return ScipamatoPublicSession.get().getLocale().getLanguage();
    }

}