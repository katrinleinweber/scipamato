package ch.difty.scipamato.core.web.security;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ch.difty.scipamato.common.AssertAs;
import ch.difty.scipamato.core.entity.User;
import ch.difty.scipamato.core.persistence.UserService;

/**
 * The implementation of {@link UserDetailsService} loads the user from the {@link UserService} and
 * wraps it into a a scipamato specific implementation of {@link UserDetails}.
 *
 * @author u.joss
 */
@Service("scipamatoUserDetailService")
public class ScipamatoUserDetailService implements UserDetailsService {

    private final UserService userService;

    public ScipamatoUserDetailService(final UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(final String username) {
        final String un = AssertAs.notNull(username, "username");
        final Optional<User> userOption = userService.findByUserName(un);
        if (!userOption.isPresent()) {
            throw new UsernameNotFoundException("No user found with name " + un);
        } else {
            return new ScipamatoUserDetails(userOption.get());
        }
    }

}