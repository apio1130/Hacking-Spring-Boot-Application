package com.greglturnquist.hackingspringboot.reactive.repository;

import com.greglturnquist.hackingspringboot.reactive.domain.Item;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ItemRepository extends ReactiveCrudRepository<Item, String> {

}
