#!/usr/bin/env bash

oc login https://ocp.datr.eu:8443 -u justin


APP=custom-sso-build
PROJECT=$APP

oc project $PROJECT

oc policy add-role-to-user view system:serviceaccount:${PROJECT}:default

oc delete secret sso-jgroup-secret sso-ssl-secret sso-app-secret
oc delete dc sso
oc delete svc sso secure-sso sso-ping
oc delete route sso secure-sso


oc create secret generic sso-jgroup-secret --from-file=certs/jgroups.jceks
oc create secret generic sso-ssl-secret --from-file=certs/datr.eu.jks
oc create secret generic sso-app-secret --from-file=certs/datr.eu.jks
oc secrets link default sso-jgroup-secret sso-ssl-secret sso-app-secret

oc new-app -f sso73-https.json \
 -p HTTPS_SECRET="sso-ssl-secret" \
 -p HTTPS_KEYSTORE="datr.eu.jks" \
 -p HTTPS_NAME="sso" \
 -p HTTPS_PASSWORD="changeme" \
 -p JGROUPS_ENCRYPT_SECRET="sso-jgroup-secret" \
 -p JGROUPS_ENCRYPT_KEYSTORE="jgroups.jceks" \
 -p JGROUPS_ENCRYPT_NAME="jgroups" \
 -p JGROUPS_ENCRYPT_PASSWORD="changeme" \
 -p SSO_ADMIN_USERNAME="admin" \
 -p SSO_ADMIN_PASSWORD="admin" \
 -p SSO_REALM="demorealm" \
 -p SSO_TRUSTSTORE="datr.eu.jks" \
 -p SSO_TRUSTSTORE_PASSWORD="changeme" \
 -p SSO_TRUSTSTORE_SECRET="sso-app-secret"