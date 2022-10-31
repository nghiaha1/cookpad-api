package com.project_4.cookpad_api.seeder;

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
    boolean seed = false;

    @Override
    public void run(String... args) throws Exception {
        if (seed) {
            userRepository.deleteAll();
            productRepository.deleteAll();
            userSeeder.generate();
        productSeeder.generate();
        }
    }
}
