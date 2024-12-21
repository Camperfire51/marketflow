package com.camperfire.marketflow;

import com.camperfire.marketflow.model.Product;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class MarketflowApplication {

	public static final Map map = new HashMap<Product, Long>();

	public static void main(String[] args) {
		SpringApplication.run(MarketflowApplication.class, args);


	}

}
