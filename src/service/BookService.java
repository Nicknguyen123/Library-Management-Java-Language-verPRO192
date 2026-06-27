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

        boolean isDuplicate = isDuplicateBook(null, book.getTitle(), book.getAuthor(),
                book.getPublicationYear());
        if (isDuplicate) {
            throw new IllegalArgumentException("❌ This book is already registered in the system: "
                    + book.getTitle());
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

        boolean isDuplicate = isDuplicateBook(book.getBookId(), newTitle, book.getAuthor(),
                book.getPublicationYear());
        if (isDuplicate) {
            throw new IllegalArgumentException("❌ Cannot update title. A book named '" + safeTitle +
                    "' by " + book.getAuthor() + " (" + book.getPublicationYear() + ") already exists in the system.");
        }

        book.setTitle(safeTitle);
        bookRepository.saveAllBook(bookList);
    }

    public void updateAuthor(Book book, String newAuthor) {
        checkBookNull(book);

        String safeAuthor = Validator.validateBasicString(newAuthor);

        boolean isDuplicate = isDuplicateBook(book.getBookId(), book.getTitle(), newAuthor,
                book.getPublicationYear());
        if (isDuplicate) {
            throw new IllegalArgumentException("❌ Cannot update author. A book named '" + book.getTitle() +
                    "' by " + safeAuthor + " (" + book.getPublicationYear() + ") already exists in the system.");
        }

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

        boolean isDuplicate = isDuplicateBook(book.getBookId(), book.getTitle(), book.getAuthor(), newYear);
        if (isDuplicate) {
            throw new IllegalArgumentException("❌ Cannot update publication year. A book named '" + book.getTitle() +
                    "' by " + book.getAuthor() + " (" + newYear + ") already exists in the system.");
        }

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

        Consumer<Book> bookConsumer = (book) -> {
            book.showBookInfo();
            System.out.println("📖━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        };
        bookList.forEach(bookConsumer);
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

    private boolean isDuplicateBook(String bookId, String title, String author, int publicationYear) {
        String safeTitle = Validator.validateBasicString(title);
        String safeAuthor = Validator.validateBasicString(author);
        int safeYear = Validator.validateNumber(publicationYear);

        Book tempBook = new Book("Check duplicate", safeTitle, safeAuthor, "check duplicate",
                0, 0);

        for (Book book : bookList) {
            if (bookId != null && book.getBookId().equals(bookId)) {
                continue;
            }

            if (book.checkDuplicateBook(tempBook)) {
                return true;
            }
        }

        return false;
    }
}