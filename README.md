# Quantity Measurement API

A RESTful Spring Boot application for performing unit conversions, comparisons, and arithmetic operations across length, weight, volume, and temperature measurements — secured with Google OAuth2 and JWT authentication.

---

## 🔗 Live Links

| Resource | URL |
|---|---|
| **Frontend App** | [quantitymeasurementapp-prakash.netlify.app](https://69e0c4dac4a3167ef5297772--quantitymeasurementapp-prakash.netlify.app/login) |
| **Swagger UI** | [API Docs on Render](https://quantitymeasurementappbackend-i56j.onrender.com/swagger-ui/index.html) |
| **Backend API** | `https://quantitymeasurementappbackend-i56j.onrender.com` |

---

## Features

- **Unit operations** — compare, convert, add, subtract, and divide quantities across multiple measurement types
- **Google OAuth2 login** — users authenticate via their Google account
- **JWT security** — all API endpoints are protected with stateless JSON Web Token authentication
- **User management** — Google user profiles are persisted automatically on first login
- **H2 in-memory database** — zero-config database with a built-in browser console
- **Spring Data JPA** — all persistence handled without raw SQL
- **Swagger / OpenAPI** — interactive API documentation available at `/swagger-ui/index.html`

---

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 17 |
| Framework | Spring Boot 3.2.5 |
| Security | Spring Security + OAuth2 Client |
| Authentication | Google OAuth2 + JWT (jjwt 0.12.5) |
| Persistence | Spring Data JPA |
| Database | H2 (in-memory) |
| API Docs | SpringDoc OpenAPI (Swagger UI) |
| Build tool | Maven |
| Hosting | Render (backend), Netlify (frontend) |

---

## Project Structure

```
com.apps.quantitymeasurement/
│
├── config/
│   └── SwaggerConfig.java                   OpenAPI / Swagger configuration
│
├── controller/
│   ├── QuantityMeasurementController.java   REST endpoints for measurements
│   └── AuthController.java                  /auth/me and /auth/status
│
├── exception/
│   ├── DatabaseException.java
│   ├── GlobalExceptionHandler.java          Converts exceptions to JSON responses
│   └── QuantityMeasurementException.java
│
├── model/
│   ├── QuantityDTO.java                     HTTP request/response data object
│   ├── QuantityMeasurementEntity.java       JPA entity for measurements table
│   ├── QuantityModel.java                   Internal domain model
│   └── UserEntity.java                      JPA entity for users table
│
├── quantity/
│   └── Quantity.java                        Core arithmetic and conversion logic
│
├── repository/
│   ├── IQuantityMeasurementRepository.java  JPA repository for measurements
│   └── UserRepository.java                  JPA repository for users
│
├── security/
│   ├── SecurityConfig.java                  URL access rules and filter chain
│   ├── JwtTokenProvider.java                JWT creation and validation
│   ├── JwtAuthenticationFilter.java         Reads JWT from every request
│   └── OAuth2SuccessHandler.java            Runs after Google login, mints JWT
│
├── service/
│   ├── IQuantityMeasurementService.java
│   └── QuantityMeasurementServiceImplementation.java
│
└── unit/
    ├── IMeasurable.java                     Unit interface
    ├── LengthUnit.java                      INCHES, FEET, YARDS, CENTIMETERS
    ├── TemperatureUnit.java                 CELSIUS, FAHRENHEIT
    ├── VolumeUnit.java                      LITRE, MILLILITRE, GALLON
    └── WeightUnit.java                      GRAM, KILOGRAM, POUND
```

---

## Getting Started (Local)

### Prerequisites

- Java 17+
- Maven 3.8+
- A Google Cloud Console account

### 1. Clone the repository

```bash
git clone https://github.com/your-username/quantitymeasurement.git
cd quantitymeasurement
```

### 2. Set up Google OAuth2 credentials

1. Go to [console.cloud.google.com](https://console.cloud.google.com)
2. Create a new project
3. Navigate to **APIs & Services → OAuth consent screen** → set to External
4. Navigate to **Clients → Create OAuth client**
   - Application type: Web application
   - Authorised redirect URI: `http://localhost:8080/login/oauth2/code/google`
5. Copy your **Client ID** and **Client Secret**

### 3. Configure `application.properties`

Open `src/main/resources/application.properties` and fill in your values:

```properties
spring.security.oauth2.client.registration.google.client-id=YOUR_CLIENT_ID
spring.security.oauth2.client.registration.google.client-secret=YOUR_CLIENT_SECRET

app.jwt.secret=YOUR_LONG_RANDOM_SECRET_STRING_MIN_32_CHARS
app.jwt.expiration-ms=86400000
```

> ⚠️ Never commit real credentials to GitHub. Add `application.properties` to `.gitignore` or use environment variables.

### 4. Run the application

Via Maven:
```bash
mvn spring-boot:run
```

Or in Spring Tool Suite 4: right-click the project → **Run As → Spring Boot App**

The app starts at `http://localhost:8080`

---

## Authentication Flow

```
1. Visit http://localhost:8080/oauth2/authorization/google
2. Log in with your Google account
3. OAuth2SuccessHandler mints a JWT and redirects to the frontend with ?token=<jwt>
4. Include the token in all subsequent API requests:
   Authorization: Bearer <your_token>
```

---

## API Endpoints

### Public (no token required)

| Method | Endpoint | Description |
|---|---|---|
| `GET` | `/auth/status` | Health check |
| `GET` | `/oauth2/authorization/google` | Initiate Google login |
| `GET` | `/swagger-ui/index.html` | Interactive API docs |

### Protected (JWT required)

| Method | Endpoint | Description |
|---|---|---|
| `GET` | `/auth/me` | Get current user profile |
| `GET` | `/api/measurements` | Get all measurements for the user |
| `GET` | `/api/measurements/count` | Get total measurement count |
| `DELETE` | `/api/measurements` | Delete all measurements |
| `POST` | `/api/measurements/compare` | Compare two quantities |
| `POST` | `/api/measurements/convert` | Convert a quantity to another unit |
| `POST` | `/api/measurements/add` | Add two quantities |
| `POST` | `/api/measurements/subtract` | Subtract two quantities |
| `POST` | `/api/measurements/divide` | Divide two quantities |

### Request Body Examples

**Compare / Add / Subtract / Divide**
```json
{
  "q1": { "value": 1, "unit": "FEET" },
  "q2": { "value": 30.48, "unit": "CENTIMETERS" }
}
```

**Convert**
```json
{
  "quantity": { "value": 100, "unit": "CENTIMETERS" },
  "targetUnit": "FEET"
}
```

### Supported Units

| Type | Units |
|---|---|
| Length | `FEET`, `INCHES`, `YARDS`, `CENTIMETERS` |
| Weight | `KILOGRAM`, `GRAM`, `POUND` |
| Volume | `LITRE`, `MILLILITRE`, `GALLON` |
| Temperature | `CELSIUS`, `FAHRENHEIT` |

---

## H2 Database Console

Available at `http://localhost:8080/h2-console` while running locally.

```
JDBC URL:  jdbc:h2:mem:quantitydb
Username:  sa
Password:  (leave empty)
```

---

## Frontend

The Angular frontend lives in a separate repository and is deployed on Netlify.

- **Live:** [quantitymeasurementapp-prakash.netlify.app](https://69e0c4dac4a3167ef5297772--quantitymeasurementapp-prakash.netlify.app/login)

Features include unit conversion, arithmetic operations, quantity comparison, and full measurement history — all secured behind Google OAuth2 login.

---

## Related Repositories

| Repo | Description |
|---|---|
| [`QuantityMeasurementApp`](https://github.com/PD-001/QuantityMeasurementApp) | This repo — Spring Boot REST API |
| [`QuantityMeasurementApp-Frontend`](https://github.com/PD-001/QuantityMeasurementApp-Frontend) | Angular 17 frontend (separate repo) |