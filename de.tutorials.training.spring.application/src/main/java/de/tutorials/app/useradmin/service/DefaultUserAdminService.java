package de.tutorials.app.useradmin.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.tutorials.app.useradmin.model.Role;
import de.tutorials.app.useradmin.model.User;
import de.tutorials.app.useradmin.persistence.RoleRepository;
import de.tutorials.app.useradmin.persistence.UserAdminRepository;

@Service
@Transactional
public class DefaultUserAdminService implements UserAdminService {

    @Inject
    protected UserAdminRepository userRepository;

    @Inject
    protected RoleRepository roleRepository;

    @Override
    public Role register(Role role) {
	return roleRepository.save(role);
    }

    @Override
    public User register(User user) {
	return userRepository.save(user);
    }

    @Override
    public List<Role> findAllRoles() {
	return roleRepository.findAll();
    }

    @Override
    public List<User> findAllUsers() {
	return userRepository.findAll();
    }

    @Override
    public List<User> findUserInRole(Role role) {
	return userRepository.findUserByRolesName(role.getName());
    }
}