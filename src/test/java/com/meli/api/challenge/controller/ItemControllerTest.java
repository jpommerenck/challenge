package com.meli.api.challenge.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.server.ResponseStatusException;

import com.meli.api.challenge.entity.Child;
import com.meli.api.challenge.entity.Children;
import com.meli.api.challenge.entity.Item;
import com.meli.service.MercadoLibreService;

@SpringBootTest
public class ItemControllerTest {

	private static final String ITEM_ID = "MLU111111";

	private ItemController itemController;
	private Item item;

	@Mock
	private MercadoLibreService service;

	@BeforeEach
	public void setup() {
		itemController = new ItemController();
		ReflectionTestUtils.setField(itemController, "service", service);
		item = createItem(ITEM_ID);
		when(service.getItem(ITEM_ID)).thenReturn(item);
	}

	@Test
	void getItemInvalidItemId() {
		assertThrows(ResponseStatusException.class, () -> itemController.getItem(null));
		assertThrows(ResponseStatusException.class, () -> itemController.getItem(StringUtils.EMPTY));
	}

	@Test
	void getItem() {
		assertEquals(itemController.getItem(ITEM_ID), item);
	}

	private Item createItem(String itemId) {
		Item item = new Item();
		item.setItemId(itemId);
		item.setTitle("Juan Test");
		item.setCategoryId("Category");
		item.setPrice(1L);
		item.setStartTime(new Date());
		item.setStopTime(new Date());
		Children c = createChildren(itemId);
		item.addChildrens(c.getChildren());
		return item;
	}

	private Children createChildren(String itemId) {
		Child c1 = new Child();
		c1.setItemId("ML11111");
		c1.setStopTime(new Date());
		Child c2 = new Child();
		c2.setItemId("ML22111");
		c2.setStopTime(new Date());
		List<Child> children = new ArrayList<Child>();
		children.add(c1);
		children.add(c2);
		Children ch = new Children();
		ch.setChildren(children);
		return ch;
	}
}
