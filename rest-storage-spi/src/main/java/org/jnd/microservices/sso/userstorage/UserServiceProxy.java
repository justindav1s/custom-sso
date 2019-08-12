package org.jnd.microservices.sso.userstorage;

import org.keycloak.models.RealmModel;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

public class UserServiceProxy {

    private String base_url = null;

    public UserServiceProxy(String base_url)    {
        this.base_url = base_url;
    }

    private RestTemplate restTemplate = new RestTemplate();

    private static final org.jboss.logging.Logger log = org.jboss.logging.Logger.getLogger(RestServiceUserStorageProvider.class);

    public User getUser(User user) {

        log.info("UserServiceProxy : getUser : " + user.getUsername());

        try {
            HttpEntity<User> request = new HttpEntity<>(user);

            ResponseEntity<User> exchange =
                    this.restTemplate.exchange(
                            base_url + "/user/get",
                            HttpMethod.POST,
                            request,
                            User.class);

            user = exchange.getBody();
        }
        catch (HttpClientErrorException hcee)   {
            //do nothing this user does not exist, the 404 causes the exception to be thrown
            log.info("UserServiceProxy : getUser  : NOT FOUND : " + user.getUsername());
        }
        return user;
    }

    public User loginUser(User user) {

        log.info("UserServiceProxy : login : " + user.getUsername());

        try {
            HttpEntity<User> request = new HttpEntity<>(user);

            ResponseEntity<User> exchange =
                    this.restTemplate.exchange(
                            base_url + "/user/login",
                            HttpMethod.POST,
                            request,
                            User.class);

            user = exchange.getBody();
        }
        catch (HttpClientErrorException hcee)   {
            log.info("UserServiceProxy : login  : NOT FOUND/BAD PASSWORD : " + user.getUsername());
        }

        log.info("UserServiceProxy : after login : " + user.toString());
        return user;
    }

    public User register(User user) {

        log.info("UserServiceProxy : register");

        HttpEntity<User> request = new HttpEntity<>(user);

        ResponseEntity<User> exchange =
                this.restTemplate.exchange(
                        base_url + "/user/register",
                        HttpMethod.POST,
                        request,
                        User.class);

        user = exchange.getBody();
        return user;
    }

    public User[] getAllUsers() {

        log.info("UserServiceProxy : getAllUsers");

        ResponseEntity<User[]> exchange =
                this.restTemplate.exchange(
                        base_url+"/user/all",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<User[]>() {});

        log.info("All Users : "+exchange.getBody());

        User[] users = exchange.getBody();

        return users;
    }

    public int getUsersCount() {

        log.info("UserServiceProxy : getUsersCount");

        ResponseEntity<Integer> exchange =
                this.restTemplate.exchange(
                        base_url+"/user/count",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<Integer>() {});

        log.info("User Count : "+exchange.getBody());

        int count = exchange.getBody();

        return count;
    }


}
