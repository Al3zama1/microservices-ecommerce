package com.example.orderservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    /*
    1 - discovery server will register all inventory service instances
    2 - when order service asks discovery server for instances of inventory service,
        all instances will be provided
    3 - since we can have multiple instances of the inventory service, we need load balancing capabilities.
        @LoadBalanced adds load balancing capabilities to the WebClient builder so that order service knows which instance
        of inventory service to call
    4 - @LoadBalanced automatically crates Web client instance with load balancing capabilities
        that knows how to deal with multiple inventory service instances.


     */
    @LoadBalanced
    public WebClient.Builder webClientBuilder(@Value("${clients.inventory-service.base-url}") String inventoryServiceBaseUrl) {
        return WebClient.builder().baseUrl(inventoryServiceBaseUrl);
    }
}
