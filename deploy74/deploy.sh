#!/usr/bin/env bash

oc login https://${IP} -u justin

PROJECT=redhat-sso

# oc delete project $PROJECT
# oc new-project $PROJECT 2> /dev/null
# while [ $? \> 0 ]; do
#     sleep 1
#     printf "."
# oc new-project $PROJECT 2> /dev/null
# done


oc project $PROJECT

oc policy add-role-to-user view system:serviceaccount:${PROJECT}:default

oc new-app -f sso74-https.yaml \
 -p APPLICATION_NAME="sso" \
 -p SSO_REALM="demorealm" \
 -p MEMORY_LIMIT="2Gi" \
 -p IMAGE_URL='quay.io/justindav1s/custom-sso:latest'
