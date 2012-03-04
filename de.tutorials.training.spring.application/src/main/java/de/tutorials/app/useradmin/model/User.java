package de.tutorials.app.useradmin.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

import org.hibernate.validator.constraints.Email;

@Entity
@Getter
@Setter
@Table(name = "UA_USER")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    protected Long id;

    @Column(unique = true)
    protected String name;

    @Email
    @Column(unique = true)
    protected String email;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "UA_USER_ROLE")
    protected Collection<Role> roles = new HashSet<Role>();

    public User() {
    }

    public User(String name, String email, Role... roles) {
	this.name = name;
	this.email = email;

	for (Role role : roles) {
	    this.roles.add(role);
	}
    }

    @Override
    public String toString() {
	StringBuilder builder = new StringBuilder();
	builder.append("User [id=");
	builder.append(id);
	builder.append(", name=");
	builder.append(name);
	builder.append(", roles=");
	builder.append(roles);
	builder.append("]");
	return builder.toString();
    }
}