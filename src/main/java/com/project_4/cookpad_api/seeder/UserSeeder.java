package com.project_4.cookpad_api.seeder;

import com.github.javafaker.Faker;
import com.project_4.cookpad_api.entity.Role;
import com.project_4.cookpad_api.entity.User;
import com.project_4.cookpad_api.entity.myenum.UserStatus;
import com.project_4.cookpad_api.repository.RoleRepository;
import com.project_4.cookpad_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserSeeder {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public static List<User> userList = new ArrayList<>();
    public static List<Role> roleList = new ArrayList<>();

    public void generate(){
        Role adminRole = new Role("ADMIN");
        Role userRole = new Role("USER");
        roleList.add(adminRole);
        roleList.add(userRole);
        roleRepository.saveAll(roleList);
        userList.add(User
                .builder()
                .username("admin1")
                .password(passwordEncoder.encode("admin1"))
                .fullName("Bui Huu Thanh")
                .address("Ha Noi")
                .phone("0979341091")
                .isVip(true)
                .isFamous(true)
                .followNumber(0)
                .email("buithanh281002@gmail.com")
                .detail("cook for fun")
                .role(adminRole)
                .status(UserStatus.ACTIVE)
                .build());

        userList.add(User
                .builder()
                .username("user1")
                .password(passwordEncoder.encode("user1"))
                .fullName("Bui Huu Thanh")
                .address("Ha Noi")
                .phone("0979341091")
                .isVip(false)
                .isFamous(false)
                .followNumber(0)
                .email("buithanh281002@gmail.com")
                .detail("cook for fun")
                .role(userRole)
                .status(UserStatus.ACTIVE)
                .build());

        userRepository.saveAll(userList);
    }
}
