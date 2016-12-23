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



## Exception handling

## Java 8 features

## XML config
