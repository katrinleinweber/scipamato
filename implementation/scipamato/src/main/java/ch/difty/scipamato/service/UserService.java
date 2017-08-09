package ch.difty.scipamato.service;

import java.util.Optional;

import ch.difty.scipamato.entity.User;
import ch.difty.scipamato.persistance.jooq.user.UserFilter;

/**
 * The {@link UserService} interface - defining {@link User} specific service methods.
 *
 * @author u.joss
 */
public interface UserService extends EntityService<Integer, User, UserFilter> {

    /**
     * Find {@link User} by username and return it as optional.
     *
     * @param userName
     * @return user optional of user
     */
    Optional<User> findByUserName(String userName);

}