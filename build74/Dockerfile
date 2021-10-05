FROM image-registry.openshift-image-registry.svc:5000/openshift/sso74-openshift-rhel8

USER root

RUN mkdir -p /opt/eap/modules/system/layers/openshift/com/oracle/main

COPY dependencies/module.xml /opt/eap/modules/system/layers/openshift/com/oracle/main

COPY dependencies/ojdbc8.jar /opt/eap/modules/system/layers/openshift/com/oracle/main

RUN chmod -R 777 /opt/eap/standalone/configuration

# RUN cp /opt/eap/standalone/configuration/standalone-openshift.xml /opt/eap/standalone/configuration/standalone-openshift.original

# COPY dependencies/standalone-openshift-74.xml /opt/eap/standalone/configuration/standalone-openshift.xml

# COPY dependencies/sso-extensions.cli /opt/eap/extensions/
# COPY dependencies/ojdbc8.jar /opt/eap/extensions/ojdbc8.jar

USER 1001