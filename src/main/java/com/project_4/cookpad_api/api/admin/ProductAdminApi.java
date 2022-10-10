package com.project_4.cookpad_api.api.admin;

import com.project_4.cookpad_api.entity.Product;
import com.project_4.cookpad_api.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/admin/products")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ProductAdminApi {

    @Autowired
    ProductService productService;

    @RequestMapping(method = RequestMethod.GET)
    public Page<Product> findAll(@RequestParam(name = "page", defaultValue = "0") int page,
                                 @RequestParam(name = "limit", defaultValue = "10") int limit){
        return productService.findAll(page, limit);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Product> create(@RequestBody Product product) {
        return ResponseEntity.ok(productService.create(product));
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{id}")
    public ResponseEntity<Product> findById(@PathVariable Long id){
        Optional<Product> optionalProduct = productService.findById(id);
        if (!optionalProduct.isPresent()){
            ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(optionalProduct.get());
    }

    @RequestMapping(method = RequestMethod.DELETE,path = "/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id){
        if (!productService.findById(id).isPresent()){
            ResponseEntity.badRequest().build();
        }
        productService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/{id}")
    public ResponseEntity<Product> update(@PathVariable Long id, @RequestBody Product updateProduct){
        Optional<Product> optionalProduct = productService.findById(id);
        if (!optionalProduct.isPresent()){
            ResponseEntity.badRequest().build();
        }
        Product existProduct = optionalProduct.get();

        existProduct.setQuantity(updateProduct.getQuantity());
        existProduct.setStatus(updateProduct.getStatus());
        existProduct.setCategories(updateProduct.getCategories());
        existProduct.setDescription(updateProduct.getDescription());
        existProduct.setDetail(updateProduct.getDetail());
        existProduct.setImage(updateProduct.getImage());
        existProduct.setOrigin(updateProduct.getOrigin());
        existProduct.setName(updateProduct.getName());
        existProduct.setPrice(updateProduct.getPrice());
        existProduct.setThumbnails(updateProduct.getThumbnails());
        existProduct.setUpdatedAt(LocalDateTime.now());
//        existProduct.setUpdatedBy();

        return ResponseEntity.ok(productService.save(existProduct));
    }
}
