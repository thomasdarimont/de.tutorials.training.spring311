package de.tutorials.app.useradmin.service;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import de.tutorials.app.system.SystemModuleConfiguration;
import de.tutorials.app.useradmin.UseradminModuleConfiguration;
import de.tutorials.app.useradmin.model.Role;
import de.tutorials.app.useradmin.model.User;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

@Transactional
@ActiveProfiles("testing")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { SystemModuleConfiguration.class,
	UseradminModuleConfiguration.class })
public class UserAdminServiceTest {

    @Inject
    UserAdminService userAdminService;

    @Test
    @Rollback(true)
    public void registerUser() {
	User user = new User();
	user.setName("User1");
	user = userAdminService.register(user);

	List<User> allUsers = userAdminService.findAllUsers();
	User savedUser = allUsers.get(0);
	assertEquals("There should be one saved user", 1, allUsers.size());
	assertEquals("The user should be equal to saved user", user, savedUser);
    }

    @Test(expected = ConstraintViolationException.class)
    @Rollback(true)
    public void registerUserWithInvalidEmailShouldFail() {
	User user = new User();
	user.setName("User1");
	user.setEmail("xxx.yyy");
	user = userAdminService.register(user);
	fail("A user with invalid email adress is not allowed!");
    }

    // @Test(expected=ConstraintViolationException.class) -> Spring Exception
    // Translation does not catch Unique Constraints on column level?!
    @Test(expected = PersistenceException.class)
    @Rollback(true)
    public void registerUserWithAlreadyExistingUsernameShouldFail() {
	User user = new User();
	user.setName("User1");
	user = userAdminService.register(user);

	user = new User();
	user.setName("User1");
	user = userAdminService.register(user);

	fail("Duplicate usernames are not allowed!");
    }

    @Test
    @Rollback(true)
    public void registerUserWithRoles() {
	User user = new User();
	user.setName("Admin");
	user.setEmail("admin@company.com");

	Role roleAdmin = new Role("admin");
	Role roleUser = new Role("user");

	roleAdmin = userAdminService.register(roleAdmin);
	roleUser = userAdminService.register(roleUser);

	user.getRoles().add(roleAdmin);
	user.getRoles().add(roleUser);

	user = userAdminService.register(user);

	List<User> allUsers = userAdminService.findAllUsers();
	User savedUser = allUsers.get(0);

	assertEquals("The user should be equal to saved user", user, savedUser);

	assertEquals("The user should have 2 roles", 2, savedUser.getRoles()
		.size());
	assertTrue("The user should have role user", savedUser.getRoles()
		.contains(roleUser));
	assertTrue("The user should have role admin", savedUser.getRoles()
		.contains(roleAdmin));
    }

    @Test
    @Rollback
    public void findAllRolesShouldReturnAllRoles() {
	userAdminService.register(new Role("foo"));
	userAdminService.register(new Role("bar"));
	userAdminService.register(new Role("user"));
	userAdminService.register(new Role("admin"));

	List<Role> roles = userAdminService.findAllRoles();
	assertEquals(4, roles.size());
    }

    @Test
    @Rollback(true)
    public void findUserByRoleName() {
	Role fooRole = userAdminService.register(new Role("foo"));
	Role barRole = userAdminService.register(new Role("bar"));

	userAdminService.register(new User("foo1", null, fooRole));
	userAdminService.register(new User("foo2", null, fooRole));
	userAdminService.register(new User("bar", null, barRole));
	userAdminService.register(new User("foo3", null, fooRole));
	userAdminService.register(new User("foobar", null, fooRole, barRole));

	List<User> foos = userAdminService.findUserInRole(fooRole);
	assertEquals(4, foos.size());

	List<User> bars = userAdminService.findUserInRole(barRole);
	assertEquals(2, bars.size());
    }
}