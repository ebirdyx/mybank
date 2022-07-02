package org.example;

import java.io.File;
import java.sql.*;

public class Database {
    private Connection conn = null;

    public Database() {
        connect();
    }

    private void connect() {
        if (conn != null) {
            return;
        }

        try {
            File file = new File("src/main/resources/database.db");
            String url = "jdbc:sqlite:" + file.getAbsolutePath();
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void runQuery(String query) {
        try {
            Statement stmt = conn.createStatement();
            stmt.execute(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ResultSet selectQuery(String query) {
        try {
            Statement stmt = conn.createStatement();
            return stmt.executeQuery(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
