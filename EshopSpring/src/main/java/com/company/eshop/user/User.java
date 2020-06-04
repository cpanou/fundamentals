package com.company.eshop.user;


import com.company.eshop.order.Order;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    @Column(nullable = false, unique = true)
    private String username;

    private String password;

    @Column(name = "firstname")
    private String firstname;
    @Column(name = "lastname")
    private String lastname;

    @OneToMany(mappedBy = "user")
    @JsonBackReference
    private List<Order> orders;

}
