# Box Tracking API

A RESTful API built with **Spring Boot** for managing boxes and the items loaded into them.

The application allows users to create and manage boxes, load items while enforcing weight and battery restrictions, retrieve box information, and track available boxes.

## Features

* Create a new box
* Retrieve all boxes
* Retrieve a box by ID
* Load multiple items into a box
* Retrieve items loaded into a box
* Check a box's battery level
* Retrieve available boxes
* Prevent boxes from exceeding their weight limits
* Prevent loading when battery capacity is below 25%
* Prevent duplicate tracker IDs and item codes
* Input validation
* Global exception handling

## Business Rules

### Box Rules

* The **Tracker ID** must be unique.
* The **Tracker ID** cannot exceed 20 characters.
* The **weight limit** cannot exceed 500 grams.
* The **battery capacity** must be between 0 and 100.
* New boxes are automatically created with an **IDLE** state.

### Item Loading Rules

Items can only be loaded when:

* The box exists.
* The battery level is at least **25%**.
* The box is in an appropriate state.
* The total weight of the items does not exceed the box's weight limit.
* Item codes are unique.

## API Endpoints

| Method | Endpoint                     | Description                      |
| ------ | ---------------------------- | -------------------------------- |
| `POST` | `/api/boxes`                 | Create a new box                 |
| `GET`  | `/api/boxes`                 | Retrieve all boxes               |
| `GET`  | `/api/boxes/{boxId}`         | Retrieve a box by ID             |
| `POST` | `/api/boxes/{boxId}/items`   | Load items into a box            |
| `GET`  | `/api/boxes/{boxId}/items`   | Retrieve items loaded into a box |
| `GET`  | `/api/boxes/available`       | Retrieve available boxes         |
| `GET`  | `/api/boxes/{boxId}/battery` | Check a box's battery level      |

## Example Requests & Responses

### Create a Box

**Request**

```http
POST /api/boxes
Content-Type: application/json
```

```json
{
  "trackerId": "BOX-001",
  "weightLimit": 500,
  "batteryCapacity": 80
}
```

**Response**

```json
{
  "id": 1,
  "trackerId": "BOX-001",
  "weightLimit": 500.0,
  "batteryCapacity": 80,
  "state": "IDLE"
}
```

### Load Items into a Box

**Request**

```http
POST /api/boxes/1/items
Content-Type: application/json
```

```json
[
  {
    "name": "Medicine-Pack",
    "weight": 100,
    "code": "MED_001"
  },
  {
    "name": "Food_Box",
    "weight": 150,
    "code": "FOOD_001"
  }
]
```

## Running the Application

### 1. Clone the Repository

```bash
git clone <your-repository-url>
```

### 2. Navigate to the Project Directory

```bash
cd box-delivery
```

### 3. Run the Application

**Linux/macOS:**

```bash
./mvnw spring-boot:run
```

**Windows:**

```cmd
mvnw.cmd spring-boot:run
```

The application will be available at:

```text
http://localhost:8080
```

## H2 Database

The project uses an **H2 in-memory database** for data persistence during application runtime.

The H2 console is available at:

```text
http://localhost:8080/h2-console
```

### Database Configuration

| Property | Value               |
| -------- | ------------------- |
| JDBC URL | `jdbc:h2:mem:boxdb` |
| Username | `sa`                |
| Password | *(empty)*           |

## API Documentation

Swagger/OpenAPI documentation is available when the application is running.

```text
http://localhost:8080/swagger-ui.html
```

or

```text
http://localhost:8080/swagger-ui/index.html
```

## Technology Stack

* Java
* Spring Boot
* Spring Data JPA
* H2 Database
* Maven
* Swagger / OpenAPI

## Author

**Kaine Azuka**
