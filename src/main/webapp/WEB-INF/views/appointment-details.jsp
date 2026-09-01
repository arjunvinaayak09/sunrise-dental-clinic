<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.sunrise.model.Appointment" %>
<%Appointment a = (Appointment) request.getAttribute("appointment");%>
<%@ page import="com.sunrise.model.User" %>

<%Appointment a = (Appointment) request.getAttribute("appointment");
User currentUser = (User) session.getAttribute("user");
boolean isDoctor = currentUser != null && "DOCTOR".equals(currentUser.getRole());%>

<!DOCTYPE html>
<html>
<head><title>Appointment Details</title><link rel="stylesheet" href="assets/style.css"></head>
<body>
<div class="nav"><b>Sunrise Dental Clinic</b><a href="dashboard">Dashboard</a><a class="logout" href="logout">Logout</a></div>
<div class="container">
<% if (a == null) { %>
    <div class="alert error">Appointment not found.</div>
<% } else { %>
<h2>Patient / Appointment Details</h2>
<table>
<tr><th>Appointment ID</th><td><%= a.getAppointmentNo() %></td></tr>
<tr><th>Patient Name</th><td><%= a.getPatientName() %></td></tr>
<tr><th>Contact Number</th><td><%= a.getContactNumber() %></td></tr>
<tr><th>Address</th><td><%= a.getAddress() %></td></tr>
<tr><th>Dentist</th><td><%= a.getDentistName() %></td></tr>
<tr><th>Treatment</th><td><%= a.getTreatmentType() %></td></tr>
<tr><th>Date</th><td><%= a.getAppointmentDate() %></td></tr>
<tr><th>Time</th><td><%= a.getAppointmentTime() %></td></tr>
<tr><th>Consultation Fee</th><td><%= a.getConsultationFee() %></td></tr>
<tr><th>Treatment Cost</th><td><%= a.getTreatmentCost() %></td></tr>
<tr><th>Total Bill</th><td><b><%= a.getTotalBill() %></b></td></tr>
</table>
<div class="actions">




<% if (!isDoctor) { %>
<div class="actions">
<div class="no-print">
<a class="button" href="appointments?action=edit&appointmentNo=<%= a.getAppointmentNo() %>">Edit</a>
<a class="button" href="appointments?action=edit&appointmentNo=<%= a.getAppointmentNo() %>">Update</a>

<form method="post" action="appointments" onsubmit="return confirm('Delete this appointment?');">
<input type="hidden" name="action" value="delete">
<input type="hidden" name="appointmentNo" value="<%= a.getAppointmentNo() %>">
<button class="danger">Delete</button>

<% } %>
</form>
</div>
<% if ("registered".equals(request.getParameter("msg"))) { %><script>alert("Patient registered successfully");</script><% } %>
<% if ("updated".equals(request.getParameter("msg"))) { %><script>alert("Appointment updated successfully");</script><% } %>
<% } %>
</div>
</body>
</html>
