package com.example.demo.repository;

import com.example.demo.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, OrderDetailPk> {
    int findProductQuantityByOrderOrderIdAndUserUserIdAndProductProductId(String orderId, String userId, String productId);

    void updateProductQuantityByOrderOrderIdAndUserUserIdAndProductProductId(OrderDetail orderDetail, String userId, String productId);

    void deleteByOrderAndUserAndProduct(Order order, User user, Product product);

}
