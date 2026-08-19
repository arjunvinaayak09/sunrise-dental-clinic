<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head><title>Find Appointment</title><link rel="stylesheet" href="assets/style.css"></head>
<body>
<div class="nav"><b>Sunrise Dental Clinic</b><a href="dashboard">Dashboard</a><a class="logout" href="logout">Logout</a></div>
<div class="container form-card">
<h2>Find Appointment</h2>
<p>Enter the Appointment ID. Patient details will be displayed from the database.</p>
<form method="get" action="appointments">
<input type="hidden" name="action" value="view">
<label>Appointment ID</label>
<input name="appointmentNo" required>
<button type="submit">View Details</button>
</form>
</div>
</body>
</html>
