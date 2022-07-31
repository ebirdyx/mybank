package org.example;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

public class User {
    private int id;
    private String username;
    private String password;

    public User(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String toSql() {
        return "INSERT INTO users (id, username, password) VALUES ("
                + id + ", '"
                + username + "', '"
                + password
                + "');";
    }

    public static User makeUserFromSql(ResultSet rs) {
        try {
            return new User(
                    rs.getInt("id"),
                    rs.getString("username"),
                    rs.getString("password"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
