import com.hdfclife.config.AppConfig;
import com.hdfclife.factory.PolicyFactory;
import com.hdfclife.config.AppConfig;
import com.hdfclife.exception.InvalidClaimException;
import com.hdfclife.exception.PolicyNotFoundException;
import com.hdfclife.exception.PolicyServiceException;
import com.hdfclife.exception.UnknownPolicyTypeException;
import com.hdfclife.factory.PolicyFactory;
import com.hdfclife.model.Claim;
import com.hdfclife.model.Policy;
import com.hdfclife.model.Urgency;
import com.hdfclife.observer.BranchLetterNotifier;
import com.hdfclife.observer.ClaimEventPublisher;
import com.hdfclife.observer.InAppNotifier;
import com.hdfclife.service.AuditLogger;
import com.hdfclife.service.ClaimService;
import com.hdfclife.store.PolicyStore;
import com.hdfclife.strategy.PremiumCalculator;
import com.hdfclife.strategy.UlipPremiumStrategy;

import java.util.Iterator;

public class Main {
    public static void main(String[] args) {
        System.out.println(AppConfig.INSTANCE.getCompanyName());

        PolicyStore store = new PolicyStore();

        Policy p1 = PolicyFactory.create("TERM", "HDFC-LIFE-1001", "Karan Kamble", 18500, "Active");
        Policy p2 = PolicyFactory.create("ULIP", "HDFC-LIFE-1002", "Aakash Kulkarni", 42000, "Active");
        Policy p3 = PolicyFactory.create("ENDOWMENT", "HDFC-LIFE-1003", "Suyash Deshmukh", 27000, "Lapsed");
        Policy p4 = PolicyFactory.create("TERM", "HDFC-LIFE-1004", "Vikram Shinde", 15200, "Active");
        Policy p5 = PolicyFactory.create("ULIP", "HDFC-LIFE-1005", "Sneha Joshi", 36000, "Active");
        Policy p6 = PolicyFactory.create("ENDOWMENT", "HDFC-LIFE-1006", "Karan Kamble", 22000, "Pending");

        store.addPolicy(p1);
        store.addPolicy(p2);
        store.addPolicy(p3);
        store.addPolicy(p4);
        store.addPolicy(p5);
        store.addPolicy(p6);

        System.out.println("All policies:");
        Iterator<Policy> iterator = store.getPolicyList().iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }

        System.out.println("Unique customer count: " + store.getCustomerNames().size());

        Policy found = store.findByPolicyNo("HDFC-LIFE-1004");
        if (found != null) {
            System.out.println("Lookup HDFC-LIFE-1004 -> " + found.getCustomerName());
        }

        System.out.println("TreeMap keys:");
        Iterator<String> treeIterator = store.getPolicyTreeMap().keySet().iterator();
        while (treeIterator.hasNext()) {
            System.out.println(treeIterator.next());
        }

        PremiumCalculator premiumCalculator = new PremiumCalculator(new UlipPremiumStrategy());
        Policy ulipPolicy = store.findByPolicyNo("HDFC-LIFE-1002");
        System.out.println("ULIP premium for HDFC-LIFE-1002 = " + premiumCalculator.calculate(ulipPolicy));

        ClaimEventPublisher publisher = new ClaimEventPublisher();
        publisher.addObserver(new InAppNotifier());
        publisher.addObserver(new BranchLetterNotifier());

        ClaimService claimService = new ClaimService(store);
        Claim highClaim = new Claim.Builder("HDFC-LIFE-1001", 25000, Urgency.HIGH)
                .hospitalName("Narayana Hospital")
                .remarks("Emergency care")
                .build();
        Claim mediumClaim = new Claim.Builder("HDFC-LIFE-1002", 30000, Urgency.MEDIUM)
                .hospitalName("Sahyadri Hospital")
                .remarks("Follow-up check")
                .build();
        Claim lowClaim = new Claim.Builder("HDFC-LIFE-1004", 18000, Urgency.LOW)
                .hospitalName("Bharat Clinic")
                .remarks("Routine review")
                .build();

        claimService.fileClaim(highClaim);
        claimService.fileClaim(mediumClaim);
        claimService.fileClaim(lowClaim);

        highClaim.updateStatus("APPROVED");
        publisher.notifyObservers(highClaim);

        System.out.println("Priority queue poll order:");
        while (!claimService.getClaimQueue().isEmpty()) {
            Claim claim = claimService.getClaimQueue().poll();
            System.out.println(claim.getUrgency());
        }

        try {
            if (store.findByPolicyNo("HDFC-LIFE-9999") == null) {
                throw new PolicyNotFoundException("Policy not found: HDFC-LIFE-9999");
            }
        } catch (PolicyNotFoundException e) {
            System.out.println(e.getMessage());
        }

        try {
            if (600000 > AppConfig.INSTANCE.getMaxClaimAmount()) {
                throw new InvalidClaimException("Claim amount exceeds maximum limit: 600000");
            }
        } catch (InvalidClaimException e) {
            System.out.println(e.getMessage());
        }

        try {
            PolicyFactory.create("INVALID", "HDFC-LIFE-7777", "Test User", 1000, "Active");
        } catch (UnknownPolicyTypeException e) {
            System.out.println(e.getMessage());
        }

        try (AuditLogger logger = new AuditLogger("audit.log")) {
            logger.logClaim(highClaim);
        } catch (PolicyServiceException e) {
            System.out.println(e.getMessage());
        }
    }
}
