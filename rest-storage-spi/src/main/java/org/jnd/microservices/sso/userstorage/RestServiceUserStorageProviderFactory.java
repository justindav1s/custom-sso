/*
 * Copyright 2016 Red Hat, Inc. and/or its affiliates
 * and other contributors as indicated by the @author tags.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jnd.microservices.sso.userstorage;

import org.jboss.logging.Logger;
import org.keycloak.common.util.EnvUtil;
import org.keycloak.component.ComponentModel;
import org.keycloak.component.ComponentValidationException;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.provider.ProviderConfigProperty;
import org.keycloak.provider.ProviderConfigurationBuilder;
import org.keycloak.storage.UserStorageProviderFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;


public class RestServiceUserStorageProviderFactory implements UserStorageProviderFactory<RestServiceUserStorageProvider> {

    private static final Logger logger = Logger.getLogger(RestServiceUserStorageProviderFactory.class);

    public static final String PROVIDER_NAME = "user-rest-service";

    protected static final List<ProviderConfigProperty> configMetadata;

    static {
        configMetadata = ProviderConfigurationBuilder.create()
                .property().name("base_url")
                .type(ProviderConfigProperty.STRING_TYPE)
                .label("REST Service Base URL")
                .defaultValue("http://localhost:8090")
                .helpText("Base URL for user REST service")
                .add().build();
    }


    @Override
    public List<ProviderConfigProperty> getConfigProperties() {
        return configMetadata;
    }

    @Override
    public void validateConfiguration(KeycloakSession session, RealmModel realm, ComponentModel config) throws ComponentValidationException {
        String fp = config.getConfig().getFirst("base_url");
        if (fp == null) throw new ComponentValidationException("A Base URL for user REST service does not exist");
    }

    @Override
    public String getId() {
        return PROVIDER_NAME;
    }

    @Override
    public RestServiceUserStorageProvider create(KeycloakSession session, ComponentModel model) {
        String base_url = model.getConfig().getFirst("base_url");

        return new RestServiceUserStorageProvider(session, model, base_url);
    }

}
