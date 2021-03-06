package ch.difty.scipamato.core.web.user;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import ch.difty.scipamato.core.auth.Role;
import ch.difty.scipamato.core.entity.User;

/**
 * Wrapper for the {@link User} object delegating all operations
 * to the delegate. In addition provides fields for the currentPassword
 * and the passwordConfirmation.
 */
public class ChangePasswordUser implements Serializable {

    private static final long serialVersionUID = 1L;

    private final User user;

    private String currentPassword;
    private String password2;

    /**
     * Instantiates an empty {@link ChangePasswordUser}.
     */
    public ChangePasswordUser() {
        this(new User(), false);
    }

    /**
     * Instantiates a {@link ChangePasswordUser} from the provided {@link User}
     */
    public ChangePasswordUser(@NotNull final User user) {
        this(user, false);
    }

    /**
     * Instantiates a {@link ChangePasswordUser} from the provided {@link User}.
     * If {@code clearPassword} is true, it will set the password to null.
     *
     * @param user
     *     the user to populate the fields from
     * @param clearPassword
     *     if true: password will be set to null.
     */
    public ChangePasswordUser(@NotNull final User user, final boolean clearPassword) {
        this.user = user;
        if (clearPassword)
            this.user.setPassword(null);
    }

    /**
     * @return the user. Is never null.
     */
    @NotNull
    public User toUser() {
        return user;
    }

    @NotNull
    public Integer getId() {
        return user.getId();
    }

    public void setId(Integer id) {
        user.setId(id);
    }

    @NotNull
    public String getUserName() {
        return user.getUserName();
    }

    public void setUserName(final String value) {
        user.setUserName(value);
    }

    @NotNull
    public String getFirstName() {
        return user.getFirstName();
    }

    public void setFirstName(final String value) {
        user.setFirstName(value);
    }

    @NotNull
    public String getLastName() {
        return user.getLastName();
    }

    public void setLastName(final String value) {
        user.setLastName(value);
    }

    @NotNull
    public String getEmail() {
        return user.getEmail();
    }

    public void setEmail(final String value) {
        user.setEmail(value);
    }

    @Nullable
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

    @NotNull
    public List<Role> getRoles() {
        return new ArrayList<>(user.getRoles());
    }

    public void setRoles(final List<Role> roles) {
        user.setRoles(new HashSet<>(roles));
    }

    public void addRole(final Role role) {
        user.addRole(role);
    }

    public void removeRole(final Role role) {
        user.removeRole(role);
    }

    @NotNull
    public String getFullName() {
        return user.getFullName();
    }

    @NotNull
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

    @NotNull
    public String getRolesString() {
        return user
            .getRoles()
            .stream()
            .map(Enum::name)
            .sorted()
            .collect(Collectors.joining(", "));
    }
}
