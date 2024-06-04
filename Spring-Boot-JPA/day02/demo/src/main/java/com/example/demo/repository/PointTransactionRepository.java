package com.example.demo.repository;

import com.example.demo.domain.PointTransaction;
import com.example.demo.domain.PointTransactionPk;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface PointTransactionRepository extends JpaRepository<PointTransaction, PointTransactionPk> {
    List<PointTransaction> findByPointTransactionDateBetween(LocalDateTime startPointTransactionDate, LocalDateTime endPointTransactionDate);

    List<PointTransaction> findByPointTransactionDateBefore(LocalDateTime startPointTransactionDate);

    List<PointTransaction> findByPointTransactionDateAfter(LocalDateTime endPointTransactionDate);

    List<PointTransaction> findByPointTransactionAmountGreaterThan(int pointTransactionAmount);

    List<PointTransaction> findByPointTransactionAmountLessThan(int pointTransactionAmount);

    List<PointTransaction> findByPointTransactionAmountBetween(int pointTransactionAmount1, int pointTransactionAmount2);
}
