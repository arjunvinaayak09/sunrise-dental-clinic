# Sunrise Dental Clinic - Maven/Tomcat 9 Web Application

## Technology
- Java 11
- Maven WAR
- Apache Tomcat 9
- JSP/Servlets
- MySQL-compatible database
- JDBC
- MVC + Service + DAO design
- Session-based authentication

The supplied assessment brief requires authentication, appointment registration, appointment lookup, billing, validation, database storage, a distributed/web application, design patterns, reports and testing. This implementation provides those core features. The brief specifically states that each visit has a unique appointment number and asks for patient/address/contact/dentist/treatment/date/time information. See the supplied brief. 

## Main roles
- ADMIN: Manage users, view reports
- RECEPTIONIST: Register, update, view, delete appointments; calculate bills
- DOCTOR: Validate/view patient details and appointment details

## Default demo credentials
See `setup/CREDENTIALS.txt`. Change them for real deployment.

## Database
Run `setup/database.sql` in MySQL Workbench/SQL Workbench after creating the database connection.

The project uses database name `sqlworkbench` and expects MySQL on localhost:3306 by default.

## Build
```bash
mvn clean package
```

The generated WAR is:
`target/sunrise-dental-clinic.war`

## Tomcat 9
Copy the WAR into Tomcat 9's `webapps` directory, or add the project as a Maven Web Application in Eclipse and select the Tomcat v9.0 Server.

Open:
`http://localhost:8080/sunrise-dental-clinic/`

## Eclipse
1. File > Import > Existing Maven Projects.
2. Select this project.
3. Configure Tomcat v9.0 Server.
4. Add the project to the server.
5. Start the server.
6. Open `http://localhost:8080/sunrise-dental-clinic/`.

## Important
This is an academic/demo implementation. Passwords are stored as SHA-256 hashes in the database, while the supplied credential text file is only for local demonstration. For production, use a proper password-hashing algorithm such as BCrypt/Argon2 and externalized secrets.
