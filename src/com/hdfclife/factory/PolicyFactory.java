package com.hdfclife.factory;

import com.hdfclife.exception.UnknownPolicyTypeException;
import com.hdfclife.model.EndowmentPolicy;
import com.hdfclife.model.Policy;
import com.hdfclife.model.TermLifePolicy;
import com.hdfclife.model.UlipPolicy;

public class PolicyFactory {
    public static Policy create(String type, String policyNo, String customer, int premium, String status) {
        if (type == null) {
            throw new UnknownPolicyTypeException("Policy type cannot be null");
        }
        switch (type.toUpperCase()) {
            case "TERM":
                return new TermLifePolicy(policyNo, customer, premium, status);
            case "ULIP":
                return new UlipPolicy(policyNo, customer, premium, status);
            case "ENDOWMENT":
                return new EndowmentPolicy(policyNo, customer, premium, status);
            default:
                throw new UnknownPolicyTypeException("Unknown policy type: " + type);
        }
    }
}