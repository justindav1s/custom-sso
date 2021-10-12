#!/usr/bin/env bash

IP=api.sno.openshiftlabs.net:6443
APP=custom-sso
PROJECT=$APP-build
USER=justin

oc login https://${IP} -u $USER

# oc delete project $PROJECT
# oc new-project $PROJECT 2> /dev/null
# while [ $? \> 0 ]; do
#     sleep 1
#     printf "."
# oc new-project $PROJECT 2> /dev/null
# done

oc project $PROJECT

oc delete buildConfig custom-sso-docker-build -n $PROJECT
oc delete secret quay-dockercfg -n $PROJECT

oc create secret docker-registry quay-dockercfg \
  --docker-server=${QUAYIO_HOST} \
  --docker-username=${QUAYIO_USER} \
  --docker-password=${QUAYIO_PASSWORD} \
  --docker-email=${QUAYIO_EMAIL} \
  -n ${PROJECT}

oc process -f docker-build-template.yaml \
    -p APPLICATION_NAME=custom-sso \
    -p SOURCE_REPOSITORY_URL="https://github.com/justindav1s/custom-sso.git" \
    -p SOURCE_REPOSITORY_REF="master" \
    -p DOCKERFILE_PATH="build74_with_secret" \
    -p DOCKERFILE_NAME="Dockerfile" \
    -n ${PROJECT} | oc apply -f -

oc start-build custom-sso-docker-build --follow


