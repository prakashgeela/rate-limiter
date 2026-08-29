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
