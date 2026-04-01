# USSD Engine Demo (In-Memory)

This is a lightweight Spring Boot demo of the USSD state machine design from the technical brief. It replaces Redis and database persistence with in-memory repositories so you can test the flow quickly.

The design is based on the DB-driven menu and transition approach described in the brief you shared earlier. fileciteturn0file0

## What is in this demo

- in-memory `ussd_menu`
- in-memory `ussd_transition`
- in-memory session store (as a Redis substitute)
- reusable account selection flow
- sample Balance, M-PESA, and Loans branching

## Main endpoint

```bash
POST /api/v1/ussd/process
Content-Type: application/json
```

Request body:

```json
{
  "sessionId": "ABC123",
  "msisdn": "254712345678",
  "input": ""
}
```

## Quick test flow with curl

### 1. Dial

```bash
curl -X POST http://localhost:8080/api/v1/ussd/process \
  -H 'Content-Type: application/json' \
  -d '{"sessionId":"ABC123","msisdn":"254712345678","input":""}'
```

Expected:

```text
CON Welcome to I&M Bank
Enter your PIN:
```

### 2. Enter PIN

```bash
curl -X POST http://localhost:8080/api/v1/ussd/process \
  -H 'Content-Type: application/json' \
  -d '{"sessionId":"ABC123","msisdn":"254712345678","input":"1234"}'
```

### 3. Select Balance

```bash
curl -X POST http://localhost:8080/api/v1/ussd/process \
  -H 'Content-Type: application/json' \
  -d '{"sessionId":"ABC123","msisdn":"254712345678","input":"1"}'
```

### 4. Select Account 2

```bash
curl -X POST http://localhost:8080/api/v1/ussd/process \
  -H 'Content-Type: application/json' \
  -d '{"sessionId":"ABC123","msisdn":"254712345678","input":"2"}'
```

Expected final response:

```text
END Account: Salary
Balance: KES 98,120.35
```

## Alternative branches

### M-PESA branch

Use step 3 with input `2`.

### Loans branch

Use step 3 with input `3`.

## Project structure

- `engine/` - orchestration loop
- `handler/` - one handler per state
- `service/` - transition, menu, session, account, auth services
- `repo/` - in-memory menu and transition repositories
- `controller/` - REST endpoints

## How to convert this later

- replace `InMemorySessionService` with Redis-backed session storage
- replace `InMemoryMenuRepository` with JPA/JDBC repository for `ussd_menu`
- replace `InMemoryTransitionRepository` with JPA/JDBC repository for `ussd_transition`
- add audit persistence
- add retry counters, lockout, and back/home navigation
# ussd-engine-demo
