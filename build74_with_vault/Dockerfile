FROM registry.redhat.io/rh-sso-7/sso74-openshift-rhel8:7.4-37

USER root

ENV EAP_HOME=/opt/eap
RUN mkdir -p $EAP_HOME/vault

COPY dependencies/sso-extensions.cli /opt/eap/extensions/
COPY dependencies/ojdbc8.jar /opt/eap/extensions/ojdbc8.jar

USER 1001