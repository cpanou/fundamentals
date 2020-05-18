package com.company.eshop.repository.templates;


import com.company.eshop.repository.DataBaseUtils;

public class UserTemplate {

    public static final String TABLE_NAME = "users";

    public static final String COLUMN_FIRSTNAME = "firstname";
    public static final String COLUMN_LASTNAME = "lastname";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_USER_ID = "userId";

    public static final String QUERY_SELECT_ALL_USERS = "SELECT * FROM "+ DataBaseUtils.dbName+"." + TABLE_NAME + ";";

    public static final String QUERY_SELECT_USER_ID = "SELECT * FROM " + DataBaseUtils.dbName+"."+ TABLE_NAME +
            " WHERE " + COLUMN_USER_ID +" = ?;";
    public static final String QUERY_SELECT_USER_USERNAME = "SELECT * FROM " + DataBaseUtils.dbName+"."+ TABLE_NAME +
            " WHERE " + COLUMN_USERNAME +" = ?;";
    public static final String QUERY_INSERT_USER =
            "INSERT INTO " + DataBaseUtils.dbName+"." + TABLE_NAME
            + " ( " + COLUMN_USERNAME +", " + COLUMN_FIRSTNAME +", "+ COLUMN_LASTNAME +", "+ COLUMN_EMAIL +" )" +
            "VALUES (?,?,?,?);";
    public static final String QUERY_DELETE_USER= "DELETE FROM " + DataBaseUtils.dbName+"."+ TABLE_NAME +
            " WHERE " + COLUMN_USER_ID +" = ?;";

}
