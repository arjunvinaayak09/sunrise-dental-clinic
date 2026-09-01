<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head><title>Manage Users</title><link rel="stylesheet" href="assets/style.css"></head>
<body>
<div class="nav"><b>Sunrise Dental Clinic</b><a href="dashboard">Dashboard</a><a class="logout" href="logout">Logout</a></div>
<div class="container form-card">
<h2>Manage Users</h2>
<% if ("created".equals(request.getParameter("msg"))) { %><script>alert("User created successfully");</script><% } %>
<form method="post" action="users">
<label>Full Name</label><input name="fullName" required>
<label>Username</label><input name="username" required>
<label>Password</label><input type="password" name="password" required>
<label>Role</label>
<select name="role"><option>RECEPTIONIST</option><option>DOCTOR</option><option>ADMIN</option></select>
<button type="submit">Create User</button>
</form>
</div>
</body>
</html>
