ALTER TABLE T_USER_ROLE DROP CONSTRAINT FK331DEE5F97972F83;

ALTER TABLE T_USER_ROLE DROP CONSTRAINT FK331DEE5FF26C6BA3;

ALTER TABLE T_ROLE_RESOURCE DROP CONSTRAINT FK379F912CF26C6BA3;

ALTER TABLE T_ROLE_RESOURCE DROP CONSTRAINT FK379F912CF201A823;

ALTER TABLE T_USER_ROLE DROP CONSTRAINT SQL090223195852140;

ALTER TABLE T_ROLE DROP CONSTRAINT SQL090223195851290;

ALTER TABLE T_ROLE_RESOURCE DROP CONSTRAINT SQL090223195851760;

ALTER TABLE T_RESOURCE DROP CONSTRAINT SQL090223195851070;

ALTER TABLE T_USER DROP CONSTRAINT SQL090223195851960;

DROP TABLE T_RESOURCE;

DROP TABLE T_USER;

DROP TABLE T_ROLE;

DROP TABLE T_ROLE_RESOURCE;

DROP TABLE T_USER_ROLE;
