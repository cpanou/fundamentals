package com.company.eshop.user;

import com.company.eshop.order.Order;
import com.company.eshop.order.OrderRepository;
import com.company.eshop.order.OrderStatus;
import com.company.eshop.product.Product;
import com.company.eshop.product.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class UserService {

    private UserRepository userRepository;
    private OrderRepository orderRepository;
    private ProductRepository productRepository;

    public UserService(UserRepository userRepository,
                       OrderRepository orderRepository,
                       ProductRepository productRepository) {
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    public List<User> getUserList() {
        List<User> userList = new ArrayList<>();
        userRepository.findAll()
                .forEach(userList::add);
        return userList;
    }

    public User getUser(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }


    /**
     * Creates a New Order for a specified User
     * @param products a List of productIds identifying products in the Database.
     * @param userId the id of the user for whom the order will be created.
     * @return
     */
    public Order checkout(List<Product> products, Long userId) {
        //(1) Fetches User from Database by userId
        User user = userRepository.findById(userId).orElse(null);
        if (user == null)
            return null;
        //(2) Create The Order
        Order order = new Order();
        order.setStatus(OrderStatus.SUBMITTED);
        //(3) Set the references to the Related entities ( User , Products )
        order.setUser(user);
        order.setProducts(products);
        //(4) Save the order to the database and retrieve it with all generated values
        //If any foreign key constraint fails an exception will be thrown
        order = orderRepository.save(order);

        //(5) Fetch provided products from the Database.
        //We use the findAllById function from the JPA Repository which accepts a List of ids as an input and returns the List of retrieved Products.
        //First we create the List of ids:
        // (5.1) The "old" way: we create a new empty list of Long Objects and add the ids of each Product
        List<Long> listOfIds = new ArrayList<>();
        for (Product product : products) {
            listOfIds.add(product.getId());
        }
        // (5.2) Lets see a nice way to get the List<Long>, using Java 8 Streams and lambdas
        listOfIds = products.stream()     //Stream reference which gives us access to handy functions like .map(), .filter(), .collect() etc..
                .map(product -> product.getId())  //The map method is used to transform the Stream of Products to a Stream of Longs (ids)
                                                  //basically we provide a function to be called for each product in the Stream. the function we provide is the product.getId()
                .collect(Collectors.toList());    //The collect method is used to turn the Stream object to a Collection.
                                                  //The Collectors.toList() method will turn the Collection into a List
        products = productRepository.findAllById(listOfIds);
        //(6) we set the retrieved products so we have all attributes and not just their ids.
        order.setProducts(products);
        return order;
    }

    //Find All orders from the database that belong to the User with the provided userId
    public List<Order> getOrders(Long userId) {
        //(1) Fetch the user from the repository
        //(2) Fetch all Orders for the userId( hint. start typing in the orderRepository:  List<Order> find .. The choose :)
        return new ArrayList<>();
    }

    //Find All orders from the database that belong to the User with the provided userId
    // based on their status either SUBMITTED or PROCESSED
    // hint use the OrderStatus fromInput() function in the controller to parse the request param
    public List<Order> getOrdersByStatus(Long userId, OrderStatus status) {
        //(1) Fetch the user from the repository
        //(2) Fetch all Orders for the userId( hint. start typing in the orderRepository:  List<Order> find .. The choose :)
        return new ArrayList<>();
    }



}
