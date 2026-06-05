# Campus Food Delivery Backend

A backend REST API for a campus food delivery platform, built with Java and Spring Boot.
It supports user authentication, dish management, and order processing,
with Redis caching to handle high-concurrency reads efficiently.

## Tech Stack
- **Java 17 + Spring Boot 3.3.5** — core framework
- **MySQL 8** — persistent storage for users, dishes, and orders
- **Redis** — caching frequently accessed dish data using the cache-aside pattern
- **JWT** — stateless token-based authentication
- **Maven** — dependency management

## Architecture
Three-layer architecture:
- **Controller** — handles HTTP requests and responses
- **Service** — contains business logic
- **Repository** — interacts with the database via JPA/Hibernate

## API Endpoints

### User
| Method | Endpoint | Auth | Description |
|--------|----------|------|-------------|
| POST | /api/users/register | No | Register a new user |
| POST | /api/users/login | No | Login and receive JWT token |

### Dishes
| Method | Endpoint | Auth | Description |
|--------|----------|------|-------------|
| POST | /api/dishes | Yes | Add a new dish |
| PUT | /api/dishes/{id} | Yes | Update a dish |
| DELETE | /api/dishes/{id} | Yes | Delete a dish |
| GET | /api/dishes/available | No | Get all available dishes |
| GET | /api/dishes/merchant/{merchantId} | Yes | Get dishes by merchant |

### Orders
| Method | Endpoint | Auth | Description |
|--------|----------|------|-------------|
| POST | /api/orders | Yes | Place a new order |
| GET | /api/orders/user/{userId} | Yes | Get orders by user |
| PATCH | /api/orders/{id}/status | Yes | Cancel an order |
| PUT | /api/orders/{id}/status | Yes | Update order status |

**Place Order Request Body:**
```json
{
  "userId": 1,
  "address": "123 University Ave",
  "orderItems": [
    {"dishId": 1, "quantity": 2},
    {"dishId": 2, "quantity": 1}
  ]
}
```

## How to Run Locally

### Prerequisites
- Java 17+
- MySQL 8
- Redis
- Maven

### Steps
1. Clone the repository
```bash
   git clone https://github.com/EsmeYang/campus-food-delivery.git
   cd campus-food-delivery
```

2. Create MySQL database
```sql
   CREATE DATABASE campus_food;
```

3. Update `src/main/resources/application.yml` with your MySQL credentials

4. Start Redis
```bash
   brew services start redis
```

5. Run the application
```bash
   mvn spring-boot:run
```

6. Test the API
```bash
   curl -s http://localhost:8080/api/dishes/available
```