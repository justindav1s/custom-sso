#!/usr/bin/env bash

oc login https://api.sno.openshiftlabs.net:6443 -u justin


PROJECT=oracle-test

oc delete project $PROJECT
oc new-project $PROJECT 2> /dev/null
while [ $? \> 0 ]; do
    sleep 1
    printf "."
oc new-project $PROJECT 2> /dev/null
done

oc project $PROJECT


oc create sa oracle -n ${PROJECT}
oc apply -f anyuid-role.yaml -n ${PROJECT}
oc process -n ${PROJECT} -f oracle-persistent.yaml | oc create -f -