package com.company.eshop.repository.templates;

import com.company.eshop.repository.DatabaseUtils;

public class OrderProductTemplate {
    public static String TABLE_NAME = "orderproducts";
    public static String COLUMN_ORDER_ID = "orderId";
    public static String COLUMN_PRODUCT_ID = "productId";
    public static String COLUMN_ID = "id";

    public static String QUERY_SELECT_ALL_OREDER_PRODUCTS = "SELECT * FROM "+ DatabaseUtils.dbName+"." + TABLE_NAME + ";";

    public static String QUERY_SELECT_ORDER_ID = "SELECT * FROM " + DatabaseUtils.dbName+"."+ TABLE_NAME +
            " WHERE " + COLUMN_ORDER_ID +" = ?;";

    public static String QUERY_SELECT_PRODUCT_ID = "SELECT * FROM " + DatabaseUtils.dbName+"."+ TABLE_NAME +
            " WHERE " + COLUMN_PRODUCT_ID +" = ?;";

    public static String QUERY_INSERT_ORDER_PRODUCTS = "INSERT INTO " + DatabaseUtils.dbName+"." + TABLE_NAME
            + " ( " + COLUMN_ORDER_ID +", " + COLUMN_PRODUCT_ID +" )" +
            "VALUES (?,?);";

    public static String QUERY_DELETE_BY_ORDER = "DELETE FROM " + DatabaseUtils.dbName+"."+ TABLE_NAME +
            " WHERE " + COLUMN_ORDER_ID +" = ?;";

    public static String QUERY_DELETE_BY_PRODUCT = "DELETE FROM " + DatabaseUtils.dbName+"."+ TABLE_NAME +
            " WHERE " + COLUMN_PRODUCT_ID +" = ?;";

}
