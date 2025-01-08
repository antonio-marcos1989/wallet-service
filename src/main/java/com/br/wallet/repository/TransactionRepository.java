package com.br.wallet.repository;

import com.br.wallet.entity.Transaction;
import com.br.wallet.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByWalletAndTimestampBefore(Wallet wallet, LocalDateTime timestamp);
}
