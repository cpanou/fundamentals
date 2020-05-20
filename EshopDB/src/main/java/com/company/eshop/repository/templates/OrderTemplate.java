package com.company.eshop.repository.templates;

import com.company.eshop.repository.DatabaseUtils;

public class OrderTemplate {
    public static String TABLE_NAME = "orders";
    public static String COLUMN_ORDER_ID = "orderId";
    public static String COLUMN_USER_ID = "userId";
    public static String COLUMN_STATUS = "status";
    public static String COLUMN_SUBMITDATE = "submitDate";
    public static String COLUMN_PROCESSEDDATE = "processDate";

    public static String QUERY_SELECT_ALL_ORDERS = "SELECT * FROM "+ DatabaseUtils.dbName+"." + TABLE_NAME + ";";

    public static String QUERY_SELECT_ORDER_ID = "SELECT * FROM " + DatabaseUtils.dbName+"."+ TABLE_NAME +
            " WHERE " + COLUMN_ORDER_ID +" = ?;";

    public static String QUERY_SELECT_USER_ID = "SELECT * FROM " + DatabaseUtils.dbName+"."+ TABLE_NAME +
            " WHERE " + COLUMN_USER_ID +" = ?;";

    public static String QUERY_INSERT_ORDER = "INSERT INTO " + DatabaseUtils.dbName+"." + TABLE_NAME
            + " ( " + COLUMN_USER_ID +", " + COLUMN_STATUS +", "+ COLUMN_SUBMITDATE +")" +
            "VALUES (?,?,?);";

    public static String QUERY_DELETE_ORDER = "DELETE FROM " + DatabaseUtils.dbName+"."+ TABLE_NAME +
            " WHERE " + COLUMN_ORDER_ID +" = ?;";

}
