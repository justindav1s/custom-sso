#!/usr/bin/env bash


export IP=api.sno.openshiftlabs.net:6443
export USER=justin

export ORG=custom-sso
export DEV_PROJECT=${ORG}
export CICD_PROJECT=cicd

export CURL="curl -k -v"
export JENKINS_USER=justin-admin-edit-view
export JENKINS_TOKEN=11f4274ec59be12eb227a08b08ee13d54b
export JENKINS=jenkins-cicd.apps.sno.openshiftlabs.net

export QUAYIO_REGISTRY=quay.io