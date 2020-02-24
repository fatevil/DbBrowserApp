package com.ataccama.restapiapp.service;

import com.ataccama.restapiapp.data.DatabaseConnectionSchemaDto;
import com.ataccama.restapiapp.data.DatabaseConnectionTableDto;
import com.ataccama.restapiapp.model.DatabaseConnection;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class DatabaseConnectionService {

    private Connection getConnection(DatabaseConnection databaseConnection) throws SQLException {
        HikariDataSource datasource = new HikariDataSource();
        datasource.setJdbcUrl(databaseConnection.getConnectionString());
        datasource.setUsername(databaseConnection.getUsername());
        datasource.setPassword(databaseConnection.getPassword());

        return datasource.getConnection();
    }

    public List<DatabaseConnectionSchemaDto> getSchemas(DatabaseConnection databaseConnection) {
        try (Connection actualConnection = getConnection(databaseConnection)) {
            DatabaseMetaData dbmd = actualConnection.getMetaData();
            ResultSet schemas = dbmd.getCatalogs();

            List<DatabaseConnectionSchemaDto> list = new ArrayList<>();
            while (schemas.next()) {
                // schema name at index 1
                String schema = schemas.getString(1);
                list.add(new DatabaseConnectionSchemaDto(schema));
            }
            return list;
        } catch (SQLException e) {
            return null;
        }
    }

    public List<DatabaseConnectionTableDto> getTables(DatabaseConnection databaseConnection, String schema) {
        try (Connection actualConnection = getConnection(databaseConnection)) {

            DatabaseMetaData md = actualConnection.getMetaData();
            ResultSet tables = md.getTables(schema, null, "%", null);

            List<DatabaseConnectionTableDto> list = new ArrayList<>();
            while (tables.next()) {
                // table name at index 3
                String tableName = tables.getString(3);
                list.add(new DatabaseConnectionTableDto(tableName));
            }

            return list;
        } catch (SQLException e) {
            return null;
        }
    }


}
