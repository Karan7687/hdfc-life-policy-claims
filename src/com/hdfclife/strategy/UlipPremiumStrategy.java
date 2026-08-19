package com.hdfclife.strategy;

import com.hdfclife.model.Policy;

public class UlipPremiumStrategy implements PremiumStrategy {
    @Override
    public int calculatePremium(Policy policy) {
        return policy.getBasePremium() * 112 / 100;
    }
}