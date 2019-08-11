https://hub.docker.com/r/oracleinanutshell/oracle-xe-11g


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