package com.project_4.cookpad_api.seeder;

import com.project_4.cookpad_api.repository.PostRepository;
import com.project_4.cookpad_api.repository.ProductRepository;
import com.project_4.cookpad_api.repository.UserRepository;
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
    PostSeeder postSeeder;
    boolean seed = true;

    @Override
    public void run(String... args) throws Exception {
        if (seed) {
            userRepository.deleteAll();
            productRepository.deleteAll();
            postRepository.deleteAll();
            userSeeder.generate();
            productSeeder.generate();
            postSeeder.generate();
        }
    }
}
