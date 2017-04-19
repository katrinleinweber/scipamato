package ch.difty.sipamato.web.model;

import java.util.List;

import org.apache.wicket.injection.Injector;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import ch.difty.sipamato.entity.SearchOrder;
import ch.difty.sipamato.paging.PaginationContext;
import ch.difty.sipamato.paging.PaginationRequest;
import ch.difty.sipamato.paging.Sort.Direction;
import ch.difty.sipamato.persistance.jooq.search.SearchOrderFilter;
import ch.difty.sipamato.service.SearchOrderService;

/**
 * Model that offers a wicket page to load {@link SearchOrder}s.
 *
 * <b>Note:</b> Currently limited to the first 20 search orders (globals first, then natural sort order) HARDCODED TODO
 *
 * @author u.joss
 */
public class SearchOrderModel extends LoadableDetachableModel<List<SearchOrder>> {

    private static final long serialVersionUID = 1L;

    @SpringBean
    private SearchOrderService service;

    private final int owner;

    public SearchOrderModel(int userId) {
        Injector.get().inject(this);
        this.owner = userId;
    }

    @Override
    protected List<SearchOrder> load() {
        final SearchOrderFilter filter = new SearchOrderFilter();
        filter.setOwnerIncludingGlobal(owner);
        final PaginationContext pc = new PaginationRequest(0, 20, Direction.ASC, "global");
        return service.findPageByFilter(filter, pc);
    }

}
