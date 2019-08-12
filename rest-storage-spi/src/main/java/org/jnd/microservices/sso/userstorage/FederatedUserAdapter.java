package org.jnd.microservices.sso.userstorage;

import org.keycloak.component.ComponentModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.storage.StorageId;
import org.keycloak.storage.adapter.AbstractUserAdapterFederatedStorage;

public class FederatedUserAdapter extends AbstractUserAdapterFederatedStorage {

    private final User user;
    private final String keycloakId;

    private static final org.jboss.logging.Logger log = org.jboss.logging.Logger.getLogger(RestServiceUserStorageProvider.class);

    public FederatedUserAdapter(KeycloakSession session, RealmModel realm, ComponentModel model, User user) {
        super(session, realm, model);
        this.user = user;
        this.keycloakId = StorageId.keycloakId(model, user.getId());
    }

    @Override
    public String getId() {
        return keycloakId;
    }

    @Override
    public String getUsername() {
        log.info("FederatedUserAdapter : getUsername : "+ user.getUsername());
        return user.getUsername();
    }

    @Override
    public void setUsername(String username) {
        log.info("FederatedUserAdapter : setUsername : "+ username);
        user.setUsername(username);
    }

    @Override
    public String getEmail() {
        return user.getEmail();
    }

    @Override
    public void setEmail(String email) {
        user.setEmail(email);
    }

    @Override
    public String getFirstName() {
        return user.getFirstname();
    }

    @Override
    public void setFirstName(String firstName) {
        user.setFirstname(firstName);
    }

    @Override
    public String getLastName() {
        return user.getLastname();
    }

    @Override
    public void setLastName(String lastName) {
        user.setLastname(lastName);
    }
}
