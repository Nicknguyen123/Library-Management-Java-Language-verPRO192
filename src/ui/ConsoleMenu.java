package ui;

import utils.ConsoleHelper;
import utils.InputHelper;

public class ConsoleMenu {
    private ConsoleHelper consoleHelper;
    private InputHelper inputHelper;
    private MemberMenu memberMenu;

    public ConsoleMenu(ConsoleHelper consoleHelper, InputHelper inputHelper, MemberMenu memberMenu) {
        this.consoleHelper = consoleHelper;
        this.inputHelper = inputHelper;
        this.memberMenu = memberMenu;
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
                    break;
                case 3:
                    break;
                case 4:
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
