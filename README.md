# CodingTask


## Use cases implemened

## Create customer (user registration)

**Request**

```bash
curl -v -XPOST "http://localhost:8080/customer"
```
**Response**
```bash
HTTP/1.1 201
Location: http://localhost:8080/customer/[username]
Content-Length: 0
```

### Create account (bank account, not customer account)

**Request**

```bash
curl -v -u user:password -XPOST "http://localhost:8080/account"
```
**Response**
```bash
HTTP/1.1 201
Location: http://localhost:8080/account/2
Content-Length: 0
```
## Account transactions (deposit / withdraw)

**Request (deposit)**
```bash
curl -w "\n" -v -H "content-type: application/json" \
-d "{\"amount\":40.0, \"type\":\"DEPOSIT\"}" \
-u user:password \
-XPOST "http://localhost:8080/account/1/transaction"
```

**Request (withdrawal)**
```bash
curl -w "\n" -v -H "content-type: application/json" \
-d "{\"amount\":40.0, \"type\":\"WITHDRAWAL\"}" \
-u user:password \
-XPOST "http://localhost:8080/account/1/transaction"
```
**Response**
```bash
HTTP/1.1 201
Location: http://localhost:8080/account/1/transaction/4
Content-Length: 0
```

## Get account balance and latest ten operations

**Request**
```bash
curl -w "\n" -v -u user:password -XGET "http://localhost:8080/account/1"
```
**Response**
```bash
HTTP/1.1 200
Content-Type: application/json;charset=UTF-8
Transfer-Encoding: chunked

{"statement":[{"type":"DEPOSIT","amount":40.00,"createDate":1482529457005},
{"type":"WITHDRAWAL","amount":40.00,"createDate":1482513247789},
{"type":"DEPOSIT","amount":40.00,"createDate":1482513232229},
{"type":"DEPOSIT","amount":15.00,"createDate":1482498830576}],
"balance":55.00}
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
## Security

API methods have been secured with simple HTTP AUTH (wich requires SSL transport).

Spring security checks that the Authorization header is present and does the login against the customer table in database.

In the service classes, the user ID is retrieved using Spring's `SecurityContextHolder` class.

**TODO**

*  Encrypt passwords in database (i.e. with SHA)
*  clear passwords in objects if not needed

## Tests

The project includes 7 tests.

A couple of unit tests have been implemented in class `AccountControllerTest`.
They use mocking on the HTTP and DAO layers (MockMvc and mocked DAOs).

Five "integration" tests using in-memory H2 database have been implemented in classes `AccountControllerITest` and `CustomerControllerITest`.
To be a real integration test, an embedded Tomcat instance should be utilized instead of MockMvc. That can be achieved by defining it as a plug-in in Maven and linking it to the `integration-tests` phase.



They are automatically invoked by Maven with `mvn package` or explicitly with `mvn test` (strictly, the integration test should run during the `integration-tests` phase).

Finally, a test coverage tool (Cobertura or JaCoCo) should be configured in Maven.

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

* JPA / Hibernate with H2 database (a `CodingTask` file is written to the user's home directory).
* Database identity columns.
* Creation timestamp and update timestamp audit columns in all entities.
* Spring Data JPA repositories.
* Entity and repository base classes.
