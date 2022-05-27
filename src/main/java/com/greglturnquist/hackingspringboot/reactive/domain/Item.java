package com.greglturnquist.hackingspringboot.reactive.domain;

import com.mongodb.client.model.geojson.Point;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Getter
@Setter
@EqualsAndHashCode
public class Item {

    @Id
    private String id;

    private String name;

    private String description;

    private double price;

    private String distributorRegion;

    private Date releaseDate;

    private int availableUnits;

    private Point location;

    private boolean active;

    private Item() {}

    public Item(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public Item(String name, String description, double price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }

}
