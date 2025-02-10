package com.pragma.franchise.domain.util;

public class DomainConstants {
    public static final String FRANCHISE_NOT_FOUND = "Franchise not found";
    public static final String FRANCHISE_NAME_ALREADY_EXISTS = "Franchise name already exists";
    public static final String BRANCH_NAME_ALREADY_EXISTS = "Branch name already exists";
    public static final String FRANCHISE_ID_REQUIRED = "Franchise id is required";
    public static final String BRANCH_NAME_TOO_LONG = "Branch name is too long";
    public static final String FRANCHISE_NAME_REQUIRED = "Franchise name is required";
    public static final String FRANCHISE_NAME_TOO_LONG = "Franchise name is too long";
    public static final int MAX_NAME_BRANCH_LENGTH = 50;
    public static final int MAX_NAME_FRANCHISE_LENGTH = 50;
    public static final String BRANCH_NOT_FOUND = "Branch not found";
    public static final String PRODUCT_NAME_REQUIRED = "Product name is required";
    public static final String PRODUCT_INVALID_STOCK = "Stock must be non-negative";

    DomainConstants() {
        throw new IllegalStateException("Utility class");
    }
}
