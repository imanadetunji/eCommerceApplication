package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import com.example.demo.model.requests.ModifyCartRequest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CartControllerTest {

    private CartController cartController;
    private UserRepository userRepository = mock(UserRepository.class);
    private UserController userController = mock(UserController.class);
    private CartRepository cartRepository = mock(CartRepository.class);
    private ItemRepository itemRepository = mock(ItemRepository.class);

    @Before
    public void setUp() {
        cartController =  new CartController();
        TestUtils.injectObjects(cartController, "userRepository", userRepository);
        TestUtils.injectObjects(cartController, "cartRepository", cartRepository);
        TestUtils.injectObjects(cartController, "itemRepository", itemRepository);
    }

    @Test
    public void addToCartHappyPath() {

        ModifyCartRequest request = createCartInfo();
        final ResponseEntity<Cart> response = cartController.addTocart(request);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());

        Cart cart = response.getBody();

        assertNotNull(cart);
        assertEquals(3, cart.getItems().size());
    }

    private ModifyCartRequest createCartInfo() {
        Cart cart = new Cart();
        // create user
        User user = new User();
        user.setId(1L);
        user.setUsername("Iman");
        user.setPassword("testPassword");
        user.setCart(cart);
        ModifyCartRequest cartRequest = createCartRequest(1L, 3, "Iman");
        // create item
        Item item = new Item();
        item.setId(1L);
        item.setName("New Item");
        item.setDescription("This is the description for a new item");
        item.setPrice(new BigDecimal("2"));
        ArrayList<Item> items = new ArrayList<Item>();
        items.add(item);


        createCart(1L, items, user);

        when(userRepository.findByUsername("Iman")).thenReturn(user);
        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));
        return cartRequest;

    }

    public Cart createCart(long cartId, ArrayList<Item> items, User user) {
        Cart newCart = new Cart();
        newCart.setId(cartId);
        newCart.setItems(items);
        newCart.setUser(user);
        return newCart;
    }

    public ModifyCartRequest createCartRequest(long itemId, int quantity, String username) {
        ModifyCartRequest cartRequest = new ModifyCartRequest();
        cartRequest.setItemId(itemId);
        cartRequest.setQuantity(quantity);
        cartRequest.setUsername(username);
        return cartRequest;
    }
}
