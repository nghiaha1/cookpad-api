package com.project_4.cookpad_api.api.admin;

import com.project_4.cookpad_api.entity.Category;
import com.project_4.cookpad_api.entity.myenum.Status;
import com.project_4.cookpad_api.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/admin/categories")
@RequiredArgsConstructor
@CrossOrigin("*")
public class CategoryAdminApi {
    @Autowired
    CategoryService categoryService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Category>> findAll(){
        return ResponseEntity.ok(categoryService.findAll());
    }

    @RequestMapping(method = RequestMethod.GET, path = "active")
    public ResponseEntity<List<Category>> findAllByActive(){
        return ResponseEntity.ok(categoryService.findAll(Status.ACTIVE));
    }
}
