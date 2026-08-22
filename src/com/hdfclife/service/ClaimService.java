package com.hdfclife.service;

import com.hdfclife.config.AppConfig;
import com.hdfclife.exception.InvalidClaimException;
import com.hdfclife.exception.PolicyNotFoundException;
import com.hdfclife.model.Claim;
import com.hdfclife.store.PolicyStore;

import java.util.PriorityQueue;

public class ClaimService {
    private final PolicyStore policyStore;
    private final PriorityQueue<Claim> claimQueue = new PriorityQueue<>();

    public ClaimService(PolicyStore policyStore) {
        this.policyStore = policyStore;
    }

    public void fileClaim(Claim claim) {
        if (policyStore.findByPolicyNo(claim.getPolicyNo()) == null) {
            throw new PolicyNotFoundException("Policy not found: " + claim.getPolicyNo());
        }

        if (claim.getClaimAmount() <= 0 || claim.getClaimAmount() > AppConfig.INSTANCE.getMaxClaimAmount()) {
            throw new InvalidClaimException("Claim amount exceeds maximum limit: " + claim.getClaimAmount());
        }

        claimQueue.add(claim);
    }

    public PriorityQueue<Claim> getClaimQueue() {
        return claimQueue;
    }
}
