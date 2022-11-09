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

    public static final int NUMBER_OF_POST = 20;
    public static final int NUMBER_OF_OBJECT = 10;
    public static List<Category> categoryList = new ArrayList<>();
    public static List<User> userList = new ArrayList<>();
    public static List<Ingredient> ingredientList = new ArrayList<>();
    public static List<Making> makingList = new ArrayList<>();
    public static List<Post> postList = new ArrayList<>();

    public void generate(){
        Set<String> mapCategory = new HashSet<>();
        for (int i = 0; i < NUMBER_OF_OBJECT; i++){
            Category category = new Category();
            String name;
            do {
                name = faker.food().dish();
            }while (mapCategory.contains(name));
            category.setName(name);
            categoryList.add(category);
            mapCategory.add(category.getName());
        }
        categoryRepository.saveAll(categoryList);

        for (int i = 0; i < NUMBER_OF_POST; i++){
            Post post = new Post();

            for (int j = 0; j < NUMBER_OF_OBJECT; j++) {
                Ingredient ingredient = new Ingredient();
                ingredient.setName(faker.food().ingredient());
                ingredientList.add(ingredient);
            }
            post.setIngredient(ingredientList);

            for (int j = 0; j < NUMBER_OF_OBJECT; j++) {
                Making making = new Making();
                making.setName(faker.food().ingredient());
                makingList.add(making);
            }
            Set<Category> categories = new HashSet<>();
            int catePerProduct = faker.number().numberBetween(1, 3);
            for (int j = 0; j < catePerProduct; j++){
                int randomCate = faker.number().numberBetween(0, categoryList.size() -1);
                Category category = categoryList.get(randomCate);
                if (categories.contains(category)){
                    continue;
                }
                categories.add(category);
            }
            int randomUserIndex = NumberUtil.getRandomNumber(0, UserSeeder.userList.size() - 1);
            User user = UserSeeder.userList.get(randomUserIndex);
            post.setMaking(makingList);
            post.setCategory(categories);
            post.setName(faker.food().ingredient());
            post.setDescription(faker.lorem().sentence());
            post.setDetail(faker.lorem().sentence());
            post.setThumbnails("https://png.pngtree.com/png-clipart/20190117/ourlarge/pngtree-food-cooking-chef-stir-fry-png-image_438767.jpg");
            post.setLikes(faker.random().nextInt(5, 10));
            post.setStatus(Status.ACTIVE);
            post.setUser(user);
            postList.add(post);
        }
        postRepository.saveAll(postList);
    }
}
