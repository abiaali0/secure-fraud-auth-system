# Secure Fraud Detection & Authentication System

A Spring Boot backend for secure user authentication, transaction monitoring, role-based access control, persistent audit logging, and rule-based suspicious-activity detection.

> **Original project year:** 2024  
> **Public repository reconstruction:** July 2026  
> **Status:** Active portfolio reconstruction using verified, original work only

## Current Public Implementation

This repository currently includes:

- Spring Boot REST API
- User registration and login
- BCrypt password hashing
- JWT Bearer authentication
- Role-based authorization
- PostgreSQL persistence
- Transaction recording
- Rule-based fraud scoring
- Suspicious-activity flags
- Persistent authentication and transaction audit events
- Unit tests
- Docker Compose
- GitHub Actions continuous integration

The résumé also references Python and machine-learning-based anomaly detection. Those features should only be added publicly after they are implemented and verified.

## Architecture

```text
Client
  ↓
Authentication API
  ↓
JWT validation
  ↓
Secured transaction API
  ↓
Fraud scoring service
  ↓
PostgreSQL persistence
  ↓
Audit events
```

## Fraud Rules in This Public Version

A transaction can be flagged when one or more conditions are met:

- Amount exceeds a high-value threshold
- Transaction occurs from a new country
- Too many transactions occur within a short period
- Multiple risk signals occur together

## API Endpoints

### Register

```http
POST /api/auth/register
```

### Login

```http
POST /api/auth/login
```

### Submit transaction

```http
POST /api/transactions
Authorization: Bearer <token>
```

### List current user's transactions

```http
GET /api/transactions
Authorization: Bearer <token>
```

## Run Locally

```bash
docker compose up --build
```

The API will be available at `http://localhost:8080` and the health endpoint at `http://localhost:8080/health`.

## Run Tests

```bash
mvn test
```

## Project Structure

```text
secure-fraud-auth-system/
├── src/
│   ├── main/
│   │   ├── java/com/abia/fraudauth/
│   │   │   ├── audit/
│   │   │   ├── auth/
│   │   │   ├── config/
│   │   │   ├── fraud/
│   │   │   ├── transaction/
│   │   │   └── user/
│   │   └── resources/
│   │       └── application.yml
│   └── test/
├── .github/workflows/ci.yml
├── Dockerfile
├── docker-compose.yml
├── pom.xml
└── README.md
```

## Development Timeline

- **2024:** Original project work
- **July 2026:** Public portfolio reconstruction and documentation

## Accuracy Note

This public version implements deterministic fraud rules. It does not claim to include a production machine-learning model until such a model is added and verified.
