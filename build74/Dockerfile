FROM image-registry.openshift-image-registry.svc:5000/openshift/sso74-openshift-rhel8

USER root
ENV EAP_HOME=/opt/eap
RUN mkdir -p $EAP_HOME/vault
RUN keytool -genseckey -alias vault \
    -storetype jceks \
    -keyalg AES \
    -keysize 128 \
    -storepass vault22 \
    -keypass vault22 \
    -validity 730 \
    -keystore $EAP_HOME/vault/vault.keystore

RUN $EAP_HOME/bin/vault.sh --keystore $EAP_HOME/vault/vault.keystore \
    --keystore-password vault22 \
    --alias vault \
    --vault-block vb \
    --attribute db_user \
    --sec-attr keycloak \
    --enc-dir $EAP_HOME/vault/ \
    --iteration 120 \
    --salt 1234abcd

RUN ls -ltr $EAP_HOME/vault

RUN $EAP_HOME/bin/vault.sh --keystore $EAP_HOME/vault/vault.keystore \
    --keystore-password vault22 \
    --alias vault \
    --vault-block vb \
    --attribute db_password \
    --sec-attr changeme \
    --enc-dir $EAP_HOME/vault/ \
    --iteration 120 \
    --salt 1234abcd

RUN ls -ltr $EAP_HOME/vault

RUN $EAP_HOME/bin/vault.sh --keystore $EAP_HOME/vault/vault.keystore \
    --keystore-password vault22 \
    --alias vault \
    --check-sec-attr \
    --vault-block vb \
    --attribute db_user \
    --enc-dir $EAP_HOME/vault/ \
    --iteration 120 \
    --salt 1234abcd

RUN $EAP_HOME/bin/vault.sh --keystore $EAP_HOME/vault/vault.keystore \
    --keystore-password vault22 \
    --alias vault \
    --check-sec-attr \
    --vault-block vb \
    --attribute db_password \
    --enc-dir $EAP_HOME/vault/ \
    --iteration 120 \
    --salt 1234abcd

COPY dependencies/openshift-launch.sh /opt/eap/bin
COPY dependencies/sso-extensions.cli /opt/eap/extensions/
COPY dependencies/ojdbc8.jar /opt/eap/extensions/ojdbc8.jar

USER 1001