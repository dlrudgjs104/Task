package com.example.demo.repository;

import com.example.demo.domain.Cart;
import com.example.demo.domain.CartPk;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, CartPk> {
    int findProductQuantityByUserUserIdAndProductProductId(String userId, String productId);

    void updateQuantityByUserUserIdAndProductProductId(String userId, String productId, int quantity);

    void updateOrderCheckByUserUserIdAndProductProductId(String userId, String productId);

    void deleteCartByUserUserId(String userId);

    void deleteProductByUserUserIdAndProductProductId(String userId, String productId);
}
