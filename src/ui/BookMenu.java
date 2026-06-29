package ui;

import model.Book;
import service.BookService;
import utils.ConsoleHelper;
import utils.InputHelper;
import utils.StringUtils;

import java.time.Year;
import java.util.List;
import java.util.function.Consumer;

public class BookMenu {
    private final ConsoleHelper consoleHelper;
    private final InputHelper inputHelper;
    private final BookService bookService;
    private static final int minYear = 1440;
    private static final int currentYear = Year.now().getValue();
    private static final int minQuantity = 1;
    private static final int maxQuantity = 100;

    public BookMenu(ConsoleHelper consoleHelper, InputHelper inputHelper, BookService bookService) {
        this.consoleHelper = consoleHelper;
        this.inputHelper = inputHelper;
        this.bookService = bookService;
    }

    public void showBookMenu() {
        int choice;
        do {
            consoleHelper.clearScreen();
            consoleHelper.bookMenu();

            choice = inputHelper.readIntInRange("👉 Enter your choice: ", 0, 5);

            switch (choice) {
                case 1:
                    addBook();
                    break;
                case 2:
                    deleteBook();
                    break;
                case 3:
                    searchBook();
                    break;
                case 4:
                    updateBook();
                    break;
                case 5:
                    displayAllBook();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("❌ Invalid choice! Please enter a number between 0 and 5.");
            }
        } while (choice != 0);
    }

