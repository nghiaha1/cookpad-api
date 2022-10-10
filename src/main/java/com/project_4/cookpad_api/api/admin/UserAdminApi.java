package com.project_4.cookpad_api.api.admin;

import com.project_4.cookpad_api.entity.Product;
import com.project_4.cookpad_api.entity.User;
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

    @RequestMapping(method = RequestMethod.GET)
    public Page<User> findAll(@RequestParam(name = "page", defaultValue = "0") int page,
                              @RequestParam(name = "limit", defaultValue = "10") int limit){
        return userService.findAll(page, limit);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{id}")
    public ResponseEntity<User> findById(@PathVariable Long id){
        Optional<User> optionalUser = userService.findById(id);
        if (!optionalUser.isPresent()){
            ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(optionalUser.get());
    }

    @RequestMapping(method = RequestMethod.DELETE,path = "/{id}")
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
        existUser.setEmail(updateUser.getEmail());
        existUser.setAddress(updateUser.getAddress());
        existUser.setDetail(updateUser.getDetail());
        existUser.setPhone(updateUser.getPhone());
        existUser.setUsername(updateUser.getUsername());
        existUser.setFullName(updateUser.getFullName());
        existUser.setAvatar(updateUser.getAvatar());
        existUser.setStatus(updateUser.getStatus());
        existUser.setRole(updateUser.getRole());
        existUser.setPassword(updateUser.getPassword());
        existUser.setUpdatedAt(LocalDateTime.now());
        existUser.setCreatedAt(LocalDateTime.now());

//        existProduct.setUpdatedBy();

        return ResponseEntity.ok(userService.save(existUser));
    }
}
