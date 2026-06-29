package ui;

import model.Book;
import utils.ConsoleHelper;
import utils.InputHelper;

public class ConsoleMenu {
    private final ConsoleHelper consoleHelper;
    private final InputHelper inputHelper;
    private final MemberMenu memberMenu;
    private final BookMenu bookMenu;
    private final BorrowingMenu borrowingMenu;
    private final ReportMenu reportMenu;

    public ConsoleMenu(ConsoleHelper consoleHelper, InputHelper inputHelper, MemberMenu memberMenu,
                       BookMenu bookMenu, BorrowingMenu borrowingMenu, ReportMenu reportMenu) {
        this.consoleHelper = consoleHelper;
        this.inputHelper = inputHelper;
        this.memberMenu = memberMenu;
        this.bookMenu = bookMenu;
        this.borrowingMenu = borrowingMenu;
        this.reportMenu = reportMenu;
    }

    public void showMainMenu() {
        int choice;
        do {
            consoleHelper.clearScreen();
            consoleHelper.mainMenu();

            choice = inputHelper.readIntInRange("👉 Enter your choice: ", 0, 4);

            switch (choice) {
                case 1:
                    memberMenu.showMemberMenu();
                    break;
                case 2:
                    bookMenu.showBookMenu();
                    break;
                case 3:
                    borrowingMenu.showBorrowingMenu();
                    break;
                case 4:
                    reportMenu.showReportMenu();
                    break;
                case 0:
                    consoleHelper.clearScreen();
                    consoleHelper.exitProgram();
                    break;
                default:
                    System.out.println("❌ Invalid choice! Please enter a number between 0 and 4.");
            }
        } while (choice != 0);
    }
}
