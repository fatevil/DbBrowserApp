package com.ataccama.restapiapp.data;

import lombok.Data;

@Data
public class ForeignKey {

    private final String primaryKey;

    private final String primaryTable;

}
