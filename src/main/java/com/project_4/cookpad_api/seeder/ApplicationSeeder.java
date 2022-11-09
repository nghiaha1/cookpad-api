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
    boolean seed = false;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    OrderSeeder orderSeeder;

    @Override
    public void run(String... args) throws Exception {
        if (seed) {
            productRepository.deleteAll();
            postRepository.deleteAll();
            orderRepository.deleteAll();
            userRepository.deleteAll();
            categoryRepository.deleteAll();
            originRepository.deleteAll();
            roleRepository.deleteAll();
            userSeeder.generate();
            productSeeder.generate();
            orderSeeder.generate();
            postSeeder.generate();
        }
    }
}
