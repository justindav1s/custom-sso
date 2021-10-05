FROM docker-registry.default.svc:5000/openshift/redhat-sso74-openshift:latest

USER root

RUN mkdir -p /opt/eap/modules/system/layers/openshift/com/oracle/main

COPY dependencies/module.xml /opt/eap/modules/system/layers/openshift/com/oracle/main

COPY dependencies/ojdbc8.jar /opt/eap/modules/system/layers/openshift/com/oracle/main

RUN cp /opt/eap/standalone/configuration/standalone-openshift.xml /opt/eap/standalone/configuration/standalone-openshift.original

COPY dependencies/standalone-openshift-74.xml /opt/eap/standalone/configuration/standalone-openshift.xml

USER 1001