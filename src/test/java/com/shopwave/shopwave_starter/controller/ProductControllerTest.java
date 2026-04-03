// Student Number: ATE/5401/14
package com.shopwave.shopwave_starter.controller;


import com.shopwave.shopwave_starter.dto.ProductDTO;
import com.shopwave.shopwave_starter.exception.GlobalExceptionHandler;
import com.shopwave.shopwave_starter.exception.ProductNotFoundException;
import com.shopwave.shopwave_starter.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
@Import({ProductControllerTest.TestConfig.class, GlobalExceptionHandler.class})
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductService productService;


    private ProductDTO necklaceProduct;
    private ProductDTO carpetProduct;

    @BeforeEach
    void setUp() {

        necklaceProduct = ProductDTO.builder()
                .id(1L)
                .name("Gold Necklace")
                .description("Luxury Jewellery")
                .price(new BigDecimal("500"))
                .stock(8)
                .categoryId(1L)
                .build();

        carpetProduct = ProductDTO.builder()
                .id(2L)
                .name("Persian Carpet")
                .description("Handmade luxury carpet")
                .price(new BigDecimal("1200"))
                .stock(5)
                .categoryId(2L)
                .build();
    }

    static class TestConfig {
        @Bean
        public ProductService productService() {
            return mock(ProductService.class);
        }
    }

    @Test
    void getAllProducts_returns200() throws Exception {
        when(productService.getAllProducts(any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(necklaceProduct, carpetProduct)));

        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name").value("Gold Necklace"))
                .andExpect(jsonPath("$.content[1].name").value("Persian Carpet"));
    }

    @Test
    void getProductById_notFound_returns404() throws Exception {
        when(productService.getProductById(anyLong()))
                .thenThrow(new ProductNotFoundException("Product with ID 999 not found."));

        mockMvc.perform(get("/api/products/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message")
                        .value("Product with ID 999 not found."));
    }
}