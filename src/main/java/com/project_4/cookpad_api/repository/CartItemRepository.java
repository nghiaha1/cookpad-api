package com.project_4.cookpad_api.repository;

import com.project_4.cookpad_api.entity.CartItem;
import com.project_4.cookpad_api.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<Product, Long> {
    @Query(value = "select * from cart_items where shopping_cart_id = ?", nativeQuery = true)
    Page<CartItem> findAllByShoppingCartId(long id, Pageable pageable);
}