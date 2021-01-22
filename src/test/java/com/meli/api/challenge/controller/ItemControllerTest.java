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

import com.meli.api.challenge.entity.Children;
import com.meli.api.challenge.entity.Item;
import com.meli.service.MercadoLibreService;

@SpringBootTest
public class ItemControllerTest {

	private static final String ITEM_ID = "MLU111111";

	private ItemController itemController;
	private Item item;
	private Item itemWithChildren;
	@Mock
	private MercadoLibreService service;

	@BeforeEach
	public void setup() {
		itemController = new ItemController();
		ReflectionTestUtils.setField(itemController, "service", service);
		item = createItem(ITEM_ID);
		itemWithChildren = createItemWithChildren(ITEM_ID);
		when(service.getItem(ITEM_ID)).thenReturn(item);
		when(service.getChildren(ITEM_ID)).thenReturn(itemWithChildren);
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

	@Test
	void getChildrenInvalidItemId() {
		assertThrows(ResponseStatusException.class, () -> itemController.getChildren(null));
		assertThrows(ResponseStatusException.class, () -> itemController.getChildren(StringUtils.EMPTY));
	}

	@Test
	void getChildren() {
		assertEquals(itemController.getChildren(ITEM_ID), itemWithChildren);
	}

	private Item createItem(String itemId) {
		Item item = new Item();
		item.setItemId(itemId);
		item.setTitle("Juan Test");
		item.setCategoryId("Category");
		item.setPrice(1L);
		item.setStartTime(new Date());
		item.setStopTime(new Date());
		return item;
	}

	private Item createItemWithChildren(String itemId) {
		Item item = createItem(itemId);
		List<Children> children = new ArrayList<Children>();
		Children c = new Children();
		c.setItemId("c");
		c.setStopTime(new Date());
		children.add(c);
		item.setChildren(children);
		return item;
	}
}
