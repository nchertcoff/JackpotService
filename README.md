## Interview project

To start the application you need to run `com.sportygroup.jackpot.Application` java class. The project requires a Kafka server at localhost:9092 and a jackpot-bets topic. 
File `application.properties` has all the configuration for Kafka and H2 database. It also contains Jackpot service configuration regarding contribution and rewards. 

### Endpoints
I created the following endpoints
- `/api/v1/jackpots/bets (POST)`: Place a bet to Kafka
- `/api/v1/jackpots/evaluate (POST)`: Evaluate if a bet wins the jackpot reward.

### Curl tests
- Place a bet
```
curl -X POST "http://localhost:8080/api/v1/jackpots/bets" -H "Content-Type: application/json" -d "{\"betId\":\"1\", \"userId\": \"1\", \"jackpotId\":\"VARIABLE\", \"betAmount\": 1000}"
```

- Evaluate if bet wins the jackpot
``` 
curl -X POST "http://localhost:8080/api/v1/jackpots/evaluate" -H "Content-Type: application/json" -d "{\"betId\":\"1\"}"
```
