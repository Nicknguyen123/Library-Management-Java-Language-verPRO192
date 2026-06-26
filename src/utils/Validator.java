package utils;

import service.MemberService;

import java.time.LocalDate;

public class Validator {
    private static final String PREMIUM_MEMBER_ID_FORMAT = "^PRE\\d{3}$";
    private static final String REGULAR_MEMBER_ID_FORMAT = "^REG\\d{3}$";
    private static final String PHONE_FORMAT = "^0[35789]\\d{8}$";
    private static final String EMAIL_FORMAT = "^[a-zA-Z0-9+-_.%]+@gmail\\.com$";
    private static final String BOOK_ID_FORMAT = "^BK\\d{3}$";
    private static final String BORROWING_ID_FORMAT = "^BOR\\d{4}$";
    private final MemberService memberService;

    public Validator(MemberService memberService) {
        this.memberService = memberService;
    }

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

    public static boolean checkEmptyString(String s) {
        if (s == null || s.trim().isEmpty()) {
            return true;
        }

        return false;
    }

    public static boolean checkYesNo(char c) {
        if (c != 'Y' && c != 'N') {
            return false;
        }

        return true;
    }

    public static boolean checkStringLength(String s) {
        if (s.length() > 60) {
            return false;
        }

        return true;
    }

    public static boolean checkMemberId(String id) {
        if (!id.matches(PREMIUM_MEMBER_ID_FORMAT) && !id.matches(REGULAR_MEMBER_ID_FORMAT)) {
            return false;
        }

        return true;
    }

    public static boolean checkName(String s) {
        for (int i = 0; i < s.length(); i++) {
            if (!Character.isLetter(s.charAt(i)) && !Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }

        return true;
    }

    public static boolean checkPhone(String s) {
        if (!s.matches(PHONE_FORMAT)) {
            return false;
        }

        return true;
    }

    public static boolean checkEmail(String s) {
        if (!s.matches(EMAIL_FORMAT)) {
            return false;
        }

        return true;
    }

    public static boolean checkIntInRange(int n, int start, int end) {
        if (n < start || n > end) {
            return false;
        }

        return true;
    }
}

