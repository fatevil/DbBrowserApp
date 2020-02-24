package com.ataccama.restapiapp.data;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DatabaseConnectionColumnStatisticDto {

    private final String schema;

    private final String table;

    private final String column;

    private final String statistic;

    private final String result;

}
