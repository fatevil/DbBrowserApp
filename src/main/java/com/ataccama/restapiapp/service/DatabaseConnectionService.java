package com.ataccama.restapiapp.service;

import com.ataccama.restapiapp.data.DatabaseConnectionColumnDto;
import com.ataccama.restapiapp.data.DatabaseConnectionSchemaDto;
import com.ataccama.restapiapp.data.DatabaseConnectionTableDto;
import com.ataccama.restapiapp.data.ForeignKey;
import com.ataccama.restapiapp.model.DatabaseConnection;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

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

            DatabaseMetaData metadata = actualConnection.getMetaData();
            ResultSet tables = metadata.getTables(schema, null, "%", null);

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

    public List<DatabaseConnectionColumnDto> getColumnInfo(DatabaseConnection databaseConnection, String schema, String table, Set<String> primaryKeys, Map<String, ForeignKey> foreignKeys) {
        try (Connection actualConnection = getConnection(databaseConnection)) {
            DatabaseMetaData metadata = actualConnection.getMetaData();
            ResultSet columns = metadata.getColumns(schema, null, table, null);

            List<DatabaseConnectionColumnDto> list = new ArrayList<>();
            while (columns.next()) {
                String columnName = columns.getString("COLUMN_NAME");
                String foreignKeyParentName = foreignKeys.containsKey(columnName) ? foreignKeys.get(columnName).getPrimaryKey() : null;
                String foreignKeyParentTable = foreignKeys.containsKey(columnName) ? foreignKeys.get(columnName).getPrimaryTable() : null;

                DatabaseConnectionColumnDto dto = DatabaseConnectionColumnDto.builder()
                        .columnName(columnName)
                        .columnType(columns.getString("TYPE_NAME"))
                        .columnSize(columns.getString("COLUMN_SIZE"))
                        .decimalDigits(columns.getString("DECIMAL_DIGITS"))
                        .isNullable(columns.getString("IS_NULLABLE"))
                        .isAutoincrement(columns.getString("IS_AUTOINCREMENT"))
                        .isPrimaryKey(primaryKeys.contains(columnName))
                        .foreignKeyName(foreignKeyParentName)
                        .foreignKeyTable(foreignKeyParentTable)
                        .build();

                list.add(dto);

                getPrimaryKeys(databaseConnection, schema, table);
            }

            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Set<String> getPrimaryKeys(DatabaseConnection databaseConnection, String schema, String table) {
        try (Connection actualConnection = getConnection(databaseConnection)) {

            DatabaseMetaData metadata = actualConnection.getMetaData();
            ResultSet resultSet = metadata.getPrimaryKeys(null, schema, table);

            Set<String> list = new HashSet<>();
            while (resultSet.next()) {

                String column = resultSet.getString("COLUMN_NAME");
                list.add(column);
            }

            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Map<String, ForeignKey> getForeignKeys(DatabaseConnection databaseConnection, String schema, String table) {
        try (Connection actualConnection = getConnection(databaseConnection)) {

            DatabaseMetaData metadata = actualConnection.getMetaData();
            ResultSet resultSet = metadata.getImportedKeys(null, schema, table);

            Map<String, ForeignKey> map = new HashMap<>();
            while (resultSet.next()) {

                String fkColumnName = resultSet.getString("FKCOLUMN_NAME");
                String pkColumnName = resultSet.getString("PKCOLUMN_NAME");
                String pkTableName = resultSet.getString("PKTABLE_NAME");

                map.put(fkColumnName, new ForeignKey(pkColumnName, pkTableName));
            }

            return map;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
