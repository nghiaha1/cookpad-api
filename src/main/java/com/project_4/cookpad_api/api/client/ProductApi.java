package com.project_4.cookpad_api.api.client;

import com.project_4.cookpad_api.entity.Product;
import com.project_4.cookpad_api.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/user/products")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ProductApi {

    @Autowired
    ProductService productService;

    @RequestMapping(method = RequestMethod.GET)
    public Page<Product> findAll(@RequestParam(name = "page", defaultValue = "0") int page,
                                 @RequestParam(name = "limit", defaultValue = "10") int limit){
        return productService.findAllByActive(page, limit);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{id}")
    public ResponseEntity<Product> findById(@PathVariable Long id){
        Optional<Product> optionalProduct = productService.findByIdActive(id);
        if (!optionalProduct.isPresent()){
            ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(optionalProduct.get());
    }
}
