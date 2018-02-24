package ch.difty.scipamato.common.persistence.code;

import java.util.List;

import ch.difty.scipamato.common.entity.CodeClassId;
import ch.difty.scipamato.common.entity.CodeLike;
import ch.difty.scipamato.common.persistence.CodeLikeService;

/**
 * Generic implementation of the {@link CodeLikeService} interface.
 *
 * @author u.joss
 *
 * @param <T>
 *            code like classes extending {@link CodeLike}
 * @param <R>
 *            repositories implementing {@link CodeLikeRepository}
 */
public abstract class JooqCodeLikeService<T extends CodeLike, R extends CodeLikeRepository<T>>
        implements CodeLikeService<T> {

    private final R repo;

    public JooqCodeLikeService(final R repo) {
        this.repo = repo;
    }

    @Override
    public List<T> findCodesOfClass(final CodeClassId codeClassId, final String languageCode) {
        return repo.findCodesOfClass(codeClassId, languageCode);
    }

}
