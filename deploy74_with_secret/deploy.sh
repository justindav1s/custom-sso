#!/usr/bin/env bash

# login

PROJECT=redhat-sso
APP=sso

oc project $PROJECT

oc delete deployment $APP
oc delete route -l application=$APP
oc delete service -l application=$APP
oc delete secret db-secret

DB_URL="jdbc:oracle:thin:@(description=(address_list=(address=(protocol=tcp)(port=1521)(host=oracle12c.oracle-test.svc.cluster.local)))(connect_data=(SID=ORCLCDB)))"
oc policy add-role-to-user view system:serviceaccount:${PROJECT}:default

oc create secret generic db-secret \
  --from-literal=DB_USER=keycloak \
  --from-literal=DB_PASSWORD=changeme \
  --from-literal=DB_URL=$DB_URL


oc new-app -f sso74-https.yaml \
 -p APPLICATION_NAME=$APP \
 -p SSO_REALM="demorealm" \
 -p MEMORY_LIMIT="2Gi" \
 -p HOSTNAME_HTTP="testing.apps.sno.openshiftlabs.net" \
 -p IMAGE_URL='quay.io/justindav1s/custom-sso:latest'
