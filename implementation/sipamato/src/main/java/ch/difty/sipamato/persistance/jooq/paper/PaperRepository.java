package ch.difty.sipamato.persistance.jooq.paper;

import java.util.List;

import ch.difty.sipamato.entity.Paper;
import ch.difty.sipamato.entity.SearchOrder;
import ch.difty.sipamato.paging.PaginationContext;
import ch.difty.sipamato.persistance.jooq.EntityRepository;
import ch.difty.sipamato.persistance.jooq.paper.searchorder.BySearchOrderRepository;

/**
 * Repository to manage {@link Paper}s
 * @author u.joss
 *
 */
public interface PaperRepository extends EntityRepository<Paper, Long, PaperFilter> {

    /**
     * Find Papers with the provided ids.  The codes are not enriched.
     * @param ids
     * @return list of papers (codes not available)
     */
    List<Paper> findByIds(List<Long> ids);

    /**
     * Find Papers (including codes) with the provided ids
     * @param ids
     * @return list of papers
     */
    List<Paper> findWithCodesByIds(List<Long> ids);

    /**
     * {@link BySearchOrderRepository#findBySearchOrder(SearchOrder)}
     */
    List<Paper> findBySearchOrder(SearchOrder searchOrder);

    /**
     * {@link BySearchOrderRepository#findPageBySearchOrder(SearchOrder, PaginationContext)}
     */
    List<Paper> findPageBySearchOrder(SearchOrder searchOrder, PaginationContext paginationContext);

    /**
     * {@link BySearchOrderRepository#countBySearchOrder(SearchOrder)}
     */
    int countBySearchOrder(SearchOrder searchOrder);

    /**
     * Find Papers by a number of PmIds
     * @param pmIds
     * @return list of {@link Paper}s
     */
    List<Paper> findByPmIds(List<Integer> pmIds);

    /**
     * Find Papers by a list of numbers
     * @param numbers
     * @return list of {@link Paper}s
     */
    List<Paper> findByNumbers(List<Long> numbers);

    /**
     * Finds the lowest free number starting from the supplied minimum  parameter. Will find gaps if those numbers
     * are equal to or larger than {@code minimumPaperNumberToBeRecycled}.
     * @param minimumPaperNumberToBeRecycled any gaps lower than this value will not be recycled
     * @return lowest free number ignoring any gaps below {@code minimumPaperNumberToBeRecycled}
     */
    long findLowestFreeNumberStartingFrom(long minimumPaperNumberToBeRecycled);

    /**
     * {@link BySearchOrderRepository#findPageOfIdsBySearchOrder(SearchOrder, PaginationContext)}
     */
    List<Long> findPageOfIdsBySearchOrder(SearchOrder searchOrder, PaginationContext paginationContext);

    /**
     * Excludes the given paperId from the results of the searchOrder with given searchOrderid.
     * @param searchOrderId the id of the search order
     * @param paperId the id of the paper
     */
    void excludePaperFromSearchOrderResults(long searchOrderId, long paperId);

}
