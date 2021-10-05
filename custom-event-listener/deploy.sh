#!/usr/bin/env bash


mvn clean package

cp target/custom-event-listener-jar-with-dependencies.jar ~/keycloak/keycloak7/standalone/deployments