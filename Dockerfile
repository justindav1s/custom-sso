FROM docker-registry.default.svc:5000/openshift/redhat-sso73-openshift:latest

USER root

RUN mkdir -p /opt/eap/modules/system/layers/openshift/com/oracle/main

COPY oracle-driver/module.xml /opt/eap/modules/system/layers/openshift/com/oracle/main

COPY oracle-driver/ojdbc8.jar /opt/eap/modules/system/layers/openshift/com/oracle/main

COPY theme /opt/eap/themes

COPY spis/rest-service-storage-spi-jar-with-dependencies.jar /opt/eap/standalone/deployments

USER 1001