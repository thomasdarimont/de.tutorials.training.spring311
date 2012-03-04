package de.tutorials.app.useradmin.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import de.tutorials.app.useradmin.model.User;

public interface UserAdminRepository extends JpaRepository<User, Long> {
    List<User> findUserByRolesName(String roleName);
}