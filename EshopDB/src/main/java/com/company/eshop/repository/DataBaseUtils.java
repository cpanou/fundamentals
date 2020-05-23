package com.company.eshop.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseUtils {
    public static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    //database name
    public static final String dbName= "eshop";
    //database url to establish connection
    public static final String dbUrl = "jdbc:mysql://localhost:3306/"+dbName+"?autoReconnect=true&useSSL=false&allowMultiQueries=false";
    //database connection credentials
    public static final String dbUser = "root";
    public static final String dbPwd = "test1";

    public static void registerDriverName() {
        try {
            //Register Driver for MySQL Connection
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection createConnection() {
        //create connection object
        Connection connection = null;
        try {
            //user the Driver manager to establish the connection and return the Connection object
            connection = DriverManager.getConnection(dbUrl, dbUser, dbPwd);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }


}
