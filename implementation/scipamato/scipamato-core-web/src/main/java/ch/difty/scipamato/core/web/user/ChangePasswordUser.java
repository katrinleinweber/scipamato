package ch.difty.scipamato.core.web.user;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import ch.difty.scipamato.common.AssertAs;
import ch.difty.scipamato.core.auth.Role;
import ch.difty.scipamato.core.entity.User;

/**
 * Wrapper for the {@link User} object delegating all operations
 * to the delegate. In addition provides fields for the currentPassword
 * and the passwordConfirmation.
 */
@SuppressWarnings("WeakerAccess")
public class ChangePasswordUser implements Serializable {

    private static final long serialVersionUID = 1L;

    private final User user;

    private String currentPassword;
    private String password2;

    public ChangePasswordUser() {
        this(new User());
    }

    public ChangePasswordUser(final User user) {
        this.user = AssertAs.notNull(user, "user");
    }

    public User toUser() {
        return user;
    }

    public Integer getId() {
        return user.getId();
    }

    public void setId(Integer id) {
        user.setId(id);
    }

    public String getUserName() {
        return user.getUserName();
    }

    public void setUserName(final String value) {
        user.setUserName(value);
    }

    public String getFirstName() {
        return user.getFirstName();
    }

    public void setFirstName(final String value) {
        user.setFirstName(value);
    }

    public String getLastName() {
        return user.getLastName();
    }

    public void setLastName(final String value) {
        user.setLastName(value);
    }

    public String getEmail() {
        return user.getEmail();
    }

    public void setEmail(final String value) {
        user.setEmail(value);
    }

    public String getPassword() {
        return user.getPassword();
    }

    public void setPassword(final String value) {
        user.setPassword(value);
    }

    public boolean isEnabled() {
        return user.isEnabled();
    }

    public void setEnabled(final boolean value) {
        user.setEnabled(value);
    }

    public List<Role> getRoles() {
        return user.getRoles();
    }

    public void setRoles(final List<Role> roles) {
        user.setRoles(roles);
    }

    public void addRole(final Role role) {
        user.addRole(role);
    }

    public void removeRole(final Role role) {
        user.removeRole(role);
    }

    public String getFullName() {
        return user.getFullName();
    }

    public String getDisplayValue() {
        return user.getDisplayValue();
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(final String value) {
        password2 = value;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(final String value) {
        currentPassword = value;
    }

    public String getRolesString() {
        return user
            .getRoles()
            .stream()
            .map(Enum::name)
            .collect(Collectors.joining(", "));
    }
}
