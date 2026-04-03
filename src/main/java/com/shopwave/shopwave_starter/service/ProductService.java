// Student Number: ATE/5401/14
package com.shopwave.shopwave_starter.service;

import com.shopwave.shopwave_starter.dto.*;
import com.shopwave.shopwave_starter.exception.ProductNotFoundException;
import com.shopwave.shopwave_starter.mapper.ProductMapper;
import com.shopwave.shopwave_starter.model.Category;
import com.shopwave.shopwave_starter.model.Product;
import com.shopwave.shopwave_starter.repository.CategoryRepository;
import com.shopwave.shopwave_starter.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {

    private final ProductRepository productRepo;
    private final CategoryRepository categoryRepo;

    public ProductDTO createProduct(CreateProductRequest request) {

        Category category = categoryRepo.findById(request.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));

        Product product = Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .stock(request.getStock())
                .category(category)
                .build();

        Product saved = productRepo.save(product);

        return ProductMapper.toDTO(saved);
    }


    @Transactional(readOnly = true)
    public Page<ProductDTO> getAllProducts(Pageable pageable) {
        return productRepo.findAll(pageable)
                .map(ProductMapper::toDTO);
    }


    @Transactional(readOnly = true)
    public ProductDTO getProductById(Long id) {
        Product product = productRepo.findById(id)
                .orElseThrow(() ->
                        new ProductNotFoundException("Product with id " + id + " not found"));

        return ProductMapper.toDTO(product);
    }


    @Transactional(readOnly = true)
    public List<ProductDTO> searchProducts(String keyword, BigDecimal maxPrice) {

        List<Product> products;

        if (keyword != null && maxPrice != null) {
            products = productRepo.findByNameContainingIgnoreCase(keyword)
                    .stream()
                    .filter(p -> p.getPrice().compareTo(maxPrice) <= 0)
                    .collect(Collectors.toList());

        } else if (keyword != null) {
            products = productRepo.findByNameContainingIgnoreCase(keyword);

        } else if (maxPrice != null) {
            products = productRepo.findByPriceLessThanEqual(maxPrice);

        } else {
            products = productRepo.findAll();
        }

        return products.stream()
                .map(ProductMapper::toDTO)
                .collect(Collectors.toList());
    }


    public ProductDTO updateStock(Long id, int delta) {

        Product product = productRepo.findById(id)
                .orElseThrow(() ->
                        new ProductNotFoundException("Product not found with id " + id));

        int updatedStock = product.getStock() + delta;

        if (updatedStock < 0) {
            throw new IllegalArgumentException("Stock cannot go below zero");
        }

        product.setStock(updatedStock);
        productRepo.save(product);

        return ProductMapper.toDTO(product);
    }
}