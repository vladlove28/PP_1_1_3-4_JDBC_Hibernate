package jm.task.core.jdbc.util;

import java.sql.*;


public class Util {
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/baseForKata";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Iloveenglish28";

    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
//            Statement statement = connection.createStatement();
//            System.out.println("Connection is OK");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

//    база данных baseForKata
//     реализуйте настройку соеденения с БД
}
