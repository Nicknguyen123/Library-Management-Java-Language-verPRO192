package service;

import model.Book;
import model.Borrowing;
import model.Member;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ReportService {
    private MemberService memberService;
    private BookService bookService;
    private BorrowingService borrowingService;

    public ReportService(MemberService memberService, BookService bookService,
                         BorrowingService borrowingService) {
        this.memberService = memberService;
        this.bookService = bookService;
        this.borrowingService = borrowingService;
    }

    public List<Borrowing> getCurrentBorrowing() {
        List<Borrowing> borrowingList = new ArrayList<>();

        for (Borrowing borrowing : borrowingService.getBorrowingList()) {
            if (borrowing.getReturnDate() == null) {
                borrowingList.add(borrowing);
            }
        }

        return borrowingList;
    }

    public List<Borrowing> getOverdueBorrowing() {
        List<Borrowing> borrowingList = new ArrayList<>();

        for (Borrowing borrowing : borrowingService.getBorrowingList()) {
            if (borrowing.getReturnDate() == null && borrowing.getDueDate().isBefore(LocalDate.now())) {
                borrowingList.add(borrowing);
            }
        }

        return borrowingList;
    }

    public List<Book> getMostPopularBook() {
        List<Book> bookList = bookService.getBookList();

        Comparator<Book> bookComparator = (book1, book2) -> book2.getTotalBorrowing() - book1.getTotalBorrowing();
        bookList.sort(bookComparator);

        return bookList;
    }

    public List<Member> getMostPopularMember() {
        List<Member> memberList = memberService.getMemberList();

        Comparator<Member> memberComparator = (member1, member2) -> member2.getTotalBorrowing() - member1.getTotalBorrowing();
        memberList.sort(memberComparator);

        return memberList;
    }
}