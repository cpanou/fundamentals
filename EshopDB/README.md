# Introduction to Databases

Extends The EshopJAXRS project to connect to a Database

1. **DBMS - Data Models section**

    **Entity** − An entity is a real-world entity having properties called attributes. For example, in a our database, a User is considered as an entity. Users has various attributes like username, email, firstname, etc.

    **Relationship** − The logical association among entities is called a relationship. Relationships are mapped with entities in various ways. Mapping cardinalities define the number of associations between two entities.

2. **DBMS - ER Model Basic Concepts**

    Entity-Set and Keys:
    - **Super Key** − A set of attributes (one or more) that collectively identifies an entity in an entity set.
    - **Candidate Key** − A minimal super key is called a candidate key. An entity set may have more than one candidate key.
    - **Primary Key** − A primary key is one of the candidate keys chosen by the database designer to uniquely identify the entity set.
    - **Foreign Key** - A Foreign key is a key that links to primary keys in other tables, thereby creating a relationship

3. **DBMS - ER Diagram Representation**

    Relationship Cardinalities:
    - **One-to-one:** 
    When a record of an entity relates to exaclty one record of another entity. To represent the Relationship both entities inherit the Primary key of the other as a Foreign key.
    - **One-to-many** ( User - Order )
    When a record of an entity relates to many records of another entity. To represent the Relationship the entity in the "Many" part of the relationship inherits the Primary key of the other as a Foreign key.
    - **Many-to-one** ( Order - User )
    When many records of an entity relate to one record of another entity. To represent the Relationship the entity in the "Many" part of the relationship inherits the Primary key of the other as a Foreign key.
    - **Many-to-many** ( Order - Prouct )
    When a record of an entity relates to many records of another entity and vice verca. To represent the Relationship we create a new Entity that inherits both Primary keys as Foreign keys.


4. **SQL Basics:**

    **INSERT** - Inserts a row ( entry, record ) to a database table
      ```SQL
      INSERT INTO table-name (column-names) 
      VALUES (values)
      ```
    **SELECT** - Retriveves rows from the database in a result-set
      ```SQL
      SELECT column-names 
      FROM table-name
      ```
    **UPDATE** - Updates existing records in a database
      ```SQL  
      UPDATE table-name
      SET column-name = value, column-name = value, ...
      WHERE condition
      ```
    **DELETE** - Removes records from a database table
      ```SQL
      DELETE table-name 
      WHERE condition
      ```
