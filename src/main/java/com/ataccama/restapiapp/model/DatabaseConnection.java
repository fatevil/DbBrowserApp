package com.ataccama.restapiapp.model;

import lombok.Data;

import javax.persistence.Entity;


/**
 * Entity class representing MySQL DB connection details.
 */
@Data
@Entity
public class DatabaseConnection {

    private final String name;

    private final String hostname;

    private final int port;

    private final String databaseName;

    private final String username;

    private final String password;

}
