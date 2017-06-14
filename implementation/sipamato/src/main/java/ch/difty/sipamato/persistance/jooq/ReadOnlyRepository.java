package ch.difty.sipamato.persistance.jooq;

import java.io.Serializable;
import java.util.List;

import ch.difty.sipamato.entity.SipamatoEntity;
import ch.difty.sipamato.entity.filter.SipamatoFilter;
import ch.difty.sipamato.lib.NullArgumentException;
import ch.difty.sipamato.paging.PaginationContext;

/**
 * The generic repository interface for reading-only entity repository methods.
 *
 * @author u.joss
 *
 * @param <T> the type of the entity, extending {@link SipamatoEntity}
 * @param <ID> the type of the ID of the entity
 * @param <F> the type of the filter extending {@link SipamatorFilter}
 */
public interface ReadOnlyRepository<T extends SipamatoEntity, ID, F extends SipamatoFilter> extends Serializable {

    /**
     * Finds all persisted entities.
     *
     * @return list of all entities {@code T}
     */
    List<T> findAll();

    /**
     * Finds the persistent entities {@code T} with the provided id.
     *
     * @param id - must not be null
     * @return the persisted entity {@code T} or null if it can't be found.
     * @throws NullArgumentException if the id is null.
     */
    T findById(ID id);

    /**
     * Finds the persisted entities matching the provided filter and pagination context.
     *
     * @param filter of type {@code F}
     * @param paginationContext {@link PaginationContext}
     * @return list of all matching entities {@code T}
     */
    List<T> findPageByFilter(F filter, PaginationContext paginationContext);

    /**
     * Counts all persisted entities matching the provided filter. 
     *
     * @param filter of type {@code F}
     * @return list of all matching entities {@code T}
     */
    int countByFilter(F filter);

    /**
     * Finds the ids of the persisted entities matching the provided filter and pagination context.
     *
     * @param filter of type {@code F}
     * @param paginationContext {@link PaginationContext}
     * @return list of the ids of type {@code ID} of matching entities {@code T}
     */
    List<ID> findPageOfIdsByFilter(F filter, PaginationContext paginationContext);

}
