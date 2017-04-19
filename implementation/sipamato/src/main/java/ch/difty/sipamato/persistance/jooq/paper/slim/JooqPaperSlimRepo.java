package ch.difty.sipamato.persistance.jooq.paper.slim;

import static ch.difty.sipamato.db.tables.Paper.PAPER;

import java.util.List;

import org.jooq.DSLContext;
import org.jooq.TableField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ch.difty.sipamato.db.tables.records.PaperRecord;
import ch.difty.sipamato.entity.SearchOrder;
import ch.difty.sipamato.entity.projection.PaperSlim;
import ch.difty.sipamato.lib.AssertAs;
import ch.difty.sipamato.paging.PaginationContext;
import ch.difty.sipamato.persistance.jooq.GenericFilterConditionMapper;
import ch.difty.sipamato.persistance.jooq.JooqReadOnlyRepo;
import ch.difty.sipamato.persistance.jooq.JooqSortMapper;
import ch.difty.sipamato.persistance.jooq.paper.PaperFilter;
import ch.difty.sipamato.persistance.jooq.paper.searchorder.PaperSlimBackedSearchOrderRepository;
import ch.difty.sipamato.service.Localization;

@Repository
public class JooqPaperSlimRepo extends JooqReadOnlyRepo<PaperRecord, PaperSlim, Long, ch.difty.sipamato.db.tables.Paper, PaperSlimRecordMapper, PaperFilter> implements PaperSlimRepository {

    private static final long serialVersionUID = 1L;

    private final PaperSlimBackedSearchOrderRepository searchOrderRepository;

    @Autowired
    public JooqPaperSlimRepo(DSLContext dsl, PaperSlimRecordMapper mapper, JooqSortMapper<PaperRecord, PaperSlim, ch.difty.sipamato.db.tables.Paper> sortMapper,
            GenericFilterConditionMapper<PaperFilter> filterConditionMapper, Localization localization, PaperSlimBackedSearchOrderRepository searchOrderRepository) {
        super(dsl, mapper, sortMapper, filterConditionMapper, localization);
        this.searchOrderRepository = AssertAs.notNull(searchOrderRepository, "searchOrderRepository");
    }

    @Override
    protected Class<? extends PaperSlim> getEntityClass() {
        return PaperSlim.class;
    }

    @Override
    protected Class<? extends PaperRecord> getRecordClass() {
        return PaperRecord.class;
    }

    @Override
    protected ch.difty.sipamato.db.tables.Paper getTable() {
        return PAPER;
    }

    @Override
    protected TableField<PaperRecord, Long> getTableId() {
        return PAPER.ID;
    }

    /** {@inheritDoc} */
    @Override
    public List<PaperSlim> findBySearchOrder(final SearchOrder searchOrder) {
        List<PaperSlim> papers = searchOrderRepository.findBySearchOrder(searchOrder);
        enrichAssociatedEntitiesOfAll(papers);
        return papers;
    }

    /** {@inheritDoc} */
    @Override
    public List<PaperSlim> findPageBySearchOrder(SearchOrder searchOrder, PaginationContext paginationContext) {
        final List<PaperSlim> entities = searchOrderRepository.findPageBySearchOrder(searchOrder, paginationContext);
        enrichAssociatedEntitiesOfAll(entities);
        return entities;
    }

    /** {@inheritDoc} */
    @Override
    public int countBySearchOrder(SearchOrder searchOrder) {
        return searchOrderRepository.countBySearchOrder(searchOrder);
    }

}
