package com.project_4.cookpad_api.service;

import com.project_4.cookpad_api.entity.CartItem;
import com.project_4.cookpad_api.entity.Product;
import com.project_4.cookpad_api.entity.ShoppingCart;
import com.project_4.cookpad_api.entity.User;
import com.project_4.cookpad_api.entity.myenum.Status;
import com.project_4.cookpad_api.repository.CartItemRepository;
import com.project_4.cookpad_api.repository.ProductRepository;
import com.project_4.cookpad_api.repository.ShoppingCartRepository;
import com.project_4.cookpad_api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
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

    @Autowired
    UserRepository userRepository;

    public ShoppingCart addToShoppingCart(Long userId, Long productId, int quantity){
        Optional<Product> optionalProduct = productRepository.findByIdAndStatus(productId, Status.ACTIVE);
        if (!optionalProduct.isPresent()){
            return null;
        }
        Optional<User> optionalUser = userRepository.findByIdAndStatus(userId, Status.ACTIVE);
        if (!optionalUser.isPresent()){
            return null;
        }
        Optional<ShoppingCart> optionalShoppingCart = shoppingCartRepository.findByUserId(userId);
        if (optionalShoppingCart.isPresent()){
            Set<CartItem> cartItems = optionalShoppingCart.get().getItems();
            boolean exits = false;
            for (CartItem item:cartItems
            ) {
                if (item.getProduct().getId().equals(productId)){
                    exits = true;
                    item.getProduct().setQuantity(item.getQuantity() + quantity);
                }
            }
            if (!exits){
                CartItem item = new CartItem();
                item.setProduct(optionalProduct.get());
                item.setQuantity(quantity);
                cartItems.add(item);
            }
            optionalShoppingCart.get().setItems(cartItems);
        } else {
            ShoppingCart shoppingCart = new ShoppingCart();
            Set<CartItem> cartItems = new HashSet<>();
            CartItem cartItem = new CartItem();
            cartItem.setProduct(optionalProduct.get());
            cartItem.setQuantity(quantity);
            cartItems.add(cartItem);
            shoppingCart.setItems(cartItems);
            shoppingCart.setUser(optionalUser.get());
            return shoppingCartRepository.save(shoppingCart);
        }
        return shoppingCartRepository.save(optionalShoppingCart.get());
    }

    public Page<CartItem> getAllCart(Long userId, int page, int limit){
        Optional<ShoppingCart> shoppingCart = shoppingCartRepository.findByUserId(userId);
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
        ShoppingCart shoppingCart = shoppingCartRepository.findById(shoppingCartId).get();
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
        ShoppingCart shoppingCart = shoppingCartRepository.findById(shoppingCartId).get();
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
