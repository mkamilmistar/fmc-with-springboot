package com.example.fcm.springbootfcm.controller;

import com.example.fcm.springbootfcm.entity.Product;
import com.example.fcm.springbootfcm.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/v1")
public class ProductController {

  ProductService productService;

  public ProductController(ProductService productService) {
    this.productService = productService;
  }

  @PostMapping("/products")
  public String saveProduct(@RequestBody Product product) throws ExecutionException, InterruptedException {
    return productService.saveProduct(product);
  }

  @GetMapping("/products/{name}")
  public Product getProduct(@PathVariable String name) throws ExecutionException, InterruptedException {
    return productService.getProductDetails(name);
  }

  @PutMapping("/products")
  public String updateProduct(@RequestBody Product product) throws ExecutionException, InterruptedException {
    return productService.updateProduct(product);
  }

  @DeleteMapping("/products/{name}")
  public String deleteProduct(@PathVariable String name) throws ExecutionException, InterruptedException {
    return productService.deleteProduct(name);
  }

  @GetMapping("/products-list")
  public List<Product> listProduct() throws ExecutionException, InterruptedException {
    return productService.getListProductDetails();
  }
}
