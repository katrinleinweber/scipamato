package ch.difty.scipamato.core.web.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ch.difty.scipamato.core.auth.Role;
import ch.difty.scipamato.core.entity.User;

class ChangePasswordUserTest {

    private final User user = new User();

    private ChangePasswordUser cpu;

    @BeforeEach
    void setUp() {
        user.setId(1);
        user.setUserName("un");
        user.setFirstName("fn");
        user.setLastName("ln");
        user.setEmail("em");
        user.setPassword("pw");
        user.setEnabled(true);
        user.setRoles(Set.of(Role.ADMIN, Role.USER));
    }

    @Test
    void fromUser_withoutResettingPassword_hasThePasswordButNoCurrentPasswordNorPassword2() {
        cpu = new ChangePasswordUser(user);

        assertUserBackedFields(cpu);

        assertThat(cpu.getPassword()).isEqualTo("pw");
        assertThat(cpu.getCurrentPassword()).isNull();
        assertThat(cpu.getPassword2()).isNull();
    }

    @Test
    void fromUser_withResettingPassword_hasNoneOfThePasswords() {
        cpu = new ChangePasswordUser(user, true);

        assertUserBackedFields(cpu);

        assertThat(cpu.getPassword()).isNull();
        assertThat(cpu.getCurrentPassword()).isNull();
        assertThat(cpu.getPassword2()).isNull();
    }

    private void assertUserBackedFields(final ChangePasswordUser u) {
        assertThat(u.getId()).isEqualTo(1);
        assertThat(u.getUserName()).isEqualTo("un");
        assertThat(u.getFirstName()).isEqualTo("fn");
        assertThat(u.getLastName()).isEqualTo("ln");
        assertThat(u.getEmail()).isEqualTo("em");

        assertThat(u.isEnabled()).isEqualTo(true);
        assertThat(u.getRoles()).contains(Role.ADMIN, Role.USER);

        assertThat(u.getFullName()).isEqualTo("fn ln");
        assertThat(u.getDisplayValue()).isEqualTo("un");
    }

    @Test
    void getSet() {
        cpu = new ChangePasswordUser();
        cpu.setId(1);
        cpu.setUserName("un");
        cpu.setFirstName("fn");
        cpu.setLastName("ln");
        cpu.setEmail("em");
        cpu.setPassword("pw");
        cpu.setPassword2("pw2");
        cpu.setCurrentPassword("cpw");
        cpu.setEnabled(true);
        cpu.setRoles(List.of(Role.ADMIN, Role.USER));

        assertUserBackedFields(cpu);

        assertThat(cpu.getCurrentPassword()).isEqualTo("cpw");
        assertThat(cpu.getPassword2()).isEqualTo("pw2");
    }

    @Test
    void canAddRole() {
        cpu = new ChangePasswordUser(user);
        cpu.addRole(Role.VIEWER);
        cpu.setRoles(List.of(Role.ADMIN, Role.USER, Role.VIEWER));
    }

    @Test
    void canRemoveRole() {
        cpu = new ChangePasswordUser(user);
        cpu.removeRole(Role.USER);
        cpu.setRoles(List.of(Role.ADMIN));
    }

    @Test
    void canGetUser_neverNull() {
        cpu = new ChangePasswordUser(user);
        assertThat(cpu.toUser()).isEqualTo(user);
    }

    @Test
    void canGetUser2_neverNull() {
        cpu = new ChangePasswordUser();
        assertThat(cpu.toUser()).isNotNull();
    }

    @Test
    void canGetUser3_neverNull() {
        cpu = new ChangePasswordUser(user, true);
        assertThat(cpu.toUser()).isNotNull();
    }

    @Test
    void canGetUser4_neverNull() {
        cpu = new ChangePasswordUser(user, false);
        assertThat(cpu.toUser()).isNotNull();
    }

    @Test
    void canGetRolesString() {
        cpu = new ChangePasswordUser(user);
        assertThat(cpu.getRolesString()).isEqualTo("ADMIN, USER");
    }

    @Test
    void canGetRoles() {
        cpu = new ChangePasswordUser(user);
        assertThat(cpu.getRoles()).containsExactlyInAnyOrder(Role.ADMIN, Role.USER);

        cpu.setRoles(List.of(Role.VIEWER, Role.USER));
        assertThat(cpu.getRoles()).containsExactlyInAnyOrder(Role.VIEWER, Role.USER);
    }
}
