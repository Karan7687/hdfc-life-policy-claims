# HDFC Life Policy Claims Console

This project is a simple Java console application for managing HDFC Life policies, calculating premium values, filing claims, and notifying observers when claim status changes.

## How to compile

```bash
cd "/Users/KARAN.K129/Projects/HDFC Life Policy Claims"
/Library/Java/JavaVirtualMachines/temurin-25.jdk/Contents/Home/bin/javac -d out $(find src -name "*.java")
```

## How to run

```bash
cd "/Users/KARAN.K129/Projects/HDFC Life Policy Claims"
/Library/Java/JavaVirtualMachines/temurin-25.jdk/Contents/Home/bin/java -cp out Main
```

## Project structure

- Model: Policy and policy subclasses
- Store: PolicyStore
- Factory: PolicyFactory
- Singleton: AppConfig as enum singleton
- Strategy: PremiumStrategy and implementation classes
- Builder: Claim.Builder
- Observer: ClaimObserver, ClaimEventPublisher, InAppNotifier, BranchLetterNotifier
- Service: ClaimService, AuditLogger
- Exceptions: PolicyServiceException hierarchy

## Requirements covered

- Seed policies are created through PolicyFactory
- Policies are stored in collections
- Premium is calculated using strategy pattern
- Claims are ordered by urgency using PriorityQueue
- Claim status changes notify all observers
- Exceptions are handled and printed in Main
- Audit logging happens with try-with-resources and AutoCloseable
