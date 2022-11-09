package com.project_4.cookpad_api.api.client;

import com.project_4.cookpad_api.entity.CartItem;
import com.project_4.cookpad_api.entity.ShoppingCart;
import com.project_4.cookpad_api.service.ShoppingCartService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/cart")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ShoppingCartApi {

    @Autowired
    ShoppingCartService shoppingCartService;

    @RequestMapping(method = RequestMethod.POST, path = "/add")
    public ResponseEntity<?> addShoppingCart(@Param("userId") long userId, @Param("productId") long productId, @Param("quantity") int quantity){
        ShoppingCart shoppingCart = shoppingCartService.addToShoppingCart(userId, productId, quantity);
        if (shoppingCart == null){
            return ResponseEntity.badRequest().body("Add fail");
        }
        return ResponseEntity.status(HttpStatus.OK).body("Add success") ;
    }

    @RequestMapping(method = RequestMethod.GET, path = "all")
    public Page<CartItem> findAll(@RequestParam(name = "page", defaultValue = "0") int page,
                                  @RequestParam(name = "limit", defaultValue = "10") int limit,
                                  @Param("shoppingCatId") long shoppingCatId){
        return shoppingCartService.getAllCart(shoppingCatId ,page, limit);
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/delete")
    public ResponseEntity<?> deleteCartItem(@Param("shoppingCatId") long shoppingCatId, @Param("productId") long productId){
        shoppingCartService.deleteCartItem(shoppingCatId, productId);
        return ResponseEntity.status(HttpStatus.OK).body("delete success") ;
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/update")
    public ResponseEntity<?> updateCart(@Param("shoppingCatId") long shoppingCatId, @Param("productId") long productId, @Param("quantity") int quantity){
        shoppingCartService.updateQuantity(shoppingCatId, productId, quantity);
        return ResponseEntity.status(HttpStatus.OK).body("success") ;
    }
}
