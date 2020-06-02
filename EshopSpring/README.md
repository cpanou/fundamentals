# Getting Started

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

2. ### Spring Framework

    * **IoC Container** : Inversion of Control (IoC) is also known as **dependency injection (DI)**. It is a process whereby objects define their dependencies (that is, the other objects they work with) only through constructor arguments, arguments to a factory method, or properties that are set on the object instance after it is constructed or returned from a factory method. The container then injects those dependencies when it creates the bean. This process is fundamentally the inverse (hence the name, Inversion of Control) of the bean itself controlling the instantiation or location of its dependencies by using direct construction of classes or a mechanism such as the Service Locator pattern.

        ![IOC Container](https://docs.spring.io/spring/docs/5.1.14.RELEASE/spring-framework-reference/images/container-magic.png)

    * **Beans**: A Spring IoC container manages one or more beans. These beans are created with the configuration metadata that you supply to the container (for example, in the form of XML <bean/> definitions). 

        The bean definition :
        - Class
        - Name
        - Scope
        - Constructor arguments
        - Properties
        - Autowiring mode
        - Lazy instantiation mode
        - Initialization method
        - Destruction method
        
           ```XML
            <beans>
                <bean id="userController" class="com.company.eshop.controllers.UsersController">
                    <constructor-arg ref="userService"/>
                </bean>

                <bean id="userService" class="com.company.eshop.service.UserService"/>
            </beans>
           ```

    Within the container itself, these bean definitions are represented as BeanDefinition objects. A bean definition is essentially a recipe for creating one or more objects. The container looks at the recipe for a named bean when asked and uses the configuration metadata encapsulated by that bean definition to create (or acquire) an actual object.
    
    ```Java
    public class UsersController {

        private UserService service;

        public UsersController(UserService service) {
            this.service = service;
        }
        
        ...
    }
    ```

    When you create a bean by the constructor approach, all normal classes are usable by and compatible with Spring. That is, the class being developed does not need to implement any specific interfaces or to be coded in a specific fashion. Simply specifying the bean class should suffice. However, depending on what type of IoC you use for that specific bean, you may need a default (empty) constructor.

    * **Spring Boot**: Spring Boot is basically an extension of the Spring framework which eliminated the boilerplate configurations required for setting up a Spring application.
    It takes an opinionated view of the Spring platform which paved the way for a faster and more efficient development eco-system.
    Here are just a few of the features in Spring Boot:

       - Opinionated ‘starter' dependencies to simplify build and application configuration
       - Embedded server to avoid complexity in application deployment
       - Metrics, Helth check, and externalized configuration
       - Automatic config for Spring functionality – whenever possible

    Another feature of Spring Boot is that it automatically scans all the classes in the same package or sub packages of Main-class for components. All the Spring configuration is automatically included by adding the Boot web starter, through a process called auto-configuration. What this means is that Spring Boot will look at the dependencies, properties, and beans that exist in the application and enable configuration based on these.

3.  ### The Application

    * **EshopApplication.java**
        By default, Spring Boot uses an embedded container to run the application. In this case, Spring Boot uses the public static void main entry-point to launch an embedded web server.
        ```Java
        @SpringBootApplication
        public class EshopApplication {

            public static void main(String[] args) {
                SpringApplication.run(EshopApplication.class, args);
            }
        }
        ```
        
        ```@SpringBootApplication``` annotation is often placed on your main class, and it implicitly defines a base “search package” for certain items. It is recommented that you locate your main application class in a root package above other classes. This is a convenience annotation that adds all of the following:

        - ```@Configuration```: Tags the class as a source of bean definitions for the application context.

        - ```@EnableAutoConfiguration```: Tells Spring Boot to start adding beans based on classpath settings, other beans, and various property settings. For example, if spring-webmvc is on the classpath, this annotation flags the application as a web application and activates key behaviors, such as setting up a DispatcherServlet. Spring Boot auto-configuration attempts to automatically configure your Spring application based on the jar dependencies that you have added.

        - ```@ComponentScan```: Tells Spring to look for other components, configurations, and services in the com/example package, letting it find the controllers.

        **package struture:**
        <pre>
        com
        +- company
            +- eshop
                +- EshopApplication.java
                |
                +- user
                |   +- User.java
                |   +- UserController.java
                |   +- UserService.java
                |   +- UserRepository.java
                |
        </pre>
        If you structure your code as suggested above (locating your application class in a root package), you can add @ComponentScan without any arguments. All of your application components (@Component, @Service, @Repository, @Controller etc.) are automatically registered as Spring Beans.

    * Configure application wide variables in application.properties

        ```Java Properties
        server.servlet.contextPath=/eshop
        server.port=8080
        ```

    * Add the User class
        ```Java
        public class User {
            private Long userId;
            private String username;
            private String email;
            private String firstName;
            private String lastName;
        }
        ```
        - Add lombok Annotations: 
            - ```@Data```: getters for all fields, setters for all non-final fields, and appropriate toString, equals and hashCode implementations
            - ```@NoArgsConstructor```: will generate a constructor with no parameters. 
            - ```@AllArgsConstructor```: generates a constructor with 1 parameter for each field in your class.
            - ```@Builder```: produces complex builder APIs for your classes.

    * Add Users Controller 
        - ```@RestController```: This is simply a specialization of the @Component class and allows implementation classes to be autodetected through the classpath scanning.
        - ```@RequestMapping```: Specify the path for this rest Controller.
        - Create the methods to get All users, get a user by his ID and create a user.
        - Map the http methods:
            - ```@GetMapping```
                -value, produces
            - ```@PostMapping```
        - Use the ```ResponseEntity```

    * Add the UserService:
        - getUsers() method that returns all users.
        - getUser(Long userId) method that returns one user by the userId.
        - addUser(User user) method that registers a new user.
        - add the dependency to the ```UserController```:
        ```JAVA
            private UserService service;

            public UsersController(UserService service) {
                this.service = service;
            }
        ```

4. ### Spring Data JPA
    * UserRepository
        ```Java               
        public interface UserRepository extends CrudRepository<User, Long> {
        }
        ```

    * Annotate the User class to represent a JPA entity:
        - ```@Entity```: Marks this class as a JPA entity.
        - ```@Id```: Marks a column as the unique identifier for an entity.
        - ```@GeneratedValue```: makes this column's value an autogenerated value based on the GenerationType enum
        - ```@Column```: specify database attributes for this column
    
    * Configure MySQL connection in ```application.properties``` file:
        ```Java properties
        spring.jpa.hibernate.ddl-auto=update

        spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
        spring.datasource.url=jdbc:mysql://${MYSQL_HOST:localhost}:3306/eshop_spring?useSSL=false
        spring.datasource.username=root
        spring.datasource.password=admin123
        ```

    * Add the Depency to the ```UserService```:
        ```JAVA
            private UserRepository userRepository;

            public UserService(UserRepository userRepository) {
                this.userRepository = userRepository;
            }
        ```

5. ### Test with Postman
    * GET Request for http://localhost:8080/eshop/users to get a list of users.
    * POST Request for http://localhost:8080/eshop/users to create a user.
        body:
        ```JSON
        {
            "email" : "tester@hotmail.gr",
            "username" : "testingBoot",
            "firstname" : "test",
            "lastname" : "tester"
        }
        ```
    * GET Request for http://localhost:8080/eshop/users/{id} to retrieve a user.


6. ### Finilizing Project

1. Full Specification
   - Implementing All Business Functions:
       - Register Users
       - Retrieve Users
       - Retrieve User
       - Checkout User
       - Get User Orders
       - Process Orders
       - Retrieve All Products
       - Retrieve Product

   - [Run in Postman](https://app.getpostman.com/run-collection/9f5ea3c25bd8cd83f863)

2. Object Model

   ![E-R Diagram](https://github.com/cpanou/fundamentals/blob/v.25.05.2020/EshopDB/docs/E-R%20Diagram.png)

3. JPA Entities mapping

   Every relationship has **two sides**:
    **The owning side** is responsible for propagating the update of the relationship to the database. Usually this is the side **with the foreign key**.
    **The inverse side** maps to the owning side.

    Fetching order details for a user is pretty easy once the owning side has been designed.
   
   Entity Relationships:

   - ManyToOne: The Many part of the Relationship inherits the id of the one part as a Foreign key. This Entity will be the owning part.
    e.g. Order ----> User 
   - OneToMany: The one part of the Relationship provides the id to the Many part as a Foreign key. This Entity will be the inverse part.
    e.g. User ----> Order
   - ManyToMany: We define the Relationship as an entity to which both parts have a oneToMany assiciation. The assiciation entity is the owner part for both.
    e.g. Order <----> Product
 

* [JPA - onToMany](https://www.baeldung.com/hibernate-one-to-many)
* [JAP - manyToMany](https://www.baeldung.com/jpa-many-to-many)

4. ASSIGNMENT:
    Check The UserService, User, Order, OrderProduct classes for information.
    1. Implement the getOrders method of the Service: we want to retrieve all Orders for a given userId.
    2. Implement the getOrdersByStatus method of the Service: we want to retrieve all Orders for a given userId and a given Status as a query param.
    3. Translate the ManyToMany Relationship between the Order <---> Products into two manyToOne Relationships.
        you will find hints in the comments!!

7. ### Authentication with Spring Security

    1. #### Authentication - Authorization

    **Authentication**: Authentication involves verifying who the person says he/she is. This may involve checking a username/password or checking that a token is signed and not expired.
    **Authorization**: Authorization involves checking resources that the user is authorized to access or modify via defined roles or claims. For example, the authenticated user is authorized for read access to a database but not allowed to modify it.

    2. #### Authentication Types:
        - **Basic Auth**: With this method, the sender places a **username:password** into the request Authorization header. The username and password are encoded with Base64, which is an encoding technique that converts the username and password into a set of 64 characters to ensure safe transmission. 
        Header e.g.:
            ```
            Authorization: Basic bG9sOnNlY3VyZQ==
            ```
        - **JWT**: During the authentication process, when a user successfully logs in using their credentials, a JSON Web Token is returned and must be saved locally (typically in local storage). Whenever the user wants to access a protected route or resource (an endpoint), the user agent must send the JWT, usually in the Authorization header using the Bearer schema, along with the request.
        Header e.g.:
            ```
            Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c
            ```
    
    3. #### Authentication and Authorization in Spring Boot:

        1. prerequisites: 
            - Add a "password" field to tha User class.
            - Add a PasswordEncoder Bean to encode the password before saving it to the database in the EshopApplication.class:
            ```Java
            @Bean
            public BCryptPasswordEncoder bCryptPasswordEncoder() {
                return new BCryptPasswordEncoder();
            }
            ```
            - Use the passwordEncoder before saving a new user to the database in the UserService.class: 
            ```Java
            //Create a password Encoder attribute to be 
            //injected by Spring with the Bean we defined.
            private BCryptPasswordEncoder bCryptPasswordEncoder;
            ```
            ```Java
            //use the password encoder to encode the provided password when creating a new User
            String encodedPass = bCryptPasswordEncoder.encode(user.getPassword());
            user.setPassword(encodedPass);
            userRepository.save(user);
            ```
            - Add the Spring Security dependency to the pom.xml file:
            ```XML
            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-starter-security</artifactId>
            </dependency>
            ```
            - Add the JWT dependency to the pom.xml file, this library enables the generation and validation of JWT tokens:
            ```XML    
            <dependency>
                <groupId>com.auth0</groupId>
                <artifactId>java-jwt</artifactId>
                <version>3.4.0</version>
            </dependency>
            ```

        2. Goals :
            - Implement an authentication filter to issue JWTS to users sending credentials.
            - Implement an authorization filter to validate requests containing JWTS.
            - Create a custom implementation of UserDetailsService to help Spring Security loading user-specific data in the framework.
            - Extend the WebSecurityConfigurerAdapter class to customize the security framework to our needs.

        3. The Authentication Filter:
            - Create a new package called security into the eshop package.
            - Create a class named ```JWTAuthenticationFilter```:
            ```Java
            public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
                    private AuthenticationManager authenticationManager;

                    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
                        this.authenticationManager = authenticationManager;
                    }
                    ...
            }
            ```
            Note that the authentication filter that we created extends the ```UsernamePasswordAuthenticationFilter``` class. When we add a new filter to Spring Security, we can explicitly define where in the filter chain we want that filter, or we can let the framework figure it out by itself. By extending the filter provided within the security framework, Spring can automatically identify the best place to put it in the security chain.
            - We will @Override 2 methods of the Base class:
                - ``attemptAuthentication``: where we parse the user's credentials and issue them to the AuthenticationManager.
                - ``successfulAuthentication``: which is the method called when a user successfully logs in. We use this method to generate a JWT for this user.

        4. The Authorization Filter:
            - Create a class named ```JWTAuthorizationFilter```:
            ```Java
            public class JWTAuthorizationFilter extends BasicAuthenticationFilter {
                
                public JWTAuthorizationFilter(AuthenticationManager authManager) {
                    super(authManager);
                }
                ...
            }
            ```
            We have extended the ```BasicAuthenticationFilter``` to make Spring replace it in the filter chain with our custom implementation. 
            - The most important part of the filter that we have to implement is the private ```getAuthentication``` method. This method reads the JWT from the Authorization header, and then uses JWT to validate the token. If everything is in place, we set the user in the SecurityContext and allow the request to move on.

            To sum up the section we have created **two fitlers**. The ```JWTAuthenticationFilter``` which checks for credentials, generates a Jwt token and provides it to the caller. And the  ```JWTAuthorizationFilter``` which checks a request for the Authorization header and then validates the provided jwt. If no token is present in the request is aborted.
        
        5. Integrating the Filters with Spring Security:
            - We create the class WebSecurity that extends th 
            ```Java
            @EnableWebSecurity
            public class WebSecurity extends WebSecurityConfigurerAdapter {
                private UserService userDetailsService;
                private BCryptPasswordEncoder bCryptPasswordEncoder;

                public WebSecurity(UserService userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder) {
                    this.userDetailsService = userDetailsService;
                    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
                }
            }
            ```
            The class is annotated with ```@EnableWebSecurity``` to enable Spring Security’s web security support and provide the Spring MVC integration. It also extends ```WebSecurityConfigurerAdapter``` and overrides a couple of its methods to set some specifics of the web security configuration.

            - ```configure(HttpSecurity http)```: a method where we can define which resources are public and which are secured. In our case, we set the SIGN_UP_URL endpoint as being public and everything else as being secured. We also configure CORS (Cross-Origin Resource Sharing) support through http.cors() and we add a custom security filter in the Spring Security filter chain.
            - ```configure(AuthenticationManagerBuilder auth)```: a method where we defined a custom implementation of UserDetailsService to load user-specific data in the security framework. We have also used this method to set the encrypt method used by our application (BCryptPasswordEncoder).
            - ```corsConfigurationSource()```: a method where we can allow/restrict our CORS support. In our case we left it wide open by permitting requests from any source (/**).

        6. Finally Spring Security doesn't come with a concrete implementation of ```UserDetailsService``` that we could use out of the box with our in-memory database, so we need to provide an implementation for the interface that Spring can use to match the provided credentials with an existing user. We modify the UserService.class with the following:
        ```Java
        public class UserService implements UserDetailsService {
        ```
        The ``UserDetailsService`` interface contains one method that we need to Override which is the ``` public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException ```. This method is called when the Filter tries to match a given user with a user from the database.

        7. Test with Postman:
            In our WebSecurity class we have defined one public endpoint the POST ./users which creates a new User. The remaining endpoints are protected by the authentication and authorization filters. Spring boot provides an additional public endpoint to enable loging in to the application:

            - Create a new POST method with this url: ```http://localhost:8080/eshop/login```
            and body:
            ```JSON
            {
                "username":"someone",
                "password":"user123"
            }
            ```
            The /login endpoint was added by default with the UsernamePasswordAuthenticationFilter we provided. it accepts a User in JSON and matches the provided credentials with a user in the database. If the User is authenticated the Response will contain an Authorization Header:
            ```Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzb21lb25lIiwiZXhwIjoxNTkwOTQ2ODQ0fQ.0UJbGX0AS9hMg7CMHcHLZjWO0Y1Jrkpjm6pnUGlSGElF_-Yd8EwKf8fnfMUBv4n7EZjoivDO3kCSLO1wA4rr8A```
            
            - To access any of the remaining endpoints that are protected we need to add the above header to all our requests. When the jwt expires we have to login again and get a fresh one.



### Reference Documentation
For further reference, please consider the following sections:
* [Using Spring Boot](https://docs.spring.io/spring-boot/docs/current/reference/html/using-spring-boot.html)
* [Spring vs Spring Boot](https://www.baeldung.com/spring-vs-spring-boot)
* [Spring IoC Container and Beans](https://docs.spring.io/spring/docs/5.1.14.RELEASE/spring-framework-reference/core.html#beans-introduction)
* [Spring Web](https://docs.spring.io/spring-boot/docs/2.3.0.RELEASE/reference/htmlsingle/#boot-features-developing-web-applications)
* [Spring Data JPA](https://docs.spring.io/spring-boot/docs/2.3.0.RELEASE/reference/htmlsingle/#boot-features-jpa-and-spring-data)

### Guides
The following guides illustrate how to use some features concretely:
* [Building REST services with Spring](https://spring.io/guides/tutorials/bookmarks/)
* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)

