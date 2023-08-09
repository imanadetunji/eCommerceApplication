package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.repositories.ItemRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ItemControllerTest {
    private ItemController itemController;

    private ItemRepository itemRepository = mock(ItemRepository.class);

    @Before
    public void setUp() {
        itemController = new ItemController();
        TestUtils.injectObjects(itemController, "itemRepository", itemRepository);
        Item item = new Item();
        item.setId(1L);
        item.setName("A Widget");
        BigDecimal price = BigDecimal.valueOf(2.99);
        item.setPrice(price);
        item.setDescription("A widget description");
        when(itemRepository.findAll()).thenReturn(Collections.singletonList(item));
    }

    @Test
    public void testGetItems() {
        final ResponseEntity<List<Item>> response = itemController.getItems();
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
    }

    @Test
    public void testGetItemById() {
        Item item = createItem(1L, "Fidget Spinner", "Toy", new BigDecimal("5"));
        when(itemRepository.findById(item.getId())).thenReturn(Optional.of(item));

        ResponseEntity<Item> response = itemController.getItemById(1L);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());

        Item itemFound = response.getBody();
        assertNotNull(itemFound);
        assertEquals(Long.valueOf(1L), itemFound.getId());
        assertEquals("Fidget Spinner", itemFound.getName());
        assertEquals("Toy", itemFound.getDescription());
        assertEquals(BigDecimal.valueOf(5), itemFound.getPrice());
    }

    public Item createItem(Long id, String name, String description, BigDecimal price) {
        Item newItem = new Item();
        newItem.setId(id);
        newItem.setName(name);
        newItem.setDescription(description);
        newItem.setPrice(price);
        return newItem;
    }
}
