package de.tutorials.app.useradmin.service;

import java.util.List;

import de.tutorials.app.useradmin.model.Role;
import de.tutorials.app.useradmin.model.User;

public interface UserAdminService {
    Role register(Role role);

    User register(User user);

    List<User> findAllUsers();

    List<Role> findAllRoles();

    List<User> findUserInRole(Role role);

}