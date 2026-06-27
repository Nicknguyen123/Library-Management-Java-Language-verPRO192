package utils;

import java.util.Scanner;

public class ConsoleHelper {
    private Scanner scanner;

    public ConsoleHelper(Scanner scanner) {
        this.scanner = scanner;
    }

    public void clearScreen() {
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
    }

    public void pause() {
        System.out.print("👉 Press Enter to continue...");

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.isEmpty()) {
                break;
            }
        }
    }

    public void mainMenu() {
        System.out.println("✨━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━✨");
        System.out.println("   🌟            LIBRARY MANAGEMENT SYSTEM             🌟        ");
        System.out.println("✨━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━✨");
        System.out.println("   1. 👥 Member Management");
        System.out.println("   2. 📖 Book Management");
        System.out.println("   3. 🔄 Borrowing Management");
        System.out.println("   4. 📊 Report");
        System.out.println("   0. 🚪 Exit Program");
        System.out.println("✨━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━✨");
    }

    public void exitProgram() {
        System.out.println("✨━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━✨");
        System.out.println("   ✅  System exited successfully.");
        System.out.println("   👋  Goodbye! See you next time.");
        System.out.println("✨━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━✨");
    }


// ====================== Member ==========================
    public void memberMenu() {
        System.out.println("👥━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━👥");
        System.out.println("   ⚙️                 MEMBER MANAGEMENT                ⚙️        ");
        System.out.println("👥━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━👥");
        System.out.println("   1. ➕ Add New Member");
        System.out.println("   2. ❌ Delete Member");
        System.out.println("   3. 🔍 Search Member");
        System.out.println("   4. 📝 Update Member");
        System.out.println("   5. 📋 Display All Members");
        System.out.println("   0. 🔙 Back to Main Menu");
        System.out.println("👥━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━👥");
    }

    public void addMemberHeading() {
        System.out.println("👥━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━👥");
        System.out.println("   ➕                  ADD NEW MEMBER                  ➕        ");
        System.out.println("👥━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━👥");
    }

    public void deleteMemberHeading() {
        System.out.println("👥━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━👥");
        System.out.println("   ❌                 DELETE MEMBER                    ❌        ");
        System.out.println("👥━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━👥");
    }

    public void searchMemberMenu() {
        System.out.println("👥━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━👥");
        System.out.println("   🔍                 SEARCH MEMBER                    🔍        ");
        System.out.println("👥━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━👥");
        System.out.println("   1. 🔑 Search by ID");
        System.out.println("   2. 📝 Search by Name");
        System.out.println("   0. ↩️ Back to Main Menu");
        System.out.println("👥━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━👥");
    }

    public void searchByMemberIdHeading() {
        System.out.println("👥━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━👥");
        System.out.println("   🔍               SEARCH BY MEMBER ID                 🔍        ");
        System.out.println("👥━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━👥");
    }

    public void searchByMemberNameHeading() {
        System.out.println("👥━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━👥");
        System.out.println("   🔍              SEARCH BY MEMBER NAME                🔍        ");
        System.out.println("👥━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━👥");
    }

    public void updateMemberHeading() {
        System.out.println("👥━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━👥");
        System.out.println("   ⚙️                 UPDATE MEMBER                    ⚙️        ");
        System.out.println("👥━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━👥");
    }

    public void updateMemberMenu() {
        System.out.println("👥━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━👥");
        System.out.println("   ⚙️                 UPDATE MEMBER                    ⚙️        ");
        System.out.println("👥━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━👥");
        System.out.println("   1. 📝 Update Member Name                              ");
        System.out.println("   2. 📞 Update Phone Number                             ");
        System.out.println("   3. ✉️ Update Email Address                            ");
        System.out.println("   0. ↩️ Back to Member Menu                             ");
        System.out.println("👥━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━👥");
    }

    public void updateMemberNameHeading() {
        System.out.println("💎━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━💎");
        System.out.println("   👤                UPDATE MEMBER NAME                👤        ");
        System.out.println("💎━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━💎");
    }

    public void updateMemberPhoneHeading() {
        System.out.println("💎━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━💎");
        System.out.println("   📱               UPDATE PHONE NUMBER                📱        ");
        System.out.println("💎━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━💎");
    }

    public void updateMemberEmailHeading() {
        System.out.println("💎━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━💎");
        System.out.println("   📧                  UPDATE MEMBER EMAIL                 📧        ");
        System.out.println("💎━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━💎");
    }

    public void displayMemberHeading() {
        System.out.println("👥━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━👥");
        System.out.println("   📋                DISPLAY MEMBERS                   📋   ");
        System.out.println("👥━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━👥");
    }

    public void memberIdNote() {
        System.out.println("⚠️  NOTE: ID prefix determines Member Type:");
        System.out.println("   - REGULAR Member: ID must start with 'REG' (e.g., REG001)");
        System.out.println("   - PREMIUM Member: ID must start with 'PRE' (e.g., PRE001)");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
    }

    //============================================= BOOK ==============================================
    public void bookMenu() {
        System.out.println("📘━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━📘");
        System.out.println("   📚                BOOK MANAGEMENT                   📚        ");
        System.out.println("📘━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━📘");
        System.out.println("   1. ➕ Add New Book");
        System.out.println("   2. ❌ Delete Book");
        System.out.println("   3. 🔍 Search Books");
        System.out.println("   4. 📝 Update Book Information");
        System.out.println("   5. 📋 Display All Books");
        System.out.println("   0. ↩️ Back to Main Menu");
        System.out.println("📘━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━📘");
    }

    public void addBookHeading() {
        System.out.println("📖━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━📖");
        System.out.println("   📚                  ADD NEW BOOK                    📚        ");
        System.out.println("📖━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━📖");
    }

    public void deleteBookHeading() {
        System.out.println("❌━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━❌");
        System.out.println("   🗑️                  DELETE BOOK                     🗑️        ");
        System.out.println("❌━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━❌");
    }

    public void searchBookMenu() {
        System.out.println("📚━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━📚");
        System.out.println("   🔍                  SEARCH BOOK MENU                🔍   ");
        System.out.println("📚━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━📚");
        System.out.println("   👉  1. Search by Book Title  📖                           ");
        System.out.println("   👉  2. Search by Author Name ✒️                           ");
        System.out.println("   👉  3. Search by Book Genre  🏷️                           ");
        System.out.println("   ❌  0. Back to Book Menu                                ");
        System.out.println("📚━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━📚");
    }

    public void searchByTitleHeading() {
        System.out.println("📖━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━📖");
        System.out.println("   🔍               SEARCH BY BOOK TITLE               🔍   ");
        System.out.println("📖━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━📖");
    }

    public void searchByAuthorHeading() {
        System.out.println("✒️━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━✒️");
        System.out.println("   🔍               SEARCH BY AUTHOR NAME              🔍   ");
        System.out.println("✒️━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━✒️");
    }

    public void searchByGenreHeading() {
        System.out.println("🏷️━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━🏷️");
        System.out.println("   🔍               SEARCH BY BOOK GENRE               🔍   ");
        System.out.println("🏷️━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━🏷️");
    }

    public void updateBookHeading() {
        System.out.println("📝━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━📝");
        System.out.println("   ⚙️               UPDATE BOOK INTERFACE               ⚙️   ");
        System.out.println("📝━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━📝");
    }

    public void updateBookMenu() {
        System.out.println("📚━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━📚");
        System.out.println("   📝                  UPDATE BOOK INFO                📝   ");
        System.out.println("📚━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━📚");
        System.out.println("   👉  1. Update Book Title  📖                           ");
        System.out.println("   👉  2. Update Author Name ✒️                           ");
        System.out.println("   👉  3. Update Book Genre  🏷️                           ");
        System.out.println("   👉  4. Update Publication Year 📅                      ");
        System.out.println("   👉  5. Update Total Quantity 🔢 (Optional)             ");
        System.out.println("   ❌  0. Back to Main Menu                                ");
        System.out.println("📚━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━📚");
    }

    public void updateBookTitleHeading() {
        System.out.println("📖━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━📖");
        System.out.println("   ⚙️                 UPDATE BOOK TITLE                 ⚙️   ");
        System.out.println("📖━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━📖");
    }

    public void updateBookAuthorHeading() {
        System.out.println("✒️━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━✒️");
        System.out.println("   ⚙️                 UPDATE AUTHOR NAME                ⚙️   ");
        System.out.println("✒️━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━✒️");
    }

    public void updateBookGenreHeading() {
        System.out.println("🏷️━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━🏷️");
        System.out.println("   ⚙️                 UPDATE BOOK GENRE                 ⚙️   ");
        System.out.println("🏷️━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━🏷️");
    }

    public void updateBookYearHeading() {
        System.out.println("📅━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━📅");
        System.out.println("   ⚙️             UPDATE PUBLICATION YEAR               ⚙️   ");
        System.out.println("📅━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━📅");
    }

    public void updateBookQuantityHeading() {
        System.out.println("🔢━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━🔢");
        System.out.println("   ⚙️                 UPDATE BOOK QUANTITY              ⚙️   ");
        System.out.println("🔢━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━🔢");
    }

    public void displayBookHeading() {
        System.out.println("📚━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━📚");
        System.out.println("   📋               DISPLAY ALL BOOKS                  📋   ");
        System.out.println("📚━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━📚");
    }

    public void bookIdNote() {
        System.out.println("⚠️ ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━ ⚠️");
        System.out.println("   [ERROR] ID must start with 'BK' followed by 3 digits (e.g., BK001, BK015).");
        System.out.println("⚠️ ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━ ⚠️");
    }
}