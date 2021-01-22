package com.meli.api.challenge.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.meli.api.challenge.entity.Item;
import com.meli.service.MercadoLibreService;

@RestController
@RequestMapping("/items")
@ComponentScan
public class ItemController {

	@Autowired(required = true)
	private MercadoLibreService service;

	@GetMapping("/{itemId}")
	public Item getItem(@PathVariable("itemId") String itemId) {
		validateItemId(itemId);
		return service.getItem(itemId);
	}

	private void validateItemId(String itemId) {
		if (StringUtils.isBlank(itemId)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No item id provided.");
		}
	}

}
