package ui;

import model.Book;
import model.Borrowing;
import model.Member;
import service.BookService;
import service.BorrowingService;
import service.MemberService;
import utils.ConsoleHelper;
import utils.InputHelper;

import java.time.LocalDate;

public class BorrowingMenu {
    private final ConsoleHelper consoleHelper;
    private final InputHelper inputHelper;
    private final BorrowingService borrowingService;
    private final BookService bookService;
    private final MemberService memberService;

    public BorrowingMenu(ConsoleHelper consoleHelper, InputHelper inputHelper,
                         BorrowingService borrowingService, BookService bookService,
                         MemberService memberService) {
        this.consoleHelper = consoleHelper;
        this.inputHelper = inputHelper;
        this.borrowingService = borrowingService;
        this.bookService = bookService;
        this.memberService = memberService;
    }

    public void showBorrowingMenu() {
        int choice;
        do {
            consoleHelper.clearScreen();
            consoleHelper.borrowingMenu();

            choice = inputHelper.readIntInRange("🔮 Enter your choice: ", 0, 4);

            switch (choice) {
                case 1:
                    borrowBook();
                    break;
                case 2:
                    returnBook();
                    break;
                case 3:
                    viewCurrentBorrowedBook();
                    break;
                case 4:
                    viewBorrowingHistory();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("❌ Invalid choice! Please enter a number between 0 and 4.");
            }
        } while (choice != 0);
    }

