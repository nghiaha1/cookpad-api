package com.project_4.cookpad_api.seeder;

import com.github.javafaker.Faker;
import com.project_4.cookpad_api.entity.ProductCategory;
import com.project_4.cookpad_api.entity.Origin;
import com.project_4.cookpad_api.entity.Product;
import com.project_4.cookpad_api.entity.myenum.Status;
import com.project_4.cookpad_api.repository.ProductCategoryRepository;
import com.project_4.cookpad_api.repository.OriginRepository;
import com.project_4.cookpad_api.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Component
public class ProductSeeder {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductCategoryRepository productCategoryRepository;

    @Autowired
    OriginRepository originRepository;

    Faker faker = new Faker();

    public static final int NUMBER_OF_CATEGORY = 10;
    public static final int NUMBER_OF_PRODUCT = 100;
    //    public static final int NUMBER_OF_ORIGIN = 226;
    public static final int NUMBER_OF_ORIGIN = 100;
    public static List<ProductCategory> productCategoryList = new ArrayList<>();
    public static List<Product> productList = new ArrayList<>();
    public static String[] countryCodes = Locale.getISOCountries();
    public static List<Origin> originList = new ArrayList<Origin>(countryCodes.length);

    public void generate() {
        countryCodes = Locale.getISOCountries();
        originList = new ArrayList<Origin>(countryCodes.length);
        for (String item : countryCodes) {
            originList.add(Origin.builder()
                            .code(item.toUpperCase())
                            .country(new Locale("", item).getDisplayCountry())
                            .status(Status.ACTIVE)
                    .build());
        }
        Collections.sort(originList);
        originRepository.saveAll(originList);

        Set<String> mapCategory = new HashSet<>();
        for (int i = 0; i < NUMBER_OF_CATEGORY; i++) {
            ProductCategory category = new ProductCategory();
            String name;
            do {
                name = faker.commerce().productName();
            } while (mapCategory.contains(name));
            category.setName(name);
            productCategoryList.add(category);
            mapCategory.add(category.getName());
        }
        productCategoryRepository.saveAll(productCategoryList);

        Set<String> mapProduct = new HashSet<>();
        for (int i = 0; i < NUMBER_OF_PRODUCT; i++) {
            Product product = new Product();
            int randomCate = faker.number().numberBetween(0, productCategoryList.size());
//            Category category = categoryList.get(randomCate);
//            product.setCategory(category);
            int randomOrigin = faker.number().numberBetween(0, originList.size());
            Origin origin = originList.get(randomOrigin);
            product.setOrigin(origin);
            product.setPrice(new BigDecimal(faker.commerce().price()));
            product.setThumbnails("https://picsum.photos/300/200?random=" + i);
            product.setDetail(faker.lorem().sentence());
            product.setDescription(faker.lorem().sentence());
            int randomStatus = faker.number().numberBetween(1, 4);
            if (randomStatus == 1) {
                product.setStatus(Status.ACTIVE);
            } else if (randomStatus == 2) {
                product.setStatus(Status.SOLDOUT);
            } else if (randomStatus == 3) {
                product.setStatus(Status.INACTIVE);
            }
            product.setQuantity(faker.number().numberBetween(10, 100));
            String name;
            do {
                name = faker.name().title();
            } while (mapProduct.contains(name));
            product.setName(name);
            mapProduct.add(name);
            product.setUpdatedAt(LocalDateTime.now());
            product.setCreatedAt(LocalDateTime.now());
            productList.add(product);
        }
        productRepository.saveAll(productList);
    }
}
