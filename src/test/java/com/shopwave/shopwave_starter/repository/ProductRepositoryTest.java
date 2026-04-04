// Student Number: ATE/5401/14
package com.shopwave.shopwave_starter.repository;


import com.shopwave.shopwave_starter.TestcontainersBase;
import com.shopwave.shopwave_starter.model.Product;
import com.shopwave.shopwave_starter.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductRepositoryTest extends TestcontainersBase {

    @Autowired
    private ProductRepository productRepository;

    @Test
    void testSaveAndFind() {
        Product product = Product.builder()
                .name("Test Product")
                .description("Test Desc")
                .price(new BigDecimal("10.0"))
                .stock(5)
                .build();
        productRepository.save(product);

        List<Product> products = productRepository.findByNameContainingIgnoreCase("Test");
        assertThat(products).isNotEmpty();
    }
}