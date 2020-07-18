package com.company.eshop.product;

public enum Category {
    ELECTRONICS,
    BOOKS,
    CLOTHING;

    public static Category parse(String value) {
        if (value != null)
            for (Category category : Category.values())
                if (category.name().equalsIgnoreCase(value))
                    return category;
        return null;
    }
}
