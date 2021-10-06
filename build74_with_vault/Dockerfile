FROM image-registry.openshift-image-registry.svc:5000/openshift/sso74-openshift-rhel8

USER root

ENV EAP_HOME=/opt/eap
RUN mkdir -p $EAP_HOME/vault

COPY dependencies/sso-extensions.cli /opt/eap/extensions/
COPY dependencies/ojdbc8.jar /opt/eap/extensions/ojdbc8.jar

USER 1001