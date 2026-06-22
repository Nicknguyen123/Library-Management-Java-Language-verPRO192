package model;

import utils.Validator;

public class Book {
    private final String bookId;
    private String title;
    private String author;
    private String genre;
    private int publicationYear;
    private int quantity;
    private int borrowCount;
    private int totalBorrowing;

    public Book(String bookId, String title, String author, String genre, int publicationYear, int quantity) {
        this.bookId = Validator.validateBasicString(bookId);
        setTitle(title);
        setAuthor(author);
        setGenre(genre);
        setPublicationYear(publicationYear);
        setQuantity(quantity);
        this.borrowCount = 0;
        this.totalBorrowing = 0;
    }

    public Book(String bookId, String title, String author, String genre, int publicationYear, int quantity,
                int borrowCount, int totalBorrowing) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.publicationYear = publicationYear;
        this.quantity = quantity;
        this.borrowCount = borrowCount;
        this.totalBorrowing = totalBorrowing;
    }

    public String getBookId() {
        return bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = Validator.validateBasicString(title);
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = Validator.validateBasicString(author);
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = Validator.validateBasicString(genre);
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = Validator.validateNumber(publicationYear);
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = Validator.validateNumber(quantity);
    }

    public int getBorrowCount() {
        return borrowCount;
    }

    public void setBorrowCount(int borrowCount) {
        this.borrowCount = Validator.validateNumber(borrowCount);
    }

    public int getTotalBorrowing() {
        return totalBorrowing;
    }

    public void setTotalBorrowing(int totalBorrowing) {
        this.totalBorrowing = Validator.validateNumber(totalBorrowing);
    }

    public String getStatus() {
        int currentQuantity = quantity - borrowCount;

        // Định nghĩa mã màu ANSI
        String RESET = "\u001B[0m";
        String GREEN = "\u001B[32m"; // Màu xanh lá
        String RED = "\u001B[31m";   // Màu đỏ

        // Trả về chuỗi kèm mã màu trực tiếp
        return (currentQuantity != 0)
                ? GREEN + "● AVAILABLE" + RESET
                : RED + "● UNAVAILABLE" + RESET;
    }

    public void showBookInfo() {
        System.out.printf("| %-10s | %-25s | %-20s | %-15s | %-10d | %-10d | %-10d | %-10d | %-12s |\n",
                bookId, title, author, genre, publicationYear,
                quantity, borrowCount, totalBorrowing, getStatus());
    }

    public void increaseBorrowCount() {
        int tempQuantity = quantity - borrowCount;

        if (tempQuantity <= 0) {
            throw new IllegalArgumentException("❌ This book is currently out of stock.");
        }

        borrowCount ++;
        totalBorrowing ++;
    }

    public void decreaseBorrowCount() {
        if (borrowCount <= 0) {
            throw new IllegalArgumentException("❌ Cannot reduce borrow count. There are no books currently borrowed.");
        }

        borrowCount --;
    }
}