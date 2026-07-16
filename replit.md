# LedgerMesh

A microservices-based personal finance platform built with Spring Boot 3/4, Spring Cloud, Java 17+, H2 (dev) / PostgreSQL (prod), and Eureka service discovery.

## Services

| Service | Port | Description |
|---|---|---|
| Config Server | 8888 | Spring Cloud Config — serves `config-repo/*.yml` |
| Discovery Server | 8761 | Eureka — service registry UI at `/` |
| Account Service | 8080 | REST API — manage accounts and transactions |

## Running on Replit

All three services start via a single workflow (`Start application`):

```bash
bash start-all.sh
```

**First run** downloads Maven dependencies — expect 3–5 minutes before services are ready.

### Notes for Replit
- Docker is not available; the account-service uses an **H2 in-memory database** instead of PostgreSQL.
- Config-server reads from `config-repo/` in the repo root (fixed from original Windows path).
- Account-service has `spring.cloud.config.enabled=false` — it boots standalone without the config-server.
- Eureka registration is non-critical; account-service retries automatically on failure.

## Project Structure

```
ledgermesh/
├── config-repo/            # Config server property files
│   └── account-service.yml
├── infra/
│   ├── config-server/      # Spring Cloud Config Server (Boot 4.1.0)
│   └── discovery-server/   # Eureka Discovery Server (Boot 4.1.0)
├── services/
│   └── account-service/    # Account REST API (Boot 3.5.3, H2)
├── start-all.sh            # Startup script for all services
└── docker-compose.yml      # For local Docker use (not needed on Replit)
```

## User preferences

- Run all services simultaneously when starting up.
