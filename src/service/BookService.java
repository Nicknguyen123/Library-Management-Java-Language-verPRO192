package service;

import model.Book;
import repository.BookRepository;
import utils.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class BookService {
    private final List<Book> bookList;
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookList = new ArrayList<>();
        this.bookRepository = bookRepository;
    }

    public List<Book> getBookList() {
        return new ArrayList<>(bookList);
    }

    public void addBook(Book book) {
        checkBookNull(book);

        Book foundBook = findBookById(book.getBookId());
        if (foundBook != null) {
            throw new IllegalArgumentException("❌ This Book already exists in the system. " +
                    "Duplicate ID: " + book.getBookId());
        }

        bookList.add(book);
        bookRepository.saveOneBook(book);
    }

    public void addBookFromFile(Book book) {
        bookList.add(book);
    }

    public void deleteBook(String id) {
        String safeId = Validator.validateBasicString(id);

        for (int i = 0; i < bookList.size(); i++) {
            if (bookList.get(i).getBookId().equals(safeId)) {
                bookList.remove(i);
                bookRepository.saveAllBook(bookList);
                return;
            }
        }

        throw new IllegalArgumentException("⚠️ Alert: Unable to remove. No book found with the specified ID: "
                + safeId);
    }

    public void updateTitle(Book book, String newTitle) {
        checkBookNull(book);

        String safeTitle = Validator.validateBasicString(newTitle);

        book.setTitle(safeTitle);
        bookRepository.saveAllBook(bookList);
    }

    public void updateAuthor(Book book, String newAuthor) {
        checkBookNull(book);

        String safeAuthor = Validator.validateBasicString(newAuthor);

        book.setAuthor(safeAuthor);
        bookRepository.saveAllBook(bookList);
    }

    public void updateGenre(Book book, String newGenre) {
        checkBookNull(book);

        String safeGenre = Validator.validateBasicString(newGenre);

        book.setGenre(safeGenre);
        bookRepository.saveAllBook(bookList);
    }

    public void updateYear(Book book, int newYear) {
        checkBookNull(book);

        int safeYear = Validator.validateNumber(newYear);

        book.setPublicationYear(safeYear);
        bookRepository.saveAllBook(bookList);
    }

    public void updateQuantity(Book book, int newQuantity) {
        checkBookNull(book);

        int safeQuantity = Validator.validateNumber(newQuantity);

        if (safeQuantity < book.getBorrowCount()) {
            throw new IllegalArgumentException("❌ Cannot reduce total quantity below the number of " +
                    "currently borrowed books!");
        }

        book.setQuantity(safeQuantity);
        bookRepository.saveAllBook(bookList);
    }

    public void showAllBook() {
        if (bookList.isEmpty()) {
            System.out.println("⚠️ No books found in the system.");
            return;
        }

        String border = "+---------------------------------------------------------------------------------" +
                "------------------------------------------------------------------------+";

        System.out.println(border);
        System.out.printf("| %-10s | %-25s | %-20s | %-15s | %-10s | %-10s | %-10s | %-10s | %-12s |\n",
                "🆔 BOOK ID", "📖 TITLE", "✍️ AUTHOR", "🎭 GENRE", "📅 PUB_YEAR", "📦 QUANTITY",
                "📚 BORROWED", "📈 TOTAL_BOR", "✨ STATUS");
        System.out.println(border);

        Consumer<Book> bookConsumer = (book) -> book.showBookInfo();
        bookList.forEach(bookConsumer);
        System.out.println(border);
    }

    public Book findBookById(String bookId) {
        String safeId = Validator.validateBasicString(bookId);

        for (Book book : bookList) {
            if (book.getBookId().equals(safeId)) {
                return book;
            }
        }

        return null;
    }

    public List<Book> findBookByTitle(String title) {
        String safeTitle = Validator.validateBasicString(title);

        List<Book> foundBook = new ArrayList<>();
        for (Book book : bookList) {
            if (book.getTitle().contains(safeTitle)) {
                foundBook.add(book);
            }
        }

        if (foundBook.isEmpty()) {
            return null;
        } else {
            return foundBook;
        }
    }

    public List<Book> findBookByAuthor(String author) {
        String safeAuthor = Validator.validateBasicString(author);

        List<Book> foundBook = new ArrayList<>();
        for (Book book : bookList) {
            if (book.getAuthor().contains(safeAuthor)) {
                foundBook.add(book);
            }
        }

        if (foundBook.isEmpty()) {
            return null;
        } else {
            return foundBook;
        }
    }

    public List<Book> findBookByGenre(String genre) {
        String safeGenre = Validator.validateBasicString(genre);

        List<Book> foundBook = new ArrayList<>();
        for (Book book : bookList) {
            if (book.getGenre().contains(safeGenre)) {
                foundBook.add(book);
            }
        }

        if (foundBook.isEmpty()) {
            return null;
        } else {
            return foundBook;
        }
    }

    private void checkBookNull(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("❌ Book cannot be null");
        }
    }
}