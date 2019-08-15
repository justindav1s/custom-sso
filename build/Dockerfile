FROM docker-registry.default.svc:5000/openshift/redhat-sso73-openshift:latest

USER root

RUN mkdir -p /opt/eap/modules/system/layers/openshift/com/oracle/main

COPY dependencies/module.xml /opt/eap/modules/system/layers/openshift/com/oracle/main

COPY dependencies/ojdbc8.jar /opt/eap/modules/system/layers/openshift/com/oracle/main

COPY dependencies/custom-theme.jar /opt/eap/standalone/deployments

COPY dependencies/jdbc-storage-spi-jar-with-dependencies.jar /opt/eap/standalone/deployments

RUN cp /opt/eap/standalone/configuration/standalone-openshift.xml /opt/eap/standalone/configuration/standalone-openshift.original

COPY dependencies/standalone-openshift-base-oracle.xml /opt/eap/standalone/configuration/standalone-openshift.xml

RUN cp /opt/eap/bin/launch/openshift-common.sh /opt/eap/bin/launch/openshift-common.original

COPY dependencies/openshift-common.sh /opt/eap/bin/launch/openshift-common.sh

RUN chmod 755 /opt/eap/bin/launch/openshift-common.sh

USER 1001