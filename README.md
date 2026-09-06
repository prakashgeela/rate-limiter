# rate-limiter
1. Functional Requirements (In-Scope)
   A. Request Processing: The system must provide a primary interface method, e.g., boolean allowRequest(String clientId), to evaluate whether an incoming request should be allowed or blocked.
   B. Client Identification: Requests are rate-limited based on a unique key (String clientId), which can represent a userId, ipAddress, or API key.
   C. Configurable Limits: The rate limit threshold ($N$ requests) and time window duration ($T$ units, e.g., seconds or minutes) must be configurable per instance or rule definition.
   D. Immediate Feedback: Exceeding the limit results in immediate rejection (returning false or throwing a custom exception, e.g., RateLimitExceededException).

2. Non-Functional Requirements (In-Scope)
   A. Thread Safety & High Concurrency: Multiple concurrent threads checking rate limits for the same or different clients must be handled safely without race conditions or performance-killing global locks.
   B. Low Latency: The decision logic must run in $O(1)$ time with sub-millisecond overhead to avoid slowing down API calls.Memory Management: Stale or inactive client records must be safely cleaned up or evicted to prevent out-of-memory errors over long operational runs.
   C. Extensibility & Decoupling:
   Algorithm Strategy: The core interface should allow plugging in different rate-limiting algorithms (e.g., Fixed Window, Sliding Window, Token Bucket).
   Storage Abstraction: The storage layer should be decoupled via an interface so that the default in-memory storage could theoretically be swapped for a distributed store without changing the core business logic.



Epic: In-Memory Rate Limiter Core SDK
Sprint 1: Core Framework & Token Bucket Strategy (MVP)
RATE-1: Core Contracts & Client Orchestrator

Scope: Create RateLimitAlgo interface, RateLimitClient entry point, and 
custom exceptions (RateLimitException).

Deliverables:

RateLimitAlgo interface with isAllowed(String key): boolean.

RateLimitClient supporting registry/routing of algorithms.

Unit test stubs demonstrating end-to-end interface contracts.

RATE-2: Token Bucket State & Math Implementation

Scope: Implement BucketTokenState for a single client/bucket.

Deliverables:

State variables: capacity, refillRatePerMs, tokens, lastRefillTime.

Lazy refill calculation (refill()) and consumption (tryConsume()).

Per-instance thread safety (concurrency handling).

Unit tests validating fractional token accrual, bursts up to capacity, and depletion.

RATE-3: Token Bucket Algorithm & In-Memory Store

Scope: Implement BucketTokenAlgo managing multiple clients.

Deliverables:

ConcurrentHashMap<String, BucketTokenState> mapping client/route keys.

Atomic retrieval and bucket initialization using computeIfAbsent.

Concurrency unit tests simulating high-throughput parallel requests for distinct and identical keys.

RATE-4: provide an interface so we can avoid hardcoding for the storage
Sprint 2: Extensibility & Secondary Strategy
RATE-5: Fixed Window Counter Implementation

Scope: Implement the alternate strategy to prove pluggability.

Deliverables:

FixedWindowCounterState (window boundary detection, request counter, reset logic).

FixedWindowCounterAlgo implementing RateLimitAlgo.

Comparative integration test showing seamless switching between BucketTokenAlgo and FixedWindowCounterAlgo in RateLimitClient.

RATE-6: Composite Routing & Multi-Endpoint Support

Scope: Extend key generation to support per-endpoint and per-user granularity.

Deliverables:

Key formatting helper (apiName + ":" + clientId).

Configurable per-route rate limit rule mapper in RateLimitClient.

Sprint 3: Operational Hardening (Non-Functional Requirements)
RATE-7: Inactive State Eviction & Memory Leaks Prevention

Scope: Prevent unbounded growth of in-memory maps over time.

Deliverables:

Background cleanup task or TTL-based eviction for idle clients/keys.

Memory benchmark tests under large key cardinality.


rate-limiter/
├── pom.xml (or build.gradle)
└── src/
├── main/
│   └── java/
│       └── com/
│           └── ratelimiter/
│               │
│               ├── RateLimitClient.java         // Public entry point / Facade
│               │
│               ├── algorithm/                   // Strategy Pattern contracts & implementations
│               │   ├── RateLimitAlgo.java       // Strategy interface
│               │   ├── BucketTokenAlgo.java     // Token Bucket implementation
│               │   └── FixedWindowCounterAlgo.java
│               │
│               ├── model/                       // State representations & config
│               │   ├── BucketTokenState.java    // In-memory token math & locks
│               │   └── FixedWindowState.java
│               │
│               ├── store/                       // Storage SPI (extensibility for Redis later)
│               │   ├── RateLimitStore.java      // Storage interface
│               │   └── InMemoryRateLimitStore.java // In-memory ConcurrentHashMap store
│               │
│               └── exception/                   // Domain exceptions
│                   └── RateLimitException.java
│
└── test/
└── java/
└── com/
└── ratelimiter/
├── RateLimitClientTest.java     // Integration/Driver test
├── BucketTokenStateTest.java    // Math & timing unit tests
└── ConcurrencyTest.java         // Multi-threaded tests


UML:

https://app.diagrams.net/#Lrate-limit.drawio#%7B%22pageId%22%3A%22nKiRzjW_MFvlQvxHT8Au%22%7D