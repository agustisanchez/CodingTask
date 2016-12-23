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

## XML config
