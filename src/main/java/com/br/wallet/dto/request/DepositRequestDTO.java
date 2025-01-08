package com.br.wallet.dto.request;

import java.math.BigDecimal;

public class DepositRequestDTO {
    private BigDecimal amount;

    public DepositRequestDTO() {
    }

    public DepositRequestDTO(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}