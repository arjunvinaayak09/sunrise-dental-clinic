<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.sunrise.model.User" %>
<%
    User user = (User) session.getAttribute("user");
    String role = user.getRole();
%>
<!DOCTYPE html>
<html>
<head><title>Dashboard</title><link rel="stylesheet" href="assets/style.css"></head>
<body>
<div class="nav">
    <b>Sunrise Dental Clinic</b>
    <span>Logged in: <%= user.getFullName() %> (<%= role %>)</span>
    <a href="<%= request.getContextPath() %>/help"
   style="background:#2e7d32;">
    Help
</a>
    <a class="logout" href="logout">Logout</a>
</div>
<div class="container">
    <h2>Dashboard</h2>
    <div class="grid">
        <% if ("ADMIN".equals(role)) { %>
            <a class="card" href="users">Manage Users</a>
            <a class="card" href="appointments?action=all">View Reports</a>
        <% } %>

        <% if ("RECEPTIONIST".equals(role)) { %>
            <a class="card" href="appointments">Register Appointment</a>
            <a class="card" href="appointments?action=all">View Appointments / Reports</a>
            <a class="card" href="appointment-search.jsp">Update / View / Delete</a>
            <a class="card" href="bill.jsp">Calculate Bill</a>
        <% } %>

        <% if ("DOCTOR".equals(role)) { %>
            <a class="card" href="appointment-search.jsp">Validate Patient Details</a>
            <a class="card" href="appointments?action=all">View Appointment Details</a>
        <% } %>
    </div>
    <div class="stat">Appointments in database: <b><%= request.getAttribute("appointmentCount") %></b></div>
</div>
<% if ("deleted".equals(request.getParameter("msg"))) { %><script>alert("Appointment deleted successfully");</script><% } %>
</body>
</html>
