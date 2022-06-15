package com.greglturnquist.hackingspringboot.reactive.service;

import com.greglturnquist.hackingspringboot.reactive.domain.Cart;
import com.greglturnquist.hackingspringboot.reactive.domain.CartItem;
import com.greglturnquist.hackingspringboot.reactive.domain.Item;
import com.greglturnquist.hackingspringboot.reactive.repository.CartRepository;
import com.greglturnquist.hackingspringboot.reactive.repository.ItemRepository;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
class InventoryService {

	private final ItemRepository itemRepository;

	private final CartRepository cartRepository;
//	private final ReactiveFluentMongoOperations fluentOperations;

	public Mono<Cart> getCart(String cartId) {
		return this.cartRepository.findById(cartId);
	}

	public Flux<Item> getInventory() {
		return this.itemRepository.findAll();
	}

	Mono<Item> saveItem(Item newItem) {
		return this.itemRepository.save(newItem);
	}

	Mono<Void> deleteItem(String id) {
		return this.itemRepository.deleteById(id);
	}

	Mono<Cart> addItemToCart(String cartId, String itemId) {
		return this.cartRepository.findById(cartId)
				.defaultIfEmpty(new Cart(cartId)) //
				.flatMap(cart -> cart.getCartItems().stream()
						.filter(cartItem -> cartItem.getItem().getId().equals(itemId))
						.findAny() //
						.map(cartItem -> {
							cartItem.increment();
							return Mono.just(cart);
						}) //
						.orElseGet(() -> {
							return this.itemRepository.findById(itemId) //
									.map(item -> new CartItem(item)) //
									.map(cartItem -> {
										cart.getCartItems().add(cartItem);
										return cart;
									});
						}))
				.flatMap(this.cartRepository::save);
	}

	Mono<Cart> removeOneFromCart(String cartId, String itemId) {
		return this.cartRepository.findById(cartId)
				.defaultIfEmpty(new Cart(cartId))
				.flatMap(cart -> cart.getCartItems().stream()
						.filter(cartItem -> cartItem.getItem().getId().equals(itemId))
						.findAny()
						.map(cartItem -> {
							cartItem.decrement();
							return Mono.just(cart);
						}) //
						.orElse(Mono.empty()))
				.map(cart -> new Cart(cart.getId(), cart.getCartItems().stream()
						.filter(cartItem -> cartItem.getQuantity() > 0)
						.collect(Collectors.toList())))
				.flatMap(this.cartRepository::save);
	}


//	public Flux<Item> getItems() {
//		return Flux.empty();
//	}

//	public Flux<Item> search(String partialName, String partialDescription, boolean useAnd) {
//		if (partialName != null) {
//			if (partialDescription != null) {
//				if (useAnd) {
//					return repository
//							.findByNameContainingAndDescriptionContainingAllIgnoreCase(
//									partialName, partialDescription);
//				} else {
//					return repository.findByNameContainingOrDescriptionContainingAllIgnoreCase(
//							partialName, partialDescription);
//				}
//			} else {
//				return repository.findByNameContaining(partialName);
//			}
//		} else {
//			if (partialDescription != null) {
//				return repository.findByDescriptionContainingIgnoreCase(partialDescription);
//			} else {
//				return repository.findAll();
//			}
//		}
//	}

//	public Flux<Item> searchByExample(String name, String description, boolean useAnd) {
//		Item item = new Item(name, description, 0.0);
//
//		ExampleMatcher matcher = (useAnd
//				? ExampleMatcher.matchingAll()
//				: ExampleMatcher.matchingAny())
//						.withStringMatcher(StringMatcher.CONTAINING)
//						.withIgnoreCase()
//						.withIgnorePaths("price");
//
//		Example<Item> probe = Example.of(item, matcher);
//
//		return repository.findAll(probe);
//	}

//	Flux<Item> searchByFluentExample(String name, String description) {
//		return fluentOperations.query(Item.class)
//				.matching(query(where("TV tray").is(name).and("Smurf").is(description)))
//				.all();
//	}

//	Flux<Item> searchByFluentExample(String name, String description, boolean useAnd) {
//		Item item = new Item(name, description, 0.0);
//
//		ExampleMatcher matcher = (useAnd
//				? ExampleMatcher.matchingAll()
//				: ExampleMatcher.matchingAny())
//						.withStringMatcher(StringMatcher.CONTAINING)
//						.withIgnoreCase()
//						.withIgnorePaths("price");
//
//		return fluentOperations.query(Item.class)
//				.matching(query(byExample(Example.of(item, matcher))))
//				.all();
//	}

}