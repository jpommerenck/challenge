package com.meli.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.meli.api.challenge.entity.Children;
import com.meli.api.challenge.entity.Item;

@Service
public class MercadoLibreService {

	static Logger log = LoggerFactory.getLogger(MercadoLibreService.class.getName());

	private static final String ACCEPT_HEADER = "Accept";
	private static final String APPLICATION_JSON = "application/json";
	private static final String GET = "GET";
	private static final String ITEM_PATH = "/items/%s";
	private static final String CHILDREN_PATH = "/items/%s/children";

	@Value("${mercadolibre.api.url}")
	private String apiUrl;

	public Item getItem(String itemId) {
		log.info(String.format("Get Item %s", itemId));
		Item item = doGet(String.format(ITEM_PATH, itemId), Item.class);
		Children children = getChildren(itemId);
		item.addChildrens(children == null ? null : getChildren(itemId).getChildren());
		return item;
	}

	private Children getChildren(String itemId) {
		log.info(String.format("Get Children %s", itemId));
		return doGet(String.format(CHILDREN_PATH, itemId), Children.class);
	}

	private <T> T doGet(String path, Class<T> valueType) {
		try {
			URL url = new URL(apiUrl + path);

			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod(GET);
			con.setRequestProperty(ACCEPT_HEADER, APPLICATION_JSON);

			int responseCode = con.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				log.info(String.format("Success GET %s", path));
				return new ObjectMapper().readValue(getResponse(con.getInputStream()), valueType);
			} else if (responseCode == HttpURLConnection.HTTP_NO_CONTENT) {
				return null;
			} else {
				throw new ResponseStatusException(responseCode, getResponse(con.getErrorStream()), null);
			}
		} catch (IOException e) {
			log.error(String.format("Error GET %s", path), e);
			throw new ResponseStatusException(HttpURLConnection.HTTP_INTERNAL_ERROR, "Unexpected error.", e);
		}
	}

	private String getResponse(InputStream is) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(is));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		return response.toString();
	}
}
