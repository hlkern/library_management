package com.atmosware.library_project.business.messages;

public class BusinessMessages {

    public static String LOGIN_FAILED = "Invalid email or password";
    public static String USER_NOT_FOUND = "User not found";
    public static String BOOK_NOT_FOUND = "Book not found";
    public static String TRANSACTION_NOT_FOUND = "Transaction not found";
    public static String ALREADY_BORROWED = "Please exclude the following books that are already borrowed: ";
    public static String ALREADY_RETURNED = "One or more books are already returned";
    public static String USER_ALREADY_EXISTS = "Username already exists, please try a different username";
    public static String EMAIL_ALREADY_EXISTS = "Email already exists, please try a different email";
    public static String MEMBERSHIP_EXPIRED = "Membership expired. Please renew your membership.";
    public static String MEMBERSHIP_IS_INACTIVE = "Your membership is not active. Please renew to continue using the service.";
    public static String UNPAYED_DUES = "You have outstanding fees. Please pay your dues to borrow new books.";
}
