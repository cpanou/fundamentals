# Garage REST - Sample REST Application

Introduction To REST with a simple Garage application that holds a collection of Cars and enables clients to:
   - retrieve the collection of cars (READ)
   - retrieve only one car (READ)
   - create more cars (CREATE)
   - update the values of a car (UPDATE)
   - delete a car (DELETE)

The above operations are refered to as CRUD operations (CREATE - READ - UPDATE - DELETE). In this project we will use JAX-RS to create a RESTfull web-service to expose these operations, performing on a collection of cars, to remote clients through HTTP.

1. **Introduction to HTTP**

    HTTP is a protocol which allows the fetching of resources. It is the foundation of any data exchange on the Web and it is a client-server protocol, which means requests are initiated by the recipient.Clients and servers communicate by exchanging individual messages. When a client wants to communicate with a server it will send an HTTP message and the server will answer with his own. 
    
    
    There are two typesof HTTP messages, requests and responses, each with its own format:
    1. **HTTP REQUEST:**
        1. An HTTP method (e.g. GET, POST, PUT, DELETE etc.).
        2. The path of the resource on the server
        3. The version of the HTTP protocol
        4. Optional Headers that convey additional context
        5. Optional body which contains a sent resource<br/>

        - **HTTP Request Methods**
            - **POST**
            The HTTP POST method sends data to the server. The type of the body of the request is indicated by the Content-Type header.
            - **GET**
            The HTTP GET method requests a representation of the specified resource. Requests using GET should only retrieve data.
            - **PUT**
            The HTTP PUT request method creates a new resource or replaces a representation of the target resource with the request payload.
            - **DELETE**
            The HTTP DELETE request method deletes the specified resource.

    2. **HTTP RESPONSE:**
        1. The version of the HTTP protocol they follow.
        2. A status code, indicating if the request was successful, or not, and why.
        3. A status message, a non-authoritative short description of the status code.
        4. HTTP headers, like those for requests.
        5. Optionally, a body containing the fetched resource.<br/>
            
        - **HTTP Response Status Codes**
            - **1xx: Informational**
            It means the request was received and the process is continuing.
            - **2xx: Success**
            It means the action was successfully received, understood, and accepted.
            - **3xx: Redirection**
            It means further action must be taken in order to complete the request.
            - **4xx: Client Error**
            It means the request contains incorrect syntax or cannot be fulfilled.
            - **5xx: Server Error**
            It means the server failed to fulfill an apparently valid request.

    [examples](https://developer.mozilla.org/en-US/docs/Web/HTTP/Overview#HTTP_Messages)<br/>
    [examples](https://developer.mozilla.org/en-US/docs/Web/HTTP/Messages)<br/>
    [examples](https://www.tutorialspoint.com/http/http_message_examples.htm)<br/>

2. **REST - JAX-RS:**

    1. **RESTfull web services:** The set of these architectural principles is called REpresentational State Transfer (REST) and is defined as:
        - **Addressable resources**
            The key abstraction of information and data in REST is a resource, and each resource must be addressable via a URI (Uniform Resource Identifier). A resource in a RESTful web service is similar to an Object in Object Oriented Programming or is like an Entity in a Database. Once a resource is identified then its representation is to be decided using a standard format so that the server can send the representation and the client can understand it.

        - **A uniform, constrained interface**
            Use a small set of well-defined methods to manipulate your resources. HTTP methods are ideal for communicating intent and mapping operations on resources to methods.
        
        - **Representation-oriented**
            You interact with services using representations of resources. A resource referenced by one URI can have different formats. Different platforms need different formats, the process of selecting the best representation for a given response when there are multiple representations available is Content Negotiation.
        
        - **Communicate statelessly**
            Each request from the client to server must contain all of the information necessary to understand the request, and cannot take advantage of any stored context on the server. Session state is therefore kept entirely on the client.

        - **Hypermedia As The Engine Of Application State (HATEOAS)**
            A Request made to the service should not depend on any previous request. Keeping an application state 

    In simplest words, in the REST architectural style, data and functionality are considered resources and are accessed using Uniform Resource Identifiers (URIs). The resources are acted upon by using a set of simple, well-defined operations. The clients and servers exchange representations of resources by using a standardized interface and protocol – typically HTTP.

    Resources are decoupled from their representation so that their content can be accessed in a variety of formats, such as HTML, XML, plain text, PDF, JPEG, JSON, and others. Metadata about the resource is available and used, for example, negotiate the appropriate representation format, and perform authentication or access control. And most importantly, every interaction with a resource is stateless.

    2. **Designing a REST Service:**
        - **Identify Object Model:**
            identifying the objects which will be presented as resources. In our case the Object is the Car. Each car will have an id to act as a unique identifier in its URI.

        - **Create Model URIs:**
            These resource URIs are endpoints for RESTful services. In our application the car is the only resource so the uris will look like:
            <pre>
               - /cars              : exposes the collection of cars
               - /cars/{car-id}     : exposes a single car
            </pre>
            URIs do not use any verb or operation. It’s very important to not include any verb in URIs. URIs should all be nouns only.
        - **Determine Representations**
            In our Application we will use the JSON format to represent the resources.

        - **Assign HTTP Methods**
            Let’s identify the possible operations in the application and map them on resource URIs and HTTP methods:
            <pre>
            - Retrieve all the available cars:  HTTP GET    /cars
            - Create a new car:                 HTTP POST   /cars
            - Retrieve a single car:            HTTP GET    /cars/{car-id}
            - Update a single car:              HTTP PUT    /cars/{car-id}
            - Remove a car:                     HTTP DELETE /cars/{car-id}
            </pre>

    3. **Using JAX-RS to implement the Service**
        JAX-RS focuses on applying Java annotations to plain Java objects. JAX-RS has annotations to bind specific URI patterns and HTTP operations to individual methods of your Java class. It also has annotations which can help you handle in input/output parameters.

        As we already said that JAX-RS is a specification; it means we need to have its implementation to run REST API code. We will use Jersey for our Jax-Rs implementation. The Java class we will use as a Resource is the **CarsResource** class. We will use the JAX-RS annotations to configure this class and its methods to handle HTTP requests and responses.

        1. JAX-RS Annotations

            - **@Path("resourcePath"):** It is used to match the URI path which is relative to the base URI. It can be specified on resource classes or methods.
                ```java
                @Path("/cars")
                public class CarsResource {
                    @GET
                    @Path("/{carId}")
                    public Car getCar( @PathParam("carId") long id ) {
                        ...
                    }
                }
                ```
            - **@GET** Annotated method will handle the HTTP GET requests on matching resource path.
                ```java
                @GET
                @Path("/{carId}")
                public Car getCar( @PathParam("carId") long id ) {
                    ...
                }
                ```
            - **@POST** Annotated method will handle the HTTP POST requests on matching resource path.
                ```java
                @POST
                @Consumes(MediaType.APPLICATION_JSON)
                @Produces(MediaType.APPLICATION_JSON)
                public Car createCar( Car car ) {
                    ...
                }
                ```
            - **@PUT** Annotated method will handle the HTTP PUT requests on matching resource path.
                ```java
                @PUT
                @Path("/{carId}")
                @Produces(MediaType.APPLICATION_JSON)
                @Consumes(MediaType.APPLICATION_JSON)
                public Car editCar(  @PathParam("carId") long carid, Car car ) {
                    ...
                }
                ```
            - **@DELETE** Annotated method will handle the HTTP DELETE requests on matching resource path.
                ```java
                @DELETE
                @Path("/{carId}")
                @Produces(MediaType.APPLICATION_JSON)
                public Car deleteCar( @PathParam("carId") long carid ) {
                    ...
                }
                ```
            - **@PathParam("ParameterName")** It is used to inject values (resource identifiers) from the URL into a method parameter.
                ```java
                @GET
                @Path("/{carId}")
                public Car getCar( @PathParam("carId") long id ) {
                    ...
                }
                ```
                In the above example the value of carId will match to @PathParam("carId") long id. For example an HTTP GET /cars/1 will be matched to the above method and the id will be populated with '1'.

            - **@Consumes** It defines which MediaType is consumed by annotated resource method.
                ```java
                @POST
                @Consumes(MediaType.APPLICATION_JSON)
                @Produces(MediaType.APPLICATION_JSON)
                public Car createCar( Car car ) {
                    ...
                }
                ```
            - **@Produces** It defines which MediaType is delivered by annotated resource methods. It can be defined at class level as well as method level. If defined at class level, all methods inside inside resource class will be returning same MIME type, if not overridden in any method.
                ```java
                @POST
                @Consumes(MediaType.APPLICATION_JSON)
                @Produces(MediaType.APPLICATION_JSON)
                public Car createCar( Car car ) {
                    ...
                }
                ```
        2. Register the Resource 
            - To register the JAX-RS REST resource with the server’s runtime, we need to extend javax.ws.rs.core.Application. This class tells our application server which JAX-RS components we want to register.The getClasses() method returns a list of JAX-RS service classes. Here we need to provide the CarsResource class so it can be discovered by the server's runtime.
            ```java            
            @ApplicationPath("/garage")
            public class MyApplication extends Application {
                    @Override
                    public Set<Class<?>> getClasses() {
                        HashSet set = new HashSet<Class<?>>();
                        set.add(JacksonJsonProvider.class);
                        set.add(CarsResource.class);
                        return set;
                    }
            }
            ```
            - **@ApplicationPath** defines the relative base URL path for all our JAX-RS services in the deployment. So in our example the CarsResource will be prefixed with the **/garage** path. e.g. 
            GET /garage/cars will be matched to the getCars() method of the CarsResource class.

3. **Deploying the application on Tomcat:**

    1. Follow the guide present in ./docs/workspace setupe.pdf.
    2. If u have cloned the repository you can skip the Intellij Workspace Configuration section and open the **GarageRest** project.
    3. Follow the integrating Tomcat with Ingtellij Workspace section.
    4. Each time you make a change you need to run the maven **package** task.

4. **Controller - Service - Repository Architectural Pattern:**

    - **Single Responsibility Principle - SRP**
        As the name suggests, this principle states that each class should have one responsibility, one single purpose. This means that a class will do only one job, which leads us to conclude it should have only one reason to change. We don’t want objects that know too much and have unrelated behavior. These classes are harder to maintain. For example, if we have a class that we change a lot, and for different reasons, then this class should be broken down into more classes, each handling **a single concern**. Surely, if an error occurs, it will be easier to find.

    - To keep the code maintainable and scalable we identify three layers to better comply with the **SRP**: exposing our API through HTTP, executing the business functions, interacting with a database.
        - The **Controller Layer** uses the JAX-RS annotations to map our API to uris and HTTP methods. Its single responsibility is to handle HTTP Requests and Responses to expose the API to the clients.
        - The **Service Layer** provides methods that implement the business logic of our Application. This layer is invoked by the **controllers** to execute the business functions and return the result. 
        - The **Repository Layer** is responsible for interacting with any persisted storage (Most commonly a database). It should handle connections, execute operations in the DB and return the results to the **services**. 

    - Each layer has its own classes for each identified resource:
        - **Controller**: CarsResource
            Maps HTTP methods to java methods that only operate on the Car resource.
        - **Service**: CarService
            Performs all business functions.
        - **Repository**: CarsRepository
            Defines methods to perform CRUD operations on the persisted data.

5. **resources:** 
    - [HTTP](https://developer.mozilla.org/en-US/docs/Web/HTTP/Overview)
    - [C.R.U.D.](https://www.codecademy.com/articles/what-is-crud)
    - [REST](https://restfulapi.net/)
    - [REST](https://www.tutorialspoint.com/restful/index.htm)
    - [SRP](https://www.baeldung.com/java-single-responsibility-principle)

6. **further reading:**
    - [HTTP](https://developer.mozilla.org/en-US/docs/Web/HTTP/Basics_of_HTTP)