    private void addBook() {
        char yesNo;
        while (true) {
            consoleHelper.clearScreen();
            consoleHelper.addBookHeading();
            consoleHelper.bookIdNote();
            System.out.println("📌 Please enter the following details:");

            String id = null;
            do {
                id = inputHelper.readBookId("📖 Enter Book ID: ");
            } while (bookService.checkDuplicateId(id));

            String title = inputHelper.readTitle("📝 Enter Book Title: ");
            title = StringUtils.beautify(title);
            String author = inputHelper.readAuthor("✒️ Enter Author Name: ");
            author = StringUtils.beautify(author);
            String genre = inputHelper.readName("🎭 Enter Book Genre: ");
            genre = StringUtils.beautify(genre);
            int year = inputHelper.readIntInRange("⏳ Enter Publication Year: ", minYear, currentYear);
            int quantity = inputHelper.readIntInRange("📦 Enter Quantity: ", minQuantity, maxQuantity);

            try {
                Book book = new Book(id, title, author, genre, year, quantity);
                bookService.addBook(book);
                System.out.println("\n✨ Successfully added Book!");

                yesNo = inputHelper.readYesNo("🔄 Do you want to add another Book (Y/N): ");

                if (yesNo == 'Y') {
                    continue;
                } else {
                    break;
                }
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());

                yesNo = inputHelper.readYesNo("🔄 Do you want to add another Book (Y/N): ");

                if (yesNo == 'Y') {
                    continue;
                } else {
                    break;
                }
            }
        }
    }

    private void deleteBook() {
        char yesNo;
        while (true) {
            consoleHelper.clearScreen();
            consoleHelper.deleteMemberHeading();
            consoleHelper.bookIdNote();

            String id = inputHelper.readBookId("🆔 Enter Book ID to delete: ");

            Book book = bookService.findBookById(id);

            if (book == null) {
                System.out.println("⚠️ Book not found with ID: " + id);

                yesNo = inputHelper.readYesNo("🔄 Do you want to delete another book (Y/N): ");

                if (yesNo == 'Y') {
                    continue;
                } else {
                    break;
                }
            }

            System.out.println("✅ Book found!");
            book.showBookInfo();
            System.out.println("📚━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━📚");

            yesNo = inputHelper.readYesNo("🔄 Do you want to delete this book (Y/N): ");

            if (yesNo == 'Y') {
                bookService.deleteBook(id);
                System.out.println("🗑️  Book deleted successfully!");

                yesNo = inputHelper.readYesNo("🔄 Do you want to delete another book (Y/N): ");

                if (yesNo == 'Y') {
                    continue;
                } else {
                    break;
                }
            } else {
                yesNo = inputHelper.readYesNo("🔄 Do you want to delete another book (Y/N): ");

                if (yesNo == 'Y') {
                    continue;
                } else {
                    break;
                }
            }
        }
    }

    private void searchBook() {
        int choice;
        do {
            consoleHelper.clearScreen();
            consoleHelper.searchBookMenu();

            choice = inputHelper.readIntInRange("💻 Enter your choice: ", 0, 3);

            switch (choice) {
                case 1:
                    searchByTitle();
                    break;
                case 2:
                    searchByAuthor();
                    break;
                case 3:
                    searchByGenre();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("❌ Invalid choice! Please enter a number between 0 and 3.");
            }
        } while (choice != 0);
    }

    private void searchByTitle() {
        char yesNo;
        while (true) {
            consoleHelper.clearScreen();
            consoleHelper.searchByTitleHeading();

            String title = inputHelper.readTitle("🔍 Enter Book Title to search: ");
            title = StringUtils.beautify(title);

            List<Book> bookList = bookService.findBookByTitle(title);

            if (bookList == null) {
                System.out.println("❌ No book matches the title: " + title);
                yesNo = inputHelper.readYesNo("🔄 Do you want to search another book (Y/N): ");

                if (yesNo == 'Y') {
                    continue;
                } else {
                    break;
                }
            }

            System.out.println("✅ Book found!");
            Consumer<Book> bookConsumer = (book) -> {
                book.showBookInfo();
                System.out.println("📚━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━📚");
            };
            bookList.forEach(bookConsumer);

            yesNo = inputHelper.readYesNo("🔄 Do you want to search another book (Y/N): ");

            if (yesNo == 'Y') {
                continue;
            } else {
                break;
            }
        }
    }

    private void searchByAuthor() {
        char yesNo;
        while (true) {
            consoleHelper.clearScreen();
            consoleHelper.searchByAuthorHeading();

            String author = inputHelper.readAuthor("🔍 Enter Author Name to search: ");
            author = StringUtils.beautify(author);

            List<Book> bookList = bookService.findBookByAuthor(author);

            if (bookList == null) {
                System.out.println("❌ No book matches the author name: " + author);

                yesNo = inputHelper.readYesNo("🔄 Do you want to search another book (Y/N): ");

                if (yesNo == 'Y') {
                    continue;
                } else {
                    break;
                }
            }

            System.out.println("✅ Book found!");
            Consumer<Book> consumer = (book) -> {
                book.showBookInfo();
                System.out.println("📚━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━📚");
            };
            bookList.forEach(consumer);

            yesNo = inputHelper.readYesNo("🔄 Do you want to search another book (Y/N): ");

            if (yesNo == 'Y') {
                continue;
            } else {
                break;
            }
        }
    }

    private void searchByGenre() {
        char yesNo;
        while (true) {
            consoleHelper.clearScreen();
            consoleHelper.searchByGenreHeading();

            String genre = inputHelper.readName("🔍 Enter Genre to search: ");
            genre = StringUtils.beautify(genre);

            List<Book> bookList = bookService.findBookByGenre(genre);

            if (bookList == null) {
                System.out.println("❌ No book matches the genre: " + genre);

                yesNo = inputHelper.readYesNo("🔄 Do you want to search another book (Y/N): ");

                if (yesNo == 'Y') {
                    continue;
                } else {
                    break;
                }
            }

            System.out.println("✅ Book found!");
            Consumer<Book> consumer = (book) -> {
                book.showBookInfo();
                System.out.println("📚━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━📚");
            };
            bookList.forEach(consumer);

            yesNo = inputHelper.readYesNo("🔄 Do you want to search another book (Y/N): ");

            if (yesNo == 'Y') {
                continue;
            } else {
                break;
            }
        }
    }

    private void updateBook() {
        char yesNo;
        while (true) {
            consoleHelper.clearScreen();
            consoleHelper.updateBookHeading();
            consoleHelper.bookIdNote();

            String id = inputHelper.readBookId("👉 Enter Book ID to update: ");

            Book book = bookService.findBookById(id);

            if (book == null) {
                System.out.println("⚠️ Book not found with ID: " + id);

                yesNo = inputHelper.readYesNo("🔄 Do you want to update another book (Y/N): ");

                if (yesNo == 'Y') {
                    continue;
                } else {
                    break;
                }
            }

            System.out.println("✅ Book found!");
            book.showBookInfo();
            System.out.println("📚━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━📚");

            yesNo = inputHelper.readYesNo("🔄 Do you want to update this book (Y/N): ");

            if (yesNo == 'Y') {
                supportUpdate(book);
                System.out.printf("✨ All Changes For Book Saved Successfully!\n");

                yesNo = inputHelper.readYesNo("🔄 Do you want to update another book (Y/N): ");

                if (yesNo == 'Y') {
                    continue;
                } else {
                    break;
                }
            } else {
                yesNo = inputHelper.readYesNo("🔄 Do you want to update another book (Y/N): ");

                if (yesNo == 'Y') {
                    continue;
                } else {
                    break;
                }
            }
        }
    }

    private void supportUpdate(Book book) {
        int choice;
        do {
            consoleHelper.clearScreen();
            consoleHelper.updateBookMenu();

            choice = inputHelper.readIntInRange("💻 Enter your choice to update: ", 0, 5);

            switch (choice) {
                case 1:
                    updateTitle(book);
                    break;
                case 2:
                    updateAuthor(book);
                    break;
                case 3:
                    updateGenre(book);
                    break;
                case 4:
                    updateYear(book);
                    break;
                case 5:
                    updateQuantity(book);
                    break;
                case 0:
                    break;
                default:
                    System.out.println("❌ Invalid choice! Please enter a number between 0 and 5.");
            }
        } while (choice != 0);
    }

    private void updateTitle(Book book) {
        char yesNo;
        while (true) {
            consoleHelper.clearScreen();
            consoleHelper.updateBookTitleHeading();

            String oldTitle = book.getTitle();
            System.out.println("✨ Current Book Title: " + oldTitle);

            String newTitle = inputHelper.readTitle("👉 Enter new title to update: ");
            newTitle = StringUtils.beautify(newTitle);

            try {
                bookService.updateTitle(book, newTitle);
                System.out.printf("🎉 Successfully Updated Book: %s ➔ %s\n", oldTitle, newTitle);
                consoleHelper.pause();
                break;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());

                yesNo = inputHelper.readYesNo("🔄 Do you want to update another title (Y/N): ");

                if (yesNo == 'Y') {
                    continue;
                } else {
                    break;
                }
            }
        }
    }

    private void updateAuthor(Book book) {
        char yesNo;
        while (true) {
            consoleHelper.clearScreen();
            consoleHelper.updateBookAuthorHeading();

            String oldAuthor = book.getAuthor();
            System.out.println("✨ Current Author Name: " + oldAuthor);

            String newAuthor = inputHelper.readAuthor("👉 Enter new author name to update: ");
            newAuthor = StringUtils.beautify(newAuthor);

            try {
                bookService.updateAuthor(book, newAuthor);
                System.out.printf("🎉 Successfully Updated Book: %s ➔ %s\n", oldAuthor, newAuthor);
                consoleHelper.pause();
                break;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());

                yesNo = inputHelper.readYesNo("🔄 Do you want to update another author (Y/N): ");

                if (yesNo == 'Y') {
                    continue;
                } else {
                    break;
                }
            }
        }
    }

    private void updateGenre(Book book) {
        consoleHelper.clearScreen();
        consoleHelper.updateBookGenreHeading();

        String oldGenre = book.getGenre();
        System.out.println("✨ Current Book Genre: " + oldGenre);

        String newGenre = inputHelper.readName("👉 Enter new genre to update: ");
        newGenre = StringUtils.beautify(newGenre);

        bookService.updateGenre(book, newGenre);
        System.out.printf("🎉 Successfully Updated Book: %s ➔ %s\n", oldGenre, newGenre);
        consoleHelper.pause();
    }

    private void updateYear(Book book) {
        char yesNo;
        while (true) {
            consoleHelper.clearScreen();
            consoleHelper.updateBookYearHeading();

            int oldYear = book.getPublicationYear();
            System.out.println("✨ Current Publication Year: " + oldYear);

            int newYear = inputHelper.readIntInRange("👉 Enter new publication year to update: ",
                    minYear, currentYear);

            try {
                bookService.updateYear(book, newYear);
                System.out.printf("🎉 Successfully Updated Book: %s ➔ %s\n", oldYear, newYear);
                consoleHelper.pause();
                break;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());

                yesNo = inputHelper.readYesNo("🔄 Do you want to update another publication year (Y/N): ");

                if (yesNo == 'Y') {
                    continue;
                } else {
                    break;
                }
            }
        }
    }

    private void updateQuantity(Book book) {
        char yesNo;
        while (true) {
            consoleHelper.clearScreen();
            consoleHelper.updateBookQuantityHeading();

            int oldQuantity = book.getQuantity();
            System.out.printf("   🔢 Current Total Quantity : %d\n", oldQuantity);
            System.out.printf("   🔄 Currently Borrowed     : %d\n", book.getBorrowCount());
            System.out.println("   [ERROR] Cannot reduce total quantity below the number   ");
            System.out.println("           of currently borrowed books!                    ");
            System.out.println("──────────────────────────────────────────────────────────");

            int newQuantity = inputHelper.readIntInRange("👉 Enter new quantity to update: ",
                    minQuantity, maxQuantity);

            try {
                bookService.updateQuantity(book, newQuantity);
                System.out.printf("🎉 Successfully Updated Book: %s ➔ %s\n", oldQuantity, newQuantity);
                consoleHelper.pause();
                break;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());

                yesNo = inputHelper.readYesNo("🔄 Do you want to update another quantity (Y/N): ");

                if (yesNo == 'Y') {
                    continue;
                } else {
                    break;
                }
            }
        }
    }

    private void displayAllBook() {
        consoleHelper.clearScreen();
        consoleHelper.displayBookHeading();

        bookService.showAllBook();
        consoleHelper.pause();
    }
}