5. **Setting up Walkthrough:**

    - Download [MySQL installer](https://dev.mysql.com/downloads/installer/)

    - Choose custom installation and then from the available products choose:
        - MySQL Server recommended version 5.7
        - MySQL Workbench
        - MySQL Notifier ( Optional but handy )
        - MySQL Shell
        - Connector/ODBC
        - Connector/J

    - Steps to create the eshop database:

        1. Open MySQL Workbench
        2. Select the default Connection ( Local Instance MySQL57 - the name depends on server version )
        3. Select File > New Query Tab
        4. Copy the contents of EshopDB/docs/create_schema.sql into the new Tab
        5. Execute the script


6. **JDBC:**

   **JDBC API:** This provides the application-to-JDBC Manager connection.<br/>
   **JDBC Driver API:** This supports the JDBC Manager-to-Driver Connection.<br/>
    (see /docs/jdbc-architecture.jpg)

    - **Connection**
        1. Import JDBC Packages: Add import statements to your Java program to import required classes in your Java code.
        2. Register JDBC Driver: This step causes the JVM to load the desired driver implementation into memory so it can fulfill your JDBC requests.
            ```Java
            public static void registerDriverName() {
                try {
                    Class.forName(JDBC_DRIVER);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
            ```
        3. Database URL Formulation: This is to create a properly formatted address that points to the database to which you wish to connect.
            ```
            [Driver= jdbc:mysql:]//[domain=localhost]:[port=3306]/[Schema Name = eshop]?autoReconnect=true&useSSL=false&allowMultiQueries=false
            ```
        4. Create Connection Object: Finally, code a call to the DriverManager object's getConnection( ) method to establish actual database connection.
            ```Java
            public static Connection createConnection() {
                //create connection object
                Connection connection = null;
                try {
                    //user the Driver manager to establish the connection and return the Connection object
                    //The getConnection method needs the database url to connect to and the credentials to authenticate
                    connection = DriverManager.getConnection(dbUrl, dbUser, dbPwd);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return connection;
            }
            ```

    - **Statements**
        1. Statement - Use this for general-purpose access to your database. Useful when you are using static SQL statements at runtime. The Statement interface cannot accept parameters.
            ```Java
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM eshop.users;");
            ```
            ([examples](https://www.tutorialspoint.com/jdbc/statement-object-example.htm))
            
        2. PreparedStatement - Use this when you plan to use the SQL statements many times. The PreparedStatement interface accepts input parameters at runtime.
            ```Java
            PreparedStatement statement = connection.prepareStatement(UserTemplate.QUERY_SELECT_USER_ID);
            statement.setLong(1, userId);
            ResultSet rs = statement.executeQuery();
            ```
            ([examples](https://www.tutorialspoint.com/jdbc/preparestatement-object-example.htm))

        3. Execute Queries:
           - **Statement** - executeQuery(String sql): Executes the given SQL statement, which returns a single ResultSet object.
           - **PreparedStatement** - executeQuery() : Executes the SQL query in this PreparedStatement object and returns the ResultSet object generated by the query.
           - **executeUpdate()**: Executes the SQL statement in this object, which must be an SQL Data Manipulation Language (DML) statement, such as INSERT, UPDATE or DELETE. returns either the row count for SQL Data  Manipulation Language (DML) statements or 0 for SQL statements that return nothing

    - **Result-sets**
        1. A ResultSet object maintains a cursor pointing to its current row of data. Initially the cursor is positioned **before the first row**. The **.next()** method moves the cursor to the **next row**, and because it returns false when there are no more rows in the ResultSet object, it can be used in a while loop to iterate through the result set.
        
        example for the ```SQL "SELECT * FROM eshop.users;"``` statement The ResultSet will like this:


                           | userId | username | firstname | lastname  | email               |
            Cursor------>  |--------|----------|-----------|-----------|---------------------|
              .next()--->  |   1    |  kwstas1 |  kwstas   |  pappas   | kwstas@gmail.com    |
              .next()--->  |   2    |  zisis2  |   zisis   |  kotsolis | zisis@hotmail.com   |
              .next()--->  |   3    |  mpamis1 |  mpampis  |   lakis   | mpamis.lakis@in.gr  | 
                                    

        2. A default ResultSet object is not updatable and has a cursor that moves forward only. Thus, you can iterate through it only once and only from the first row to the last row.

        3. Reading the ResultSet and creating an Object from our Model: 
            ```Java
            //Execute the query and obtain the result set.
            ResultSet resultSet = statement.executeQuery("SELECT * FROM eshop.users;");
            //While there are more rows in the result set keep parsing users
            while (resultSet.next()) {
                //Create a new User and pass in the attribues obtained from the result set
                User user = new User();
                user.setUserId( resultSet.getLong(UserTemplate.COLUMN_USER_ID) );
                user.setEmail( resultSet.getString(UserTemplate.COLUMN_EMAIL) );
                user.setUsername( resultSet.getString(UserTemplate.COLUMN_USERNAME) );
                user.setFirstName( resultSet.getString(UserTemplate.COLUMN_FIRSTNAME) );
                user.setLastName( resultSet.getString(UserTemplate.COLUMN_LASTNAME) );
                //add the new user to the list of users we retrieved.
                usersList.add(user);
            }
            ```     
            - **.getLong()** and **.getString()** methods of the ResultSet are used to retrieve the column values. We can pass in either the column index we want to retrieve or the column name.
            for example in the table above if we want to retrieve the username attribute we can either use: **.getString( 2 )** OR **.getString("username")**. The name of the method we need to use depends on the column data type. for a varchar we will use the .getString() method, for an integer we use the .getInt() method e.t.c.


    - **Transactions**
        1. By default a jdbc connection has the auto commit option set to true. This means that anytime we execute a DML statement, the changes are automaticaly "saved" in the database. In cases we want to execute multiple statements and the integrity of the operation as a whole stands on all of the statements executing successfuly, this poses the risk of saving corrupt data in the database. By setting the autocommit option to false and using the Connection methods **.rollback()** and **.commit()** we can control the when the changes are saved.
            ```Java
            connection.setAutoCommit(false);
            ```

        2. Execute Multiple statements - Using the method **.addBatch()** and **executeBatch()**:
            ```Java
            //2.1 - for each product in the order we create an insert statement and add it to a batch of statements to be
            // executed in the database
            for(Product product : order.getProducts()) {
                //2.2 - we pass the values to the parameters of the statement
                orderProductsStatement.setLong(1, order.getOrderId());
                orderProductsStatement.setLong(2, product.getProductId());
                //2.3 - the add batch method adds the set of parameters above to be used when we execute the query
                orderProductsStatement.addBatch();
            }
            //2.4 - when we call the executebatch() method the same statement is executed
            // for each set of parameters we added in the step above
            int[] batchResult = orderProductsStatement.executeBatch();
            ```

        3. Rollnack on Error - If any execution did not return the expected result we use the **.rollback()** method to undo any changes made in the database.
            ```Java
            for ( int res : batchResult) {
                //for each result we check its value ( row count)
                if (res != 1) {
                    //1.3 - If any row count is not 1 as excpeted we rollback ALL the changes and return
                    connection.rollback();
                    return null;
                }
            }
            ```

        4. Commit on Success - If all operations completed succesfuly we call the **.commit()** method to save the changes.
            ```Java
            connection.commit();
            ```


7. **resources:**
    1. Databases:
        - https://www.tutorialspoint.com/dbms/dbms_data_models.htm

    2. SQL:
        - https://www.w3schools.com/sql/
        - https://www.dofactory.com/sql/tutorial

    3. JDBC: 
        - https://www.tutorialspoint.com/jdbc/index.htm
        - https://www.javatpoint.com/java-jdbc
        - https://docs.oracle.com/javase/tutorial/jdbc/basics/index.html
