package com.project_4.cookpad_api.repository;

import com.project_4.cookpad_api.entity.ShoppingCart;
import com.project_4.cookpad_api.entity.myenum.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    Optional<ShoppingCart> findByUserIdAndStatus(Long userId, Status status);

    Optional<ShoppingCart> findByIdAndStatus(Long shoppingCartId, Status status);
}
