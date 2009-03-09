CREATE SEQUENCE t_user_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;

CREATE SEQUENCE t_role_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;

CREATE SEQUENCE t_resource_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;

CREATE SEQUENCE t_members_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;

CREATE SEQUENCE t_customer_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;

CREATE TABLE t_user (
		id INT4 DEFAULT nextval('t_user_id_seq'::regclass) NOT NULL,
		disabled BOOL NOT NULL,
		email VARCHAR(255),
		name VARCHAR(255),
		password VARCHAR(255),
		username VARCHAR(255)
	);

CREATE TABLE t_role (
		id INT4 DEFAULT nextval('t_role_id_seq'::regclass) NOT NULL,
		description VARCHAR(255),
		name VARCHAR(255)
	);

CREATE TABLE t_role_resource (
		role_id INT4 NOT NULL,
		resource_id INT4 NOT NULL
	);

CREATE TABLE t_resource (
		id INT4 DEFAULT nextval('t_resource_id_seq'::regclass) NOT NULL,
		type VARCHAR(255),
		value VARCHAR(255)
	);

CREATE TABLE t_user_role (
		user_id INT4 NOT NULL,
		role_id INT4 NOT NULL
	);

CREATE TABLE t_customer (
		id INT4 DEFAULT nextval('t_customer_id_seq'::regclass) NOT NULL,
		name VARCHAR(255)
	);

CREATE TABLE t_members (
		id INT4 DEFAULT nextval('t_members_id_seq'::regclass) NOT NULL,
		name VARCHAR(255),
		telephone VARCHAR(255),
		customer_id INT4
	);

-- CREATE UNIQUE INDEX t_resource_pkey ON t_resource (id ASC);

-- CREATE UNIQUE INDEX t_user_pkey ON t_user (id ASC);

-- CREATE UNIQUE INDEX t_role_resource_pkey ON t_role_resource (role_id ASC, resource_id ASC);

-- CREATE UNIQUE INDEX t_user_role_pkey ON t_user_role (user_id ASC, role_id ASC);

-- CREATE UNIQUE INDEX t_role_pkey ON t_role (id ASC);

ALTER TABLE t_role ADD CONSTRAINT t_role_pkey PRIMARY KEY (id);

ALTER TABLE t_role_resource ADD CONSTRAINT t_role_resource_pkey PRIMARY KEY (role_id, resource_id);

ALTER TABLE t_user ADD CONSTRAINT t_user_pkey PRIMARY KEY (id);

ALTER TABLE t_user_role ADD CONSTRAINT t_user_role_pkey PRIMARY KEY (user_id, role_id);

ALTER TABLE t_resource ADD CONSTRAINT t_resource_pkey PRIMARY KEY (id);

ALTER TABLE t_customer ADD CONSTRAINT t_customer_pkey PRIMARY KEY (id);

ALTER TABLE t_members ADD CONSTRAINT t_members_pkey PRIMARY KEY (id);

ALTER TABLE t_role_resource ADD CONSTRAINT fk379f912cf201a823 FOREIGN KEY (resource_id)
	REFERENCES t_resource (id);

ALTER TABLE t_user_role ADD CONSTRAINT fk331dee5ff26c6ba3 FOREIGN KEY (role_id)
	REFERENCES t_role (id);

ALTER TABLE t_user_role ADD CONSTRAINT fk331dee5f97972f83 FOREIGN KEY (user_id)
	REFERENCES t_user (id);

ALTER TABLE t_role_resource ADD CONSTRAINT fk379f912cf26c6ba3 FOREIGN KEY (role_id)
	REFERENCES t_role (id);

ALTER TABLE t_members ADD CONSTRAINT fkf420bc4e77425623 FOREIGN KEY (customer_id)
	REFERENCES t_customer (id);
