package com.hdfclife.model;

public class EndowmentPolicy extends Policy {
    public EndowmentPolicy(String policyNo, String customerName, int basePremium, String status) {
        super(policyNo, customerName, "ENDOWMENT", basePremium, status);
    }
}