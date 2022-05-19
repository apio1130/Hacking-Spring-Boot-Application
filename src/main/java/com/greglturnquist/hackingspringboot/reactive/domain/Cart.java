package com.greglturnquist.hackingspringboot.reactive.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
public class Cart {

    @Id
    private String id;

    private List<CartItem> cartItems;

    private Cart() {}

    public Cart(String id) {
        this(id, new ArrayList<>());
    }

    public Cart(String id, List<CartItem> cartItems) {
        this.id = id;
        this.cartItems = cartItems;
    }

}
