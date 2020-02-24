package com.ataccama.restapiapp.controller;

import com.ataccama.restapiapp.data.DatabaseConnectionColumnDto;
import com.ataccama.restapiapp.data.DatabaseConnectionSchemaDto;
import com.ataccama.restapiapp.data.DatabaseConnectionTableDto;
import com.ataccama.restapiapp.data.ForeignKey;
import com.ataccama.restapiapp.exception.DatabaseConnectionNotFoundException;
import com.ataccama.restapiapp.model.DatabaseConnection;
import com.ataccama.restapiapp.repository.DatabaseConnectionRepository;
import com.ataccama.restapiapp.service.DatabaseConnectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
public class DatabaseConnectionExtrasController {

    @Autowired
    private DatabaseConnectionRepository repository;

    @Autowired
    private DatabaseConnectionService service;

    @GetMapping("/databaseConnections/{connectionId}/schemas")
    public List<DatabaseConnectionSchemaDto> getSchemas(@PathVariable Long connectionId) {

        DatabaseConnection databaseConnection = repository
                .findById(connectionId)
                .orElseThrow(() -> new DatabaseConnectionNotFoundException(connectionId));

        return service.getSchemas(databaseConnection);
    }

    @GetMapping("/databaseConnections/{connectionId}/tables/{schema}")
    public List<DatabaseConnectionTableDto> getTables(@PathVariable Long connectionId, @PathVariable String schema) {

        DatabaseConnection databaseConnection = repository
                .findById(connectionId)
                .orElseThrow(() -> new DatabaseConnectionNotFoundException(connectionId));

        return service.getTables(databaseConnection, schema);
    }

    @GetMapping("/databaseConnections/{connectionId}/columns/{schema}/{table}")
    public List<DatabaseConnectionColumnDto> getTables(@PathVariable Long connectionId, @PathVariable String schema, @PathVariable String table) {

        DatabaseConnection databaseConnection = repository
                .findById(connectionId)
                .orElseThrow(() -> new DatabaseConnectionNotFoundException(connectionId));

        Map<String, ForeignKey> foreignKeys = service.getForeignKeys(databaseConnection, schema, table);
        Set<String> primaryKeys = service.getPrimaryKeys(databaseConnection, schema, table);

        return service.getColumnInfo(databaseConnection, schema, table, primaryKeys, foreignKeys);
    }

    @GetMapping("/databaseConnections/{connectionId}/preview/{schema}/{table}")
    public String getDataPreview(@PathVariable Long connectionId, @PathVariable String schema, @PathVariable String table) {

        DatabaseConnection databaseConnection = repository
                .findById(connectionId)
                .orElseThrow(() -> new DatabaseConnectionNotFoundException(connectionId));

        return service.getDataPreview(databaseConnection, schema, table);
    }

}
