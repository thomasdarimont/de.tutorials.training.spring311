CREATE TABLE ua_role (
  id identity NOT NULL,
  name varchar(255) not NULL,
  PRIMARY KEY (id),
  UNIQUE KEY ua_role_name (name)
);

CREATE TABLE ua_user (
  id identity NOT NULL,
  email varchar(255) default NULL,
  name varchar(255) not NULL,
  PRIMARY KEY (id),
  UNIQUE KEY ua_user_email (email),
  UNIQUE KEY ua_user_name (name)
);

CREATE TABLE ua_user_role (
  UA_USER_id bigint(20) NOT NULL,
  roles_id bigint(20) NOT NULL,
  UNIQUE KEY unique_user_role (UA_USER_id, roles_id)
);

ALTER TABLE ua_user_role ADD FOREIGN KEY (roles_id) REFERENCES ua_role (id);
ALTER TABLE ua_user_role ADD FOREIGN KEY (UA_USER_id) REFERENCES ua_user (id);