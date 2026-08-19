# System Summary, Assumptions, Design Patterns and Architecture

## System summary
The application is a browser-based Java web system for Sunrise Dental Clinic. The supplied assessment requires login, appointment registration, appointment lookup, billing, validation, database storage, reports, a web/distributed application and suitable design patterns. It also specifies a unique appointment number and patient/address/contact/dentist/treatment/date/time data.

## Roles
- **Admin:** login, manage users, view reports, logout.
- **Receptionist:** login, register appointment, view/update/delete by appointment ID, calculate/print bill, logout.
- **Doctor:** login, validate/view patient details, view appointment details, logout.

## Architecture
**Presentation:** JSP, HTML, CSS, JavaScript.

**Business:** `AuthService` and `AppointmentService` for authentication, validation and business rules.

**Data:** `UserDAO`, `AppointmentDAO`, JDBC and MySQL database `sqlworkbench`.

This is a three-tier architecture with MVC request handling.

## Design patterns
1. **MVC** - Models are `User` and `Appointment`; JSPs are views; Servlets are controllers.
2. **DAO** - `UserDAO` and `AppointmentDAO` isolate SQL/JDBC code.
3. **Service Layer** - business validation is separated from controllers and DAOs.
4. **Controller delegation / Front Controller style** - protected requests are routed through servlet controllers and session authentication.

## Database assumptions
- MySQL-compatible server on `localhost:3306`.
- Database: `sqlworkbench`.
- Local development credentials are in `setup/CREDENTIALS.txt`.
- Appointment number is unique.
- Costs cannot be negative.

## Security assumptions
Prepared statements are used for SQL. Passwords are stored as SHA-256 hashes for this academic/demo implementation. Production should use BCrypt/Argon2, HTTPS, externalized secrets, CSRF protection and stronger authorization controls.

## Web service
`GET /sunrise-dental-clinic/api/appointments?appointmentNo=APT001` returns appointment JSON for an authenticated session.

## Messages
- Wrong login: **Invalid username or password**
- Registration: **Patient registered successfully**
- Update: **Appointment updated successfully**
- Delete: **Appointment deleted successfully**
- Logout: **Logout successful**

## Reports
The included report page lists appointment, patient, dentist, treatment, date/time and total bill data. Further reports can include dentist schedules and billing totals.

## Testing
A JUnit test is included for bill calculation. Login, CRUD, validation and authorization should also be covered by integration tests before production.
