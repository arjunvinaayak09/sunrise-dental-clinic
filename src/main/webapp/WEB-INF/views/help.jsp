<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.sunrise.model.User" %>

<%
    User currentUser = (User) session.getAttribute("user");

    if (currentUser == null) {
        response.sendRedirect(request.getContextPath() + "/index.jsp");
        return;
    }

    String role = currentUser.getRole();

    if (role == null) {
        role = "";
    }

    role = role.trim().toUpperCase();

    String displayRole = role;

    if ("ADMIN".equals(role)) {
        displayRole = "Administrator";
    } else if ("RECEPTIONIST".equals(role)) {
        displayRole = "Receptionist";
    } else if ("DOCTOR".equals(role)) {
        displayRole = "Doctor";
    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">

    <title>Help & Guidance - Sunrise Dental Clinic</title>

    <style>
        * {
            box-sizing: border-box;
        }

        body {
            margin: 0;
            font-family: Arial, Helvetica, sans-serif;
            background: #f1f5f9;
            color: #222;
        }

        .nav {
            background: #0d47a1;
            color: white;
            padding: 16px 30px;
            display: flex;
            align-items: center;
            gap: 10px;
            box-shadow: 0 2px 8px rgba(0,0,0,0.15);
        }

        .clinic-name {
            font-size: 21px;
            font-weight: bold;
            margin-right: auto;
        }

        .nav a {
            color: white;
            text-decoration: none;
            padding: 10px 15px;
            border-radius: 5px;
        }

        .nav a:hover {
            background: rgba(255,255,255,0.15);
        }

        .help-button {
            background: #2e7d32;
        }

        .logout {
            background: #d32f2f;
        }

        .logout:hover {
            background: #b71c1c !important;
        }

        .container {
            width: 90%;
            max-width: 1100px;
            margin: 35px auto;
        }

        .header-card,
        .guide-card {
            background: white;
            padding: 30px;
            border-radius: 12px;
            box-shadow: 0 4px 15px rgba(0,0,0,0.08);
            margin-bottom: 25px;
        }

        .header-card h1,
        .guide-card h2 {
            color: #0d47a1;
        }

        .guide-card h3 {
            margin-top: 25px;
            color: #333;
        }

        .guide-card li {
            margin-bottom: 10px;
            line-height: 1.5;
        }

        .important {
            background: #fff3cd;
            border-left: 5px solid #ffb300;
            padding: 15px;
            margin-top: 20px;
            border-radius: 5px;
        }

        .success {
            background: #e8f5e9;
            border-left: 5px solid #2e7d32;
            padding: 15px;
            margin-top: 20px;
            border-radius: 5px;
        }

        .back-button {
            display: inline-block;
            background: #1976d2;
            color: white;
            padding: 12px 22px;
            text-decoration: none;
            border-radius: 5px;
            margin-top: 10px;
        }

        .back-button:hover {
            background: #125ca1;
        }

        @media (max-width: 700px) {
            .nav {
                flex-wrap: wrap;
            }

            .clinic-name {
                width: 100%;
                margin-bottom: 10px;
            }

            .container {
                width: 95%;
            }
        }
    </style>
</head>

<body>

<div class="nav">

    <div class="clinic-name">
        Sunrise Dental Clinic
    </div>

    <a href="<%= request.getContextPath() %>/dashboard">
        Dashboard
    </a>

    <a class="help-button"
       href="<%= request.getContextPath() %>/help">
        Help
    </a>

    <a class="logout"
       href="<%= request.getContextPath() %>/logout">
        Logout
    </a>

</div>

<div class="container">

    <div class="header-card">

        <h1>Help & Guidance</h1>

        <p>
            Welcome,
            <strong>
                <%= currentUser.getFullName() %>
            </strong>
        </p>

        <p>
            You are logged in as:
            <strong>
                <%= displayRole %>
            </strong>
        </p>

        <p>
            Follow the instructions below to use
            the Sunrise Dental Clinic system.
        </p>

    </div>


<% if ("ADMIN".equals(role)) { %>

    <!-- ADMIN -->

    <div class="guide-card">

        <h2>Administrator Guidance</h2>

        <h3>1. Administrator Login</h3>

        <ol>
            <li>Enter the administrator username.</li>
            <li>Enter the administrator password.</li>
            <li>Click Login.</li>
            <li>If the credentials are incorrect, an invalid username or password message is displayed.</li>
        </ol>

        <h3>2. Manage Users</h3>

        <ol>
            <li>Open Manage Users.</li>
            <li>View the existing users.</li>
            <li>Click Create/Add User to create a new account.</li>
            <li>Enter username, password, role and full name.</li>
            <li>Click Save.</li>
        </ol>

        <h3>3. Update User</h3>

        <ol>
            <li>Open Manage Users.</li>
            <li>Select the required user.</li>
            <li>Click Edit/Update.</li>
            <li>Change the required information.</li>
            <li>Click Save.</li>
        </ol>

        <h3>4. Delete User</h3>

        <ol>
            <li>Open Manage Users.</li>
            <li>Select the user.</li>
            <li>Click Delete.</li>
            <li>Confirm the deletion.</li>
        </ol>

        <h3>5. View Reports</h3>

        <ol>
            <li>Open Reports from the administrator dashboard.</li>
            <li>Review appointment and system information.</li>
            <li>Use the available report options.</li>
        </ol>

        <div class="important">
            <strong>Important:</strong>
            Administrator user management controls system accounts.
            Use Update and Delete carefully.
        </div>

    </div>

<% } else if ("RECEPTIONIST".equals(role)) { %>

    <!-- RECEPTIONIST -->

    <div class="guide-card">

        <h2>Receptionist Guidance</h2>

        <h3>1. Receptionist Login</h3>

        <ol>
            <li>Enter the receptionist username.</li>
            <li>Enter the receptionist password.</li>
            <li>Click Login.</li>
        </ol>

        <h3>2. Register Appointment</h3>

        <ol>
            <li>Open Register Appointment.</li>
            <li>Enter patient name.</li>
            <li>Enter contact number.</li>
            <li>Enter address.</li>
            <li>Enter dentist name.</li>
            <li>Select treatment type.</li>
            <li>Select appointment date.</li>
            <li>Select appointment time.</li>
            <li>Enter consultation fee.</li>
            <li>Enter treatment cost.</li>
            <li>Click Register Appointment.</li>
        </ol>

        <h3>3. Treatment Types</h3>

        <p>The receptionist can select one of the available treatment types:</p>

        <ul>
            <li>Dental Cleaning</li>
            <li>Dental Filling</li>
            <li>Tooth Extraction</li>
        </ul>

        <h3>4. Edit Appointment</h3>

        <ol>
            <li>Open Appointment Details.</li>
            <li>Search for the appointment.</li>
            <li>Click Edit.</li>
            <li>Change the patient or appointment information.</li>
            <li>Click Save.</li>
        </ol>

        <h3>5. Calculate Bill</h3>

        <ol>
            <li>Open Calculate Bill.</li>
            <li>Enter the Appointment Number.</li>
            <li>Click Find Appointment.</li>
            <li>Patient details will be displayed.</li>
            <li>Consultation fee will be displayed.</li>
            <li>Treatment cost will be displayed.</li>
            <li>Total bill will be calculated automatically.</li>
            <li>Click Print Bill to print the bill.</li>
        </ol>

        <h3>6. Appointment Details</h3>

        <ol>
            <li>Open Appointment Details.</li>
            <li>Search for an appointment.</li>
            <li>Review patient details.</li>
            <li>Use Edit when information needs to be changed.</li>
        </ol>

        <div class="success">
            <strong>Tip:</strong>
            Always verify the appointment number before calculating
            or printing a bill.
        </div>

    </div>

<% } else if ("DOCTOR".equals(role)) { %>

    <!-- DOCTOR -->

    <div class="guide-card">

        <h2>Doctor Guidance</h2>

        <h3>1. Doctor Login</h3>

        <ol>
            <li>Enter the doctor username.</li>
            <li>Enter the doctor password.</li>
            <li>Click Login.</li>
        </ol>

        <h3>2. View Patient Details</h3>

        <ol>
            <li>Open the patient/appointment section.</li>
            <li>Search for the required appointment.</li>
            <li>Enter the appointment number.</li>
            <li>Click Search or Validate.</li>
            <li>The patient details will be displayed.</li>
        </ol>

        <h3>3. Validate Patient Details</h3>

        <ol>
            <li>Check the patient name.</li>
            <li>Check the contact number.</li>
            <li>Check the address.</li>
            <li>Check the treatment type.</li>
            <li>Check the appointment date and time.</li>
        </ol>

        <h3>4. View Appointment Details</h3>

        <ol>
            <li>Open Appointment Details.</li>
            <li>Search for the appointment.</li>
            <li>Review the patient information.</li>
            <li>Confirm that the information matches the appointment.</li>
        </ol>

        <h3>5. Treatment Information</h3>

        <ol>
            <li>Review the treatment type.</li>
            <li>Review the appointment date and time.</li>
            <li>Review the assigned dentist.</li>
        </ol>

        <div class="important">
            <strong>Important:</strong>
            Check patient information carefully before treatment.
        </div>

    </div>

<% } else { %>

    <div class="guide-card">

        <h2>Help</h2>

        <p>
            Your account does not have a recognised system role.
        </p>

        <p>
            Please contact the system administrator.
        </p>

    </div>

<% } %>


    <div class="guide-card">

        <h2>Logout</h2>

        <p>
            When you finish using the system,
            click the Logout button at the top-right.
        </p>

        <p>
            You will be returned to the login page.
        </p>

        <a class="back-button"
           href="<%= request.getContextPath() %>/dashboard">
            Back to Dashboard
        </a>

    </div>

</div>

</body>
</html>