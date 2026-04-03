// Student Number: ATE/5401/14
package com.shopwave.shopwave_starter.mapper;

import com.shopwave.shopwave_starter.dto.ProductDTO;
import com.shopwave.shopwave_starter.model.Product;

public class ProductMapper {

    public static ProductDTO toDTO(Product product) {
        if (product == null) return null;

        return ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .stock(product.getStock())
                .categoryId(product.getCategory().getId())
                .build();
    }
}
