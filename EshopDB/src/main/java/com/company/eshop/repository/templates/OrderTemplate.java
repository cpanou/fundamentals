package com.company.eshop.repository.templates;

import com.company.eshop.repository.DataBaseUtils;

public class OrderTemplate {

    public static final String TABLE_NAME = "orders";

    public static final String COLUMN_ORDER_ID = "orderId";
    public static final String COLUMN_SUBMIT_DATE = "submitDate";
    public static final String COLUMN_PROCESS_DATE = "processDate";
    public static final String COLUMN_STATUS = "status";
    public static final String COLUMN_USER_ID = "userId";

    public static final String QUERY_SELECT_ORDERS_ORDER_ID = "SELECT * FROM"+ DataBaseUtils.dbName+"."+TABLE_NAME+" WHERE " + COLUMN_ORDER_ID + " =?;";

    public static final String QUERY_INSERT_ORDER = "INSERT INTO"+ DataBaseUtils.dbName+"."+TABLE_NAME+ " ( " + COLUMN_ORDER_ID +", "+ COLUMN_SUBMIT_DATE+", "+COLUMN_PROCESS_DATE + ", " + COLUMN_STATUS + " )"+ "VALUES (?,?,?,?);";


}
