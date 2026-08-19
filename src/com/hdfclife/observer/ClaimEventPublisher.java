package com.hdfclife.observer;

import com.hdfclife.model.Claim;
import java.util.ArrayList;
import java.util.List;

public class ClaimEventPublisher {
    private final List<ClaimObserver> observers = new ArrayList<>();

    public void addObserver(ClaimObserver observer) {
        observers.add(observer);
    }

    public void notifyObservers(Claim claim) {
        for (int i = 0; i < observers.size(); i++) {
            observers.get(i).onClaimUpdate(claim);
        }
    }
}
