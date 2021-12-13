package com.example.AEPB.AirCoin.model;

import lombok.Data;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Data
public class AirCoin {
    private BigDecimal totalAmount;

    public AirCoin(BigDecimal totalAmount){
        this.totalAmount = totalAmount;
    }
    public BigDecimal getTotalAmount() {
        return totalAmount.setScale(3, RoundingMode.DOWN);
    }
}
