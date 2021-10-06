#!/bin/bash

VAULT_DIR=secrets
JBOSS_HOME=/Users/justin/rh/rh-sso/rh-sso-7.4

DB_USER=keycloak
DB_PASSWORD=changeme

rm -rf $VAULT_DIR/*

keytool -genseckey \
    -alias vault \
    -storetype jceks \
    -keyalg AES \
    -keysize 128 \
    -storepass vault22 \
    -keypass vault22 \
    -validity 730 \
    -keystore $VAULT_DIR/vault.keystore 

$JBOSS_HOME/bin/vault.sh \
    --keystore $VAULT_DIR/vault.keystore \
    --keystore-password vault22 \
    --alias vault \
    --vault-block vb \
    --attribute db_user \
    --sec-attr $DB_USER \
    --enc-dir $VAULT_DIR \
    --iteration 120 \
    --salt 1234abcd    

$JBOSS_HOME/bin/vault.sh \
    --keystore $VAULT_DIR/vault.keystore \
    --keystore-password vault22 \
    --alias vault \
    --vault-block vb \
    --attribute db_password \
    --sec-attr $DB_PASSWORD \
    --enc-dir $VAULT_DIR \
    --iteration 120 \
    --salt 1234abcd  