# kafka-outbox-pattern
Implementation of the outbox pattern using Spring Cloud Stream Kafka

## Running the app:

Start the Confluent Platform:

```
git clone https://github.com/confluentinc/cp-docker-images
cd cp-docker-images
git checkout 5.2.1-post
cd examples/cp-all-in-one/
docker-compose up -d --build
```

Go to the root of the sample project and do:

`./gradlew clean build -b ./account-service/build.gradle.kts`

`./gradlew clean build -b ./transfer-service/build.gradle.kts`

`java -jar account-service/build/libs/account-service-1.0.0-SNAPSHOT.jar`

`java -jar transfer-service/build/libs/transfer-service-1.0.0-SNAPSHOT.jar`

Create an account:

`curl -X POST http://localhost:8080/accounts -H "content-type: application/json" -d '{"ownerId": "1234D", "ownerName": "John Doe", "funds": 2000.00}'`

Create some transfers against the generated account:

`curl -X PUT http://localhost:8080/accounts/transfers -H "content-type: application/json" -d '{"sourceAccountId": 1, "destinationAccountId": 9999, "ammount": 100.00}'`

Observe the state of:
 
 - The account database table (account-service): `http://localhost:8080/accounts`
 
 - The outbox database table (message-relay-service): `http://localhost:8085/outbox`
 
 - The transfer databse table (transfer-service): `http://localhost:8084/transfers`


