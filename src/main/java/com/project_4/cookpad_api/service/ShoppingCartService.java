package com.project_4.cookpad_api.service;

import com.project_4.cookpad_api.entity.CartItem;
import com.project_4.cookpad_api.entity.Product;
import com.project_4.cookpad_api.entity.ShoppingCart;
import com.project_4.cookpad_api.entity.myenum.Status;
import com.project_4.cookpad_api.repository.CartItemRepository;
import com.project_4.cookpad_api.repository.ProductRepository;
import com.project_4.cookpad_api.repository.ShoppingCartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class ShoppingCartService {

    @Autowired
    ShoppingCartRepository shoppingCartRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CartItemRepository cartItemRepository;

    public ShoppingCart addToShoppingCart(Long userId, Long productId, int quantity){
        Optional<Product> optionalProduct = productRepository.findByIdAndStatus(productId, Status.ACTIVE);
        if (!optionalProduct.isPresent()){
            return null;
        }
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserIdAndStatus(userId, Status.ACTIVE).get();
        Set<CartItem> cartItems = shoppingCart.getItems();
        boolean exits = false;
        for (CartItem item:cartItems
             ) {
            if (item.getProduct().getId().equals(productId)){
                exits = true;
                item.getProduct().setQuantity(item.getQuantity() + quantity);
                item.totalPrice();
            }
        }
        if (!exits){
            CartItem item = new CartItem();
            item.setProduct(optionalProduct.get());
            item.setQuantity(quantity);
            cartItems.add(item);
        }
        shoppingCart.setItems(cartItems);
        return shoppingCartRepository.save(shoppingCart);
    }

    public Page<CartItem> getAllCart(Long userId, int page, int limit){
        Optional<ShoppingCart> shoppingCart = shoppingCartRepository.findByUserIdAndStatus(userId, Status.ACTIVE);
        if (!shoppingCart.isPresent()){
            return null;
        }
        Long shoppingCartId = shoppingCart.get().getId();
        return cartItemRepository.findAllByShoppingCartId(shoppingCartId, PageRequest.of(page, limit));
    }


    public ResponseEntity<?> deleteCartItem(Long shoppingCartId, Long productId){
        Optional<Product> optionalProduct = productRepository.findByIdAndStatus(productId, Status.ACTIVE);
        if (!optionalProduct.isPresent()){
            return null;
        }
        ShoppingCart shoppingCart = shoppingCartRepository.findByIdAndStatus(shoppingCartId, Status.ACTIVE).get();
        Set<CartItem> cartItems = shoppingCart.getItems();
        boolean exits = false;
        for (CartItem item:cartItems
        ) {
            if (item.getProduct().getId().equals(productId)){
                exits = true;
                cartItems.remove(item);
            }
        }
        if (!exits){
            return ResponseEntity.badRequest().body("Product not found");
        }
        shoppingCart.setItems(cartItems);
        shoppingCartRepository.save(shoppingCart);
        return ResponseEntity.ok(shoppingCart);
    }

    public ShoppingCart updateQuantity(Long shoppingCartId, Long productId, int quantity){
        Optional<Product> optionalProduct = productRepository.findByIdAndStatus(productId, Status.ACTIVE);
        if (!optionalProduct.isPresent()){
            return null;
        }
        ShoppingCart shoppingCart = shoppingCartRepository.findByIdAndStatus(shoppingCartId, Status.ACTIVE).get();
        Set<CartItem> cartItems = shoppingCart.getItems();
        for (CartItem item:cartItems
        ) {
            if (item.getProduct().getId().equals(productId)){
                item.setQuantity(quantity);
                shoppingCart.setItems(cartItems);
            }
        }
        return shoppingCartRepository.save(shoppingCart);
    }
}
