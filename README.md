# Wallet Service

**Wallet Service** is an application developed with Spring Boot to manage digital wallets and financial transactions. The application offers features such as creating wallets, deposits, withdrawals, transfers and checking historical balances.

---
## Instructions

### How to Install

#### Clone the Repository:
```bash
git clone https://github.com/antonio-marcos1989/wallet-service.git
cd wallet-service
```

#### Build the Docker Image:

Navigate to the root directory of the project (where the Dockerfile is located) and run:

```bash
docker build -t wallet-service:latest .
```

---

### How to run

#### Start the Container:

Run the following command to start the container:

```bash
docker run -p 8080:8080 wallet-service:latest
```

#### Access the Application:

Swagger UI for API documentation and testing:
```bash
http://localhost:8080/swagger-ui/index.htm
```

---

### Architecture Design

The application uses a layered architecture, ensuring separation of concerns, scalability and ease of maintenance. The main layers are:

- **Entity**: Represents the database tables, including the `Wallet` and `Transaction` entities, with JPA mapping for persistence.

- **Controller**: Defines the API endpoints and manages HTTP requests, delegating business logic to the service layer.

- **Service**: Contains business logic, such as validations and rules for deposits, withdrawals, transfers and queries.

- **Repository**: Manages access to the database using Spring Data JPA, allowing you to save, search and update entities.

- **DTO (Data Transfer Object)**: Facilitates communication between layers, ensuring data validation and structuring of information received and sent.

- **Config**: Includes settings such as interceptors for tracking with `X-Request-ID`, centralized logs with `SLF4J/Logbac` and monitoring via Spring Actuator.

---
### Requirements met

#### Functional requirements
Create Wallet:

- Implemented through the `POST /api/wallets` endpoint, allowing the creation of wallets for users.

Retrieve Balance:
- Endpoint `GET /api/wallets/{id}` returns the current balance of a specific wallet.

Retrieve Historical Balance:
- Endpoint `GET /api/wallets/{id}/historical-balance` provides the wallet balance at a specific point in time in the past.

Deposit Funds:
- Endpoint `POST /api/wallets/{id}/deposit` allows users to deposit funds into their wallets.

Withdraw Funds:
- Endpoint `POST /api/wallets/{id}/withdraw` allows users to withdraw funds.

Transfer Funds:
- Endpoint `POST /api/wallets/transfer` allows users to transfer funds between user wallets.

---
#### Non-functional requirements
Availability:
- The application is designed as a Dockerized microservice, ensuring easy deployment and scalability. - Monitoring implemented via **Spring Actuator**, with endpoints such as `/actuator/health` to check the health of the service.

Traceability:
- Each request includes the `X-Request-ID` header, ensuring complete tracking of all operations.
- Detailed logs configured with `SLF4J` and `Logback`, storing information about all transactions for auditing.

Resilience:
- The use of Spring Retry ensures that temporary failures, such as database unavailability, are handled automatically. **[Not implemented]**

---

### Logs

The application was configured to generate detailed logs, facilitating the tracking of operations and system auditing.

#### Location of logs
Logs are stored in the main directory at:
```bash
logs/wallet-service.log
```

#### Information recorded in logs
Logs include:

Request details:
- Unique identification of each request through the `X-Request-ID` header.
- Information such as the operation performed and results.

Errors and exceptions:
- Records of failures during execution, including details of the problem.

Transactions:
- Data on deposits, withdrawals, transfers and queries.

---
### Design Decisions and Tradeoffs

#### Tradeoffs
- **Simple Database**: H2 (in-memory) was used to simplify development. In production, it is recommended to use databases such as PostgreSQL or MySQL.

- **High Availability**: High availability configurations, such as load balancing, were not included in this version.

---

### Contact
For questions or support, please contact:

- **Email**: amalves1989@gmail.com