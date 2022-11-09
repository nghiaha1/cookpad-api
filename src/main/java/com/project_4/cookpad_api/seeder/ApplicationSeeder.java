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
    CategoryRepository categoryRepository;
    @Autowired
    OriginRepository originRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PostRepository postRepository;
    @Autowired
    PostSeeder postSeeder;
    boolean seed = false;

    @Override
    public void run(String... args) throws Exception {
        if (seed) {
            userRepository.deleteAll();
            productRepository.deleteAll();
            postRepository.deleteAll();
            categoryRepository.deleteAll();
            roleRepository.deleteAll();
            originRepository.deleteAll();
            userSeeder.generate();
            productSeeder.generate();
            postSeeder.generate();
        }
    }
}
