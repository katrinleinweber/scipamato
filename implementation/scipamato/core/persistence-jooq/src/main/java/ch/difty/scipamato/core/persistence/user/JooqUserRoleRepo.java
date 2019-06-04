package ch.difty.scipamato.core.persistence.user;

import static ch.difty.scipamato.core.db.tables.UserRole.USER_ROLE;

import java.util.HashSet;
import java.util.Set;

import org.jooq.DSLContext;
import org.jooq.InsertValuesStep2;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import ch.difty.scipamato.core.auth.Role;
import ch.difty.scipamato.core.db.tables.records.UserRoleRecord;
import ch.difty.scipamato.core.entity.User;

/**
 * The repository to manage {@link Role}s for {@link User}s.
 *
 * @author u.joss
 */
@Repository
@CacheConfig(cacheNames = "userRolesByUserId")
public class JooqUserRoleRepo implements UserRoleRepository {

    private final DSLContext dsl;

    public JooqUserRoleRepo(@Qualifier("dslContext") final DSLContext dsl) {
        this.dsl = dsl;
    }

    @Override
    @Cacheable(key = "#userId")
    public Set<Role> findRolesForUser(final Integer userId) {
        return new HashSet<>(dsl
            .select(USER_ROLE.ROLE_ID)
            .from(USER_ROLE)
            .where(USER_ROLE.USER_ID.eq(userId))
            .fetch()
            .map(rec -> Role.of((Integer) rec.getValue(0))));
    }

    @Override
    @CacheEvict(key = "#userId")
    public Set<Role> addNewUserRolesOutOf(final Integer userId, final Set<Role> roles) {
        InsertValuesStep2<UserRoleRecord, Integer, Integer> step = dsl.insertInto(USER_ROLE, USER_ROLE.USER_ID,
            USER_ROLE.ROLE_ID);
        for (final Role r : roles)
            step = step.values(userId, r.getId());
        step
            .onDuplicateKeyIgnore()
            .execute();
        return findRolesForUser(userId);
    }

    @Override
    @CacheEvict(key = "#userId")
    public void deleteAllRolesExcept(final Integer userId, final Set<Integer> roleIds) {
        dsl
            .deleteFrom(USER_ROLE)
            .where(USER_ROLE.USER_ID
                .equal(userId)
                .and(USER_ROLE.ROLE_ID.notIn(roleIds)))
            .execute();
    }

}