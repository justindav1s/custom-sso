FROM registry.redhat.io/redhat-sso-7/sso73-openshift:latest

USER root

RUN mkdir -p /opt/eap/modules/system/layers/openshift/com/oracle/main

COPY oracle-driver/module.xml /opt/eap/modules/system/layers/openshift/com/oracle/main

COPY oracle-driver/ojdbc8.jar /opt/eap/modules/system/layers/openshift/com/oracle/main

COPY themes/custom-theme.jar /opt/eap/standalone/deployments

COPY spis/rest-service-storage-spi-jar-with-dependencies.jar /opt/eap/standalone/deployments

RUN cp /opt/eap/standalone/configuration/standalone-openshift.xml /opt/eap/standalone/configuration/standalone-openshift.original

COPY config/standalone-openshift-base-oracle.xml /opt/eap/standalone/configuration/standalone-openshift.xml

USER 1001