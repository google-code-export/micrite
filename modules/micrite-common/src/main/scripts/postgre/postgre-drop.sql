--drop constraint

ALTER TABLE t_role_resource DROP CONSTRAINT fk379f912cf201a823;

ALTER TABLE t_user_role DROP CONSTRAINT fk331dee5ff26c6ba3;

ALTER TABLE t_user_role DROP CONSTRAINT fk331dee5f97972f83;

ALTER TABLE t_role_resource DROP CONSTRAINT fk379f912cf26c6ba3;

ALTER TABLE t_members DROP CONSTRAINT fkf420bc4e77425623;

ALTER TABLE t_role DROP CONSTRAINT t_role_pkey;

ALTER TABLE t_role_resource DROP CONSTRAINT t_role_resource_pkey;

ALTER TABLE t_user DROP CONSTRAINT t_user_pkey;

ALTER TABLE t_user_role DROP CONSTRAINT t_user_role_pkey;

ALTER TABLE t_resource DROP CONSTRAINT t_resource_pkey;

ALTER TABLE t_members DROP CONSTRAINT t_members_pkey;

ALTER TABLE t_customer DROP CONSTRAINT t_customer_pkey;


-- drop index

DROP INDEX t_resource_pkey;

DROP INDEX t_user_pkey;

DROP INDEX t_role_resource_pkey;

DROP INDEX t_user_role_pkey;

DROP INDEX t_role_pkey;

DROP INDEX t_members_pkey;

DROP INDEX t_customer_pkey;

-- drop table

DROP TABLE t_user;

DROP TABLE t_role;

DROP TABLE t_role_resource;

DROP TABLE t_resource;

DROP TABLE t_user_role;

DROP TABLE t_members;

DROP TABLE t_customer;
-- drop sequence

DROP SEQUENCE t_user_id_seq;

DROP SEQUENCE t_role_id_seq;

DROP SEQUENCE t_resource_id_seq;

DROP SEQUENCE t_members_id_seq;

DROP SEQUENCE t_customer_id_seq;
