package com.project_4.cookpad_api.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class CartItemId implements Serializable {
    @Column(name = "shopping_cart_id")
    private long shoppingCartId; // thuộc về shopping Cart nào.
    @Column(name = "product_id")
    private long productId;
}
