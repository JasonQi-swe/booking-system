### Booking System & Sender-service
#### **Functions:**

- The [sender-service](https://github.com/JasonQi-swe/transaction-sender-service) send a list of transactions 
- The booking-system processes all received transactions and return those transaction where the booking cost exceeds the account balance

#### **APIs**

Booking-system
- POST /transactions

Sender service
- GET /start-sending-transactions
- GET /stop-sending-transactions

#### **Features**
- Reactive/Non-blocking programming
- Docker containerized application
- Microservices
- Added test cases

#### **Included tests**
1. testTransactions_all_accepted
2. testTransactions_all_rejected
3. testTransactions_one_rejected

#### **How to test**
1. Start the booking-system application
2. Then, start the transaction-sender-service
3. Trigger the sending transactions process by http://localhost:8080/start-sending-transactions. Then, the transaction-sender-service will send two transactions every 2 seconds
4. Stop the sending process by http://localhost:8080/stop-sending-transactions

#### **How to test via Docker**
1. **Make sure the file docker-compose.yml is in the parent directory of the two services**
2. Do the `mvn clean package` in the two services to generate the jar files
3. Run command in terminal: `docker-compose build`
4. Run command in terminal: `docker-compose up`
5. Again, trigger the sending transactions by http://localhost:8080/start-sending-transactions
6. Stop the sending process by http://localhost:8080/stop-sending-transactions

#### **Technologies**
- Spring Webflux
- Spring Boot
- Spring JPA
- Java 17
- MySQL/H2
- Docker