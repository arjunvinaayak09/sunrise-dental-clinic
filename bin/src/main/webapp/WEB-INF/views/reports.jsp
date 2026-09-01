<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List,com.sunrise.model.Appointment" %>
<%
List<Appointment> list = (List<Appointment>) request.getAttribute("appointments");
%>
<!DOCTYPE html>
<html>
<head><title>Reports</title><link rel="stylesheet" href="assets/style.css"></head>
<body>
<div class="nav"><b>Sunrise Dental Clinic</b><a href="dashboard">Dashboard</a><a class="logout" href="logout">Logout</a></div>
<div class="container">
<h2>Appointment Report</h2>
<table>
<tr><th>Appointment ID</th><th>Patient</th><th>Contact</th><th>Dentist</th><th>Treatment</th><th>Date</th><th>Time</th><th>Total</th><th>Details</th></tr>
<% for (Appointment a : list) { %>
<tr>
<td><%= a.getAppointmentNo() %></td><td><%= a.getPatientName() %></td><td><%= a.getContactNumber() %></td>
<td><%= a.getDentistName() %></td><td><%= a.getTreatmentType() %></td><td><%= a.getAppointmentDate() %></td>
<td><%= a.getAppointmentTime() %></td><td><%= a.getTotalBill() %></td>
<td><a href="appointments?action=view&appointmentNo=<%= a.getAppointmentNo() %>">View</a></td>
</tr>
<% } %>
</table>
</div>
</body>
</html>
