# Payment Service

## Overview
The Payment Service is a Spring Boot application that handles payment processing and refunds using Razorpay.

## Technologies Used
- Java
- Spring Boot
- Maven

## Project Structure
- `src/main/java/com/quickcart/paymentservice` - Main application code
- `src/test/java/com/quickcart/paymentservice` - Test code

## Endpoints
### Create Payment
- **URL:** `/payments/create`
- **Method:** `POST`
- **Request Body:** `OrderDto`
- **Response:** `String` (order ID)

### Refund Payment
- **URL:** `/payments/refund`
- **Method:** `POST`
- **Request Body:** `RefundPaymentDto`
- **Response:** `String` (refund ID)

## Running the Application
To run the application, use the following command:
```bash
mvn spring-boot:run