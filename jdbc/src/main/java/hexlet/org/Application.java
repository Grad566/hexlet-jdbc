package hexlet.org;

import java.sql.DriverManager;
import java.sql.SQLException;

public class Application {
    public static void main(String[] args) throws SQLException {
        // Соединение с базой данных тоже нужно отслеживать
        try (var conn = DriverManager.getConnection("jdbc:h2:mem:hexlet_test")) {

            var sql = "CREATE TABLE users (id BIGINT PRIMARY KEY AUTO_INCREMENT, username VARCHAR(255), phone VARCHAR(255))";
            try (var statement = conn.createStatement()) {
                statement.execute(sql);
            }

            var sql2 = "INSERT INTO users (username, phone) VALUES (?, ?)";
            try (var preparedStatement = conn.prepareStatement(sql2)) {
                preparedStatement.setString(1, "Goody");
                preparedStatement.setString(2, "8912389457");
                preparedStatement.executeUpdate();
            }

            var sql3 = "SELECT * FROM users";
            try (var statement3 = conn.createStatement()) {
                var resultSet = statement3.executeQuery(sql3);
                while (resultSet.next()) {
                    System.out.println(resultSet.getString("username"));
                    System.out.println(resultSet.getString("phone"));
                }
            }

            String deleteByUsernameSql = "DELETE FROM users WHERE username = ?";
            try (var deleteByUsernameStatement = conn.prepareStatement(deleteByUsernameSql)) {
                deleteByUsernameStatement.setString(1, "Goody");
                deleteByUsernameStatement.executeUpdate();
            }
        }
    }
}