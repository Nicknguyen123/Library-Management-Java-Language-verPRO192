package ui;

import model.Member;
import model.PremiumMember;
import model.RegularMember;
import service.MemberService;
import utils.ConsoleHelper;
import utils.InputHelper;
import utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class MemberMenu {
    private final ConsoleHelper consoleHelper;
    private final InputHelper inputHelper;
    private final MemberService memberService;

    public MemberMenu(ConsoleHelper consoleHelper, InputHelper inputHelper, MemberService memberService) {
        this.consoleHelper = consoleHelper;
        this.inputHelper = inputHelper;
        this.memberService = memberService;
    }

    public void showMemberMenu() {
        int choice;
        do {
            consoleHelper.clearScreen();
            consoleHelper.memberMenu();

            choice = inputHelper.readIntInRange("👉 Enter your choice: ", 0, 5);

            switch (choice) {
                case 1:
                    addMember();
                    break;
                case 2:
                    deleteMember();
                    break;
                case 3:
                    searchMember();
                    break;
                case 4:
                    updateMember();
                    break;
                case 5:
                    displayAllMember();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("❌ Invalid choice! Please enter a number between 0 and 4.");
            }
        } while (choice != 0);
    }

    private void addMember() {
        char yesNo;
        while (true) {
            consoleHelper.clearScreen();
            consoleHelper.addMemberHeading();
            consoleHelper.memberIdNote();
            System.out.println("📌 Please enter the following details:");

            String id = null;
            do {
                id = inputHelper.readMemberId("📝 Enter Member ID: ");
            } while (memberService.checkDuplicateId(id));

            String name = inputHelper.readName("📝 Enter Member Name (Max 60 chars): ");
            name = StringUtils.beautify(name);

            String phone = null;
            do {
                phone = inputHelper.readPhone("📞 Enter Phone Number: ");
            } while (memberService.checkDuplicatePhone(phone));

            String email = null;
            do {
                email = inputHelper.readEmail("✉️ Enter Email: ");
            } while (memberService.checkDuplicateEmail(email));

            try {
                Member member = null;
                if (id.startsWith("PRE")) {
                    member = new PremiumMember(id, name, phone, email);
                } else {
                    member = new RegularMember(id, name, phone, email);
                }

                memberService.addMember(member);
                System.out.println("\n✨ Successfully added Premium Member!");

                yesNo = inputHelper.readYesNo("🔄 Do you want to add another Premium Member (Y/N): ");
                if (yesNo == 'Y') {
                    continue;
                } else {
                    break;
                }
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());

                yesNo = inputHelper.readYesNo("🔄 Do you want to add another Premium Member (Y/N): ");
                if (yesNo == 'Y') {
                    continue;
                } else {
                    break;
                }
            }
        }
    }

    private void deleteMember() {
        char yesNo;
        while (true) {
            consoleHelper.clearScreen();
            consoleHelper.deleteMemberHeading();
            consoleHelper.memberIdNote();

            String id = inputHelper.readMemberId("📝 Enter Member ID to delete: ");

            Member member = memberService.findMemberById(id);

            if (member == null) {
                System.out.println("⚠️ Member not found with ID: " + id);

                yesNo = inputHelper.readYesNo("🔄 Do you want to delete another Member (Y/N): ");
                if (yesNo == 'Y') {
                    continue;
                } else {
                    break;
                }
            }

            System.out.println("✅ Member found!");
            member.showMemberInfo();
            System.out.println("👥━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

            yesNo = inputHelper.readYesNo("🔄 Do you want to delete this Member (Y/N): ");
            if (yesNo == 'Y') {
                memberService.deleteMember(id);
                System.out.println("🗑️  Member deleted successfully!");

                yesNo = inputHelper.readYesNo("🔄 Do you want to delete another Member (Y/N): ");
                if (yesNo == 'Y') {
                    continue;
                } else {
                    break;
                }
            } else {
                yesNo = inputHelper.readYesNo("🔄 Do you want to delete another Member (Y/N): ");
                if (yesNo == 'Y') {
                    continue;
                } else {
                    break;
                }
            }
        }
    }

    private void searchMember() {
        int choice;
        do {
            consoleHelper.clearScreen();
            consoleHelper.searchMemberMenu();

            choice = inputHelper.readIntInRange("💎 Enter your choice: ", 0, 2);

            switch (choice) {
                case 1:
                    searchById();
                    break;
                case 2:
                    searchByName();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("❌ Invalid choice! Please enter a number between 0 and 2.");
            }
        } while (choice != 0);
    }

    private void searchById() {
        char yesNo;
        while (true) {
            consoleHelper.clearScreen();
            consoleHelper.searchByMemberIdHeading();
            consoleHelper.memberIdNote();

            String id = inputHelper.readMemberId("📝 Enter Member ID to search: ");

            Member member = memberService.findMemberById(id);

            if (member == null) {
                System.out.println("⚠️ Member not found with ID: " + id);

                yesNo = inputHelper.readYesNo("🔄 Do you want to search another Member (Y/N): ");
                if (yesNo == 'Y') {
                    continue;
                } else {
                    break;
                }
            }

            System.out.println("✅ Member found!");
            member.showMemberInfo();
            System.out.println("👥━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

            yesNo = inputHelper.readYesNo("🔄 Do you want to search another Member (Y/N): ");
            if (yesNo == 'Y') {
                continue;
            } else {
                break;
            }
        }
    }

    private void searchByName() {
        char yesNo;
        while (true) {
            consoleHelper.clearScreen();
            consoleHelper.searchByMemberNameHeading();

            String name = inputHelper.readName("📝 Enter Member Name to search (Max 60 chars): ");
            name = StringUtils.beautify(name);

            List<Member> foundList = memberService.findMemberByName(name);

            if (foundList == null) {
                System.out.println("⚠️ Member not found with Name: " + name);

                yesNo = inputHelper.readYesNo("🔄 Do you want to search another Member (Y/N): ");

                if (yesNo == 'Y') {
                    continue;
                } else {
                    break;
                }
            }

            System.out.println("✅ Member found!");
            for (Member member : foundList) {
                member.showMemberInfo();
                System.out.println("👥━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            }

            yesNo = inputHelper.readYesNo("🔄 Do you want to search another Member (Y/N): ");

            if (yesNo == 'Y') {
                continue;
            } else {
                break;
            }
        }
    }

    private void updateMember() {
        char yesNo;
        while (true) {
            consoleHelper.clearScreen();
            consoleHelper.updateMemberHeading();
            consoleHelper.memberIdNote();

            String id = inputHelper.readMemberId("📝 Enter Member ID to search: ");

            Member member = memberService.findMemberById(id);

            if (member == null) {
                System.out.println("⚠️ Member not found with ID: " + id);

                yesNo = inputHelper.readYesNo("🔄 Do you want to update another Member (Y/N): ");

                if (yesNo == 'Y') {
                    continue;
                } else {
                    break;
                }
            }

            System.out.println("✅ Member found!");
            member.showMemberInfo();
            System.out.println("👥━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

            yesNo = inputHelper.readYesNo("🔄 Do you want to update this Member (Y/N): ");

            if (yesNo == 'Y') {
                supportUpdate(member);
                System.out.printf("✨ All Changes For Premium Member Saved Successfully!\n");

                yesNo = inputHelper.readYesNo("🔄 Do you want to update another member (Y/N): ");

                if (yesNo == 'Y') {
                    continue;
                } else {
                    break;
                }
            } else {
                yesNo = inputHelper.readYesNo("🔄 Do you want to update another Member (Y/N): ");

                if (yesNo == 'Y') {
                    continue;
                } else {
                    break;
                }
            }
        }
    }

    private void supportUpdate(Member member) {
        int choice;
        do {
            consoleHelper.clearScreen();
            consoleHelper.updateMemberMenu();

            choice = inputHelper.readIntInRange("👉 Enter your choice: ", 0, 3);

            switch (choice) {
                case 1:
                    updateName(member);
                    break;
                case 2:
                    updatePhoneNumber(member);
                    break;
                case 3:
                    updateEmail(member);
                    break;
                case 0:
                    break;
                default:
                    System.out.println("❌ Invalid choice! Please enter a number between 0 and 3.");
            }
        } while (choice != 0);
    }

    private void updateName(Member member) {
        consoleHelper.clearScreen();
        consoleHelper.updateMemberNameHeading();

        String oldName = member.getName();
        System.out.println("✨ Current Member Name: " + oldName);

        String newName = inputHelper.readName("👉 Enter new name to update: ");
        newName = StringUtils.beautify(newName);
        memberService.updateName(member, newName);
        System.out.printf("🎉 Successfully Updated Member: %s ➔ %s\n", oldName, newName);
        consoleHelper.pause();
    }

    private void updatePhoneNumber(Member member) {
        char yesNo;
        while (true) {
            consoleHelper.clearScreen();
            consoleHelper.updateMemberPhoneHeading();

            String oldPhone = member.getPhone();
            System.out.println("✨ Current Phone Number: " + oldPhone);

            String newPhone = inputHelper.readPhone("👉 Enter new phone number to update: ");

            try {
                memberService.updatePhone(member, newPhone);
                System.out.printf("🎉 Successfully Updated Member: %s ➔ %s\n", oldPhone, newPhone);
                consoleHelper.pause();
                break;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());

                yesNo = inputHelper.readYesNo("🔄 Do you want to update another phone number (Y/N): ");

                if (yesNo == 'Y') {
                    continue;
                } else {
                    break;
                }
            }
        }
    }

    private void updateEmail(Member member) {
        char yesNo;
        while (true) {
            consoleHelper.clearScreen();
            consoleHelper.updateMemberEmailHeading();

            String oldEmail = member.getEmail();
            System.out.println("✨ Current Member Email: " + oldEmail);

            String newEmail = inputHelper.readEmail("📝 Enter New Email Address to update: ");

            try {
                memberService.updateEmail(member, newEmail);
                System.out.printf("🎉 Successfully Updated Member: %s ➔ %s\n", oldEmail, newEmail);
                consoleHelper.pause();
                break;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());

                yesNo = inputHelper.readYesNo("🔄 Do you want to update another email (Y/N): ");

                if (yesNo == 'Y') {
                    continue;
                } else {
                    break;
                }
            }
        }
    }

    private void displayAllMember() {
        consoleHelper.clearScreen();
        consoleHelper.displayMemberHeading();

        memberService.showAllMember();
        consoleHelper.pause();
    }
}
