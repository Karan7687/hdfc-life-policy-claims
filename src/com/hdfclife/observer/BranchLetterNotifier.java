package com.hdfclife.observer;

import com.hdfclife.model.Claim;

public class BranchLetterNotifier implements ClaimObserver {
    @Override
    public void onClaimUpdate(Claim claim) {
        System.out.println("BranchLetterNotifier: Dispatching letter for " + claim.getPolicyNo() + " with status " + claim.getStatus());
    }
}
