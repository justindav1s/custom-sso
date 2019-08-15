## Test DB setup

https://hub.docker.com/r/webdizz/oracle-xe-11g-sa

also

https://www.oracle.com/technetwork/database/enterprise-edition/databaseappdev-vm-161299.html

also

https://hub.docker.com/u/justindav1s/content/sub-85b1342e-98f7-42ca-8792-408196f49f3c

docker run -d -it -P --name keycloakdb -v /Users/jusdavis/OracleDBData:/ORCL store/oracle/database-enterprise:12.2.0.1

docker exec -it keycloakdb bash -c "source /home/oracle/.bashrc; /bin/bash"

sqlplus sys/Oradoc_db1@ORCLCDB as sysdba

alter session set "_ORACLE_SCRIPT"=true;

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
	 CONSTRAINT "USERS_PK" PRIMARY KEY ("ID"));

INSERT INTO USERS (ID, USERNAME, PASSWORD, FIRSTNAME, LASTNAME, EMAIL, PHONE, ROLES, GROUPS) VALUES ('1', 'justin', 'changeme', 'Justin', 'Davis', 'justin@email.com', '555 2345', 'fish, dairy, arable', 'user, admin');
INSERT INTO USERS (ID, USERNAME, PASSWORD, FIRSTNAME, LASTNAME, EMAIL, PHONE, ROLES, GROUPS) VALUES ('2', 'test1', 'changeme', 'Test', 'One', 'test1@email.com', '0489 1234', 'fish', 'user, fisheries');
INSERT INTO USERS (ID, USERNAME, PASSWORD, FIRSTNAME, LASTNAME, EMAIL, PHONE, ROLES, GROUPS) VALUES ('3', 'test2', 'changeme', 'Test', 'Two', 'test2@email.com', '0789 123456', 'dairy, arable', 'user, farming');

## Configuring RHSSO to use Oracle on start up.

Everything starts here :

/opt/eap/bin/openshift-launch.sh


https://access.redhat.com/solutions/3250791

https://access.redhat.com/documentation/en-us/red_hat_jboss_enterprise_application_platform/7.2/html-single/configuration_guide/index#example_oracle_datasource

docker login registry.redhat.io
docker pull registry.redhat.io/redhat-sso-7/sso73-openshift
docker run -it --rm registry.redhat.io/redhat-sso-7/sso73-openshift /bin/bash



http://www.janua.fr/understanding-keycloak-user-federation/

ARJUNA012141: Multiple last resources have been added to the current transaction.
https://access.redhat.com/solutions/3362101
https://access.redhat.com/solutions/912183



