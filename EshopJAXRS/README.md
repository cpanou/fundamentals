# Eshop JAX-RS
       
A more advanced REST service that includes a more complex Application Model, showcases ErrorHandling and manipulating Responses and expands on the architectural pattern.

1. **The Eshop Project**
    - The Application Model can be found in the /docs folder

    - [PostMan API specification](https://documenter.getpostman.com/view/6391147/SzYgSb9d?version=latest)

    - Model to Resources:

        - uri format:

                http://{domain:port}/{application_path}/{resource_path}
            e.g.
                http://{localhost:8080}/{eshop_war/eshop}/{users}

        - resource paths:
        <pre>
            **/users**                              exposes all users
            **/users/{user-id}**                    exposes a single user under his {user-id}
            **/users/{user-id}/cart**               exposes a single user's cart
            **/users/{user-id}/cart/{product-id}**  exposes a single product under a user's cart
            **/products**                           exposes all products
            **/products/{product-id}**              exposes a single product under its {product-id}
        </pre>

    - Supported Operations:
        - Retrieve the List of all Users 
        - Create a new User
        - Retrieve a User
        - Retrieve a User’s cart
        - Add a Product to a User’s cart
        - Remove a Product from a User’s cart
        - Checkout a user
        - Retrieve the List of all Products
        - Create a New Product
        - Retrieve a Product

    - Operations to HTTP methods:
        <pre>
        - Retrieve the List of all Users           GET       /users	                              
        - Create a new User                        POST      /users	                            
        - Retrieve a User                          GET       /users/{user-id}	                   
        - Retrieve a User’s cart                   GET       /users/{user-id}/cart             	  
        - Add a Product to a User’s cart           POST      /users/{user-id}/cart	              
        - Removes a product from the user’s cart   DELETE    /users/{user-id}/cart/{product-id}	
        - Checkout a user                          GET       /users/{user-id}/checkout	          
        - Returns a list of all the products       GET       /products	                          
        - Create a new Product                     POST      /products	                          
        - Retrieve a Product                       GET       /products/{product-id}	              
        </pre>

2. **Error Handling**

    - Use the JAX-RS Response object to control the HTTP Response.
    - Override default JAX-RS behavior with ExceptionMappers using the @Provider annotation to handle the following exceptions:
        - NotFoundException
        - NotAllowedException
    - Application specific errors and messages:
        - ApplicationError enum: holds an application specific error codes and messages to return to the client.
        - ApplicationException Object: exception that holds an ApplicationError object describing the error. The exception is thrown from the services and should be caught by the mapper.
        - ApplicationException Mapper: Provider that handles an ApplicationException and creates a response from the ApplicationError object.
        see the ApplicationError enumerator for the list of defined errors.

3. **Expanding Controller-Service-Repository pattern**

     see /docs/GET Request Flow.png 
     see /docs/POST Request Flow.png

    - We need a strictly defined interface to communicate with the clients that does not depend on the Application model:
        1. We need to transfer new objects and not our Application model.( see /docs/Introducing DTOs.PNG )
        2. We need a way to transform the new objects to and from our Application model.

4. **Data Transfer Objects**

    Objects used to de-couple the controller layer from the underlying application model. These objects allow the developer to create a clearly defined interface for communicating with the clients which makes the application more resilient to changes and allow more control over the transfered information. 

    - Response DTOs:
        - GetUserResponse.java: represents a User. Used in multiple endpoints  (e.g. response entity of a GET /users/{user-id})
        - UsersListResponse.java: list of GetUserResponse objects. (response entity of GET /users)
        - CreateUserResponse.java: representation of a new user. (response entity of POST /users)
        - CheckoutResponse.java: holds an Order object. (response entity of GET /users/{user-id}/checkout)
    - Request DTOs:
        - CreateUserRequest.java: Mandatory values to create a new user. (entity of POST /users)

5. **Mappers**

    There are two types of transformations in our Application:
    - From DTO to Application model (Application model)
    - From Application model to DTO

    The transformations take place when:
    - a controller invokes a service (from DTO to model)
    - a service returns to the controller (from model to DTO)

    In order to keep the **service layer's** single repsponsibility intact we define the Mapper classes to perform the transformations:
    - UserDtoMapper methods:
        - mapCreateUserRequestToUser(): from DTO to model, used in the **createUser()** endpoint
        - mapUserToCreateUserResponse(): from model to DTO, used in the **createUser()** endpoint 
        - mapUserToUserResponse(): from model to DTO, used in the **getUser()** endpoint
        - mapUserListToGetUserResponseList(): from model to DTO, used in the **getUsers()** endpoint
        - mapCheckoutResponseFromOrder(): from model to DTO, used in the **checkout()** endpoint

6. **Resources:**
    - [Exception Handling](https://mincong.io/2018/12/03/exception-handling-in-jax-rs/)
    - [DTO](https://www.dineshonjava.com/transfer-object/)

6. **further reading:**
    - [S.O.L.I.D.] (https://www.baeldung.com/solid-principles)