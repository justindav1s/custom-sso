#!/usr/bin/env bash

openssl x509 -inform PEM -in datr.eu.crt -pubkey -noout > datr.eu_publickey.pem