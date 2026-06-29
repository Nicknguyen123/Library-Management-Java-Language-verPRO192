package app;

import model.Book;
import repository.BookRepository;
import repository.BorrowingRepository;
import repository.MemberRepository;
import service.BookService;
import service.BorrowingService;
import service.MemberService;
import service.ReportService;
import ui.*;
import utils.ConsoleHelper;
import utils.InputHelper;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ConsoleHelper consoleHelper = new ConsoleHelper(scanner);
        InputHelper inputHelper = new InputHelper(scanner);

        MemberRepository memberRepository = new MemberRepository();
        BookRepository bookRepository = new BookRepository();

        MemberService memberService = new MemberService(memberRepository);
        BookService bookService = new BookService(bookRepository);
        BorrowingRepository borrowingRepository = new BorrowingRepository(bookService, memberService);
        BorrowingService borrowingService = new BorrowingService(bookService, memberService,
                borrowingRepository, bookRepository, memberRepository);
        ReportService reportService = new ReportService(memberService, bookService, borrowingService);

        MemberMenu memberMenu = new MemberMenu(consoleHelper, inputHelper, memberService);
        BookMenu bookMenu = new BookMenu(consoleHelper, inputHelper, bookService);
        BorrowingMenu borrowingMenu = new BorrowingMenu(consoleHelper, inputHelper, borrowingService,
                bookService, memberService);
        ReportMenu reportMenu = new ReportMenu(consoleHelper, inputHelper, reportService);

        memberRepository.loadData(memberService);
        bookRepository.loadData(bookService);
        borrowingRepository.loadData(borrowingService);
        ConsoleMenu consoleMenu = new ConsoleMenu(consoleHelper, inputHelper, memberMenu, bookMenu,
                borrowingMenu, reportMenu);
        consoleMenu.showMainMenu();
    }
}