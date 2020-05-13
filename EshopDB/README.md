# Introduction to Databases

1. DBMS - Data Models section

    Entity − An entity is a real-world entity having properties called attributes. For example, in a our database, a User is considered as an entity. Users has various attributes like username, email, firstname, etc.

    Relationship − The logical association among entities is called a relationship. Relationships are mapped with entities in various ways. Mapping cardinalities define the number of associations between two         entities.

2. DBMS - ER Model Basic Concepts

    Entity-Set and Keys:
    - Super Key − A set of attributes (one or more) that collectively identifies an entity in an entity set.
    - Candidate Key − A minimal super key is called a candidate key. An entity set may have more than one candidate key.
    - Primary Key − A primary key is one of the candidate keys chosen by the database designer to uniquely identify the entity set.
    - Foreign Key - A Foreign key is a key that links to primary keys in other tables, thereby creating a relationship

3. DBMS - ER Diagram Representation

    Relationship Cardinalities:
    - One-to-one: 
    When a record of an entity relates to exaclty one record of another entity. To represent the Relationship both entities inherit the Primary key of the other as a Foreign key.
    - One-to-many ( User - Order )
    When a record of an entity relates to many records of another entity. To represent the Relationship the entity in the "Many" part of the relationship inherits the Primary key of the other as a Foreign key.
    - Many-to-one ( Order - User )
    When many records of an entity relate to one record of another entity. To represent the Relationship the entity in the "Many" part of the relationship inherits the Primary key of the other as a Foreign key.
    - Many-to-many ( Order - Prouct )
    When a record of an entity relates to many records of another entity and vice verca. To represent the Relationship we create a new Entity that inherits both Primary keys as Foreign keys.


4. SQL Basics:

    INSERT: - Inserts a row ( entry, record ) to a database table
        
        INSERT INTO table-name (column-names) 
        VALUES (values)

    SELECT: - Retriveves rows from the database in a result-set
        
        SELECT column-names 
        FROM table-name

    UPDATE: - Updates existing records in a database
        
        UPDATE table-name
        SET column-name = value, column-name = value, ...
        WHERE condition

    DELETE: - Removes records from a database table

        DELETE table-name 
        WHERE condition

5. Setting up Walkthrough:

    - Download MySQL installer
        https://dev.mysql.com/downloads/installer/

    - Choose custom installation and then from the available products choose:
        - MySQL Server recommended version 5.7
        - MySQL Workbench
        - MySQL Notifier ( Optional but handy )
        - MySQL Shell
        - Connector/ODBC

    - Steps to create the eshop database:

        1. Open MySQL Workbench
        2. Select the default Connection ( Local Instance MySQL57 - the name depends on server version )
        3. Select File > New Query Tab
        4. Copy the contents of EshopDB/docs/create_schema.sql into the new Tab
        5. Execute the script


6. JDBC:

    JDBC API: This provides the application-to-JDBC Manager connection.
    JDBC Driver API: This supports the JDBC Manager-to-Driver Connection.
    (see /docs/jdbc-architecture.jpg)

    - Connection
        1. Import JDBC Packages: Add import statements to your Java program to import required classes in your Java code.
        2. Register JDBC Driver: This step causes the JVM to load the desired driver implementation into memory so it can fulfill your JDBC requests.
        3. Database URL Formulation: This is to create a properly formatted address that points to the database to which you wish to connect.
        4. Create Connection Object: Finally, code a call to the DriverManager object's getConnection( ) method to establish actual database connection.

    - Statements
        1. Statement - Use this for general-purpose access to your database. Useful when you are using static SQL statements at runtime. The Statement interface cannot accept parameters.
            (example - https://www.tutorialspoint.com/jdbc/statement-object-example.htm )
        2. PreparedStatement - Use this when you plan to use the SQL statements many times. The PreparedStatement interface accepts input parameters at runtime.
            (example - https://www.tutorialspoint.com/jdbc/preparestatement-object-example.htm )

    - Result-sets
        1. Navigating a Result Set
        2. Viewing a Result Set
        



7. resources: 
    1. Databases:
        - https://www.tutorialspoint.com/dbms/dbms_data_models.htm

    2. SQL:
        - https://www.w3schools.com/sql/
        - https://www.dofactory.com/sql/tutorial

    3. JDBC: 
        - https://www.tutorialspoint.com/jdbc/index.htm
        - https://www.javatpoint.com/java-jdbc
        - https://docs.oracle.com/javase/tutorial/jdbc/basics/index.html
