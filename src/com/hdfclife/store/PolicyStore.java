package com.hdfclife.store;

import com.hdfclife.model.Policy;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.List;
import java.util.Set;
import java.util.Map;

public class PolicyStore {
    private final List<Policy> policyList = new ArrayList<>();
    private final Set<String> customerNames = new HashSet<>();
    private final Map<String, Policy> policyMap = new HashMap<>();
    private final Map<String, Policy> policyTreeMap = new TreeMap<>();

    public void addPolicy(Policy policy) {
        policyList.add(policy);
        customerNames.add(policy.getCustomerName());
        policyMap.put(policy.getPolicyNo(), policy);
        policyTreeMap.put(policy.getPolicyNo(), policy);
    }

    public List<Policy> getPolicyList() { return policyList; }
    public Set<String> getCustomerNames() { return customerNames; }
    public Map<String, Policy> getPolicyMap() { return policyMap; }
    public Map<String, Policy> getPolicyTreeMap() { return policyTreeMap; }

    public Policy findByPolicyNo(String policyNo) {
        return policyMap.get(policyNo);
    }
}
