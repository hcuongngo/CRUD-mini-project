package com.restapi.demo.controllers;

import com.restapi.demo.models.Product;
import com.restapi.demo.models.ResponseObject;
import com.restapi.demo.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/v1/Products")
public class ProductController {
    //Dependency Injection
    @Autowired
    private ProductRepository repository;

    @GetMapping("")
        //http://localhost:8080/api/v1/Products
    List<Product> getAllProducts() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    ResponseEntity<ResponseObject> findById(@PathVariable Long id) {
        Optional<Product> foundProduct = repository.findById(id);
        return foundProduct.isPresent() ?
                ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject("OK", "Query Sucess", foundProduct)) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ResponseObject("false", "Can not find product with id = " + id, "")
                );
    }

    @PostMapping("")
    ResponseEntity<ResponseObject> insertProduct(@RequestBody Product newProduct) {
        //list product must not have the same name!
        List<Product> foundProduct =  repository.findByProductName(newProduct.getProductName().trim());
        return foundProduct.size() > 0 ?ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                new ResponseObject("failed", "Product name already taken", "")
        ): ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("OK", "Insert Success", repository.save(newProduct))
        );
    }

    //upsert, if found then update, otherwise insert
    @PutMapping("/{id}")
    ResponseEntity<ResponseObject> updateProduct(@RequestBody Product newProduct, @PathVariable Long id) {
        Product updatedProduct = repository.findById(id).map(product -> {
            product.setProductName(newProduct.getProductName());
            product.setYear(newProduct.getYear());
            product.setPrice(newProduct.getPrice());
            product.setUrl(newProduct.getUrl());
            return repository.save(product);
        }).orElseGet(()->{
            newProduct.setId(id);
            return repository.save(newProduct);
        });

        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("OK", "Update product success", updatedProduct)
        );
    }
}
