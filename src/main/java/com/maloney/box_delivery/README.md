Box Tracking API

A RESTful API built with Spring Boot for managing boxes and the items loaded into them.

The application allows users to create and manage boxes, load items while enforcing weight and battery restrictions, retrieve box information, and track available boxes.

--Features
Create a new box
Retrieve all boxes
Retrieve a box by ID
Load multiple items into a box
Retrieve items loaded into a box
Check a box's battery level
Retrieve available boxes
Prevent boxes from exceeding their weight limits
Prevent loading when battery capacity is below 25%
Prevent duplicate tracker IDs and item codes
Input validation and global exception handling

--Business Rules
Box
Tracker ID must be unique and cannot exceed 20 characters.
Weight limit cannot exceed 500 grams.
Battery capacity must be between 0 and 100.
New boxes are automatically created with an IDLE state.
Item Loading

--Items can only be loaded when:

The box exists.
The battery level is at least 25%.
The box is in an appropriate state.
The total weight does not exceed the box's weight limit.
Item codes are unique.


--API Endpoints
Method	  Endpoint	                           Description
POST	  /api/boxes	                    Create a new box
GET	     /api/boxes	                        Retrieve all boxes
GET     	/api/boxes/{boxId}	             Retrieve a box by ID
POST	/api/boxes/{boxId}/items	        Load items into a box
GET	    /api/boxes/{boxId}/items	        Retrieve items in a box
GET    	/api/boxes/available	           Retrieve available boxes
GET   	/api/boxes/{boxId}/battery	       Check a box's battery level


--Example: Create a Box
-Request
{
"trackerId": "BOX-001",
"weightLimit": 500,
"batteryCapacity": 80
}
-Response
{
"id": 1,
"trackerId": "BOX-001",
"weightLimit": 500.0,
"batteryCapacity": 80,
"state": "IDLE"
}
-Example: Load Items
Request

POST /api/boxes/1/items

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

Running the Application
1. Clone the repository
   git clone <repository-url>
2. Navigate to the project directory
   cd box-delivery
3. Run the application
   ./mvnw spring-boot:run

For Windows:
mvnw.cmd spring-boot:run

The application runs on:
http://localhost:8080

H2 Database
The project uses an H2 in-memory database.

The H2 console is available at:
http://localhost:8080/h2-console

Use:
JDBC URL: jdbc:h2:mem:boxdb
Username: sa
Password:

Author
Kaine Azuka