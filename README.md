# RHSSO using an Oracle DB

## Test DB setup

https://hub.docker.com/r/webdizz/oracle-xe-11g-sa

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


## Configuring RHSSO to use Oracle on start up.

Everything starts here :

/opt/eap/bin/openshift-launch.sh


https://access.redhat.com/solutions/3250791

https://access.redhat.com/documentation/en-us/red_hat_jboss_enterprise_application_platform/7.2/html-single/configuration_guide/index#example_oracle_datasource

10132  docker login registry.redhat.io
10133  docker pull registry.redhat.io/redhat-sso-7/sso73-openshift
10134  docker run -it --rm registry.redhat.io/redhat-sso-7/sso73-openshift /bin/bash



