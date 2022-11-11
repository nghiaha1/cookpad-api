package com.project_4.cookpad_api.service;

import com.project_4.cookpad_api.entity.*;
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
import java.util.List;
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

    public ShoppingCart addToShoppingCart(User user, Long productId, int quantity){
        Optional<Product> optionalProduct = productRepository.findByIdAndStatus(productId, Status.ACTIVE);
        if (!optionalProduct.isPresent()){
            return null;
        }
        Optional<ShoppingCart> optionalShoppingCart = shoppingCartRepository.findByUserId(user.getId());
        if (optionalShoppingCart.isPresent()){
            Set<CartItem> cartItems = optionalShoppingCart.get().getItems();
            boolean exits = false;
            for (CartItem item:cartItems
            ) {
                if (item.getProduct().getId().equals(productId)){
                    exits = true;
                    item.setQuantity(item.getQuantity() + quantity);
                    if (item.getQuantity() > optionalProduct.get().getQuantity()){
                        item.setQuantity(optionalProduct.get().getQuantity());
                    }
                }
            }
            if (!exits){
                CartItem item = new CartItem();
                item.setProduct(optionalProduct.get());
                item.setQuantity(quantity);
                item.setShoppingCart(optionalShoppingCart.get());
                cartItems.add(item);
            }
            optionalShoppingCart.get().setItems(cartItems);
            return shoppingCartRepository.save(optionalShoppingCart.get());
        } else {
            ShoppingCart shoppingCart = new ShoppingCart();
            Set<CartItem> cartItems = new HashSet<>();
            CartItem cartItem = new CartItem();
            cartItem.setProduct(optionalProduct.get());
            if (quantity > optionalProduct.get().getQuantity()){
                quantity = optionalProduct.get().getQuantity();
            }
            cartItem.setQuantity(quantity);
            cartItems.add(cartItem);
            CartItemId cartItemId = new CartItemId(shoppingCart.getId(), productId);
            cartItem.setId(cartItemId);
            cartItem.setShoppingCart(shoppingCart);
            shoppingCart.setItems(cartItems);
            shoppingCart.setUser(user);
            return shoppingCartRepository.save(shoppingCart);
        }
    }

    public List<CartItem> getAllCart(Long userId){
        Optional<ShoppingCart> shoppingCart = shoppingCartRepository.findByUserId(userId);
        if (!shoppingCart.isPresent()){
            return null;
        }
        Long shoppingCartId = shoppingCart.get().getId();
        List<CartItem> cartItemList = cartItemRepository.findAllByShoppingCartId(shoppingCartId);
        return cartItemList;
    }


    public ShoppingCart deleteCartItem(Long shoppingCartId, Long productId){
        Optional<Product> optionalProduct = productRepository.findByIdAndStatus(productId, Status.ACTIVE);
        if (!optionalProduct.isPresent()){
            return null;
        }
        Optional<ShoppingCart> optionalShoppingCart = shoppingCartRepository.findById(shoppingCartId);
        if (!optionalShoppingCart.isPresent()){
            return null;
        }
        ShoppingCart shoppingCart = optionalShoppingCart.get();
        Set<CartItem> cartItems = shoppingCart.getItems();
        boolean exits = false;
        for (CartItem item:cartItems
        ) {
            if (item.getProduct().getId().equals(productId)){
                exits = true;
                cartItems.remove(item);
                CartItemId cartItemId = new CartItemId(shoppingCartId, productId);
                cartItemRepository.deleteById(cartItemId);
            }
        }
        if (!exits){
            return null;
        }
        shoppingCart.setItems(cartItems);
        shoppingCartRepository.save(shoppingCart);
        return shoppingCart;
    }

    public ShoppingCart updateQuantity(Long shoppingCartId, Long productId, int quantity){
        Optional<Product> optionalProduct = productRepository.findByIdAndStatus(productId, Status.ACTIVE);
        if (!optionalProduct.isPresent()){
            return null;
        }
        Optional<ShoppingCart> optionalShoppingCart = shoppingCartRepository.findById(shoppingCartId);
        if (!optionalShoppingCart.isPresent()){
            return null;
        }
        ShoppingCart shoppingCart = optionalShoppingCart.get();
        Set<CartItem> cartItems = shoppingCart.getItems();
        for (CartItem item:cartItems
        ) {
            if (item.getProduct().getId().equals(productId)){
                item.setQuantity(quantity);
                shoppingCart.setItems(cartItems);
                cartItemRepository.save(item);
            }
        }
        return shoppingCartRepository.save(shoppingCart);
    }
}
