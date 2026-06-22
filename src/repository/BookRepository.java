package repository;

import model.Book;
import service.BookService;

import java.io.*;
import java.util.List;

public class BookRepository {
    private static final String FOLDER = "data";
    private static final String FILE_PATH = "data/Book.txt";

    public void saveOneBook(Book book) {
        File folder = new File(FOLDER);

        if (!folder.exists()) {
            folder.mkdirs();
        }

        try (
                FileWriter fileWriter = new FileWriter(FILE_PATH, true);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        ) {

            String dataLine = convertObjectToString(book);
            bufferedWriter.write(dataLine);
            bufferedWriter.newLine();

        } catch (IOException e) {
            System.out.println("❌ Failed to save Book!");
        }
    }

    public void saveAllBook(List<Book> bookList) {
        File folder = new File(FOLDER);

        if (!folder.exists()) {
            folder.mkdirs();
        }

        try (
                FileWriter fileWriter = new FileWriter(FILE_PATH, false);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        ) {

            for (Book book : bookList) {
                String dataLine = convertObjectToString(book);
                bufferedWriter.write(dataLine);
                bufferedWriter.newLine();
            }

        } catch (IOException e) {
            System.out.println("❌ Failed to save Book!");
        }
    }

    public void loadData(BookService bookService) {
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
                Book book = convertStringToObject(dataLine);
                bookService.addBookFromFile(book);
            }

        } catch (IOException e) {
            System.out.println("⚠️ Warning: Failed to load Book data!");
        }
    }

    private String convertObjectToString(Book book) {
        return book.getBookId() + '|' + book.getTitle() + '|' + book.getAuthor() + '|' + book.getGenre()
                + '|' + book.getPublicationYear() + '|' + book.getQuantity() + '|' + book.getBorrowCount()
                + '|' + book.getTotalBorrowing();
    }

    private Book convertStringToObject(String data) {
        String[] source = data.split("\\|");

        String id = source[0];
        String title = source[1];
        String author = source[2];
        String genre = source[3];
        int year = Integer.parseInt(source[4]);
        int quantity = Integer.parseInt(source[5]);
        int borrowCount = Integer.parseInt(source[6]);
        int totalBorrowing = Integer.parseInt(source[7]);

        return new Book(id, title, author, genre, year, quantity, borrowCount, totalBorrowing);
    }
}