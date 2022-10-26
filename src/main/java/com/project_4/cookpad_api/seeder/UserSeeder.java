package com.project_4.cookpad_api.seeder;

import com.github.javafaker.Faker;
import com.project_4.cookpad_api.entity.Role;
import com.project_4.cookpad_api.entity.User;
import com.project_4.cookpad_api.entity.myenum.Status;
import com.project_4.cookpad_api.repository.RoleRepository;
import com.project_4.cookpad_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class UserSeeder {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    Faker faker = new Faker();

    public static final int NUMBER_OF_USER = 100;

    public static List<User> userList = new ArrayList<>();
    public static List<Role> roleList = new ArrayList<>();

    public void generate(){
        Role adminRole = roleRepository.findByName("ADMIN");
        Role userRole = roleRepository.findByName("USER");
        if (adminRole == null){
            adminRole = new Role("ADMIN");
            roleList.add(adminRole);
        }
        if (userRole == null){
            userRole = new Role("USER");
            roleList.add(userRole);
        }
        if (roleList != null){
            roleRepository.saveAll(roleList);
        }
        userList.add(User
                .builder()
                .username("admin1")
                .password(passwordEncoder.encode("admin1"))
                .fullName("Bui Huu Thanh")
                .address("Ha Noi")
                .phone("0979341091")
                .followNumber(0)
                .email("buithanh2810021@gmail.com")
                .detail("cook for fun")
                .role(adminRole)
                .status(Status.ACTIVE)
                .build());

        userList.add(User
                .builder()
                .username("user1")
                .password(passwordEncoder.encode("user1"))
                .fullName("Bui Huu Thanh")
                .address("Ha Noi")
                .phone("0979341091")
                .followNumber(0)
                .email("buithanh2810022@gmail.com")
                .detail("cook for fun")
                .role(userRole)
                .status(Status.ACTIVE)
                .build());


        for (int i = 0; i < NUMBER_OF_USER; i++){
            User user = new User();
            int randomStatus = faker.number().numberBetween(1, 4);
            if (randomStatus == 1){
                user.setStatus(Status.ACTIVE);
            }else if (randomStatus == 2){
                user.setStatus(Status.LOCKED);
            }else if (randomStatus == 3){
                user.setStatus(Status.INACTIVE);
            }
            user.setCreatedAt(LocalDateTime.now());
            user.setUpdatedAt(LocalDateTime.now());
            user.setPassword(passwordEncoder.encode(UUID.randomUUID().toString()));
            user.setDetail(faker.lorem().sentence());
            int randomRole = faker.number().numberBetween(1, 3);
            if (randomRole == 1){
                user.setRole(userRole);
            } else if (randomRole == 2) {
                user.setRole(adminRole);
            }
            user.setAvatar(faker.avatar().image());
            user.setUsername(faker.name().username());
            user.setPhone(faker.phoneNumber().cellPhone());
            user.setFullName(faker.name().fullName());
            user.setAddress(faker.address().fullAddress());
            user.setFollowNumber(faker.number().numberBetween(10, 500));
            user.setEmail(null);
            userList.add(user);
        }
        userRepository.saveAll(userList);
    }
}
