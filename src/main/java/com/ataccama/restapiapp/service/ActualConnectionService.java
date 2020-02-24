package com.ataccama.restapiapp.service;

import com.ataccama.restapiapp.model.DatabaseConnection;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Service
public class ActualConnectionService {

    private Map<Long, Connection> map = new HashMap<>();

    public synchronized Connection getConnection(DatabaseConnection databaseConnection) throws SQLException {
        if (map.containsKey(databaseConnection.getId())) {
            Connection connection = map.get(databaseConnection.getId());
            if (!connection.isClosed()) {
                return connection;
            }
        }

        HikariDataSource datasource = new HikariDataSource();
        datasource.setJdbcUrl(databaseConnection.getConnectionString());
        datasource.setUsername(databaseConnection.getUsername());
        datasource.setPassword(databaseConnection.getPassword());

        datasource.setConnectionTimeout(2000);

        Connection connection = datasource.getConnection();
        map.put(databaseConnection.getId(), connection);
        return connection;
    }

}
