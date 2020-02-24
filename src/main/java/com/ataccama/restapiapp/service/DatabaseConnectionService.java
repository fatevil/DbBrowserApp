package com.ataccama.restapiapp.service;

import com.ataccama.restapiapp.model.DatabaseConnection;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.SQLException;

@Service
public class DatabaseConnectionService {

    private Connection getConnection(DatabaseConnection databaseConnection) throws SQLException {
        HikariDataSource datasource = new HikariDataSource();
        datasource.setJdbcUrl(databaseConnection.getConnectionString());
        datasource.setUsername(databaseConnection.getUsername());
        datasource.setPassword(databaseConnection.getPassword());

        return datasource.getConnection();
    }

}
