package com.project_4.cookpad_api.api.admin;

import com.project_4.cookpad_api.entity.Product;
import com.project_4.cookpad_api.entity.User;
import com.project_4.cookpad_api.entity.myenum.Status;
import com.project_4.cookpad_api.search.SearchBody;
import com.project_4.cookpad_api.service.ProductService;
import com.project_4.cookpad_api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/admin/users")
@RequiredArgsConstructor
@CrossOrigin("*")
public class UserAdminApi {

    @Autowired
    UserService userService;

//    @RequestMapping(method = RequestMethod.GET)
//    public Page<User> findAll(@RequestParam(name = "page", defaultValue = "0") int page,
//                              @RequestParam(name = "limit", defaultValue = "10") int limit){
//        return userService.findAll(page, limit);
//    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> findAll(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "limit", defaultValue = "10") int limit,
            @RequestParam(name = "fullName", required = false) String fullName,
            @RequestParam(name = "email", required = false) String email,
            @RequestParam(name = "username", required = false) String username,
            @RequestParam(name = "phone", required = false) String phone,
            @RequestParam(name = "sort", required = false) String sort,
            @RequestParam(name = "start", required = false) String start,
            @RequestParam(name = "end", required = false) String end
    ) {
        SearchBody searchBody = SearchBody.SearchBodyBuilder.aSearchBody()
                .withPage(page)
                .withLimit(limit)
                .withUsername(username)
                .withPhone(phone)
                .withFullName(fullName)
                .withEmail(email)
                .withSort(sort)
                .withStart(start)
                .withEnd(end)
                .build();

        return ResponseEntity.ok(userService.findAll(searchBody));
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{id}")
    public ResponseEntity<User> findById(@PathVariable Long id){
        Optional<User> optionalUser = userService.findById(id);
        if (!optionalUser.isPresent()){
            ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(optionalUser.get());
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> create(@RequestBody User user) {
        Optional<User> optionalUser = userService.findByNameActive(user.getUsername());
        Optional<User> optionalUser1 = userService.findByEmail(user.getEmail());
        if (optionalUser.isPresent()){
            return ResponseEntity.badRequest().body("UserName already exist!");
        }if (optionalUser1.isPresent()){
            return ResponseEntity.badRequest().body("Email already exist!");
        }
        user.setFollowNumber(0);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        user.setPassword(userService.encodePassword("iamnewuser"));
        return ResponseEntity.ok(userService.save(user));
    }

    @RequestMapping(method = RequestMethod.PUT, path = "delete/{id}")
    public ResponseEntity<?> softDelete(@PathVariable Long id){
        Optional<User> optionalUser = userService.findById(id);
        if (!optionalUser.isPresent()){
            return ResponseEntity.badRequest().build();
        }
        User user = optionalUser.get();
        user.setStatus(Status.INACTIVE);
        return ResponseEntity.ok(userService.save(user));
    }

    @RequestMapping(method = RequestMethod.DELETE,path = "delete/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id){
        if (!userService.findById(id).isPresent()){
            ResponseEntity.badRequest().build();
        }
        userService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/{id}")
    public ResponseEntity<User> update(@PathVariable Long id, @RequestBody User updateUser){
        Optional<User> optionalUser = userService.findById(id);
        if (!optionalUser.isPresent()){
            ResponseEntity.badRequest().build();
        }
        User existUser = optionalUser.get();
        existUser.setAddress(updateUser.getAddress());
        existUser.setDetail(updateUser.getDetail());
        existUser.setPhone(updateUser.getPhone());
        existUser.setEmail(updateUser.getEmail());
        existUser.setFullName(updateUser.getFullName());
        existUser.setAvatar(updateUser.getAvatar());
        existUser.setStatus(updateUser.getStatus());
        existUser.setRole(updateUser.getRole());
//        existUser.setPassword(updateUser.getPassword());
        existUser.setUpdatedAt(LocalDateTime.now());
        existUser.setCreatedAt(LocalDateTime.now());

//        existProduct.setUpdatedBy();

        return ResponseEntity.ok(userService.save(existUser));
    }
}
