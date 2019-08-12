package org.jnd.microservices.sso.userstorage;

import org.keycloak.component.ComponentModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.RoleContainerModel;
import org.keycloak.models.RoleModel;
import org.keycloak.storage.StorageId;
import org.keycloak.storage.adapter.AbstractUserAdapterFederatedStorage;

import java.util.*;

public class FederatedUserAdapter extends AbstractUserAdapterFederatedStorage {

    private final User user;
    private final String keycloakId;

    private static final org.jboss.logging.Logger log = org.jboss.logging.Logger.getLogger(JdbcUserStorageProvider.class);

    public FederatedUserAdapter(KeycloakSession session, RealmModel realm, ComponentModel model, User user) {
        super(session, realm, model);
        this.user = user;

        log.info("FederatedUserAdapter : constructor user : "+ this.user);
        log.info("FederatedUserAdapter : constructor user id : "+ this.user.getId());

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
        log.info("FederatedUserAdapter : getEmail");
        return user.getEmail();
    }

    @Override
    public void setEmail(String email) {
        log.info("FederatedUserAdapter : setEmail");
        user.setEmail(email);
    }

    @Override
    public String getFirstName() {
        log.info("FederatedUserAdapter : getFirstName");
        return user.getFirstname();
    }

    @Override
    public void setFirstName(String firstName) {
        log.info("FederatedUserAdapter : setFirstName");
        user.setFirstname(firstName);
    }

    @Override
    public String getLastName() {
        log.info("FederatedUserAdapter : getLastName");
        return user.getLastname();
    }

    @Override
    public void setLastName(String lastName) {
        log.info("FederatedUserAdapter : setLastName");
        user.setLastname(lastName);
    }

    @Override
    protected Set<RoleModel> getFederatedRoleMappings() {
        log.info("FederatedUserAdapter : getFederatedRoleMappings");
        return getFederatedStorage().getRoleMappings(realm, this.getId());
    }

    @Override
    public Map<String, List<String>> getAttributes() {
        log.info("FederatedUserAdapter : getAttributes");
        Map<String,List<String>> attributes = new HashMap<String,List<String>>();
        for (String key : user.getAttributes().keySet())    {
            List<String> values = new ArrayList<>();
            values.add(user.getAttributes().get(key));
            attributes.put(key, values);
        }
        return attributes;
    }
}
