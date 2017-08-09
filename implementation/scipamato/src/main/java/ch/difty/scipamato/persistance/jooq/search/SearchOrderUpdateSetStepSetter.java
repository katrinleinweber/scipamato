package ch.difty.scipamato.persistance.jooq.search;

import static ch.difty.scipamato.db.tables.SearchOrder.SEARCH_ORDER;

import org.jooq.UpdateSetFirstStep;
import org.jooq.UpdateSetMoreStep;
import org.springframework.stereotype.Component;

import ch.difty.scipamato.db.tables.records.SearchOrderRecord;
import ch.difty.scipamato.entity.SearchOrder;
import ch.difty.scipamato.entity.filter.SearchCondition;
import ch.difty.scipamato.lib.AssertAs;
import ch.difty.scipamato.lib.DateUtils;
import ch.difty.scipamato.persistance.jooq.UpdateSetStepSetter;

/**
 * The update step setter used for updating {@link SearchOrder}s.<p>
 *
 * <b>Note:</b> the {@link SearchCondition}s are not updated here.
 *
 * @author u.joss
 */
@Component
public class SearchOrderUpdateSetStepSetter implements UpdateSetStepSetter<SearchOrderRecord, SearchOrder> {

    /** {@inheritDoc} */
    @Override
    public UpdateSetMoreStep<SearchOrderRecord> setFieldsFor(UpdateSetFirstStep<SearchOrderRecord> step, SearchOrder e) {
        AssertAs.notNull(step, "step");
        AssertAs.notNull(e, "entity");
        // @formatter:off
        return step
            .set(SEARCH_ORDER.NAME, e.getName())
            .set(SEARCH_ORDER.OWNER, e.getOwner())
            .set(SEARCH_ORDER.GLOBAL, e.isGlobal())

            .set(SEARCH_ORDER.CREATED, DateUtils.tsOf(e.getCreated()))
            .set(SEARCH_ORDER.CREATED_BY, e.getCreatedBy())
            .set(SEARCH_ORDER.LAST_MODIFIED, DateUtils.tsOf(e.getLastModified()))
            .set(SEARCH_ORDER.LAST_MODIFIED_BY, e.getLastModifiedBy())
            .set(SEARCH_ORDER.VERSION, e.getVersion() + 1);
         // @formatter:on
    }

}