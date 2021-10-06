FROM image-registry.openshift-image-registry.svc:5000/openshift/sso74-openshift-rhel8

USER root
RUN  mkdir /opt/eap/bin/vault
RUN  /opt/eap/bin/vault.sh --keystore EAP_HOME/vault/vault.keystore --keystore-password vault22 --alias vault --vault-block vb --attribute password --sec-attr 0penS3sam3 --enc-dir EAP_HOME/vault/ --iteration 120 --salt 1234abcd
RUN ls -ltr /opt/eap/bin/vault
COPY dependencies/sso-extensions.cli /opt/eap/extensions/
COPY dependencies/ojdbc8.jar /opt/eap/extensions/ojdbc8.jar

USER 1001