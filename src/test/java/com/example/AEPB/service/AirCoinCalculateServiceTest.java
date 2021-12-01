package com.example.AEPB.service;

import com.example.AEPB.exception.AmountOutOfRangeException;
import com.example.AEPB.model.AirCoin;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class AirCoinCalculateServiceTest {

    private final AirCoinCalculateService airCoinCalculateService = new AirCoinCalculateService();

    @Test
    public void should_return_true_when_give_same_values() {
        // given
        AirCoin airCoinA = new AirCoin(new BigDecimal("0.123"));
        AirCoin airCoinB = new AirCoin(new BigDecimal("0.123"));

        // when
        boolean isResult = airCoinCalculateService.isEqualAirCoinAmount(airCoinA, airCoinB);

        // then
        assertTrue(isResult);
    }

    @Test
    public void should_return_false_when_give_different_values() {
        // given
        AirCoin airCoinA = new AirCoin(new BigDecimal("0.123"));
        AirCoin airCoinB = new AirCoin(new BigDecimal("0.1"));

        // when
        boolean result = airCoinCalculateService.isEqualAirCoinAmount(airCoinA, airCoinB);

        // then
        assertFalse(result);
    }

    @Test(expected = AmountOutOfRangeException.class)
    public void should_throw_out_of_range_exception_when_over_limit() {
        // given
        AirCoin airCoinA = new AirCoin(new BigDecimal("5000000.001"));
        AirCoin airCoinB = new AirCoin(new BigDecimal("0.1"));

        // when
        airCoinCalculateService.isEqualAirCoinAmount(airCoinA, airCoinB);
    }

    @Test(expected = AmountOutOfRangeException.class)
    public void should_throw_out_of_range_exception_when_deliver_negative_number() {
        // given
        AirCoin airCoinA = new AirCoin(new BigDecimal("-1"));
        AirCoin airCoinB = new AirCoin(new BigDecimal("0.1"));

        // when
        airCoinCalculateService.isEqualAirCoinAmount(airCoinA, airCoinB);
    }
}
