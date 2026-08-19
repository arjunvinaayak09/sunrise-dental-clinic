<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.sunrise.model.Appointment" %>

<%
    Appointment a = (Appointment) request.getAttribute("appointment");
    String error = (String) request.getAttribute("error");

    boolean edit = a != null;
%>

<!DOCTYPE html>
<html>

<head>
    <title>Register Appointment</title>
    <link rel="stylesheet" href="assets/style.css">
</head>

<body>

<div class="nav">
    <b>Sunrise Dental Clinic</b>
    <a href="dashboard">Dashboard</a>
    <a href="<%= request.getContextPath() %>/help"
   style="background:#2e7d32;">
    Help
</a>
    <a class="logout" href="logout">Logout</a>
</div>

<div class="container form-card">

    <h2>
        <%= edit ? "Update Appointment" : "Register New Appointment" %>
    </h2>

    <% if (error != null) { %>
        <div class="alert error">
            <%= error %>
        </div>
    <% } %>

    <form method="post" action="appointments">

        <input type="hidden" name="action" value="save">

        <input type="hidden"
               name="mode"
               value="<%= edit ? "update" : "create" %>">

        <!-- Appointment Number -->
        <label>Appointment ID / Number</label>

        <input type="text"
               name="appointmentNo"
               required
               value="<%= edit ? a.getAppointmentNo() : "" %>"
               <%= edit ? "readonly" : "" %>>


        <!-- Patient Name -->
        <label>Patient Name</label>

        <input type="text"
               name="patientName"
               required
               value="<%= edit ? a.getPatientName() : "" %>">


        <!-- Contact Number -->
        <label>Contact Number</label>

        <input type="text"
               name="contactNumber"
               required
               value="<%= edit ? a.getContactNumber() : "" %>">


        <!-- Address -->
        <label>Address</label>

        <textarea name="address" required><%= edit ? a.getAddress() : "" %></textarea>


        <!-- Dentist Name -->
        <label>Dentist Name</label>

        <input type="text"
               name="dentistName"
               required
               value="<%= edit ? a.getDentistName() : "" %>">


        <!-- Treatment Type -->
        <label>Treatment Type</label>

        <select name="treatmentType" required>

            <option value="">-- Select Treatment Type --</option>

            <option value="Dental Cleaning"
                <%= edit && "Dental Cleaning".equals(a.getTreatmentType()) ? "selected" : "" %>>
                Dental Cleaning
            </option>

            <option value="Tooth Filling"
                <%= edit && "Tooth Filling".equals(a.getTreatmentType()) ? "selected" : "" %>>
                Tooth Filling
            </option>

            <option value="Tooth Extraction"
                <%= edit && "Tooth Extraction".equals(a.getTreatmentType()) ? "selected" : "" %>>
                Tooth Extraction
            </option>

            <option value="Root Canal Treatment"
                <%= edit && "Root Canal Treatment".equals(a.getTreatmentType()) ? "selected" : "" %>>
                Root Canal Treatment
            </option>

        </select>


        <!-- Appointment Date -->
        <label>Appointment Date</label>

        <input type="date"
               name="appointmentDate"
               required
               value="<%= edit ? a.getAppointmentDate() : "" %>">


        <!-- Appointment Time -->
        <label>Appointment Time</label>

        <input type="time"
               name="appointmentTime"
               required
               value="<%= edit ? a.getAppointmentTime().toString().substring(0, 5) : "" %>">


        <!-- Consultation Fee -->
        <label>Consultation Fee</label>

        <input type="number"
               step="0.01"
               min="0"
               name="consultationFee"
               value="<%= edit ? a.getConsultationFee() : "1000.00" %>">


        <!-- Treatment Cost -->
        <label>Treatment Cost</label>

        <input type="number"
               step="0.01"
               min="0"
               name="treatmentCost"
               value="<%= edit ? a.getTreatmentCost() : "0.00" %>">


        <!-- Buttons -->
        <button type="submit">Save</button>

        <a class="button secondary" href="dashboard">Cancel</a>

    </form>

</div>


<% if (!edit && "registered".equals(request.getParameter("msg"))) { %>

    <script>
        alert("Patient registered successfully");
    </script>

<% } %>

</body>

</html>