package com.ataccama.restapiapp.exception;

public class DatabaseConnectionNotFoundException extends RuntimeException {

    public DatabaseConnectionNotFoundException(Long id) {
        super("DatabaseConnection of ID " + id + " not found.");
    }
}
