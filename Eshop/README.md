# haufullstack
Fullstack - Fundamentals


Git - basic commands:

    git add : adds files to the repository
    git commit -a -m " : commits changes
    git push : uploads changes to the remote repository
    git clone <repository_name> : downloads a repository
    git pull : downloads remote changes to local repository
    git checkout <branch_name> : change branch
    git checkout -b <branch_name> : creates a new branch
    git merge <branch_name> : take code from another branch

  resources: 
    https://towardsdatascience.com/getting-started-with-git-and-github-6fcd0f2d4ac6h
    https://product.hubspot.com/blog/git-and-github-tutorial-for-beginners
    ttps://git-scm.com/docs/gittutorial
    https://try.github.io/


Introduction to Databases

Steps to create the database:

    1. Open MySQL Workbench
    2. Select the default Connection ( Local Instance MySQL57 )
    3. Select File > New Query Tab
    4. Copy the contents of Eshop/docs/create_schema.sql into the new Tab
    5. Execute the script.


1. DBMS - Data Models section

2. DBMS - ER Model Basic Concepts

    Super Key − A set of attributes (one or more) that collectively identifies an entity in an entity set.

    Candidate Key − A minimal super key is called a candidate key. An entity set may have more than one candidate key.

    Primary Key − A primary key is one of the candidate keys chosen by the database designer to uniquely identify the entity set.

    Foreign Key - A Foreign key is a key that link to primary keys in other tables, thereby creating a relationship

 3. DBMS - ER Diagram Representation

    One-to-one
        
    One-to-many ( User - Order )

    Many-to-one ( Order - User )

    Many-to-many ( Order - Prouct )

SQL Basics:

    INSERT
    UPDATE
    SELECT
    DELETE


resources: 
    Databases:
        https://www.tutorialspoint.com/dbms/dbms_data_models.htm

    SQL:
        https://www.w3schools.com/sql/
        https://www.dofactory.com/sql/tutorial
