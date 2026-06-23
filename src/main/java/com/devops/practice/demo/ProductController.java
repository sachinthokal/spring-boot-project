package com.devops.practice.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductController {

    // 1. Initialize the Logger instance
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    private final List<Product> products = new ArrayList<>();

    public ProductController() {
        products.add(new Product(1L, "DevOps Manual", 29.99));
        products.add(new Product(2L, "Docker Sticker Pack", 4.99));
        products.add(new Product(3L, "Kubernetes T-Shirt", 19.99));
        products.add(new Product(4L, "Helm Cheatsheet", 9.99));
    }

    @GetMapping("/products")
    public List<Product> getAllProducts() {
        // 2. Add an INFO log here
        logger.info("GET /api/products called. Returning {} products", products.size());
        return products;
    }

    @PostMapping("/products")
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        // 3. Log data when a new product is added
        logger.info("POST /api/products called. Adding product: {}", product.getName());
        products.add(product);
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    @GetMapping("/slow")
    public String getSlowResponse() throws InterruptedException {
        // 4. Log warnings for intentional latency
        logger.warn("GET /api/slow called. Initiating a 5-second sleep delay...");
        Thread.sleep(5000); 
        logger.info("GET /api/slow completed successfully after delay.");
        return "Sorry for the delay! This tests your timeout configurations.";
    }

    @GetMapping("/error")
    public ResponseEntity<String> triggerError() {
        // 5. Log errors for simulated issues (Great for tracking in Grafana Loki/ELK)
        logger.error("GET /api/error called. Simulating an Internal Server Error!");
        return new ResponseEntity<>("Internal Server Error Simulated!", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}