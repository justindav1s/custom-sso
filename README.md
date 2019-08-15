# Building Customised RHSSO/Keycloak images to run on Openshift

The RHSSO/Keycloak provided by default on Openshift is configured to use H2, Postgresql or MySQL as persistent backends for realm and user data. It also comes bundled with some default UI themes.

It's very often the case that these default components do not meet user requirements.

Users need to customise the base image because :

   * A different DB flavour is required. Oracle, MSSQL
   * Cusomised UI components are required.
   * Keycloak is extendable by implementing various SPI interfaces. These customised components need adding to the base image.

This repository contains example artefacts designed to customise the base Image.

## Folders
   * __build__ : contains a Dockerfile, all the resources referenced by the docker file, a Openshift BuildConfig and script to trigger a Dockerbuild on OPenshift, that will deliver a customised image.
   * __deploy__ : Scripts and config to deploy the custom image, and scripts to generate the required certificates.
   * __jdbc-storage-spi__ : an example implementation of the User Storage SPI, for Users stored in a custom DB Schema
   * __oracle__ : resources for setting up an test Oracle database in a local container or on openshift
   * __realm__ : a test realm for the jdbc storage spi
   * __theme__ : all the base files for creating a customised theme
   * __jenkins__ : resources for a CICD pipline to orchestrate the build and deployment of a keycloak image. [TO BE FINISHED]
   * __config__ : the bask JBoss configuration used by the SSO 7.3. A good place to begin customisation.
