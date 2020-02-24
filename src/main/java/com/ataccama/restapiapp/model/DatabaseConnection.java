package com.ataccama.restapiapp.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


/**
 * Entity class representing MySQL DB connection details.
 */
@Data
@Entity
@NoArgsConstructor
public class DatabaseConnection {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String hostname;

    private int port;

    private String databaseName;

    private String username;

    private String password;

    public String getConnectionString() {
        StringBuilder st = new StringBuilder();
        st.append("jdbc:mysql://");
        st.append(hostname);
        st.append(":");
        st.append(port);
        st.append("/");
        st.append(databaseName);

        return st.toString();
    }

}
