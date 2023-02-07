#### **Problem Description:**
A retailer offers a reward program to its customers awarding points based on each recorded
purchase as follows. For every dollar spent over $50 on the transaction, the customer receives one point. In addition, for every dollar spent over $100, the customer receives another point.

`Ex: for a $120 purchase, the customer receives
(120 - 50) x 1 + (120 - 100) x 1 = 90 points`

Given a record of every transaction during a three-month period, calculate the reward points
earned for each customer per month and total.

#### **Implementaiton Details:**
The solution is implemented using a simple spring boot app usign gradle and mongodb.

Models:
* Customer
* Transaction
* Reward

Rest apis are provided for each model to perform crud operations except Rewards. Each added transaction will generate a reward document based on the formula.
Specific apis are provided to get customer total reward points and reward points accumulated in a specific month.

#### **Application Links:**
A dockeried version of the application is deployed on aws using free tier aws instance and free tier mongo atlas instance.
Note: exposing the public ip as this is demonstration purpose to play with the apis in swagger. It will be taken down in few days.
* ApiDoc       : http://92.92.92.189:8080/v3/api-docs/
* Swagger      : http://92.92.92.189:8080/swagger-ui/index.html
* Actuator     : http://92.92.92.189:8080/actuator
* Health Check : http://92.92.92.189:8080/actuator/health
* Metrics      : http://92.92.92.189:8080/actuator/metrics
* Prometheus end point for scrapping metrics: http://92.92.92.189:8080/actuator/prometheus

If self deployed:
* ApiDoc       : http://localhost:8080/v3/api-docs/
* Swagger      : http://localhost:8080/swagger-ui/index.html
* Actuator     : http://localhost:8080/actuator
* Health Check : http://localhost:8080/actuator/health
* Metrics      : http://localhost:8080/actuator/metrics
* Prometheus end point for scrapping metrics: http://localhost:8080/actuator/prometheus


#### **Instructions for running the application:**

##### **Prerequisites**
* A running mongodb instance. Need to supply the following details as environment variables.
    * MONGO_USER_NAME
    * MONGO_PASSWORD
    * MONGO_HOST
    * MONGO_DB

##### **IntelliJ IDE**
* Checkout the source code and import the project.
* Add the following VM option to use TLS v2 when communicating with for latest mongodb versions.
`-Djdk.tls.client.protocols=TLSv1.2`
* By starting the application you will be able to hit the application on port 8080.

#### **Docker**
* Please update hardecoded values in dockerfile with proper mongo config values.
Note: hardcoded for demonstration purposes. Generally these are injected at deploy time using some sort of secrets manager.
* Build the docker image using the following command in application root directory.
`docker build -t "customer-rewards-001" .`
* Run the docker image using the following command.
`docker run -d -p 8080:8080 -t customer-rewards-002`

    

    

