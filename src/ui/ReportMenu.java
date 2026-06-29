package ui;

import model.Book;
import model.Borrowing;
import model.Member;
import service.ReportService;
import utils.ConsoleHelper;
import utils.InputHelper;

import java.util.List;
import java.util.function.Consumer;

public class ReportMenu {
    private final ConsoleHelper consoleHelper;
    private final InputHelper inputHelper;
    private final ReportService reportService;
    private static final int topRank = 5;

    public ReportMenu(ConsoleHelper consoleHelper, InputHelper inputHelper, ReportService reportService) {
        this.consoleHelper = consoleHelper;
        this.inputHelper = inputHelper;
        this.reportService = reportService;
    }

    public void showReportMenu() {
        int choice;
        do {
            consoleHelper.clearScreen();
            consoleHelper.reportMenu();

            choice = inputHelper.readIntInRange("🔮 Enter your choice: ", 0, 4);

            switch (choice) {
                case 1:
                    viewAllCurrentBorrowedBook();
                    break;
                case 2:
                    viewOverdueBook();
                    break;
                case 3:
                    viewPopularBook();
                    break;
                case 4:
                    viewPopularMember();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("❌ Invalid choice! Please enter a number between 0 and 4.");
            }
        } while (choice != 0);
    }

    private void viewAllCurrentBorrowedBook() {
        consoleHelper.clearScreen();
        consoleHelper.viewCurrentBorrowedBookHeading();

        List<Borrowing> borrowingList = reportService.getCurrentBorrowing();

        if (borrowingList.isEmpty()) {
            System.out.println("✨ No books are currently being borrowed.");
            consoleHelper.pause();
            return;
        }

        for (Borrowing borrowing : borrowingList) {
            borrowing.showBorrowingInfo();
            System.out.println("💳━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        }

        consoleHelper.pause();
    }

    private void viewOverdueBook() {
        consoleHelper.clearScreen();
        consoleHelper.viewOverdueBookHeading();

        List<Borrowing> borrowingList = reportService.getOverdueBorrowing();

        if (borrowingList.isEmpty()) {
            System.out.println("✨ No overdue books found.");
            consoleHelper.pause();
            return;
        }

        for (Borrowing borrowing : borrowingList) {
            borrowing.showBorrowingInfo();
            System.out.println("💳━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        }

        consoleHelper.pause();
    }

    private void viewPopularBook() {
        consoleHelper.clearScreen();
        consoleHelper.viewPopularBookHeading();

        List<Book> bookList = reportService.getMostPopularBook();

        if (bookList.isEmpty()) {
            System.out.println("📭 No books have been borrowed yet.");
            consoleHelper.pause();
            return;
        }

        if (bookList.size() >= topRank) {
            for (int i = 0; i < topRank; i++) {
                bookList.get(i).showBookInfo();
                System.out.println("📖━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            }
        } else {
            Consumer<Book> bookConsumer = (book) -> {
                book.showBookInfo();
                System.out.println("📖━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            };

            bookList.forEach(bookConsumer);
        }

        consoleHelper.pause();
    }

    private void viewPopularMember() {
        consoleHelper.clearScreen();
        consoleHelper.viewPopularMemberHeading();

        List<Member> memberList = reportService.getMostPopularMember();

        if (memberList.isEmpty()) {
            System.out.println("📭 No member have borrowed yet.");
            consoleHelper.pause();
            return;
        }

        if (memberList.size() >= topRank) {
            for (int i = 0; i < topRank; i++) {
                memberList.get(i).showMemberInfo();
                System.out.println("👥━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            }
        } else {
            Consumer<Member> memberConsumer = (member) -> {
                member.showMemberInfo();
                System.out.println("👥━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            };

            memberList.forEach(memberConsumer);
        }

        consoleHelper.pause();
    }
}
