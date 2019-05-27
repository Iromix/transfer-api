## TRANSFERSTOMP - money transfer app
Simple application for money transfers between accounts written in Java. Test are written in Spock Framework in Groovy.

### 1. How to run
Run mvn clean package to create jar file

Go to folder target and run jar file to run application 
```sh
java -jar ./target/money-transfers-with-deps-1.0.jar
```
server will run on address http://localhost:7000


### 2. Mainly used 3rd components
[Javalin](https://javalin.io/) - a simple, lightweight web framework for Java and Kotlin

[Lombok](https://projectlombok.org/) - a boilerplate code remover and space saver that generates code

[Spock Framework](http://spockframework.org/) - testing and specification framework for Java and Groovy applications

### 3. Endpoints
```sh
/transfer/:from/:to - transfer money between accounts with number from and to e.g. /transfer/1/2 (needed request with money object body)
/account/currency/:code - create account with given currency code e.g. /account/currency/PLN
/account/:number/deposit - deposit money on account with given number (needed request with money object body)
/account/:number/withdraw - withdraw money from account with given number (needed request with money object body)
/account/:number - get account with given number as a JSON result
```
```sh
money object body structure:
{
  "amount": 30,
  "currency": "USD"
}
```
