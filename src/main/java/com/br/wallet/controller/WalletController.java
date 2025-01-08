package com.br.wallet.controller;

import com.br.wallet.dto.request.CreateWalletRequestDTO;
import com.br.wallet.dto.request.DepositRequestDTO;
import com.br.wallet.dto.request.TransferRequestDTO;
import com.br.wallet.dto.request.WithdrawRequestDTO;
import com.br.wallet.dto.response.TransactionResponseDTO;
import com.br.wallet.dto.response.WalletResponseDTO;
import com.br.wallet.service.WalletService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Tag(name = "Wallet Management", description = "Operations related to wallet and transaction management")
@RestController
@RequestMapping("/api/wallets")
public class WalletController {

    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @Operation(
            summary = "Create a new wallet",
            description = "Creates a wallet for a specific user with an initial balance of zero."
    )
    @PostMapping
    public ResponseEntity<WalletResponseDTO> createWallet(
            @RequestBody CreateWalletRequestDTO requestDTO) {
        return ResponseEntity.ok(walletService.createWallet(requestDTO));
    }

    @Operation(
            summary = "Get current balance",
            description = "Retrieves the current balance of a user's wallet."
    )
    @GetMapping("/{userId}/balance")
    public ResponseEntity<WalletResponseDTO> getBalance(
            @Parameter(description = "The ID of the user whose wallet balance is being retrieved", required = true)
            @PathVariable Long userId) {
        return ResponseEntity.ok(walletService.getBalance(userId));
    }

    @Operation(
            summary = "Get historical balance",
            description = "Retrieves the wallet balance as of a specific timestamp (ISO 8601 format)."
    )
    @GetMapping("/{userId}/historical-balance")
    public ResponseEntity<BigDecimal> getHistoricalBalance(
            @Parameter(description = "The ID of the user whose wallet historical balance is being retrieved", required = true)
            @PathVariable Long userId,
            @Parameter(description = "Timestamp in ISO 8601 format", required = true, example = "2025-01-06T15:00:00")
            @RequestParam String timestamp) {
        LocalDateTime time = LocalDateTime.parse(timestamp);
        return ResponseEntity.ok(walletService.getHistoricalBalance(userId, time));
    }

    @Operation(
            summary = "Make a deposit",
            description = "Adds a specified amount to a user's wallet balance."
    )
    @PostMapping("/{userId}/deposit")
    public ResponseEntity<WalletResponseDTO> deposit(
            @Parameter(description = "The ID of the user receiving the deposit", required = true)
            @PathVariable Long userId,
            @RequestBody DepositRequestDTO requestDTO) {
        return ResponseEntity.ok(walletService.deposit(userId, requestDTO));
    }

    @Operation(
            summary = "Make a withdrawal",
            description = "Withdraws a specified amount from a user's wallet balance."
    )
    @PostMapping("/{userId}/withdraw")
    public ResponseEntity<WalletResponseDTO> withdraw(
            @Parameter(description = "The ID of the user making the withdrawal", required = true)
            @PathVariable Long userId,
            @RequestBody WithdrawRequestDTO requestDTO) {
        return ResponseEntity.ok(walletService.withdraw(userId, requestDTO));
    }

    @Operation(
            summary = "Make a transfer",
            description = "Transfers a specified amount between two wallets."
    )
    @PostMapping("/transfer")
    public ResponseEntity<Void> transfer(
            @RequestBody TransferRequestDTO requestDTO) {
        walletService.transfer(requestDTO);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "List user transactions",
            description = "Retrieves all transactions associated with a user's wallet."
    )
    @GetMapping("/{userId}/transactions")
    public ResponseEntity<List<TransactionResponseDTO>> getTransactions(
            @Parameter(description = "The ID of the user whose transactions are being listed", required = true)
            @PathVariable Long userId) {
        return ResponseEntity.ok(walletService.getTransactions(userId));
    }
}