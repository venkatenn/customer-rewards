#### **Problem Description:**
A retailer offers a reward program to its customers awarding points based on each recorded
purchase as follows. For every dollar spent over $50 on the transaction, the customer receives one point. In addition, for every dollar spent over $100, the customer receives another point.

`Ex: for a $120 purchase, the customer receives
(120 - 50) x 1 + (120 - 100) x 1 = 90 points`

**Given a record of every transaction during a three-month period, calculate the reward points
earned for each customer per month and total.**

#### **Implementaiton Details:**
The solution is implemented using a simple spring boot app usign gradle and mongodb.

Models:
* Customer
* Transaction
* Reward

Rest apis are provided for each model to perform crud operations except Rewards. Each added transaction will generate a reward document based the formula.
Specific apis are provided to get customer total reward points and reward points accumulated in a specific month.

#### **Instructions for running the application:**

##### **Prerequisites**
* A running mongodb instance. Need to supply the following details as environment variables.
    * MONGO_USER_NAME
    * MONGO_PASSWORD
    * MONGO_HOST
    * MONGO_DB

    

