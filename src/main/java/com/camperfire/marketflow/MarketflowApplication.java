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

		Product product = new Product();

		product.setName("Banana");

		map.put(product, 1L);

		product.setName("Apple");

		System.out.println(map.get(product));

		String someString = new String("Some String") ;
		String someString2 = new String("Some String") ;

		System.out.println(someString.equals(someString2));


        Product product1 = new Product();
        Product product2 = new Product();

        System.out.println(product1.hashCode());
        System.out.println(product2.hashCode());

		SpringApplication.run(MarketflowApplication.class, args);


	}

}
