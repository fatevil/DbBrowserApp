package com.ataccama.restapiapp.data;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DatabaseConnectionTableStatisticDto {

    private final String schema;

    private final String table;

    private final String count;

}
