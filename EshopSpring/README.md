# Eshop with Spring Boot

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

        com<br/>
        +- company<br/>
            +- eshop<br/>
                +- EshopApplication.java<br/>
                |<br/>
                +- user<br/>
                |   +- User.java<br/>
                |   +- UserController.java<br/>
                |   +- UserService.java<br/>
                |   +- UserRepository.java<br/>
                |<br/>

        If you structure your code as suggested above (locating your application class in a root package), you can add @ComponentScan without any arguments. All of your application components (@Component, @Service, @Repository, @Controller etc.) are automatically registered as Spring Beans.

    * Configure application wide variables in application.properties

        ```Java Properties
        server.servlet.contextPath=/eshop
        server.port=8080
        ```

    * Add the User class
        - Add lombok Annotations: ```@Data, @NoArgsConstructor, @AllArgsConstructor```

    * Add Users Controller 
        - @RestController: This is simply a specialization of the @Component class and allows implementation classes to be autodetected through the classpath scanning.
        - @RequestMapping: Specify the path for this rest Controller.
        - Create the methods to get All users, get a user by his ID and create a user.
        - Map the http methods:
            - @GetMapping
                -value, produces
            - @PostMapping
        - Use the ResponseEntity

    * Add the UserService:
        - getUsers() method that returns all users.
        - getUser(Long userId) method that returns one user by the userId.
        - addUser(User user) method that registers a new user.

4. ### Spring Data JPA
    * UserRepository
    * Annotate the User class as an entity:
        - ```@Entity```: Marks this class as a JPA entity.
        - ```@Id```: Marks a column as the unique identifier for an entity.
        - ```@GeneratedValue```: makes this column's value an autogenerated value based on the GenerationType enum
        - ```@Column```: specify database attributes for this column
    * application.properties configuration for MySQL
        ```Java properties
        spring.jpa.hibernate.ddl-auto=update
        spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
        spring.datasource.url=jdbc:mysql://${MYSQL_HOST:localhost}:3306/eshop_spring
        spring.datasource.username=root
        spring.datasource.password=admin123
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

