package com.restapi.demo.controllers;

import com.restapi.demo.models.Product;
import com.restapi.demo.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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



}
