package com.project_4.cookpad_api.api.admin;

import com.project_4.cookpad_api.entity.Category;
import com.project_4.cookpad_api.entity.Origin;
import com.project_4.cookpad_api.entity.Product;
import com.project_4.cookpad_api.entity.myenum.Status;
import com.project_4.cookpad_api.repository.OriginRepository;
import com.project_4.cookpad_api.search.SearchBody;
import com.project_4.cookpad_api.service.CategoryService;
import com.project_4.cookpad_api.service.OriginService;
import com.project_4.cookpad_api.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/admin/products")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ProductAdminApi {

    @Autowired
    ProductService productService;

    @Autowired
    CategoryService categoryService;
    @Autowired
    OriginService originService;
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> findAll(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "limit", defaultValue = "10") int limit,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "startPrice", required = false) String startPrice,
            @RequestParam(name = "endPrice", required = false) String endPrice,
            @RequestParam(name = "cateId", defaultValue = "-1") Long cateId,
            @RequestParam(name = "status", defaultValue = "-1") int status
    ){
        SearchBody searchBody = SearchBody.SearchBodyBuilder.aSearchBody()
                .withPage(page)
                .withLimit(limit)
                .withNameProduct(name)
                .withStartPrice(startPrice)
                .withEndPrice(endPrice)
                .withCateId(cateId)
                .withStatus(status)
                .build();
        return ResponseEntity.ok(productService.findAll(searchBody));
    }

    @RequestMapping(method = RequestMethod.GET, path = "/total")
    public int totalProduct(){
        return productService.totalProduct();
    }

    @RequestMapping(method = RequestMethod.GET, path = "/totalByStatus/{status}")
    public int totalProductByStatus(@PathVariable int status){
        return productService.totalProductByStatus(status);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Product> create(@RequestBody Product product) {
        Optional<Category> optionalCategory = categoryService.findById(product.getCategory().getId());
        product.setCategory(optionalCategory.get());
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
    @RequestMapping(method = RequestMethod.PUT, path = "delete/{id}")
    public ResponseEntity<?> softDelete(@PathVariable Long id){
        Optional<Product> optionalProduct = productService.findByIdActive(id);
        if (!optionalProduct.isPresent()){
            return ResponseEntity.badRequest().build();
        }
        Product product = optionalProduct.get();
        product.setStatus(Status.INACTIVE);
        return ResponseEntity.ok(productService.save(product));
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/{id}")
    public ResponseEntity<Product> update(@PathVariable Long id, @RequestBody Product updateProduct, Principal principal){
        Optional<Product> optionalProduct = productService.findById(id);
        if (!optionalProduct.isPresent()){
            ResponseEntity.badRequest().build();
        }
        Product existProduct = optionalProduct.get();

        Optional<Category> optionalCategory = categoryService.findById(updateProduct.getCategory().getId());
        Optional<Origin> optionalOrigin = originService.findById(updateProduct.getOrigin().getId());
        existProduct.setStatus(updateProduct.getStatus());
        existProduct.setCategory(optionalCategory.get());
        existProduct.setDescription(updateProduct.getDescription());
        existProduct.setDetail(updateProduct.getDetail());
        existProduct.setName(updateProduct.getName());
        existProduct.setPrice(updateProduct.getPrice());
        existProduct.setThumbnails(updateProduct.getThumbnails());
        existProduct.setUpdatedAt(LocalDateTime.now());
        existProduct.setQuantity(updateProduct.getQuantity());
        existProduct.setOrigin(optionalOrigin.get());
//        existProduct.setUpdatedBy();

        return ResponseEntity.ok(productService.save(existProduct));
    }
}
