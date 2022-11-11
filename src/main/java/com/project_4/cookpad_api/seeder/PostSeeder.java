package com.project_4.cookpad_api.seeder;

import com.github.javafaker.Faker;
import com.project_4.cookpad_api.entity.*;
import com.project_4.cookpad_api.entity.myenum.Status;
import com.project_4.cookpad_api.repository.*;
import com.project_4.cookpad_api.util.NumberUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class PostSeeder {
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    PostRepository postRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    MakingRepository makingRepository;
    @Autowired
    IngredientRepository ingredientRepository;
    Faker faker = new Faker();

    public static final int NUMBER_OF_POST = 100;
    public static final int NUMBER_OF_OBJECT = 5;
    public static List<Post> postList = new ArrayList<>();

    public void generate(){
        for (int i = 0; i < NUMBER_OF_POST; i++){
            List<Ingredient> ingredientList = new ArrayList<>();
            List<Making> makingList = new ArrayList<>();
            Set<String> mapIngredient = new HashSet<>();
            for (int j = 0; j < NUMBER_OF_OBJECT; j++){
                Ingredient ingredient = new Ingredient();
                String country;
                do {
                    country = faker.food().ingredient();
                }while (mapIngredient.contains(country));
                ingredient.setName(country);
                ingredientList.add(ingredient);
                mapIngredient.add(ingredient.getName());
            }
            Set<String> mapMaking = new HashSet<>();
            for (int k = 0; k < NUMBER_OF_OBJECT; k++){
                Making making = new Making();
                String country;
                do {
                    country = faker.food().ingredient();
                }while (mapMaking.contains(country));
                making.setName(country);
                makingList.add(making);
                mapMaking.add(making.getName());
            }

            Set<User> mapUserLikes = new HashSet<>();
            for (int k = 0; k < UserSeeder.userList.size(); k++){
                int randomUserIndex = faker.number().numberBetween(0, UserSeeder.userList.size());
                User user = UserSeeder.userList.get(randomUserIndex);
                mapUserLikes.add(user);
            }

            Post post = new Post();
            post.setIngredient(ingredientList);
            post.setMaking(makingList);
            int randomUserIndex = NumberUtil.getRandomNumber(0, UserSeeder.userList.size());
            User user = UserSeeder.userList.get(randomUserIndex);
            post.setMaking(makingList);
            post.setName(faker.food().ingredient());
            post.setDetail(faker.lorem().sentence());
            post.setThumbnails("https://picsum.photos/300/200?random="+i);
            post.setLikes(faker.random().nextInt(5, 10));
            post.setStatus(Status.ACTIVE);
//            post.setUserIdLikes(mapUserLikes);
            post.setUser(user);
            int randomCate = faker.number().numberBetween(0, ProductSeeder.categoryList.size());
            Category category = ProductSeeder.categoryList.get(randomCate);
            post.setCategory(category);
            int randomOrigin = faker.number().numberBetween(0, ProductSeeder.originList.size());
            Origin origin = ProductSeeder.originList.get(randomOrigin);
            post.setOrigin(origin);
            int randomStatus = faker.number().numberBetween(1, 4);
            if (randomStatus == 1){
                post.setStatus(Status.ACTIVE);
            }else if (randomStatus == 2){
                post.setStatus(Status.LOCKED);
            }else if (randomStatus == 3){
                post.setStatus(Status.INACTIVE);
            }
            int randomEatNumber = faker.number().numberBetween(1, 11);
            long randomCookingTime = faker.random().nextLong(120);
            post.setEatNumber(randomEatNumber);
            post.setCookingTime(randomCookingTime);
            postList.add(post);
        }
        postRepository.saveAll(postList);
    }
}
