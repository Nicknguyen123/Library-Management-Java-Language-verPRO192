package repository;

import model.Book;
import model.Borrowing;
import model.Member;
import service.BookService;
import service.BorrowingService;
import service.MemberService;

import java.io.*;
import java.time.LocalDate;
import java.util.List;

public class BorrowingRepository {
    private static final String FOLDER = "data";
    private static final String FILE_PATH = "data/Borrowing.txt";
    private BookService bookService;
    private MemberService memberService;

    public BorrowingRepository(BookService bookService, MemberService memberService) {
        this.bookService = bookService;
        this.memberService = memberService;
    }

    public void saveOneBorrowing(Borrowing borrowing) {
        File folder = new File(FOLDER);

        if (!folder.exists()) {
            folder.mkdirs();
        }

        try (
                FileWriter fileWriter = new FileWriter(FILE_PATH, true);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        ) {

            String dataLine = convertObjectToString(borrowing);
            bufferedWriter.write(dataLine);
            bufferedWriter.newLine();

        } catch (IOException e) {
            System.out.println("❌ Failed to save Borrowing!");
        }
    }

    public void saveAllBorrowing(List<Borrowing> borrowingList) {
        File folder = new File(FOLDER);

        if (!folder.exists()) {
            folder.mkdirs();
        }

        try (
                FileWriter fileWriter = new FileWriter(FILE_PATH, false);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        ) {

            for (Borrowing borrowing : borrowingList) {
                String dataLine = convertObjectToString(borrowing);
                bufferedWriter.write(dataLine);
                bufferedWriter.newLine();
            }

        } catch (IOException e) {
            System.out.println("❌ Failed to save Borrowing!");
        }
    }

    public void loadData(BorrowingService borrowingService) {
        File file = new File(FILE_PATH);

        if (!file.exists()) {
            return;
        }

        try (
                FileReader fileReader = new FileReader(FILE_PATH);
                BufferedReader bufferedReader = new BufferedReader(fileReader);
        ) {

            String dataLine;
            while ((dataLine = bufferedReader.readLine()) != null) {
               Borrowing borrowing = convertStringToObject(dataLine);
               borrowingService.addBorrowingFromFile(borrowing);
            }

        } catch (IOException e) {
            System.out.println("⚠️ Warning: Failed to load Borrowing data!");
        }
    }

    private String convertObjectToString(Borrowing borrowing) {
        String borrowId = borrowing.getTransactionId();
        String bookId = borrowing.getBook().getBookId();
        String memberId = borrowing.getMember().getId();
        String borrowDate = borrowing.getBorrowDate().toString();
        String dueDate = borrowing.getDueDate().toString();
        String returnDate = (borrowing.getReturnDate() == null) ? "" : borrowing.getReturnDate().toString();

        return borrowId + '|' + bookId + '|' + memberId + '|' + borrowDate + '|' + dueDate + '|' + returnDate
                + '|' + borrowing.getFine();
    }

    private Borrowing convertStringToObject(String dataLine) {
        String[] source = dataLine.split("\\|");

        String borrowId = source[0];
        String bookId = source[1];
        Book book = bookService.findBookById(bookId);
        String memberId = source[2];
        Member member = memberService.findMemberById(memberId);
        LocalDate borrowDate = LocalDate.parse(source[3]);
        LocalDate dueDate = LocalDate.parse(source[4]);
        LocalDate returnDate = null;

        if (!source[5].isEmpty()) {
            returnDate = LocalDate.parse(source[5]);
        }
        long fine = Long.parseLong(source[6]);

        return new Borrowing(borrowId, book, member, borrowDate, dueDate, returnDate, fine);
    }
}