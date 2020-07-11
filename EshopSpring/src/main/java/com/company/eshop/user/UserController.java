package com.company.eshop.user;


import com.company.eshop.order.Order;
import com.company.eshop.order.OrderStatus;
import com.company.eshop.product.Product;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserResponseDto>> getUserList() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.getUserList());
    }

    @GetMapping(value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponseDto> getUser(@PathVariable("id") Long id) {
        //showcasing lombok and builder pattern
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.getUser(id));
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponseDto> createUser(@RequestBody CreateUserRequestDto user) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userService.createUser(user));
    }

    @PostMapping(value="token",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RefreshTokenResponse> refreshUserToken(@RequestBody RefreshTokenRequest token) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userService.refreshToken(token));
    }


    @PostMapping(value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Order> checkout(@PathVariable("id") Long id,
                                          @RequestBody List<Product> products) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userService.checkout(products, id));
    }

    @GetMapping(value = "/{id}/orders",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Order>> getUserOrders(@PathVariable("id") Long id,
                                                     //The RequestParam is a Query param. to test this try http://localhost:8080/eshop/users/1/orders?status=xxxxxx
                                                     @RequestParam(value = "status", required = false) String statusInput) {
        //(1) If An OrderStatus is provided call the getOrders(id, OrderStatus) method from the service
        //(2) use the fromInput() method of the Order Status enum and check for null result. if not null a valid status was provided
        OrderStatus status = OrderStatus.fromInput(statusInput);
        if (status == null)
            return ResponseEntity.status(HttpStatus.OK)
                    .body(userService.getOrders(id));

        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.getOrders(id, status));
    }


}
