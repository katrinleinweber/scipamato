package ch.difty.scipamato.persistence;

import java.io.Serializable;
import java.util.List;

import ch.difty.scipamato.NullArgumentException;
import ch.difty.scipamato.entity.PublicPaper;
import ch.difty.scipamato.entity.filter.PublicPaperFilter;
import ch.difty.scipamato.persistence.paging.PaginationContext;

public interface PublicPaperRepository extends Serializable {

    /**
     * Finds the persisted {@link PublicPaper} with the provided id.
     * If a repo requires the language code, the default language code will be used.
     *
     * @param id - must not be null
     * @return the persisted {@link PublicPaper} or null if it can't be found.
     * @throws NullArgumentException if the id is null.
     */
    PublicPaper findById(Long id);

    /**
     * Finds the persisted {@link PublicPaper}s matching the provided filter and pagination context.
     * If a repo requires the language code, the default language code will be used.
     *
     * @param filter {@link PublicPaperFilter}
     * @param paginationContext {@link PaginationContext}
     * @return list of all matching {@link PublicPaper}s
     */
    List<PublicPaper> findPageByFilter(PublicPaperFilter filter, PaginationContext paginationContext);

    /**
     * Counts all persisted {@link PublicPaper}s matching the provided filter. 
     *
     * @param filter {@link PublicPaper}s
     * @return list of all matching {@link PublicPaper}s
     */
    int countByFilter(PublicPaperFilter filter);
}
