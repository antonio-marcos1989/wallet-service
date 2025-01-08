package com.br.wallet.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.br.wallet.dto.request.CreateWalletRequestDTO;
import com.br.wallet.dto.request.DepositRequestDTO;
import com.br.wallet.dto.request.TransferRequestDTO;
import com.br.wallet.dto.request.WithdrawRequestDTO;
import com.br.wallet.dto.response.TransactionResponseDTO;
import com.br.wallet.dto.response.WalletResponseDTO;
import com.br.wallet.entity.Transaction;
import com.br.wallet.entity.Wallet;
import com.br.wallet.repository.TransactionRepository;
import com.br.wallet.repository.WalletRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WalletService {

    private static final Logger logger = LoggerFactory.getLogger(WalletService.class);

    private final WalletRepository walletRepository;
    private final TransactionRepository transactionRepository;

    public WalletService(WalletRepository walletRepository, TransactionRepository transactionRepository) {
        this.walletRepository = walletRepository;
        this.transactionRepository = transactionRepository;
    }

    public WalletResponseDTO createWallet(CreateWalletRequestDTO requestDTO) {
        logger.info("Starting wallet creation for User ID: {}", requestDTO.getUserId());

        Wallet wallet = new Wallet(requestDTO.getUserId(), BigDecimal.ZERO);
        wallet = walletRepository.save(wallet);

        logger.info("Wallet successfully created for User ID: {}, Wallet ID: {}", wallet.getUserId(), wallet.getId());
        return new WalletResponseDTO(wallet.getId(), wallet.getUserId(), wallet.getBalance());
    }

    public WalletResponseDTO getBalance(Long userId) {
        logger.info("Retrieving current balance for User ID: {}", userId);

        Wallet wallet = walletRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Wallet not found"));

        logger.info("Balance retrieved for User ID: {}, Balance: {}", userId, wallet.getBalance());
        return new WalletResponseDTO(wallet.getId(), wallet.getUserId(), wallet.getBalance());
    }

    public BigDecimal getHistoricalBalance(Long userId, LocalDateTime timestamp) {
        logger.info("Retrieving historical balance for User ID: {} at timestamp: {}", userId, timestamp);

        Wallet wallet = walletRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Wallet not found"));
        List<Transaction> transactions = transactionRepository.findByWalletAndTimestampBefore(wallet, timestamp);

//        return transactions.stream()
//                .map(Transaction::getAmount)
//                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal historicalBalance = transactions.stream()
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        logger.info("Historical balance retrieved for User ID: {}, Balance: {}", userId, historicalBalance);
        return historicalBalance;
    }

    @Transactional
    public WalletResponseDTO deposit(Long userId, DepositRequestDTO requestDTO) {
        logger.info("Starting deposit for User ID: {}, Amount: {}", userId, requestDTO.getAmount());

        Wallet wallet = walletRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Wallet not found"));
        wallet.setBalance(wallet.getBalance().add(requestDTO.getAmount()));
        Transaction transaction = new Transaction(requestDTO.getAmount(), LocalDateTime.now(), wallet);
        transactionRepository.save(transaction);

        logger.info("Deposit successful for User ID: {}, New Balance: {}", userId, wallet.getBalance());

        return new WalletResponseDTO(wallet.getId(), wallet.getUserId(), wallet.getBalance());
    }

    @Transactional
    public WalletResponseDTO withdraw(Long userId, WithdrawRequestDTO requestDTO) {
        logger.info("Starting withdrawal for User ID: {}, Amount: {}", userId, requestDTO.getAmount());

        Wallet wallet = walletRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Wallet not found"));
        if (wallet.getBalance().compareTo(requestDTO.getAmount()) < 0) {
            throw new RuntimeException("Insufficient funds");
        }
        wallet.setBalance(wallet.getBalance().subtract(requestDTO.getAmount()));
        Transaction transaction = new Transaction(requestDTO.getAmount().negate(), LocalDateTime.now(), wallet);
        transactionRepository.save(transaction);

        logger.info("Withdrawal successful for User ID: {}, New Balance: {}", userId, wallet.getBalance());

        return new WalletResponseDTO(wallet.getId(), wallet.getUserId(), wallet.getBalance());
    }

    @Transactional
    public void transfer(TransferRequestDTO requestDTO) {
        logger.info("Starting transfer from User ID: {} to User ID: {}, Amount: {}",
                requestDTO.getFromUserId(), requestDTO.getToUserId(), requestDTO.getAmount());

        withdraw(requestDTO.getFromUserId(), new WithdrawRequestDTO(requestDTO.getAmount()));
        deposit(requestDTO.getToUserId(), new DepositRequestDTO(requestDTO.getAmount()));

        logger.info("Transfer successful from User ID: {} to User ID: {}, Amount: {}",
                requestDTO.getFromUserId(), requestDTO.getToUserId(), requestDTO.getAmount());
    }

    public List<TransactionResponseDTO> getTransactions(Long userId) {
        logger.info("Retrieving transactions for User ID: {}", userId);

        Wallet wallet = walletRepository.findByUserId(userId)
                .orElseThrow(() -> {
                    logger.error("Wallet not found for User ID: {}", userId);
                    return new RuntimeException("Wallet not found");
                });

        List<TransactionResponseDTO> transactions = wallet.getTransactions().stream()
                .map(transaction -> new TransactionResponseDTO(transaction.getAmount(), transaction.getTimestamp()))
                .collect(Collectors.toList());

        logger.info("Transactions retrieved for User ID: {}, Total Transactions: {}", userId, transactions.size());
        return transactions;
    }
}