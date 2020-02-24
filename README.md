
Available at http://localhost:8080/ . Is provided with provisional HAL browser, that displays some of the REST API endpoints. 

Does not display DatabaseConnectionExtrasController endpoints, which are:

http://localhost:8080/databaseConnections/{connectionId}/schemas
http://localhost:8080/databaseConnections/{connectionId}/tables/{schema}
http://localhost:8080/databaseConnections/{connectionId}/columns/{schema}/{table}
http://localhost:8080/databaseConnections/{connectionId}/preview/{schema}/{table}

### Basic usage

- Navigate to main page at http://localhost:8080.
- To create DB record, make a non-get (POST) request with database connection information 
- To display that record, use "Get" option in databaseConnections row  
- Use above mentioned endpoints to browse selected database!

### Current flaws

- Too many connections Exception (fix by reusing the connections)
- possibility SQL Injection
- Error code handling
- Insufficient SQLException handling at many cases