    private void borrowBook() {
        char yesNo;
        while (true) {
            consoleHelper.clearScreen();
            consoleHelper.borrowBookHeading();
            consoleHelper.idNoteForBorrowing();
            System.out.println("📌 Please enter the following details:");
            System.out.println("──────────────────────────────────────────────────────────");

            int currentSize = borrowingService.getBorrowingList().size();
            String borrowId = String.format("BOR%04d", currentSize + 1);
            System.out.printf("🪪 Generated Borrowing ID: [%s]\n", borrowId);

            Book book = checkBookAfterInput();
            Member member = checkMemberAfterInput();
            LocalDate borrowDate = checkBorrowDateAfterInput();

            try {
                Borrowing borrowing = new Borrowing(borrowId, book, member, borrowDate);
                borrowingService.borrowBook(borrowing);
                System.out.println("\n✨ Successfully added Borrowing!");

                yesNo = inputHelper.readYesNo("🔄 Do you want to add another Borrowing (Y/N): ");

                if (yesNo == 'Y') {
                    continue;
                } else {
                    break;
                }
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());

                yesNo = inputHelper.readYesNo("🔄 Do you want to add another Borrowing (Y/N): ");

                if (yesNo == 'Y') {
                    continue;
                } else {
                    break;
                }
            }
        }
    }

    private Book checkBookAfterInput() {
        while (true) {
            String bookId = inputHelper.readBookId("📖 Enter Book ID: ");

            Book book = bookService.findBookById(bookId);
            if (book == null) {
                System.out.println("❌ Book not found in the system.");
                continue;
            }

            if (!book.checkAvailableQuantity()) {
                System.out.println("❌ This book is currently out of stock.");
                continue;
            }

            return book;
        }
    }

    private Member checkMemberAfterInput() {
        while (true) {
            String memberId = inputHelper.readMemberId("👉 Enter Member ID: ");

            Member member = memberService.findMemberById(memberId);
            if (member == null) {
                System.out.println("❌ Member not found in the system.");
                continue;
            }

            if (member.checkReachLimit()) {
                System.out.println("❌ Borrowing limit reached: This Member has already " +
                        "borrowed " + member.getLimitBorrow() + " books and cannot borrow more.");
                continue;
            }

            return member;
        }
    }

    private LocalDate checkBorrowDateAfterInput() {
        while (true) {
            LocalDate borrowDate = inputHelper.readDate("📅 Enter the borrow date: ");

            if (borrowDate.isAfter(LocalDate.now())) {
                System.out.println("🚫 Invalid Date: The borrow date must be today or earlier." +
                        " Future dates are not allowed.");
                continue;
            }

            return borrowDate;
        }
    }

    private void returnBook() {
        char yesNo;
        while (true) {
            consoleHelper.clearScreen();
            consoleHelper.returnBookHeading();
            System.out.println("ID must start with 'BOR' followed by 4 digits (e.g., BOR0001, BOR0015).");
            System.out.println("💡 Required format: DD/MM/YYYY (e.g., 08/06/2026)");

            String borrowId = inputHelper.readBorrowingId("👉 Enter Borrowing ID: ");

            Borrowing borrowing = borrowingService.findBorrowingByReturnDate(borrowId);

            if (borrowing == null) {
                System.out.println("❌ No active (unreturned) borrowing transaction found with this ID!");

                yesNo = inputHelper.readYesNo("🔄 Do you want to return another Borrowing (Y/N): ");

                if (yesNo == 'Y') {
                    continue;
                } else {
                    break;
                }
            }

            System.out.println("✅ Borrowing found!");
            borrowing.showBorrowingInfo();
            System.out.println("💳━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

            yesNo = inputHelper.readYesNo("🔄 Do you want to return this Borrowing (Y/N): ");

            if (yesNo == 'Y') {
                supportReturnBook(borrowing);

                yesNo = inputHelper.readYesNo("🔄 Do you want to return another Borrowing (Y/N): ");

                if (yesNo == 'Y') {
                    continue;
                } else {
                    break;
                }
            } else {
                yesNo = inputHelper.readYesNo("🔄 Do you want to return another Borrowing (Y/N): ");

                if (yesNo == 'Y') {
                    continue;
                } else {
                    break;
                }
            }
        }
    }

    private void supportReturnBook(Borrowing borrowing) {
        char yesNo;
        while (true) {
            consoleHelper.clearScreen();
            System.out.println("📊 CURRENT BORROWING DETAILS");
            borrowing.showBorrowingInfo();
            System.out.println("👉 Note: The return date must be AFTER or EQUAL to the borrowed date!");
            System.out.println("──────────────────────────────────────────────────────────");

            LocalDate returnDate = checkReturnDateAfterInput(borrowing);

            try {
                borrowingService.returnBook(borrowing, returnDate);
                System.out.println("\n✨ Successfully returned Borrowing!");
                consoleHelper.pause();
                break;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());

                yesNo = inputHelper.readYesNo("🔄 Do you want to return another date (Y/N): ");

                if (yesNo == 'Y') {
                    continue;
                } else {
                    break;
                }
            }
        }
    }

    private LocalDate checkReturnDateAfterInput(Borrowing borrowing) {
        while (true) {
            LocalDate returnDate = inputHelper.readDate("📅 Enter the return date: ");

            if (!borrowing.checkReturnDate(returnDate)) {
                System.out.println("❌ Return Date must be after Borrow Date!");
                continue;
            }

            return returnDate;
        }
    }

    private void viewCurrentBorrowedBook() {
        consoleHelper.clearScreen();
        consoleHelper.viewCurrentBorrowedBookHeading();

        borrowingService.displayCurrentBorrowing();
        consoleHelper.pause();
    }

    private void viewBorrowingHistory() {
        char yesNo;
        while (true) {
            consoleHelper.clearScreen();
            consoleHelper.viewBorrowingHistory();
            consoleHelper.memberIdNote();

            String memberId = inputHelper.readMemberId("🔎 Enter Member ID to Search: ");

            Member member = memberService.findMemberById(memberId);

            if (member == null) {
                System.out.println("⚠️ Member not found with ID: " + memberId);

                yesNo = inputHelper.readYesNo("🔄 Do you want to search another member (Y/N): ");
                if (yesNo == 'Y') {
                    continue;
                } else {
                    break;
                }
            }

            System.out.println("✅ Member found!");
            member.showMemberInfo();
            System.out.println("──────────────────────────────────────────────────────────");

            yesNo = inputHelper.readYesNo("🔄 Do you want to view the histoty of this member (Y/N): ");

            if (yesNo == 'Y') {
                consoleHelper.clearScreen();
                borrowingService.displayHistoryBorrowing(member);

                yesNo = inputHelper.readYesNo("🔄 Do you want to view another member (Y/N): ");
                if (yesNo == 'Y') {
                    continue;
                } else {
                    break;
                }
            } else {
                yesNo = inputHelper.readYesNo("🔄 Do you want to view another member (Y/N): ");
                if (yesNo == 'Y') {
                    continue;
                } else {
                    break;
                }
            }
        }
    }
}
