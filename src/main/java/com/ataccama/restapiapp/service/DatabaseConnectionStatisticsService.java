package com.ataccama.restapiapp.service;

import com.ataccama.restapiapp.data.ColumnStatisticType;
import com.ataccama.restapiapp.data.DatabaseConnectionColumnStatisticDto;
import com.ataccama.restapiapp.model.DatabaseConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.*;

@Service
public class DatabaseConnectionStatisticsService {

    @Autowired
    private ActualConnectionService actualConnectionService;

    public DatabaseConnectionColumnStatisticDto getColumnStatistic(DatabaseConnection databaseConnection, String schema, String table, String column, ColumnStatisticType statisticType) throws SQLException {
        Connection actualConnection = actualConnectionService.getConnection(databaseConnection);

        // not ideal, DB should be prevented from having sql injection
        // prepared statement does not allow parametrized table name
        String sql = String.format("SELECT %s(?) FROM %s", statisticType.name(), table);
        PreparedStatement statement = actualConnection.prepareStatement(sql);
        statement.setString(1, column);

        ResultSet resultSet = statement.executeQuery();
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();

        resultSet.next();
        String result = String.valueOf(resultSet.getDouble(1));
        return DatabaseConnectionColumnStatisticDto.builder()
                .schema(schema)
                .table(table)
                .column(column)
                .statistic(statisticType.name())
                .result(result)
                .build();

    }
}
