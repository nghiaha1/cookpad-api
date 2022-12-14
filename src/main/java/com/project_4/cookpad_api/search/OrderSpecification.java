package com.project_4.cookpad_api.search;
import com.project_4.cookpad_api.entity.Order;
import com.project_4.cookpad_api.entity.OrderDetail;
import com.project_4.cookpad_api.entity.Product;
import com.project_4.cookpad_api.entity.User;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.time.LocalDateTime;


public class OrderSpecification implements Specification<Order> {
    private SearchCriteria criteria;

    public OrderSpecification(SearchCriteria criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<Order> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        switch (criteria.getOperator()) {
            case EQUALS:
                return criteriaBuilder.equal(
                        root.get(criteria.getKey()),
                        criteria.getValue());
            case LIKE:
                return criteriaBuilder.like(
                        root.get(criteria.getKey()),
                        "%" + criteria.getValue() + "%");
            case GREATER_THAN:
                return criteriaBuilder.greaterThan(
                        root.get(criteria.getKey()),
                        String.valueOf(criteria.getValue()));

            case GREATER_THAN_OR_EQUALS:
                if (root.get(criteria.getKey()).getJavaType() == LocalDateTime.class) {
                    return criteriaBuilder.greaterThanOrEqualTo(
                            root.get(criteria.getKey()), (LocalDateTime) criteria.getValue());
                } else {
                    return criteriaBuilder.greaterThanOrEqualTo(
                            root.get(criteria.getKey()),
                            String.valueOf(criteria.getValue()));
                }
            case LESS_THAN:
                return criteriaBuilder.lessThan(
                        root.get(criteria.getKey()),
                        String.valueOf(criteria.getValue()));
            case LESS_THAN_OR_EQUALS:
                if (root.get(criteria.getKey()).getJavaType() == LocalDateTime.class) {
                    return criteriaBuilder.lessThanOrEqualTo(
                            root.get(criteria.getKey()), (LocalDateTime) criteria.getValue());
                } else {
                    return criteriaBuilder.lessThanOrEqualTo(
                            root.get(criteria.getKey()),
                            String.valueOf(criteria.getValue()));
                }
            case JOIN_DETAIL_PRODUCT:
                Join<OrderDetail, Product> orderDetailProductJoin = root.join("orderDetails").join("product");
                Predicate predicate = criteriaBuilder.or(
                        criteriaBuilder.like(root.get("id"), "%" + criteria.getValue() + "%"),
                        criteriaBuilder.like(
                                orderDetailProductJoin.get(criteria.getKey()), "%" + criteria.getValue() + "%"
                        ));
                return predicate;
            case JOIN_USER:
                From<Order, User> orderUserJoin = root.join("user");
                Predicate predicateOrderCustomer = criteriaBuilder.like(
                        // ho???c t??m trong b???ng customer b???n ghi c?? name gi???ng v???i gi?? tr???
                        orderUserJoin.get(criteria.getKey()), "%" + criteria.getValue() + "%"
                );
//                return criteriaBuilder.like(
//                        // ho???c t??m trong b???ng customer b???n ghi c?? name gi???ng v???i gi?? tr???
//                        orderUserJoin.get(criteria.getKey()), "%" + criteria.getValue() + "%"
//                );
            case JOIN:
                Join<OrderDetail, Product> orderDetailProductJoin1 = root.join("orderDetails").join("product");
                Predicate predicate1 = criteriaBuilder.or(
                        // t??m trong order b???n ghi c?? id gi???ng gi?? tr??? truy???n v??o
                        criteriaBuilder.like(root.get("id"), "%" + criteria.getValue() + "%"),
                        // ho???c t??m trong b???ng product b???n ghi c?? name gi???ng v???i gi?? tr???
                        criteriaBuilder.like(orderDetailProductJoin1.get("name"), "%" + criteria.getValue() + "%")
                );
                return predicate1;
        }
        return null;
    }
}
