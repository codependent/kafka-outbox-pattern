# kafka-outbox-pattern
Implementation of the outbox pattern using Spring Cloud Stream Kafka

## Running the app:

Download the Confluent Platform:

```
git clone https://github.com/confluentinc/cp-docker-images
```

Configure transactional support in development mode (1 broker) in `examples/cp-all-in-one/docker-compose.yml`:

* Necessary for transactional producers:

```
KAFKA_TRANSACTION_STATE_LOG_REPLICATION_FACTOR: 1
```

* Necessary for exactly_once KStreams:

```
KAFKA_TRANSACTION_STATE_LOG_MIN_ISR: 1
```

Start the Confluent Platform:

```
cd cp-docker-images
git checkout 5.2.2-post
cd examples/cp-all-in-one/
docker-compose up -d --build
```

Go to the root of the sample project and do:

`./gradlew clean build -b ./account-service/build.gradle.kts`

`./gradlew clean build -b ./message-relay-service/build.gradle.kts`

`./gradlew clean build -b ./transfer-service/build.gradle.kts`

`java -jar account-service/build/libs/account-service-1.0.0-SNAPSHOT.jar`

`java -jar account-service/build/libs/message-relay-service-1.0.0-SNAPSHOT.jar`

`java -jar transfer-service/build/libs/transfer-service-1.0.0-SNAPSHOT.jar`

Create two accounts:

```
curl -X POST http://localhost:8080/accounts -H "content-type: application/json" -d '{"ownerId": "1234D", "ownerName": "John Doe", "funds": 2000.00}'
```

```
curl -X POST http://localhost:8080/accounts -H "content-type: application/json" -d '{"ownerId": "2225X", "ownerName": "Anne Mary", "funds": 2000.00}'
```

Create some transfers between the existing accounts:

```
curl -X PUT http://localhost:8080/accounts/transfers -H "content-type: application/json" -d '{"sourceAccountId": 1, "destinationAccountId": 2, "ammount": 100.00}'
```

Observe the state of:
 
 - The account database table (account-service): `http://localhost:8080/accounts`
 
 - The outbox database table (message-relay-service): `http://localhost:8085/outbox`
 
 - The transfer database table (transfer-service): `http://localhost:8084/transfers`


