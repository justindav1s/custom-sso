package org.jnd.microservices.sso.userstorage;

import lombok.extern.jbosslog.JBossLog;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;

@JBossLog
public class DatabaseProxy {

    private String datasource_name = null;
    HashMap<String, User> users = new HashMap<>();

    public DatabaseProxy(String datasource_name)    {

        this.datasource_name = datasource_name;

        User user1 = new User("justin", "password", "Justin", "Davis", "justin@email.com", "oracle_pk_1");
        users.put(user1.getUsername(), user1);
        users.put(user1.getId(), user1);
        User user2 = new User("test1", "password", "Test", "One", "test1@email.com","oracle_pk_2");
        users.put(user2.getUsername(), user2);
        users.put(user2.getId(), user2);
        User user3 = new User("test2", "password", "Test", "Two", "test2@email.com", "oracle_pk_3");
        users.put(user3.getUsername(), user3);
        users.put(user3.getId(), user3);

    }




    public User getUserByUsername(User user) {

        log.info("DatabaseProxy : getUserByUsername : Username " + user.getUsername());

        return users.get(user.getUsername());
    }

    public User getUserById(User user) {

        log.info("DatabaseProxy : getUserById : Id " + user.getId());

        User userWithId = users.get(user.getId());

        log.info("DatabaseProxy : getUserById : returning " + userWithId);

        return userWithId;
    }

    public User loginUser(User user) {

        log.info("DatabaseProxy : login : " + user.getUsername());

        User loginUser = users.get(user.getUsername());

        if (loginUser.getPassword().equals(user.getPassword())) {
            user = loginUser;
            log.info("DatabaseProxy : after login : " + user.toString());
        } else
            user = null;


        return user;
    }

    public User register(User user) {

        log.info("DatabaseProxy : register");

        users.put(user.getUsername(), user);

        return user;
    }

    public User[] getAllUsers() {

        log.info("DatabaseProxy : getAllUsers");

        User[] usersarr = new User[users.entrySet().size()];
        users.entrySet().toArray(usersarr);

        return usersarr;
    }

    public int getUsersCount() {

        log.info("DatabaseProxy : getUsersCount");

        int count = users.size();

        return count;
    }


}
