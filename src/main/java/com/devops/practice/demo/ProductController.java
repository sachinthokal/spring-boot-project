package com.devops.practice.demo;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductController {

    private final List<Product> products = new ArrayList<>();

    public ProductController() {
        // Pre-populating some data for ease of use
        products.add(new Product(1L, "DevOps Manual", 29.99));
        products.add(new Product(2L, "Docker Sticker Pack", 4.99));
    }

    // 1. GET ALL PRODUCTS (Standard Check)
    @GetMapping("/products")
    public List<Product> getAllProducts() {
        return products;
    }

    // 2. POST A NEW PRODUCT (To test state/database simulations)
    @PostMapping("/products")
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        products.add(product);
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    // 3. CHAOS ENDPOINT: Simulated Slowness (Great for testing API Gateway timeouts or Grafana latency charts)
    @GetMapping("/slow")
    public String getSlowResponse() throws InterruptedException {
        Thread.sleep(5000); // Delays response by 5 seconds
        return "Sorry for the delay! This tests your timeout configurations.";
    }

    // 4. CHAOS ENDPOINT: Simulated Error (Great for testing alerting systems like Prometheus/PagerDuty)
    @GetMapping("/error")
    public ResponseEntity<String> triggerError() {
        return new ResponseEntity<>("Internal Server Error Simulated!", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}