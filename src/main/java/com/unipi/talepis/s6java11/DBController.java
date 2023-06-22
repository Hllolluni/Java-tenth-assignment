package com.unipi.talepis.s6java11;

import org.springframework.web.bind.annotation.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@RestController
public class DBController {
    private final static String url = "jdbc:sqlite:src/main/resources/DB/s6java11.db";

    @RequestMapping("/allbooks")
    public String findAllBooks() {
        String SQL = "Select * from Book";
        StringBuilder stringBuilder = new StringBuilder();
        try {
            Connection connection = DriverManager.getConnection(url);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL);
            while (resultSet.next()) {
                stringBuilder.append(resultSet.getString("title"))
                        .append(",")
                        .append(resultSet.getString("author"))
                        .append("<br/>");
            }
            statement.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return stringBuilder.toString();
    }

    @GetMapping("/allbooksv2")
    public List<Book> findAllBooksV2() {
        String SQL = "Select * from Book";
        List<Book> books = new ArrayList<>();
        try {
            Connection connection = DriverManager.getConnection(url);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL);
            while (resultSet.next()) {
                Book book = new Book();
                book.setAuthor(resultSet.getString("author"));
                book.setTitle(resultSet.getString("title"));
                book.setDescription(resultSet.getString("description"));
                book.setYear(resultSet.getInt("year"));
                books.add(book);
            }
            statement.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return books;
    }

    @PostMapping("/addbook")
    public Book insertBook(@RequestBody Book book) {
        String SQL = "Insert into Book values (?,?,?,?)";
        try {
            Connection connection = DriverManager.getConnection(url);
            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setString(2, book.getAuthor());
            preparedStatement.setString(3, book.getDescription());
            preparedStatement.setInt(4, book.getYear());
            int result = preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.close();

            if (result == 1) {
                return book;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @PutMapping("/updatebook/{title}")
    public Book updateBook(@PathVariable String title, @RequestBody Book book) {
        String SQL = "UPDATE Book SET title = ? , author = ? , description = ? , year = ? where title like '" + title+"'";

        try {
            Connection connection = DriverManager.getConnection(url);
            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setString(2, book.getAuthor());
            preparedStatement.setString(3, book.getDescription());
            preparedStatement.setInt(4, book.getYear());
            int result = preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.close();

            if (result == 1) {
                return book;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @DeleteMapping("/{title}")
    public String deleteBook(@PathVariable String title) {
        String SQL = "Delete from Book where title like ?";

        try {
            Connection connection = DriverManager.getConnection(url);
            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setString(1, title);
            int result = preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.close();
            if (result == 1){
                return "Book with title: " + title + " deleted!";
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return "No book with this title!";
    }

    @GetMapping("/{title}")
    public Book getBookByTitle(@PathVariable String title) {
        String SQL = "SELECT * from Book where title = '" + title +"'";
        List<Book> books = new ArrayList<>();

        try {
            Connection connection = DriverManager.getConnection(url);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL);

            while (resultSet.next()) {
                Book book = new Book();
                book.setAuthor(resultSet.getString("author"));
                book.setTitle(resultSet.getString("title"));
                book.setDescription(resultSet.getString("description"));
                book.setYear(resultSet.getInt("year"));
                books.add(book);
            }
            if (books.size() >= 1){
                return books.get(0);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
