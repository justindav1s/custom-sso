package org.jnd.microservices.sso.userstorage;

import lombok.extern.jbosslog.JBossLog;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

@JBossLog
public class DatabaseProxy {

    private DataSource datasource = null;

    HashMap<String, User> users = new HashMap<>();

    public DatabaseProxy(DataSource datasource)    {

        this.datasource = datasource;
    }

    private Connection getConnection() {
        Connection conn = null;
        try {
            conn = datasource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }


    public User getUserByUsername(User user) {

        log.info("DatabaseProxy : getUserByUsername : Username " + user.getUsername());

        String sql = "SELECT * from Users WHERE USERNAME='"+user.getUsername()+"'";

        User userFromDb = null;

        try {
            Connection conn = getConnection();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next())
            {
                String id = rs.getString("id");
                String firstname = rs.getString("FIRSTNAME");
                String lastname = rs.getString("LASTNAME");
                String username = rs.getString("USERNAME");
                String password = rs.getString("PASSWORD");
                String roles = rs.getString("ROLES");
                String email = rs.getString("EMAIL");
                String phone = rs.getString("PHONE");
                String groups = rs.getString("GROUPS");

                log.info("DatabaseProxy : getUserByUsername : from database "+" id :"+id+" username :"+username+" password :"+password
                        +" firstname :"+firstname+" lastname :"+lastname+" email :"+email+" roles :"+roles
                        +" groups :"+groups+" phone :"+phone);

                userFromDb = new User(id, username, password, firstname, lastname, email, phone, roles, groups);
            }
            rs.close();
            st.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userFromDb;
    }

    public User getUserById(User user) {

        log.info("DatabaseProxy : getUserById : Id " + user.getId());


        String sql = "SELECT * from Users WHERE ID='"+user.getId()+"'";

        User userFromDb = null;
        try {
            Connection conn = getConnection();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next())
            {
                String id = rs.getString("id");
                String firstname = rs.getString("FIRSTNAME");
                String lastname = rs.getString("LASTNAME");
                String username = rs.getString("USERNAME");
                String password = rs.getString("PASSWORD");
                String roles = rs.getString("ROLES");
                String email = rs.getString("EMAIL");
                String phone = rs.getString("PHONE");
                String groups = rs.getString("GROUPS");

                log.info("DatabaseProxy : getUserById : from database "+" id :"+id+" username :"+username+" password :"+password
                        +" firstname :"+firstname+" lastname :"+lastname+" email :"+email+" roles :"+roles
                        +" groups :"+groups+" phone :"+phone);

                userFromDb = new User(id, username, password, firstname, lastname, email, phone, roles, groups);
            }
            rs.close();
            st.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userFromDb;

    }

    public User loginUser(User user) {

        log.info("DatabaseProxy : login : " + user.getUsername());

        User loginUser = this.getUserByUsername(user);

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

        String sql = "SELECT * from Users";
        ArrayList<User> allUsers = new ArrayList<>();
        User userFromDb = null;
        try {
            Connection conn = getConnection();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next())
            {
                String id = rs.getString("id");
                String firstname = rs.getString("FIRSTNAME");
                String lastname = rs.getString("LASTNAME");
                String username = rs.getString("USERNAME");
                String password = rs.getString("PASSWORD");
                String roles = rs.getString("ROLES");
                String email = rs.getString("EMAIL");
                String phone = rs.getString("PHONE");
                String groups = rs.getString("GROUPS");

                log.info("DatabaseProxy : getAllUsers : from database "+" id :"+id+" username :"+username+" password :"+password
                        +" firstname :"+firstname+" lastname :"+lastname+" email :"+email+" roles :"+roles
                        +" groups :"+groups+" phone :"+phone);

                userFromDb = new User(id, username, password, firstname, lastname, email, phone, roles, groups);
                allUsers.add(userFromDb);
            }
            rs.close();
            st.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        User[] usersarr = new User[allUsers.size()];
        allUsers.toArray(usersarr);

        return usersarr;
    }

    public int getUsersCount() {

        log.info("DatabaseProxy : getUsersCount");
        String sql = "SELECT COUNT(*) AS count FROM Users";

        int count = 0;
        try {
            Connection conn = getConnection();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next())
            {
                count = rs.getInt("count");
                log.info("DatabaseProxy : from database "+" count :"+count);
            }
            rs.close();
            st.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return count;
    }


}
