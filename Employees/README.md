# Employees Registry

   Implementation of an employee registry service with Spring Boot following the Controller-Service-Mapper-Repository pattern.

1. ### Setup
    * Goto [Spring initializr](https://start.spring.io/)
    * Choose:
        - Maven Project
        - Java Language
        - 2.3.0 Spring Boot Version
        - Group: com.company
        - artifact: eshop
        - Packaging: Jar
        - Java: 11
    * Add Dependencies:
        - **Spring Web** - It is used for building the web application, including RESTful applications using Spring MVC. It uses Tomcat as the default embedded container.
        - **Spring Data JPA** - Persist data in SQL stores with Java Persistence API using Spring Data and Hibernate.
        - **Lombok** - Java annotation library which helps to reduce boilerplate code.
    * Generate, extract the zip and open it in Intellij.
    * Open the pom.xml change parent to 2.1.4.RELEASE (if the hibernate dependency error still exists)
    * Install Intellij lombok plugin
    * Enable annotation processing
    * Restart IDE
    * Ready!

2. ### Object Model:

    ![E-R Diagram](https://github.com/cpanou/fundamentals/blob/master/Employees/E-R%20Diagram.png)

    * **Object Representation:** 
     * Employee:
        ```Java
        id
        username : unique
        firstname
        lastname
        password
        address
        phone
        hireDate
        leaveDate
        position
        ```
     * Department:
        ```Java
        id
        name
        sector : enum [TECHNOLOGY, FINANCE, HR]
        company
        ```
     * Company:
        ```Java
        id
        name
        taxNumber
        ```

        Think of the apropriate annotations to create the Entities and the relationships between them out the above model.
        
        Additional annotations for JSON mapping to avoid the JSON mapping recursion problem:
        ```Java
        @JsonBackReference //JSON mapper will ignore the attribute when mapping to JSON
        @JsonManagedReference //JSON mapper will print the attribute when mapping to JSON
        ```

        **After the application is executed for the first time, copy the contents of the data.sql script in a query tab in the MySQL Workbench, to initialize the Database with Mock data**

    * **DTOS:**
    
                EmployeeResponse:
        ```Java
        id
        username
        fullName : firstname + lastname
        address
        phone
        workingPeriod : [ hireDate + " - " + leaveDate || hireDate + "- PRESENT"] when leaveDate is null
        status ["ACTIVE" || "INACTIVE"] when leaveDate is not null
        position (e.g. Senior Developer, Solution-Architect etc.)
        department (department name)
        ```
                        EmployeeRequest:
        ```Java
        username
        firstname
        lastname
        password
        address
        phone
        position
        department
        ```

    * [Spring Data JPA](https://docs.spring.io/spring-boot/docs/2.3.0.RELEASE/reference/htmlsingle/#boot-features-jpa-and-spring-data)
    * [JPA - onToMany](https://www.baeldung.com/hibernate-one-to-many)
    * [JAP - manyToMany](https://www.baeldung.com/jpa-many-to-many)


3.  ### The Application

    * We want to expose following operations:
       - **Register an Employee:** create a new employee in the database providedan Employee Request.
       - **Get All Employees of a Department:** get the list of Employees that belong to the provided department specified by its id.
       - **Get All Employees of a company:** get the list of Employees that belong to the provided company specified by its id.

    * Operations to HTTP methods:
        
        | Operation                         | HTTP Method  | BODY              | PATH                                            | 
        |-----------------------------------|--------------|-------------------|-------------------------------------------------|
        | Register Employees                |     POST     |  EmployeeRequest  |  /registry/employees                            |
        | Get All Employees of a Department |     GET      |         -         |  /registry/employees/department/{department-id} |
        | Get All Employees of a company    |     GET      |         -         |  /registry/employees/company/{company-id}       |

    * [The Controller](https://www.baeldung.com/building-a-restful-web-service-with-spring-and-java-based-configuration#controller)

4. ### Security

    You need to implement a JWT Authentication method for the Employees of the Application.

    - Implement an **authentication filter** to issue JWTS to users sending credentials username and passwords. The passwords need to be encoded.
    - Implement an **authorization filter** to validate requests containing JWTS.
    - Create a custom implementation of UserDetailsService to help Spring Security load user-specific data in the framework.
    - Extend the WebSecurityConfigurerAdapter class to customize the security framework to our needs.

5. ### Error Handling
    Refer to [EshopSpring](https://github.com/cpanou/fundamentals/tree/master/EshopSpring#error-handling) for additional info on Exception Handling
    * Custom Error Object to return in case of Application Errors:
    ```Java
    public class Error {
        private String error;
        private Integer code;
    }
    ```

    * You need to implement two Exceptions for each of the following cases:
        - A Department is not found by its id.
        - A Company is not found by its id.

    * Catch the exceptions in the mapper. **hint** the mapper accepts a list of possible exceptions to catch:
    ```JAVA
        @ExceptionHandler(value = {DepartmentNotFoundException.class, CompanyNotFoundException.class})
    ```
    You need to be able to distinguise between the exception types and then Respond with the following Errors for each exception and a StatusCode of **NOT_FOUND**:
    ```Java
    Error("Department not Found", 2);
    ```
    ```Java
    Error("Company not Found", 1);
    ```
    e.g.
    Postman Request:
        GET http://localhost:8080/registry/employees/department/3
        
    Response:
       ```JSON
        {
            "error": "Department not Found",
            "code": 2
        }
       ```
    * [Error Handling](https://www.baeldung.com/exception-handling-for-rest-with-spring)


**!Start Coding!**

