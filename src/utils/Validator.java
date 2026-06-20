package utils;

import java.time.LocalDate;

public class Validator {
    public static final String PHONE_FORMAT = "^0[35789]\\d{8}$";
    public static final String EMAIL_FORMAT = "^[a-zA-Z0-9+-_.%]+@gmail\\.com$";
    public static final String REGULAR_MEMBER_ID_FORMAT = "^REG\\d{3}$";
    public static final String BOOK_ID_FORMAT = "^BK\\d{3}$";
    public static final String BORROWING_ID_FORMAT = "^BOR\\d{4}$";


    public static String validateBasicString(String src) {
        if (src == null || src.trim().isEmpty()) {
            throw new IllegalArgumentException("❌ Input string cannot be null or empty");
        }

        return src.trim();
    }

    public static String validateBasicEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("❌ Email cannot be null or empty");
        }

        if (!email.contains("@")) {
            throw new IllegalArgumentException("❌ Email must contain '@'");
        }

        return email.trim();
    }

    public static int validateNumber(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("❌ Value cannot be negative");
        }

        return n;
    }

    public static LocalDate validateDate(LocalDate date) {
        if (date == null) {
            throw new IllegalArgumentException("❌ Date cannot be null");
        }

        return date;
    }
}

