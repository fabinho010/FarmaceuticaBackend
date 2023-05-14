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

    //Metdodo que me ejecuta la query en la base de datos
    public ResultSet loadSelect(String query) {
        ResultSet rs = null;
        Statement st;
        try {
            st = connection.createStatement();
            rs = st.executeQuery(query);
        } catch (SQLException e) {
            System.out.println("Error en Database.loadSelect: " + e.getMessage());
        }

        // Verificar si no hay filas seleccionadas
        boolean hasRows;
        try {
            hasRows = rs != null && rs.next();
        } catch (SQLException e) {
            System.out.println("Error al verificar filas en ResultSet: " + e.getMessage());
            hasRows = false;
        }

        if (!hasRows) {
            System.out.println("SIN RESULTADOS");
        }

        return rs;
    }


    //Metoso que me ejecuta los updates
        public void loadUpdate(String query){
            ResultSet rs;
            Statement st;
            rs = null;
            try {
                st = connection.createStatement();
               int rowsAffected = st.executeUpdate(query);
            } catch (SQLException e) {
                System.out.println("Error a Database.loadUpdate" + e.getMessage());
            }
        }



    public void initDatabaseConnection() {
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
    public   void closeDatabaseConnection() {
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
