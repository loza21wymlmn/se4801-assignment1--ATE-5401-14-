// Student Number: ATE/5401/14
package com.shopwave.shopwave_starter.repository;



import com.shopwave.shopwave_starter.model.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    void findByNameContainingIgnoreCase_returnsCorrectResults() {
        Product p1 = new Product();
        p1.setName("Gold Necklace");
        p1.setDescription("Luxury Jewellery");
        p1.setPrice(new BigDecimal("500"));
        p1.setStock(8);

        Product p2 = new Product();
        p2.setName("Persian Carpet");
        p2.setDescription("Handmade Carpet");
        p2.setPrice(new BigDecimal("800"));
        p2.setStock(5);

        productRepository.save(p1);
        productRepository.save(p2);

        List<Product> results =
                productRepository.findByNameContainingIgnoreCase("gold");

        assertEquals(1, results.size());
        assertEquals("Gold Necklace", results.get(0).getName());
    }
}