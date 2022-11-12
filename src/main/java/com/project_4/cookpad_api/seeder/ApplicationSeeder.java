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
    OriginRepository originRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PostRepository postRepository;
    @Autowired
    PostSeeder postSeeder;
    boolean seed = true;
    @Autowired
    ProductCategoryRepository productCategoryRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    OrderSeeder orderSeeder;

    @Override
    public void run(String... args) throws Exception {
        if (seed) {
            userSeeder.generate();
            productSeeder.generate();
            orderSeeder.generate();
            postSeeder.generate();
        }
    }
}
