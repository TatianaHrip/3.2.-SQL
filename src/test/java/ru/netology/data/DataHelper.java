package ru.netology.data;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.*;


public class DataHelper {
    @BeforeEach
    void setUp() throws SQLException {
        Faker faker = new Faker();
        String dataSQL = "INSERT INTO users(login, password) VALUES (?, ?);";

        try (
                Connection conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/app", "app", "pass"
                );
                PreparedStatement dataStmt = conn.prepareStatement(dataSQL);
        ) {
            dataStmt.setString(1, faker.name().username());
            dataStmt.setString(2, "password");
            dataStmt.executeUpdate();
            dataStmt.setString(1, faker.name().username());
            dataStmt.setString(2, "password");
            dataStmt.executeUpdate();
        }
    }

    @Test
    void stubTest() throws SQLException {
        String countSQL = "SELECT COUNT(*) FROM users;";
        String cardsSQL = "SELECT id, number, balance_in_kopecks FROM cards WHERE user_id = ?;";

        try (
                Connection conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/app", "app", "pass"
                );
                Statement countStmt = conn.createStatement();
                PreparedStatement cardsStmt = conn.prepareStatement(cardsSQL);
        ) {
            try (ResultSet rs = countStmt.executeQuery(countSQL)) {
                if (rs.next()) {
                    // выборка значения по индексу столбца (нумерация с 1)
                    int count = rs.getInt(1);
                    // TODO: использовать
                    System.out.println(count);
                }
            }

            cardsStmt.setInt(1, 1);
            try (ResultSet rs = cardsStmt.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String number = rs.getString("number");
                    int balanceInKopecks = rs.getInt("balance_in_kopecks");
                    // TODO: сложить всё в список
                    System.out.println(id + " " + number + " " + balanceInKopecks);
                }
            }
        }
    }
}

