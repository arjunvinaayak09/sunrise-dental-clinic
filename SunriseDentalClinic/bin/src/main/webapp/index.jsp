<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Sunrise Dental Clinic - Login</title>
    <link rel="stylesheet" href="assets/style.css">
</head>
<body class="login-body">
<div class="login-card">
    <h1>Sunrise Dental Clinic</h1>
    <p>Patient & Appointment Management System</p>

    <% if ("invalid".equals(request.getParameter("error"))) { %>
        <script>alert("Invalid username or password");</script>
        <div class="alert error">Invalid username or password</div>
    <% } %>
    <% if ("success".equals(request.getParameter("logout"))) { %>
        <script>alert("Logout successful");</script>
        <div class="alert success">Logout successful</div>
    <% } %>

    <form method="post" action="login">
        <label>Username</label>
        <input name="username" required>
        <label>Password</label>
        <input type="password" name="password" required>
        <button type="submit">Login</button>
    </form>
</div>
</body>
</html>
