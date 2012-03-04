package de.tutorials.app.useradmin.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "UA_ROLE")
public class Role implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    protected Long id;

    @Column(unique = true)
    protected String name;

    public Role() {
    }

    public Role(String name) {
	this.name = name;
    }

    @Override
    public String toString() {
	StringBuilder builder = new StringBuilder();
	builder.append("Role [id=");
	builder.append(id);
	builder.append(", name=");
	builder.append(name);
	builder.append("]");
	return builder.toString();
    }
}