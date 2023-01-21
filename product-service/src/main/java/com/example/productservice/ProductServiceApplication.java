package com.example.productservice;

import com.example.productservice.model.Product;
import com.example.productservice.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;

@SpringBootApplication
@EnableEurekaClient
public class ProductServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(ProductRepository productRepository) {
		return args -> {
			if (productRepository.count() < 1) {
				System.out.println("ADDING A NEW ITEM");
				Product product = new Product();
				product.setName("iphone 13");
				product.setDescription("iphone 13");
				product.setPrice(BigDecimal.valueOf(1000));
				productRepository.save(product);
			}

			System.out.println(productRepository.count());
		};
	}

}
