# LedgerMesh 💰

## Personal Finance & Budgeting Platform

A portfolio-grade **microservices-based personal finance management platform** built with **Spring Boot 3, Java 21, Kafka, PostgreSQL, Docker, Kubernetes and modern cloud-native practices**.

LedgerMesh allows users to manage simulated financial accounts, track transactions, create budgets, analyze spending patterns and receive intelligent notifications.

The project focuses on demonstrating **real-world microservices architecture, event-driven communication, distributed systems patterns, security, observability and DevOps practices**.

---

# 🚀 Project Overview

LedgerMesh is a personal finance platform where users can:

- Create and manage multiple financial accounts
    - Checking accounts
    - Savings accounts
    - Credit cards (simulation only)

- Record and categorize transactions

- Create budgets by category

- Monitor spending limits

- Receive notifications for:
    - Budget limit breaches
    - Large transactions
    - Recurring payments

- View financial analytics:
    - Monthly spending
    - Category breakdown
    - Spending trends

The goal is not to build a banking application, but to create a realistic enterprise-grade system that demonstrates modern backend engineering practices.

---

# 🎯 Why LedgerMesh?

Financial applications naturally contain multiple independent business domains:

- Authentication
- Account management
- Transactions
- Budgeting
- Notifications
- Reporting

These domains have different responsibilities and data ownership, making them a strong candidate for a **microservices architecture**.

---

# 🏗️ Architecture Overview


```
                         ┌──────────────────┐
                         │   API Gateway    │
                         │ Spring Cloud GW  │
                         └────────┬─────────┘
                                  │

        ┌─────────────┬───────────┼───────────┬──────────────┐
        │             │           │           │              │

 ┌──────▼──────┐ ┌────▼─────┐ ┌───▼──────┐ ┌──▼────────┐ ┌──▼──────────┐
 │ Auth        │ │ Account  │ │Transaction│ │ Budget    │ │Notification │
 │ Service     │ │ Service  │ │ Service   │ │ Service   │ │ Service     │
 └──────┬──────┘ └────┬─────┘ └────┬──────┘ └────┬──────┘ └────┬────────┘
        │             │            │              │             │

        └─────────────┴────────────┴──────────────┴─────────────┘

                              Kafka

                                │

                        ┌───────▼────────┐
                        │ Reporting      │
                        │ Analytics      │
                        │ Service        │
                        └────────────────┘

```

---

# 🧩 Microservices

| Service | Responsibility | Database |
|---|---|---|
| Auth Service | User authentication, JWT/OAuth2 | PostgreSQL |
| Account Service | Manage financial accounts | PostgreSQL |
| Transaction Service | Transaction management & events | PostgreSQL |
| Budget Service | Budget rules and limit evaluation | PostgreSQL |
| Notification Service | Email and application notifications | PostgreSQL/MongoDB |
| Reporting Service | Analytics and read models | PostgreSQL/ElasticSearch |
| API Gateway | Routing, security, rate limiting | - |
| Config Server | Central configuration management | - |
| Discovery Server | Eureka service registry | - |

---

# 🛠️ Technology Stack

## Backend

- Java 21
- Spring Boot 3.x
- Spring Cloud 2024.x
- Spring Data JPA
- Spring Security
- Spring Cloud Gateway

## Communication

- REST APIs
- Apache Kafka
- Event-driven architecture

## Databases

- PostgreSQL
- Redis
- Elasticsearch (optional)

Each microservice owns its database schema.

This follows the microservices principle:

> Database per service

---

# 🔐 Security

Authentication and authorization are implemented using:

- Keycloak
- OAuth2 / OpenID Connect
- JWT tokens
- Role-based access control


Example roles:

```
USER
ADMIN
```

---

# 📡 Event Driven Architecture

LedgerMesh uses Kafka for asynchronous communication.

Example flow:

```
Transaction Created

        |
        v

Transaction Service

        |
        v

Kafka Event

(TransactionCreated)

        |
        +----------------+
        |                |
        v                v

Budget Service      Reporting Service

        |
        v

BudgetExceeded Event

        |
        v

Notification Service

        |
        v

Email Notification

```

---

# ⚡ Resilience & Reliability

Implemented patterns:

- Circuit Breaker
- Retry mechanism
- Rate Limiting
- Bulkhead isolation
- Graceful degradation


Using:

- Resilience4j

---

