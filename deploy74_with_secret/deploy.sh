#!/usr/bin/env bash

# login

PROJECT=redhat-sso

oc project ${PROJECT}

oc delete deployment sso
oc delete route -l application=sso
oc delete service -l application=sso
oc delete secret db-secret db.properties db.properies

oc policy add-role-to-user view system:serviceaccount:${PROJECT}:default

oc create secret generic db-secret \
  --from-literal=DB_USER=keycloak \
  --from-literal=DB_PASSWORD=changeme \
  --from-literal=DB_URL=jdbc:oracle:thin:@oracle12c.oracle-test.svc.cluster.local:1521:ORCLCDB

# oc create secret generic db-properties \
#   --from-file=secrets/db.properties  

oc new-app -f sso74-https.yaml \
 -p APPLICATION_NAME="sso" \
 -p SSO_REALM="demorealm" \
 -p MEMORY_LIMIT="2Gi" \
 -p IMAGE_URL='quay.io/justindav1s/custom-sso:latest'
