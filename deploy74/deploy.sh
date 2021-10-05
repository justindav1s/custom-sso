#!/usr/bin/env bash

oc login https://${IP} -u justin

TLS_DOMAIN=openshiftlabs.net
PROJECT=custom-sso

oc delete project $PROJECT
oc new-project $PROJECT 2> /dev/null
while [ $? \> 0 ]; do
    sleep 1
    printf "."
oc new-project $PROJECT 2> /dev/null
done


oc policy add-role-to-user view system:serviceaccount:${PROJECT}:default

# oc delete secret sso-jgroup-secret sso-ssl-secret sso-app-secret
# oc delete dc sso
# oc delete svc sso secure-sso sso-ping
# oc delete route sso secure-sso


oc create secret generic sso-jgroup-secret --from-file=certs/jgroups.jceks
oc create secret generic sso-tls-secret --from-file=certs/${TLS_DOMAIN}.jks
oc create secret generic sso-app-secret --from-file=certs/${TLS_DOMAIN}.jks
oc secrets link default sso-jgroup-secret sso-tls-secret sso-app-secret

oc new-app -f sso74-https.yaml \
 -p APPLICATION_NAME="sso" \
 -p HOSTNAME_HTTP="sso-custom-sso.apps.sno.openshiftlabs.net" \
 -p HOSTNAME_HTTPS="sso-custom-sso.apps.sno.openshiftlabs.net" \
 -p HTTPS_SECRET="sso-tls-secret" \
 -p HTTPS_KEYSTORE="${TLS_DOMAIN}.jks" \
 -p HTTPS_KEYSTORE_TYPE="JKS" \
 -p HTTPS_NAME="sso" \
 -p HTTPS_PASSWORD="changeme" \
 -p DB_MIN_POOL_SIZE="3" \
 -p DB_MAX_POOL_SIZE="10" \
 -p JGROUPS_ENCRYPT_SECRET="sso-jgroup-secret" \
 -p JGROUPS_ENCRYPT_KEYSTORE="jgroups.jceks" \
 -p JGROUPS_ENCRYPT_NAME="jgroups" \
 -p JGROUPS_ENCRYPT_PASSWORD="changeme" \
 -p JGROUPS_CLUSTER_PASSWORD="changeme" \
 -p SSO_ADMIN_USERNAME="admin" \
 -p SSO_ADMIN_PASSWORD="admin" \
 -p SSO_REALM="demorealm" \
 -p SSO_TRUSTSTORE="${TLS_DOMAIN}.jks" \
 -p SSO_TRUSTSTORE_PASSWORD="changeme" \
 -p SSO_TRUSTSTORE_SECRET="sso-app-secret" \
 -p MEMORY_LIMIT="2Gi"
