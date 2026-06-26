package utils;

import service.MemberService;

import java.util.Scanner;

public class InputHelper {
    private Scanner scanner;

    public InputHelper(Scanner scanner) {
        this.scanner = scanner;
    }

    private String readStringNonEmpty(String message) {
        while (true) {
            System.out.print(message);
            String input = scanner.nextLine();

            if (Validator.checkEmptyString(input)) {
                System.out.println("❌ Input string cannot be null or empty");
                continue;
            }

            return input.trim();
        }
    }

    public char readYesNo(String message) {
        while (true) {
            String input = readStringNonEmpty(message).toUpperCase();
            char c = input.charAt(0);

            if (!Validator.checkYesNo(c)) {
                System.out.println("⚠️ Invalid input! Please enter 'y' to continue or 'n' to exit.");
                continue;
            }

            return c;
        }
    }

    public String readMemberId(String message) {
        while (true) {
            String input = readStringNonEmpty(message).toUpperCase();

            if (!Validator.checkMemberId(input)) {
                System.out.println("❌ Invalid Member ID! ID must start with 'REG' (e.g., REG001) " +
                        "or 'PRE' (e.g., PRE001).");
                continue;
            }

            System.out.println("✅ [SUCCESS] Valid ID! Member format checked successfully.");
            return input;
        }
    }

    public String readName(String message) {
        while (true) {
            String input = readStringNonEmpty(message);

            if (!Validator.checkStringLength(input)) {
                System.out.println("❌ Invalid Input! Name length must not exceed 60 characters.");
                continue;
            }

            if (!Validator.checkName(input)) {
                System.out.println("❌ Invalid input! Only letters and spaces are allowed.");
                continue;
            }

            System.out.println("✅ Success: Valid input!");
            return input;
        }
    }

    public String readPhone(String message) {
        while (true) {
            String input = readStringNonEmpty(message);

            if (!Validator.checkPhone(input)) {
                System.out.println("❌ Invalid phone number format!");
                System.out.println("💡 Please enter a 10-digit number starting with 0 (e.g., 03, 05, " +
                        "07, 08, 09).");
                continue;
            }

            System.out.println("✅ Phone number format is valid!");
            return input;
        }
    }

    public String readEmail(String message) {
        while (true) {
            String input = readStringNonEmpty(message);

            if (!Validator.checkEmail(input)) {
                System.out.println("❌ Invalid email format!");
                System.out.println("💡 Please enter a valid Gmail address (e.g., example@gmail.com).");
                continue;
            }

            System.out.println("✅ Email format is valid!");
            return input;
        }
    }

    private int readInt(String message) {
        while (true) {
            System.out.print(message);
            String input = scanner.nextLine().trim();

            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("❌ Error: Invalid input! Please enter an integer (e.g: 1, 2, 3,...).");
            }
        }
    }

    public int readIntInRange(String message, int start, int end) {
        while (true) {
            int n = readInt(message);

            if (!Validator.checkIntInRange(n, start, end)) {
                System.out.printf("❌ Error: Invalid input! Please enter a number between %d and %d.\n",
                        start, end);
                continue;
            }

            System.out.println("✅ Success: Valid input!");
            return n;
        }
    }


}
