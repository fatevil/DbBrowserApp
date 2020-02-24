package com.ataccama.restapiapp.service;

import com.ataccama.restapiapp.data.DatabaseConnectionColumnStatisticDto;
import com.ataccama.restapiapp.model.DatabaseConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.*;

@Service
public class DatabaseConnectionStatisticsService {

    @Autowired
    private ActualConnectionService actualConnectionService;

    public DatabaseConnectionColumnStatisticDto getColumnStatistic(DatabaseConnection databaseConnection, String schema, String table, String column) throws SQLException {
        Connection actualConnection = actualConnectionService.getConnection(databaseConnection);

        // not ideal, DB should be prevented from having sql injection
        // prepared statement does not allow parametrized table name
        String sql = String.format("SELECT MIN(%s) as min, MAX(%s) as max, AVG(%s) as avg FROM %s", column, column, column, table);
        PreparedStatement statement = actualConnection.prepareStatement(sql);

        ResultSet resultSet = statement.executeQuery();
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();

        resultSet.next();
        String min = resultSet.getString("min");
        String max = resultSet.getString("max");
        String avg = resultSet.getString("avg");
        return DatabaseConnectionColumnStatisticDto.builder()
                .schema(schema)
                .table(table)
                .column(column)
                .min(min)
                .max(max)
                .avg(avg)
                .median("")
                .build();

    }
}
