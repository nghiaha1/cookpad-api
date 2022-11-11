package com.project_4.cookpad_api.repository;

import com.project_4.cookpad_api.entity.CartItem;
import com.project_4.cookpad_api.entity.CartItemId;
import com.project_4.cookpad_api.entity.Product;
import com.project_4.cookpad_api.entity.ShoppingCart;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, CartItemId> {
    List<CartItem> findAllByShoppingCartId(Long shoppingCartId);
}