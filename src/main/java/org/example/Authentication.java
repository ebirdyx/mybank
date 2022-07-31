package org.example;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Authentication {
    private final Database db;

    private User authenticatedUser;

    public Authentication(Database database) {
        this.db = database;

        createUsersTable();

        if (!db.isTableSeeded("users")) {
            seedUsers();
        }
    }

    private void createUsersTable() {
        String query = "CREATE TABLE IF NOT EXISTS users (\n"
                + "id integer PRIMARY KEY,\n"
                + "username TEXT,\n"
                + "password TEXT"
                + "); ";

        db.runQuery(query);
    }

    private void seedUsers() {
        User user1 = new User(1, "admin", "admin");
        User user2 = new User(2, "user", "user");

        db.runQuery(user1.toSql());
        db.runQuery(user2.toSql());
    }

    public boolean authenticate(String username, String password) {
        String query = "SELECT * FROM users WHERE username = '"
                + username + "' and password = '" + password + "';";

        ResultSet res = db.selectQuery(query);

        User user;

        try {
            if (res.next()) {
                user = User.makeUserFromSql(res);

                if (username.equals(user.getUsername())
                        && password.equals(user.getPassword())) {
                    authenticatedUser = user;
                    return true;
                }
            }

            return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public User getAuthenticatedUser() {
        return authenticatedUser;
    }
}
