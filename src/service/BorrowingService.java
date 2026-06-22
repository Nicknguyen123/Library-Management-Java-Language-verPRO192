package service;

import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import model.Book;
import model.Borrowing;
import model.Member;
import repository.BookRepository;
import repository.BorrowingRepository;
import repository.MemberRepository;
import utils.Validator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class BorrowingService {
    private final List<Borrowing> borrowingList;
    private final BookService bookService;
    private final MemberService memberService;
    private final BorrowingRepository borrowingRepository;
    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;

    public BorrowingService(BookService bookService, MemberService memberService,
                            BorrowingRepository borrowingRepository, BookRepository bookRepository,
                            MemberRepository memberRepository) {
        this.borrowingList = new ArrayList<>();
        this.bookService = bookService;
        this.memberService = memberService;
        this.borrowingRepository = borrowingRepository;
        this.bookRepository = bookRepository;
        this.memberRepository = memberRepository;
    }

    public void borrowBook(Borrowing borrowing) {
        checkBorrowNull(borrowing);

        if (findBorrowingById(borrowing.getTransactionId()) != null) {
            throw new IllegalArgumentException("❌ This Borrowing already exists in the system. " +
                    "Duplicate ID: " + borrowing.getTransactionId());
        }

        Book book = bookService.findBookById(borrowing.getBook().getBookId());
        if (book == null) {
            throw new IllegalArgumentException("❌ Book not found in the system.");
        }

        int curQuantity = book.getQuantity() - book.getBorrowCount();
        if (curQuantity == 0) {
            throw new IllegalArgumentException("❌ This book is currently out of stock.");
        }

        Member member = memberService.findMemberById(borrowing.getMember().getId());
        if (member == null) {
            throw new IllegalArgumentException("❌ Member not found in the system.");
        }

        if (member.getCurrentBorrowedCount() >= member.getLimitBorrow()) {
            throw new IllegalArgumentException("❌ Borrowing limit reached: This Member has already " +
                    "borrowed " + member.getLimitBorrow() + " books and cannot borrow more.");
        }

        if (borrowing.getBorrowDate().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("🚫 Invalid Date: The borrow date must be today or earlier." +
                    " Future dates are not allowed.");
        }

        boolean isDuplicate = checkDuplicateBorrowing(member.getId(), book.getBookId());
        if (isDuplicate) {
            throw new IllegalArgumentException("🚫 Duplicate Book Error: You cannot borrow the same book twice!");
        }

        borrowingList.add(borrowing);
        book.increaseBorrowCount();
        member.increaseBorrowCount();

        borrowingRepository.saveOneBorrowing(borrowing);
        bookRepository.saveAllBook(bookService.getBookList());
        memberRepository.saveAllMember(memberService.getMemberList());
    }

    public void addBorrowingFromFile(Borrowing borrowing) {
        borrowingList.add(borrowing);
    }

    public void returnBook(Borrowing borrowing, LocalDate returnDate) {
        checkBorrowNull(borrowing);

        LocalDate safeReturnDate = Validator.validateDate(returnDate);

        if (returnDate.isBefore(borrowing.getBorrowDate())) {
            throw new IllegalArgumentException("❌ Return Date must be after Borrow Date!");
        }

        borrowing.setReturnDate(safeReturnDate);
        borrowing.getBook().decreaseBorrowCount();
        borrowing.getMember().decreaseBorrowCount();

        borrowingRepository.saveAllBorrowing(borrowingList);
        bookRepository.saveAllBook(bookService.getBookList());
        memberRepository.saveAllMember(memberService.getMemberList());
    }

    public void displayCurrentBorrowing() {
        if (borrowingList.isEmpty()) {
            System.out.println("⚠️ No borrowing found in the system.");
            return;
        }

        String border = "+-------------------------------------------------------------------------------" +
                "-------------------------+";
        System.out.println(border);
        System.out.printf("| %-15s | %-12s | %-12s | %-12s | %-12s | %-12s | %-10s |\n",
                "🆔 TX_ID", "📖 BOOK_ID", "👤 MEM_ID", "📅 BORROW_DT",
                "⏳ DUE_DT", "🔄 RETURN_DT", "💸 FINE");
        System.out.println(border);

        boolean found = false;
        for (Borrowing borrowing : borrowingList) {
            if (borrowing.getReturnDate() == null) {
                borrowing.showBorrowingInfo();
                found = true;
            }
        }

        if (!found) {
            System.out.println("⚠️ There are no active (unreturned) borrowings at the moment.");
        }
        System.out.println(border);
    }

    public void displayHistoryBorrowing(Member member) {
        memberService.checkMemberNull(member);

        if (borrowingList.isEmpty()) {
            System.out.println("⚠️ No borrowing found in the system.");
            return;
        }

        String border = "+-------------------------------------------------------------------------------" +
                "-------------------------+";
        System.out.println(border);
        System.out.printf("| %-15s | %-12s | %-12s | %-12s | %-12s | %-12s | %-10s |\n",
                "🆔 TX_ID", "📖 BOOK_ID", "👤 MEM_ID", "📅 BORROW_DT",
                "⏳ DUE_DT", "🔄 RETURN_DT", "💸 FINE");
        System.out.println(border);

        boolean found = false;
        for (Borrowing borrowing : borrowingList) {
            Member temp = borrowing.getMember();
            if (temp.getId().equals(member.getId())) {
                borrowing.showBorrowingInfo();
                found = true;
            }
        }

        if (!found) {
            System.out.printf("⚠️ No borrowing history found for Member ID: %s (%s)\n",
                    member.getId(), member.getName());
        }
        System.out.println(border);
    }


    private Borrowing findBorrowingById(String id) {
        String safeId = Validator.validateBasicString(id);

        for (Borrowing borrowing : borrowingList) {
            if (borrowing.getTransactionId().equals(safeId)) {
                return borrowing;
            }
        }

        return null;
    }

    private void checkBorrowNull(Borrowing borrowing) {
        if (borrowing == null) {
            throw new IllegalArgumentException("❌ Borrowing cannot be null");
        }
    }

    private boolean checkDuplicateBorrowing(String memberId, String bookId) {
        String safeMemberId = Validator.validateBasicString(memberId);
        String safeBookId = Validator.validateBasicString(bookId);

        for (Borrowing borrowing : borrowingList) {
            if (borrowing.getReturnDate() == null) {
                Member member = borrowing.getMember();
                Book book = borrowing.getBook();
                if (member.getId().equals(safeMemberId) && book.getBookId().equals(safeBookId)) {
                    return true;
                }
            }
        }

        return false;
    }
}