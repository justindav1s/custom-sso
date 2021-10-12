#!/usr/bin/env bash

# login

PROJECT=redhat-sso

oc project ${PROJECT}

oc delete deployment sso
oc delete route -l application=sso
oc delete service -l application=sso

oc policy add-role-to-user view system:serviceaccount:${PROJECT}:default

oc create secret generic jboss-vault \
  --from-file=secrets/VAULT.dat \
  --from-file=secrets/vault.keystore

oc new-app -f sso74-https.yaml \
 -p APPLICATION_NAME="sso" \
 -p SSO_REALM="demorealm" \
 -p MEMORY_LIMIT="2Gi" \
 -p IMAGE_URL='quay.io/justindav1s/custom-sso:latest'
