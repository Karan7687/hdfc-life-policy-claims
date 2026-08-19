package com.hdfclife.strategy;

import com.hdfclife.model.Policy;

public class EndowmentPremiumStrategy implements PremiumStrategy {
    @Override
    public int calculatePremium(Policy policy) {
        return policy.getBasePremium() * 108 / 100;
    }
}
