#!/usr/bin/env bash

openssl x509 -inform PEM -in openshiftlabs.net.crt -pubkey -noout > openshiftlabs.net_publickey.pem