package com.hdfclife.strategy;

import com.hdfclife.model.Policy;

public class TermPremiumStrategy implements PremiumStrategy {
    @Override
    public int calculatePremium(Policy policy) {
        return policy.getBasePremium() * 100 / 100;
    }
}
