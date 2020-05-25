package com.company.eshop.repository.templates;

import com.company.eshop.repository.DataBaseUtils;

public class ProductTemplate {
    public static String TABLE_NAME = "products";
    public static String COLUMN_PRODUCT_ID = "productId";
    public static String COLUMN_PRODUCT_NAME = "productName";
    public static String COLUMN_PRICE = "price";

    public static String QUERY_SELECT_ALL_PRODUCTS = "SELECT * FROM "+ DataBaseUtils.dbName+"." + TABLE_NAME + ";";

    public static String QUERY_SELECT_PRODUCT_ID = "SELECT * FROM " + DataBaseUtils.dbName+"."+ TABLE_NAME +
            " WHERE " + COLUMN_PRODUCT_ID +" = ?;";

    public static String QUERY_SELECT_PRODUCT_PRODUCT_NAME = "SELECT * FROM " + DataBaseUtils.dbName+"."+ TABLE_NAME +
            " WHERE " + COLUMN_PRODUCT_NAME +" = ?;";

    public static String QUERY_INSERT_PRODUCT = "INSERT INTO " + DataBaseUtils.dbName+"." + TABLE_NAME
            + " ( " + COLUMN_PRODUCT_NAME +", " + COLUMN_PRICE + " )" +
            "VALUES (?,?);";

    public static String QUERY_DELETE_PRODUCT = "DELETE FROM " + DataBaseUtils.dbName+"."+ TABLE_NAME +
            " WHERE " + COLUMN_PRODUCT_ID +" = ?;";

}
