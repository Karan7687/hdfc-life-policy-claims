package com.hdfclife.service;

import com.hdfclife.exception.PolicyServiceException;
import com.hdfclife.model.Claim;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class AuditLogger implements AutoCloseable {
    private final BufferedWriter writer;

    public AuditLogger(String fileName) {
        try {
            this.writer = new BufferedWriter(new FileWriter(fileName, false));
        } catch (IOException e) {
            throw new PolicyServiceException("Failed to open audit log", e);
        }
    }

    public void logClaim(Claim claim) {
        try {
            writer.write(claim.getPolicyNo() + " | " + claim.getUrgency() + " | " + claim.getClaimAmount() + " | " + claim.getStatus());
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            throw new PolicyServiceException("Failed to write audit log", e);
        }
    }

    @Override
    public void close() {
        try {
            writer.close();
        } catch (IOException e) {
            throw new PolicyServiceException("Failed to close audit log", e);
        }
    }
}
