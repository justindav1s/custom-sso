# RHSSO using an Oracle DB

## Test DB setup

https://hub.docker.com/r/webdizz/oracle-xe-11g-sa

also

https://www.oracle.com/technetwork/database/enterprise-edition/databaseappdev-vm-161299.html

CREATE USER keycloak IDENTIFIED BY changeme;
GRANT create session TO keycloak;
GRANT create table TO keycloak;
GRANT create view TO keycloak;
GRANT create any trigger TO keycloak;
GRANT create any procedure TO keycloak;
GRANT create sequence TO keycloak;
GRANT create synonym TO keycloak;
GRANT CONNECT, RESOURCE, DBA TO keycloak;


CREATE USER users IDENTIFIED BY changeme;
GRANT create session TO users;
GRANT create table TO users;
GRANT create view TO users;
GRANT create any trigger TO users;
GRANT create any procedure TO users;
GRANT create sequence TO users;
GRANT create synonym TO users;
GRANT CONNECT, RESOURCE, DBA TO users;

CREATE TABLE "USERS"."USERS"
   ("ID" VARCHAR2(20 BYTE) NOT NULL ENABLE,
	"USERNAME" VARCHAR2(20 BYTE) NOT NULL ENABLE,
	"PASSWORD" VARCHAR2(20 BYTE) NOT NULL ENABLE,
	"FIRSTNAME" VARCHAR2(20 BYTE),
	"LASTNAME" VARCHAR2(20 BYTE),
	"EMAIL" VARCHAR2(20 BYTE),
	"ROLES" VARCHAR2(100 BYTE),
	 CONSTRAINT "USERS_PK" PRIMARY KEY ("ID"))

INSERT INTO USERS (ID, USERNAME, PASSWORD, FIRSTNAME, LASTNAME, EMAIL, ROLES) VALUES ('1', 'justin', 'changeme', 'Justin', 'Davis', 'justin@email.com', 'fish, dairy, arable');
INSERT INTO USERS (ID, USERNAME, PASSWORD, FIRSTNAME, LASTNAME, EMAIL, ROLES) VALUES ('2', 'test1', 'changeme', 'Test', 'One', 'test1@email.com', 'fish');
INSERT INTO USERS (ID, USERNAME, PASSWORD, FIRSTNAME, LASTNAME, EMAIL, ROLES) VALUES ('3', 'test2', 'changeme', 'Test', 'Two', 'test2@email.com', 'dairy, arable');

## Configuring RHSSO to use Oracle on start up.

Everything starts here :

/opt/eap/bin/openshift-launch.sh


https://access.redhat.com/solutions/3250791

https://access.redhat.com/documentation/en-us/red_hat_jboss_enterprise_application_platform/7.2/html-single/configuration_guide/index#example_oracle_datasource

10132  docker login registry.redhat.io
10133  docker pull registry.redhat.io/redhat-sso-7/sso73-openshift
10134  docker run -it --rm registry.redhat.io/redhat-sso-7/sso73-openshift /bin/bash



