package com.hdfclife.observer;

import com.hdfclife.model.Claim;

public class InAppNotifier implements ClaimObserver {
    @Override
    public void onClaimUpdate(Claim claim) {
        System.out.println("InAppNotifier: Claim status for " + claim.getPolicyNo() + " updated to " + claim.getStatus());
    }
}
