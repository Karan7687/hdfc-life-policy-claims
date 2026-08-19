package com.hdfclife.strategy;

import com.hdfclife.model.Policy;

public class PremiumCalculator {
    private PremiumStrategy strategy;

    public PremiumCalculator(PremiumStrategy strategy) {
        this.strategy = strategy;
    }

    public void setStrategy(PremiumStrategy strategy) {
        this.strategy = strategy;
    }

    public int calculate(Policy policy) {
        return strategy.calculatePremium(policy);
    }
}