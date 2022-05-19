package com.greglturnquist.hackingspringboot.reactive.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class CartItem {

    private Item item;

    private int quantity;

    private CartItem() {}

    public CartItem(Item item) {
        this.item = item;
        this.quantity = 1;
    }

    public void increment() {
        this.quantity++;
    }

}
