package com.hdfclife.strategy;

import com.hdfclife.model.Policy;

public interface PremiumStrategy {
    int calculatePremium(Policy policy);
}