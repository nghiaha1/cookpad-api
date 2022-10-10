package com.project_4.cookpad_api.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "cart_items")
public class CartItem {
    @EmbeddedId
    private CartItemId id;
    private String productName;
    private String productImage;
    private int quantity;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;
    @ManyToOne(cascade = CascadeType.ALL)
    @MapsId("shopping_cart_id")
    @JoinColumn(name = "shopping_cart_id")
    private ShoppingCart shoppingCart;

    @OneToOne(cascade = CascadeType.ALL)
    @MapsId("product_id")
    @JoinColumn(name = "product_id")
    private Product product;

    public BigDecimal totalPrice(){
        if (this.quantity > 0){
            this.totalPrice = this.unitPrice.multiply(new BigDecimal(this.quantity));
        }else {
            this.totalPrice = this.unitPrice;
        }
        return totalPrice;
    }
}
