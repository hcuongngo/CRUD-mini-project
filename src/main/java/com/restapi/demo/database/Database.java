package com.restapi.demo.database;

import com.restapi.demo.models.Product;
import com.restapi.demo.repositories.ProductRepository;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Database {
    private static final Logger logger = LoggerFactory.getLogger(Database.class);
    @Bean
    CommandLineRunner initDatabase(ProductRepository productRepository) {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                Product productA = new Product("Macbook 16in", 2020, 2400.0, "");
                Product productB = new Product("Ipad air", 2020, 1000.0, "");
                logger.info("insert data: " + productRepository.save(productA));
                logger.info("insert data: " +  productRepository.save(productB));
            }
        };
    }
}
