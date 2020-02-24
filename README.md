
## MySQL Browswer

Available at http://localhost:8080/ . Is provided with provisional HAL browser, that displays some of the REST API endpoints. Does not display the following DatabaseConnectionExtrasController endpoints:

http://localhost:8080/databaseConnections/{connectionId}/schemas
http://localhost:8080/databaseConnections/{connectionId}/tables/{schema}
http://localhost:8080/databaseConnections/{connectionId}/columns/{schema}/{table}
http://localhost:8080/databaseConnections/{connectionId}/preview/{schema}/{table}
http://localhost:8080/databaseConnections/{connectionId}/statistics/{schema}/{table}/{column}")
http://localhost:8080/databaseConnections/{connectionId}/statistics/{schema}/{table}")

### Basic usage

- Navigate to main page at http://localhost:8080.
- To create DB record, make a non-get (POST) request with database connection information 
- To display that record, use "Get" option in databaseConnections row  
- Use above mentioned endpoints to browse selected database!

### Current flaws

- possibility of SQL Injection !!!
- Insufficient commentary
- 0 tests
- use DatabaseCredentails instead of DatabaseConnection naming
- introduce interface instead of implementation
- restrict public DatabaseConnection permissions

### Considarations 

- on requesting data from non-existing schemas / tables, should we return exception or just an empty output?
- add Logging (Lombok)