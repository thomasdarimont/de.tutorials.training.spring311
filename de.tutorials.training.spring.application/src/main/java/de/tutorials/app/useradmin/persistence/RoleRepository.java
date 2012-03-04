package de.tutorials.app.useradmin.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import de.tutorials.app.useradmin.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
}