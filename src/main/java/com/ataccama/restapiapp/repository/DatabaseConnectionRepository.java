package com.ataccama.restapiapp.repository;

import com.ataccama.restapiapp.model.DatabaseConnection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DatabaseConnectionRepository extends JpaRepository<DatabaseConnection, Long> {

}
