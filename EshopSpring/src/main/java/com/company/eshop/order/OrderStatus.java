package com.company.eshop.order;

public enum OrderStatus {
    SUBMITTED,
    PROCESSED
    ;

    public static OrderStatus fromInput(String value) {
        for (OrderStatus status : OrderStatus.values())
            if (status.name().equalsIgnoreCase(value))
                return status; //return OrderStatus.SUBMITTED
        return null;
    }

}
