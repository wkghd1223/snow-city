# [Snow City](https://snow-city.world/)

This project is a near real-time dynamic web application designed to automatically update its pages upon data insertion or modification in the database. 
The primary goal was to achieve real-time updates with using RDS like AWS Aurora, but I thought it would be enough to use DynamoDB, I didn’t use RDS.

## Tech Stack

- **Frontend:** Vue 3 (Node.js 18) - vite
- **Backend:** Spring Boot 3 (Java 17) - gradle
- **Database:** AWS DynamoDB
- **Serverless Processing:** AWS Lambda
- **Messaging Service:** AWS SQS
