package com.company.eshop.product;

public enum Type {
    LAPTOP,
    MOBILE,
    NOVEL,
    FANTASY,
    SUMMER,
    WINTER;


    public static Type parse(String value) {
        if (value != null)
            for (Type type : Type.values())
                if (type.name().equalsIgnoreCase(value))
                    return type;
        return null;
    }
}
