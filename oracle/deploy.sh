#!/usr/bin/env bash

oc login https://ocp.datr.eu:8443 -u justin


APP=oracle-xe
PROJECT=custom-sso

oc delete project $PROJECT
oc new-project $PROJECT 2> /dev/null
while [ $? \> 0 ]; do
    sleep 1
    printf "."
oc new-project $PROJECT 2> /dev/null
done

oc project $PROJECT

oc new-app -f deploy-config.yaml \
 -p APPLICATION=$APP \
 -p IMAGE="oracleinanutshell/oracle-xe-11g" \
 -p IMAGE_TAG="latest"

oc adm policy add-scc-to-user privileged -z $APP
oc adm policy add-scc-to-user anyuid -z $APP