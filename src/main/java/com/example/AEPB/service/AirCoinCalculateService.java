package com.example.AEPB.service;

import com.example.AEPB.model.AirCoin;
import com.example.AEPB.exception.AmountOutOfRangeException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class AirCoinCalculateService {
    private static final int MAX_AMOUNT = 5000000;

    public boolean isEqualAirCoinAmount(AirCoin airCoinA, AirCoin airCoinB) {
        BigDecimal amountA = airCoinA.getTotalAmount();
        BigDecimal amountB = airCoinB.getTotalAmount();
        amountValidation(amountA);
        amountValidation(amountB);
        return amountA.compareTo(amountB) == 0;
    }

    private void amountValidation(BigDecimal amount){
        BigDecimal maxAmount = new BigDecimal(MAX_AMOUNT);
        if (amount.compareTo(maxAmount)>0){
            throw new AmountOutOfRangeException(String.format("Air coin amount: %s over 5000000.", amount));
        } else if (amount.compareTo(BigDecimal.ZERO)<0) {
            throw new AmountOutOfRangeException(String.format("Air coin amount: %s less than 0.", amount));
        }
    }
}
