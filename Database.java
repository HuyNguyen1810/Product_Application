package com.tutorial.apidemo.database;

import com.tutorial.apidemo.model.Product;
import com.tutorial.apidemo.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class Database {
    private static final Logger logger = LoggerFactory.getLogger(Database.class);

    @Bean
    CommandLineRunner initDatabase(ProductRepository productRepository){
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                Product productA = new Product("Acer Nitro 5 Tiger", 2022, 1100.0, "");
                Product productB = new Product("Acer Nitro 5 Eagle", 2021, 1000.0, "");
                logger.info("insert data: "+productRepository.save(productA));
                logger.info("insert data: "+productRepository.save(productB));

            }
        };
    }
}
