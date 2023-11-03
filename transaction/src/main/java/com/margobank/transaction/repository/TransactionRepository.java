package com.margobank.transaction.repository;

import com.margobank.transaction.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Long> {
    Page<Transaction> findByCustomerIdOrderByCreatedDateDesc(Pageable pageable, String customerId);
    Page<Transaction> findByDescriptionLikeOrderByCreatedDateDesc(Pageable pageable, String description);
    Page<Transaction> findByAccountNumberInOrderByCreatedDateDesc(Pageable pageable, List<Long> accountNumbers);
    Page<Transaction> findAllByOrderByCreatedDateDesc(Pageable pageable);
}
