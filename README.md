# Grading System (Spring)

A grading system with decision support for CvSU Imus.

## Usage

> TIP: Run this in VSCode for `REST Client` extension.

Step 0: After cloning this repo, install the required extensions for VSCode by searching
`@recommended` in the Extensions tab.

Step 1: Copy the `example.application.properties` file to `application.properties` (Run the command
below for instant copy).

```sh
cp src/main/resources/example.application.properties src/main/resources/application.properties
```

Step 2: Create a MySQL database named `dev_grading_system`.

> TIP: If you change your default username or password for MySQL, configure it at the
> `application.properties` file

Step 2.1 (OPTIONAL: Run this if you can't start Spring): Install Maven dependencies.

```sh
./mvnw install
```

Step 3: Start the Spring application either by clicking the play button at the top-right or running
`./mvnw spring-boot:run`

Step 4: Check the `.http` files in the `http` folder for available API endpoints.

Step 5: Create some `Courses`, `Sections`, & `Students` first using the `.http` files because they
are needed for the database relations with `Grades`, `Subjects`, & `Subject's Sections`.
