package com.project_4.cookpad_api.entity;
import com.project_4.cookpad_api.entity.base.BaseEntity;
import com.project_4.cookpad_api.entity.myenum.OrderStatus;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "orders")
public class Order extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;
    private BigDecimal totalPrice;
    private OrderStatus status;


    @OneToMany(mappedBy = "order")
    private Set<OrderDetail> orderDetails;

    public void calculateTotalPrice() {
        this.totalPrice = new BigDecimal(0);
        if (this.orderDetails != null && this.orderDetails.size() > 0) {
            for (OrderDetail orderDetail :
                    orderDetails) {
                BigDecimal orderDetailTotalPrice = orderDetail.getUnitPrice().multiply(new BigDecimal(orderDetail.getQuantity()));
                this.totalPrice = this.totalPrice.add(orderDetailTotalPrice);
            }
        }
    }

    public void addTotalPrice(OrderDetail orderDetail) {
        if (this.totalPrice == null) {
            this.totalPrice = new BigDecimal(0);
        }
        BigDecimal quantityInBigDecimal = new BigDecimal(orderDetail.getQuantity());
        this.totalPrice = this.totalPrice.add(
                orderDetail.getUnitPrice().multiply(quantityInBigDecimal));
    }
}
