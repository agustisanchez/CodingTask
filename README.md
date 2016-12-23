# CodingTask


## Use cases implemened

### Create account (bank account, not customer account)

**Request**

```bash
curl -v -XPOST "http://localhost:8080/account"
```
**Response**
```bash
HTTP/1.1 201
Location: http://localhost:8080/account/2
Content-Length: 0
```
## Deposit / withdraw

**Request (deposit)**
```bash
curl -w "\n" -v -H "content-type: application/json" -d "{\"amount\":40.0}" -XPOST "http://localhost:8080/account/1/deposit"
```
**Response**
```bash
HTTP/1.1 201
Location: http://localhost:8080/account/1/deposit/4
Content-Length: 0
```

## Get account balance and latest ten operations

**Request**
```bash
curl -w "\n" -v -XGET "http://localhost:8080/account/1"
```
**Response**
```bash
HTTP/1.1 200
Content-Type: application/json;charset=UTF-8
Transfer-Encoding: chunked

{"statement":[{"type":"DEPOSIT","amount":40.00,"createDate":1482529457005},{"type":"WITHDRAWAL","amount":40.00,"createDate":1482513247789},{"type":"DEPOSIT","amount":40.00,"createDate":1482513232229},{"type":"DEPOSIT","amount":15.00,"createDate":1482498830576}],"balance":55.00}
```
## Exception handling

Class `GlobalExceptionHandler` centralizes the mapping of business and system exceptions into error codes and HTTP status codes.

REST controllers take care of the success scenario.

**Sample error response**
```bash
HTTP/1.1 400
Content-Type: application/json;charset=UTF-8
Transfer-Encoding: chunked

{"errorCode":"amount.must.be.positive"}
```
## Java 8 features

Collection stream , the `map` operation and a lambda expression are used to convert a list of `AccountTransaction` objects into a list of `TransactionDTO` objects:
```java
		List<TransactionResponseDTO> statement = accountTransactionDAO.findTop10ByAccountOrderByCreateDateDesc(account)
				.map(i -> new TransactionResponseDTO(i)).collect(Collectors.toList());
 ```
 
 Other Java 8 features that could be considered: `Optional` and the new Time API.
 
## XML config

I prefer Spring XML config over Java config as the former is far more compact.

## Persistence

JPA / Hibernate with H2 database (a `CodingTask` file is written to the user's home directory).
Database identity columns.
Creation timestamp and update timestamp audit columns in all entities.
Spring Data JPA repositories.
Entity and repository base classes.


