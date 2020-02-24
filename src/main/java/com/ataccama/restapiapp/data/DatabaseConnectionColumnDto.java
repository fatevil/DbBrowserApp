package com.ataccama.restapiapp.data;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class DatabaseConnectionColumnDto {

    private final String columnName;

    private final String columnType;

    private final String columnSize;

    private final String decimalDigits;

    private final String isNullable;

    private final String isAutoincrement;

    private final boolean isPrimaryKey;

    private final String foreignKeyTable;

    private final String foreignKeyName;

}

