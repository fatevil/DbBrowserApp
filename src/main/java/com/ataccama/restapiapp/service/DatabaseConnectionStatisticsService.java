package com.ataccama.restapiapp.service;

import com.ataccama.restapiapp.data.DatabaseConnectionColumnStatisticDto;
import com.ataccama.restapiapp.data.DatabaseConnectionTableStatisticDto;
import com.ataccama.restapiapp.model.DatabaseConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.*;

@Service
public class DatabaseConnectionStatisticsService {

    @Autowired
    private ActualConnectionService actualConnectionService;

    public DatabaseConnectionColumnStatisticDto getTableStatistic(DatabaseConnection databaseConnection, String schema, String table, String column) throws SQLException {
        Connection actualConnection = actualConnectionService.getConnection(databaseConnection);

        // get the overall count
        // select table ordered by the specific column
        // take the first record with offset of half of the overall count

        String sql = String.format("SELECT MIN(%s) as min, MAX(%s) as max, AVG(%s) as avg FROM %s", column, column, column, table);
        PreparedStatement statement = actualConnection.prepareStatement(sql);

        ResultSet resultSet = statement.executeQuery();
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
                .median("unimplemented")
                .build();
    }

    public DatabaseConnectionTableStatisticDto getTableStatistic(DatabaseConnection databaseConnection, String schema, String table) throws SQLException {
            Connection actualConnection = actualConnectionService.getConnection(databaseConnection);
            String sql = String.format("SELECT COUNT(*) as count FROM %s", table);
            PreparedStatement statement = actualConnection.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            String count = resultSet.getString("count");
            return DatabaseConnectionTableStatisticDto.builder()
                    .schema(schema)
                    .table(table)
                    .count(count)
                    .build();
        }
}
