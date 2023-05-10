package dao;

import com.zaxxer.hikari.HikariDataSource;

public class Database {
    private static HikariDataSource dataSource;

    public static void main(String[] args) {
        try{
            initDatabaseConnection();
        } finally {
            closeDatabaseConnection();
        }

    }


    private static void initDatabaseConnection() {
        dataSource = new HikariDataSource();
        dataSource.setJdbcUrl("jdbc:mariadb://localhost:3307/farmaceutica");
        dataSource.setUsername("root");
        dataSource.setPassword("2200");

    }
    private static void closeDatabaseConnection() {

        dataSource.close();
    }

}