# 📊 Observability

The platform includes:

## Distributed tracing

- Micrometer Tracing
- Zipkin / Tempo


## Metrics

- Prometheus
- Grafana


## Centralized logging

- ELK Stack
- Loki


Example:

A request can be traced:

```
Gateway
   |
Account Service
   |
Transaction Service
   |
Kafka
   |
Budget Service
   |
Notification Service

```

---

# 🐳 Infrastructure

Local development environment:

Docker Compose provides:

- PostgreSQL
- Kafka
- Zookeeper
- Redis
- Keycloak
- Zipkin

---

# ☸️ Kubernetes Deployment

The project supports Kubernetes deployment.

Included:

- Deployments
- Services
- ConfigMaps
- Secrets
- Horizontal Pod Autoscaler


Supported environments:

- Minikube
- Kind
- Cloud Kubernetes clusters

---

# 📁 Repository Structure

```
ledgermesh/

│
├── docker-compose.yml
│
├── infra/
│   ├── config-server/
│   ├── discovery-server/
│   └── k8s/
│
├── gateway/
│
├── services/
│
│   ├── auth-service/
│   ├── account-service/
│   ├── transaction-service/
│   ├── budget-service/
│   ├── notification-service/
│   └── reporting-service/
│
├── docs/
│
│   ├── architecture.md
│   └── adr/
│
└── README.md

```

---

# 🚦 Development Roadmap

## Phase 0 - Infrastructure

- Create repository
- Setup Docker Compose
- Configure:
    - PostgreSQL
    - Kafka
    - Redis
    - Eureka
    - Config Server


## Phase 1 - Core Services

Implemented:

- Account Service
- Transaction Service

Features:

- CRUD APIs
- Database persistence
- Swagger documentation
- Kafka events


## Phase 2 - API Gateway

Features:

- Service routing
- Eureka discovery
- Rate limiting


## Phase 3 - Event Driven Features

Build:

- Budget Service
- Notification Service

Features:

- Budget evaluation
- Event consumption
- Email notifications


## Phase 4 - Resilience

Add:

- Circuit breakers
- Retry policies
- Failure simulation


## Phase 5 - Observability

Add:

- Distributed tracing
- Metrics dashboards
- Centralized logging


## Phase 6 - Security

Integrate:

- Keycloak
- OAuth2
- JWT


## Phase 7 - Reporting & Deployment

Add:

- CQRS-lite reporting
- Kubernetes deployment
- CI/CD pipeline


---

# 🧪 Testing Strategy

Testing tools:

- JUnit 5
- Mockito
- Testcontainers
- Spring Cloud Contract


Integration tests run with real infrastructure:

```
Test
 |
Docker Container
 |
PostgreSQL
 |
Kafka

```

---

# 🔄 CI/CD Pipeline

Using GitHub Actions:

Pipeline:

```
Commit

 ↓

Build

 ↓

Run Tests

 ↓

Create Docker Image

 ↓

Push Image

 ↓

Deploy Kubernetes

```

---

# 🌟 Future Improvements

Possible extensions:

## Frontend

React + Vite dashboard:

- Spending charts
- Account overview
- Budget tracking


## Advanced Architecture

- Saga Pattern
- gRPC communication
- Full CQRS
- Event sourcing


## Cloud Deployment

Deploy to:

- AWS
- Azure
- Google Cloud


---

# 🎓 What This Project Demonstrates

LedgerMesh demonstrates knowledge of:

✅ Microservices architecture  
✅ Domain-driven design concepts  
✅ Event-driven systems  
✅ Kafka producers and consumers  
✅ Distributed tracing  
✅ Cloud-native development  
✅ Kubernetes fundamentals  
✅ CI/CD automation  
✅ Security with OAuth2/OIDC  
✅ Resilience patterns  
✅ Testing enterprise applications  


---

# 🚀 Getting Started

## Clone repository

```bash
git clone https://github.com/<username>/ledgermesh.git

cd ledgermesh
```


## Start infrastructure

```bash
docker compose up -d
```


## Run services

Example:

```bash
cd services/account-service

mvn spring-boot:run
```


---

# 📌 Project Status

🚧 Currently under active development.

The project is being built incrementally following real enterprise software engineering practices.

---

# 👨‍💻 Author

**Romeo Rabiaj**

Backend Engineer | Java | Spring Boot | Microservices | EDI Integration
