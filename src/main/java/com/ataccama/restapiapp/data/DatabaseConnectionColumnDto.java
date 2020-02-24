package com.ataccama.restapiapp.data;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DatabaseConnectionColumnDto {

    private final String columnName;

    private final String columnType;

    private final String columnSize;

    private final String decimalDigits;

    private final String isNullable;

    private final String isAutoincrement;

}

