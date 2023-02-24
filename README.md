#### **Problem Description:**
A retailer offers a reward program to its customers awarding points based on each recorded
purchase as follows. For every dollar spent over $50 on the transaction, the customer receives one point. In addition, for every dollar spent over $100, the customer receives another point.

`Ex: for a $120 purchase, the customer receives
(120 - 50) x 1 + (120 - 100) x 1 = 90 points`

Given a record of every transaction during a three-month period, calculate the reward points
earned for each customer per month and total.

#### **Implementaiton Details:**
The solution is implemented using a simple spring boot app using gradle and mongodb.

Models:
* Customer
* Transaction
* Reward

Rest apis are provided for each model to perform crud operations except Rewards. Each added transaction will generate a reward document based on the formula.
Specific apis are provided to get customer total reward points and reward points accumulated in a specific month.

#### **Application Links:** [Terminated after 2 weeks]
A dockeried version of the application is deployed on aws using free tier aws instance and free tier mongo atlas instance.
Note: exposing the public ip as this is demonstration purpose to play with the apis in swagger. It will be taken down in few days.
* ApiDoc       : http://34.207.237.224:8080/v3/api-docs/
* Swagger      : http://34.207.237.224:8080/swagger-ui/index.html
* Actuator     : http://34.207.237.224:8080/actuator
* Health Check : http://34.207.237.224:8080/actuator/health
* Metrics      : http://34.207.237.224:8080/actuator/metrics
* Prometheus end point for scrapping metrics: http://34.207.237.224:8080/actuator/prometheus

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

##### **Docker**
* Please update hardcoded values in dockerfile with proper mongodb config values.
Note: hardcoded for demonstration purposes. Generally these are injected at deploy time using some sort of secrets manager.
* Build the docker image using the following command in application root directory.
`docker build -t "customer-rewards-001" .`
* Run the docker image using the following command.
`docker run -d -p 8080:8080 -t customer-rewards-001`

#### **Jacoco Code Coverage**

![Screen Shot 2023-02-06 at 11 55 25 PM](https://user-images.githubusercontent.com/124640067/217152428-aac7d417-f572-43ec-a4af-c26516d58e33.png)

#### **Collections Sample Data**

![Screen Shot 2023-02-07 at 12 21 28 PM](https://user-images.githubusercontent.com/124640067/217320579-3939e5a4-33c3-4782-8711-6e7e1853d976.png)
![Screen Shot 2023-02-07 at 12 21 55 PM](https://user-images.githubusercontent.com/124640067/217320610-361c52ec-69e8-49f1-a736-4a75ad696216.png)
![Screen Shot 2023-02-07 at 12 21 44 PM](https://user-images.githubusercontent.com/124640067/217320628-8ce9eb6c-7d52-46e6-8b55-28924687be1c.png)
![Screen Shot 2023-02-24 at 1 03 12 PM](https://user-images.githubusercontent.com/124640067/221259943-f2ca5b34-f83f-4ddd-9b27-52a3a79f6f98.png)
![Screen Shot 2023-02-24 at 1 03 29 PM](https://user-images.githubusercontent.com/124640067/221259969-3b168b05-c585-44f2-87c4-0781da56725f.png)
