package app;

import repository.MemberRepository;
import service.MemberService;
import ui.ConsoleMenu;
import ui.MemberMenu;
import utils.ConsoleHelper;
import utils.InputHelper;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ConsoleHelper consoleHelper = new ConsoleHelper(scanner);
        InputHelper inputHelper = new InputHelper(scanner);

        MemberRepository memberRepository = new MemberRepository();

        MemberService memberService = new MemberService(memberRepository);

        MemberMenu memberMenu = new MemberMenu(consoleHelper, inputHelper, memberService);

        memberRepository.loadData(memberService);
        ConsoleMenu consoleMenu = new ConsoleMenu(consoleHelper, inputHelper, memberMenu);
        consoleMenu.showMainMenu();
    }
}