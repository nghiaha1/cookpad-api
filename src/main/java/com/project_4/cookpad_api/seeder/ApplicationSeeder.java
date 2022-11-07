package com.project_4.cookpad_api.seeder;

import com.project_4.cookpad_api.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ApplicationSeeder implements CommandLineRunner {
    @Autowired
    UserSeeder userSeeder;

    @Autowired
    ProductSeeder productSeeder;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    PostRepository postRepository;
    @Autowired
    OriginRepository originRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    OrderSeeder orderSeeder;
    boolean seed = true;

    @Override
    public void run(String... args) throws Exception {
        if (seed) {
            ;
            orderRepository.deleteAll();
            originRepository.deleteAll();
            categoryRepository.deleteAll();
            userRepository.deleteAll();
            productRepository.deleteAll();
            postRepository.deleteAll();
            userSeeder.generate();
            productSeeder.generate();
            orderSeeder.generate();
        }
    }
}