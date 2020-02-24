package com.ataccama.restapiapp.controller;

import com.ataccama.restapiapp.data.*;
import com.ataccama.restapiapp.exception.DatabaseConnectionNotFoundException;
import com.ataccama.restapiapp.model.DatabaseConnection;
import com.ataccama.restapiapp.repository.DatabaseConnectionRepository;
import com.ataccama.restapiapp.service.DatabaseConnectionService;
import com.ataccama.restapiapp.service.DatabaseConnectionStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
public class DatabaseConnectionExtrasController {

    // add endpoints to HAL Browser https://stackoverflow.com/questions/34285829/spring-data-rest-how-to-expose-custom-rest-controller-method-in-the-hal-browse

    @Autowired
    private DatabaseConnectionRepository repository;

    @Autowired
    private DatabaseConnectionService databaseConnectionService;

    @Autowired
    private DatabaseConnectionStatisticsService statisticsService;

    @GetMapping("/databaseConnections/{connectionId}/schemas")
    public List<DatabaseConnectionSchemaDto> getSchemas(@PathVariable Long connectionId) throws SQLException {

        DatabaseConnection databaseConnection = repository
                .findById(connectionId)
                .orElseThrow(() -> new DatabaseConnectionNotFoundException(connectionId));

        return databaseConnectionService.getSchemas(databaseConnection);
    }

    @GetMapping("/databaseConnections/{connectionId}/tables/{schema}")
    public List<DatabaseConnectionTableDto> getColumns(@PathVariable Long connectionId, @PathVariable String schema) throws SQLException {

        DatabaseConnection databaseConnection = repository
                .findById(connectionId)
                .orElseThrow(() -> new DatabaseConnectionNotFoundException(connectionId));

        return databaseConnectionService.getTables(databaseConnection, schema);
    }

    @GetMapping("/databaseConnections/{connectionId}/columns/{schema}/{table}")
    public List<DatabaseConnectionColumnDto> getColumns(@PathVariable Long connectionId, @PathVariable String schema, @PathVariable String table) throws SQLException {

        DatabaseConnection databaseConnection = repository
                .findById(connectionId)
                .orElseThrow(() -> new DatabaseConnectionNotFoundException(connectionId));

        Map<String, ForeignKey> foreignKeys = databaseConnectionService.getForeignKeys(databaseConnection, schema, table);
        Set<String> primaryKeys = databaseConnectionService.getPrimaryKeys(databaseConnection, schema, table);

        return databaseConnectionService.getColumns(databaseConnection, schema, table, primaryKeys, foreignKeys);
    }

    @GetMapping("/databaseConnections/{connectionId}/preview/{schema}/{table}")
    public String getDataPreview(@PathVariable Long connectionId, @PathVariable String schema, @PathVariable String table) throws SQLException {

        DatabaseConnection databaseConnection = repository
                .findById(connectionId)
                .orElseThrow(() -> new DatabaseConnectionNotFoundException(connectionId));

        return databaseConnectionService.getDataPreview(databaseConnection, schema, table);
    }

    @GetMapping("/databaseConnections/{connectionId}/statistics/{schema}/{table}/{column}")
    public DatabaseConnectionColumnStatisticDto getColumnStatistics(@PathVariable Long connectionId, @PathVariable String schema, @PathVariable String table, @PathVariable String column) throws SQLException {

        DatabaseConnection databaseConnection = repository
                .findById(connectionId)
                .orElseThrow(() -> new DatabaseConnectionNotFoundException(connectionId));

        return statisticsService.getTableStatistic(databaseConnection, schema, table, column);
    }

    @GetMapping("/databaseConnections/{connectionId}/statistics/{schema}/{table}")
    public DatabaseConnectionTableStatisticDto getTableStatistics(@PathVariable Long connectionId, @PathVariable String schema, @PathVariable String table) throws SQLException {

        DatabaseConnection databaseConnection = repository
                .findById(connectionId)
                .orElseThrow(() -> new DatabaseConnectionNotFoundException(connectionId));

        return statisticsService.getTableStatistic(databaseConnection, schema, table);
    }


}
