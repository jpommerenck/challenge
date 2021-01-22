package com.meli.api.challenge.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.meli.api.challenge.entity.Child;
import com.meli.api.challenge.entity.Children;
import com.meli.api.challenge.entity.Item;
import com.meli.service.MercadoLibreService;

@RestController
@RequestMapping("/v1/items")
@ComponentScan
public class ItemController2 {

	@Autowired(required = true)
	private MercadoLibreService service;

	@GetMapping("/{itemId}")
	public Item getItem(@PathVariable("itemId") String itemId) {
		validateItemId(itemId);
		return createItem(itemId);
	}

	@GetMapping("/{itemId}/children")
	public Children getIChildren(@PathVariable("itemId") String itemId) {
		validateItemId(itemId);
		return createChildren(itemId);
	}

	private void validateItemId(String itemId) {
		if (StringUtils.isBlank(itemId)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No item id provided.");
		}
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
