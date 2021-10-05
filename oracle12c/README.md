https://pittar.medium.com/running-oracle-12c-on-openshift-container-platform-ca471a9f7057

https://hub.docker.com/u/justindav1s/content/sub-85b1342e-98f7-42ca-8792-408196f49f3c

./deploy.sh


On pod Terminal

source /home/oracle/.bashrc

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

oc port-forward oracle12c-1-p7l6q 1521:1521

Use DBeaver to login tothe ORCLCDB Database on localhost using keycloak/changeme as credentials