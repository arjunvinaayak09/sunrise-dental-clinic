<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.sunrise.model.Appointment" %>
<%
Appointment a = (Appointment) request.getAttribute("appointment");
String error = (String) request.getAttribute("error");
boolean edit = a != null;
%>
<!DOCTYPE html>
<html>
<head><title>Register Appointment</title><link rel="stylesheet" href="assets/style.css"></head>
<body>
<div class="nav"><b>Sunrise Dental Clinic</b><a href="dashboard">Dashboard</a><a class="logout" href="logout">Logout</a></div>
<div class="container form-card">
<h2><%= edit ? "Update Appointment" : "Register New Appointment" %></h2>
<% if (error != null) { %><div class="alert error"><%= error %></div><% } %>
<form method="post" action="appointments">
<input type="hidden" name="action" value="save">
<input type="hidden" name="mode" value="<%= edit ? "update" : "create" %>">
<label>Appointment ID / Number</label>
<input name="appointmentNo" required value="<%= edit ? a.getAppointmentNo() : "" %>" <%= edit ? "readonly" : "" %>>
<label>Patient Name</label>
<input name="patientName" required value="<%= edit ? a.getPatientName() : "" %>">
<label>Contact Number</label>
<input name="contactNumber" required value="<%= edit ? a.getContactNumber() : "" %>">
<label>Address</label>
<textarea name="address" required><%= edit ? a.getAddress() : "" %></textarea>
<label>Dentist Name</label>
<input name="dentistName" required value="<%= edit ? a.getDentistName() : "" %>">
<label>Treatment Type</label>
<input name="treatmentType" required value="<%= edit ? a.getTreatmentType() : "" %>">
<label>Appointment Date</label>
<input type="date" name="appointmentDate" required value="<%= edit ? a.getAppointmentDate() : "" %>">
<label>Appointment Time</label>
<input type="time" name="appointmentTime" required value="<%= edit ? a.getAppointmentTime().toString().substring(0,5) : "" %>">
<label>Consultation Fee</label>
<input type="number" step="0.01" min="0" name="consultationFee" value="<%= edit ? a.getConsultationFee() : "1000.00" %>">
<label>Treatment Cost</label>
<input type="number" step="0.01" min="0" name="treatmentCost" value="<%= edit ? a.getTreatmentCost() : "0.00" %>">
<button type="submit">Save</button>
<a class="button secondary" href="dashboard">Cancel</a>
</form>
</div>
<% if (!edit && "registered".equals(request.getParameter("msg"))) { %><script>alert("Patient registered successfully");</script><% } %>
</body>
</html>
