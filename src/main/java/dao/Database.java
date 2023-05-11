package dao;

import Model.Chip;
import Model.Doctor;
import com.zaxxer.hikari.HikariConfig;
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

    public  Doctor loginDoctor(String email, String password) throws SQLException {
            /*
            public resulSet loadSelect(Strinng Query){
            ResultSet rs
            rs = null;
            try{
                rs= st.executeQuey(query)
            }catch(SQL Exception){
            dout error bbbd
            }
            return st
            */
            Database db = new Database();
            db.initDatabaseConnection();
            String query = "SELECT * FROM doctor;";
            System.out.println("conexion establecida");
            try {
                ResultSet resultSet = db.statement.executeQuery(query);
                System.out.println("Estoy dentro");
                while (resultSet.next()){
                     email = resultSet.getString("mail");
                    password = resultSet.getString("pass");

                    System.out.println("email: " + email);
                    System.out.println("gender: " + password);
                    System.out.println("--------------------");
                }
                resultSet.close();

            }catch (SQLException e){
                System.out.println("Error en la query" + e.getMessage());
            }

            db.closeDatabaseConnection();
        /*try (Connection connection = dataSource.getConnection()) {
            System.out.println("Reading data....");
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
                    return new Doctor(name,email,password,lastLogin,session);
                }
                closeDatabaseConnection();
            }
            //Si no se encuentra un doctor
            return null;
        }*/
        return null;
    }

    /*
    public void  updateDoctor(String Qquery){

        try{
        }catch sql{
        sout --> 
        }
    }
    * */


    private void initDatabaseConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch (ClassNotFoundException e){
            System.out.println("Error en BBDD.conectat.Class " + e.getMessage());
        }
        connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/farmacia","root","2200");
        }catch (SQLException e){
            System.out.println("Error BBDD.conectar.Connection " + e.getMessage());
        }
        statement = null;
        try {
            statement = connection.createStatement();
        }catch (SQLException e){
            System.out.println("BBDD.conectar.statement " + e.getMessage());
        }
    }
    private  void closeDatabaseConnection() {
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
            System.out.println("Error al cerrar el objeto Statement: " + e.getMessage());
        }

        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println("Error al cerrar la conexión: " + e.getMessage());
        }
    }

}
