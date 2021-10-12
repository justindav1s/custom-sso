#!/usr/bin/env bash

# login

PROJECT=redhat-sso2

# oc delete project $PROJECT
# oc new-project $PROJECT 2> /dev/null
# while [ $? \> 0 ]; do
#     sleep 1
#     printf "."
# oc new-project $PROJECT 2> /dev/null
# done

oc project $PROJECT
oc delete deployment sso
oc delete route -l application=sso
oc delete service -l application=sso
oc delete secret db-secret

# jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS=(PROTOCOL=TCP)(HOST=oracle12c.oracle-test.svc.cluster.local})(PORT=1521))(CONNECT_DATA=(SERVER=dedicated)(SERVICE_NAME=ORCLCDB})))
# jdbc:oracle:thin:@oracle12c.oracle-test.svc.cluster.local:1521:ORCLCDB
# jdbc:oracle:thin:@(description=(address_list=(address=(protocol=tcp)(port=1521)(host=oracle12c.oracle-test.svc.cluster.local)))(connect_data=(INSTANCE_NAME=ORCLCDB)))

oc policy add-role-to-user view system:serviceaccount:${PROJECT}:default

oc create secret generic db-secret \
  --from-literal=DB_USER=keycloak \
  --from-literal=DB_PASSWORD=changeme \
  --from-literal=DB_URL="jdbc:oracle:thin:@(description=(address_list=(address=(protocol=tcp)(port=1521)(host=oracle12c.oracle-test.svc.cluster.local)))(connect_data=(SID=ORCLCDB)))"


oc new-app -f sso74-https.yaml \
 -p APPLICATION_NAME="sso" \
 -p SSO_REALM="demorealm" \
 -p MEMORY_LIMIT="2Gi" \
 -p IMAGE_URL='quay.io/justindav1s/custom-sso:latest'
