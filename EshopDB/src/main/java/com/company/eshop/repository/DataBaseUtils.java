package com.company.eshop.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseUtils {

    public static String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    public static final String dbName= "eshop";
    public static final String dbUrl = "jdbc:mysql://localhost:3306/"+dbName+"?autoReconnect=true&useSSL=false&allowMultiQueries=false";
    public static final String dbUser = "root";
    public static final String dbPwd = "test1";

    public static boolean registerJDBCDriver() {
        //(1) Specify the JDBC Driver
        //The JVM needs to have an implementation of the driver name (com.mysql.cj.jdbc.Driver)
        //(2) Update the pom file with the mysql-connector-java jar
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Method to create DB Connection
     */
    public static Connection createConnection(){
        //(3) Create a jdbc connection object
        Connection connection = null;
        try {
            System.out.println("Connecting to database...");
            //(4) Use the Driver Manager to create the Connection Object
            //The Url connects to the mysql server and schema
            //we need to use the username and password for the connection
            connection = DriverManager.getConnection(dbUrl, dbUser, dbPwd);

        }catch ( SQLException e) {
            //ERROR - driver for connection not found!!
            e.printStackTrace();
        }

        return connection;
    }


}
