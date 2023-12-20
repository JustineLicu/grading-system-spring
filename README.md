# Grading System (Spring)

A grading system with decision support for CvSU Imus.

## Usage

Step 1: Copy the `example.application.properties` file to `application.properties`.

```sh
cp src/main/resources/example.application.properties src/main/resources/application.properties
```

Step 2: Create a MySQL database named `dev_grading_system`.

Step 2.1 (OPTIONAL: Run this if you can't start Spring): Install Maven dependencies.

```sh
./mvnw install
```

Step 3: That's it. Start the Spring application, & access it at <http://localhost:8080>
