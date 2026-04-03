// Student Number: ATE/5401/14
package com.shopwave.shopwave_starter.config;


import com.shopwave.shopwave_starter.model.Category;
import com.shopwave.shopwave_starter.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final CategoryRepository categoryRepository;

    @Override
    public void run(String... args) {

        if (categoryRepository.count() == 0) {

            Category jewelleryCategory = Category.builder()
                    .name("Jewellery")
                    .build();

            Category carpetCategory = Category.builder()
                    .name("Carpets")
                    .build();

            categoryRepository.save(jewelleryCategory);
            categoryRepository.save(carpetCategory);
        }
    }
}
