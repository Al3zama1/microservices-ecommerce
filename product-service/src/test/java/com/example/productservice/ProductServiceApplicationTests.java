package com.example.productservice;

import com.example.productservice.dto.ProductRequest;
import com.example.productservice.repository.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class ProductServiceApplicationTests {

	@Container
	static MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:6.0.3"));

	@DynamicPropertySource
	static void properties(DynamicPropertyRegistry registry) {
		registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
	}

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private ProductRepository productRepository;

	@BeforeEach
	void setup() {
		productRepository.deleteAll();
	}

	@AfterEach
	void reset() {
		productRepository.deleteAll();
	}
	@Test
	void shouldCreateProduct() throws Exception {
		// Given
		ProductRequest productRequest = getProductRequest();

		// When
		mockMvc.perform(post("/api/products")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(productRequest)))
				.andExpect(status().isCreated());

		// Then
		assertThat(productRepository.findAll().size()).isEqualTo(1);
	}

	@Test
	void shouldReturnProduct() throws Exception{
		// Given
		ProductRequest productRequest = getProductRequest();

		// When, Then
		mockMvc.perform(post("/api/products")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(productRequest)))
				.andExpect(status().isCreated());

		mockMvc.perform(get("/api/products")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.size()", Matchers.is(1)))
				.andExpect(jsonPath("$[0].name", Matchers.is("Iphone 13")));
	}

	private ProductRequest getProductRequest() {
		return ProductRequest.builder()
				.name("Iphone 13")
				.description("iphone 13")
				.price(BigDecimal.valueOf(1200))
				.build();
	}

}
