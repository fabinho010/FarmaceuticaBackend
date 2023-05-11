package dao;

import Model.Chip;
import Model.Doctor;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Database {
    private Connection connection;
    private Statement statement;
    private static HikariDataSource dataSource;

    public static Doctor loginDoctor(String email, String password) throws SQLException {
        initDatabaseConnection();
        String query = "SELECT * FROM Doctor where email= ? AND password=?";
        try (Connection connection = dataSource.getConnection()) {
            try (Statement statement = connection.prepareStatement(query)){
                statement.setCursorName(email);
                statement.setCursorName(password);

                ResultSet resultSet = statement.executeQuery(query);

                if (resultSet.next()){
                    String name = resultSet.getString("name");
                    email = resultSet.getString("email");
                    password = resultSet.getNString("password");
                    Date lastLog = resultSet.getDate("lastLog");
                    int session = resultSet.getInt("session");
                    //Adapto mi codigo
                    LocalDateTime lastLogin = lastLog.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                    String sessionString = Integer.toString(session);
                    closeDatabaseConnection();
                    return new Doctor(name,email,password,lastLogin,sessionString);
                }
                closeDatabaseConnection();
            }
            //Si no se encuentra un doctor
            return null;
        }
    }


    private static void initDatabaseConnection() {
        dataSource = new HikariDataSource();
        dataSource.setJdbcUrl("jdbc:maysql://localhost:3307/farmaceutica");
        dataSource.setUsername("root");
        dataSource.setPassword("2200");

    }
    private static void closeDatabaseConnection() {

        dataSource.close();
    }

}
