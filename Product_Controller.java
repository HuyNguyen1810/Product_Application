package com.tutorial.apidemo.controller;

import com.tutorial.apidemo.model.Product;
import com.tutorial.apidemo.model.Response_Object;
import com.tutorial.apidemo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/v1/Products")
public class Product_Controller {
    @Autowired
    private ProductRepository repository;

    @GetMapping("")
    //this request is : http://localhost:8080/api/v1/Products
    List<Product> getAllProducts(){
        return repository.findAll();
    }
    @GetMapping("/{id}")
    // Let's return an object with: message, status, data
    ResponseEntity<Response_Object> findById(@PathVariable Long id){
        Optional<Product> foundProduct = repository.findById(id);
        return foundProduct.isPresent() ?
            ResponseEntity.status(HttpStatus.OK).body(
                    new Response_Object("ok", "Query product successfully", foundProduct)
            ):
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new Response_Object("failed", "Cannot find product with id: "+id, "")
            );


    }

    @PostMapping("/insert")

    ResponseEntity<Response_Object> insertProduct(@RequestBody Product newProduct){
        List<Product> foundProducts = repository.findByProductName(newProduct.getProductName().trim());
        if(!foundProducts.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new Response_Object("failed", "Product name already taken", "")
            );
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new Response_Object("ok", "Insert Product successfully", repository.save(newProduct))
        );
    }
    @PutMapping("/{id}")

    ResponseEntity<Response_Object> updateProduct(@RequestBody Product newProduct, @PathVariable Long id) {
        Product updatedProduct = repository.findById(id)
                .map(product -> {
                    product.setProductName(newProduct.getProductName());
                    product.setProductYear(newProduct.getProductYear());
                    product.setPrice(newProduct.getPrice());
                    product.setUrl(newProduct.getUrl());
                    return repository.save(product);
                }).orElseGet(() -> {
                    newProduct.setId(id);
                    return repository.save(newProduct);
                });
            return ResponseEntity.status(HttpStatus.OK).body(
                    new Response_Object("ok", "Update Product successfully",updatedProduct)
            );

    }

    @DeleteMapping("/{id}")
    ResponseEntity<Response_Object> deleteProduct(@PathVariable Long id){
        boolean exists = repository.existsById(id);
        if(exists){
            repository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new Response_Object("ok", "Delete Product successfully", "")
            );
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new Response_Object("failed", "Cannot find Product to delete", "")
        );
    }


}
