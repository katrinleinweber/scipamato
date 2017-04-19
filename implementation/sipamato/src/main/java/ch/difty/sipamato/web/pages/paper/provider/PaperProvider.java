package ch.difty.sipamato.web.pages.paper.provider;

import java.util.Iterator;

import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.IFilterStateLocator;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;

import ch.difty.sipamato.entity.Paper;
import ch.difty.sipamato.paging.PaginationContext;
import ch.difty.sipamato.paging.PaginationRequest;
import ch.difty.sipamato.paging.Sort.Direction;
import ch.difty.sipamato.persistance.jooq.paper.PaperFilter;
import ch.difty.sipamato.service.PaperService;

/**
 * The data provider providing the wicket components access to the persisted paper data
 *
 * @author u.joss
 */
public class PaperProvider extends SortableDataProvider<Paper, String> implements IFilterStateLocator<PaperFilter> {

    private static final long serialVersionUID = 1L;

    @SpringBean
    private PaperService service;

    private PaperFilter filter;

    public PaperProvider() {
        this(null);
    }

    public PaperProvider(PaperFilter filter) {
        Injector.get().inject(this);
        this.filter = filter != null ? filter : new PaperFilter();
        setSort(Paper.AUTHORS, SortOrder.ASCENDING);
    }

    /** protected for test purposes */
    protected void setService(PaperService service) {
        this.service = service;
    }

    /** protected for test purposes */
    protected PaperFilter getFilter() {
        return filter;
    }

    @Override
    public Iterator<Paper> iterator(long offset, long size) {
        Direction dir = getSort().isAscending() ? Direction.ASC : Direction.DESC;
        String sortProp = getSort().getProperty();
        PaginationContext pc = new PaginationRequest((int) offset, (int) size, dir, sortProp);
        return service.findPageByFilter(filter, pc).iterator();
    }

    @Override
    public long size() {
        return service.countByFilter(filter);
    }

    @Override
    public IModel<Paper> model(Paper entity) {
        return new Model<>(entity);
    }

    @Override
    public PaperFilter getFilterState() {
        return filter;
    }

    @Override
    public void setFilterState(PaperFilter state) {
        this.filter = state;
    }

}
