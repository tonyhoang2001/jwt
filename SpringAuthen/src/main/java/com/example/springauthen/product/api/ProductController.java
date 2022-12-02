package com.example.springauthen.product.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    ProductRepository productRepository;

    @GetMapping()
    public List<Product> getList(){
        return productRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Product> create(@RequestBody @Valid Product product){
        Product product1 = productRepository.save(product);
        return ResponseEntity
                .ok()
                .body(product1);
    }


}
