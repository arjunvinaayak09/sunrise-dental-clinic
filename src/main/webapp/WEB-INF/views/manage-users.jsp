<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.sunrise.model.User" %>

<!DOCTYPE html>

<html>

<head>

    <title>Manage Users - Sunrise Dental Clinic</title>

    <style>

        body {
            font-family: Arial, sans-serif;
            background: #f4f7fb;
            margin: 0;
        }

        .nav {
            background: #ffffff;
            padding: 18px 30px;
            box-shadow: 0 2px 8px rgba(0,0,0,0.1);
        }

        .nav b {
            font-size: 20px;
        }

        .nav a {
            margin-left: 25px;
            text-decoration: none;
        }

        .logout {
            float: right;
        }

        .container {
            width: 90%;
            max-width: 1100px;
            margin: 30px auto;
        }

        .card {
            background: white;
            padding: 25px;
            margin-bottom: 25px;
            border-radius: 10px;
            box-shadow: 0 3px 12px rgba(0,0,0,0.1);
        }

        h2, h3 {
            margin-top: 0;
        }

        label {
            display: block;
            margin-top: 12px;
            font-weight: bold;
        }

        input,
        select {
            width: 100%;
            padding: 10px;
            margin-top: 5px;
            box-sizing: border-box;
            border: 1px solid #ccc;
            border-radius: 5px;
        }

        button {
            border: none;
            padding: 10px 18px;
            margin-top: 15px;
            border-radius: 5px;
            cursor: pointer;
        }

        .save-button {
            background: #198754;
            color: white;
        }

        .update-button {
            background: #0d6efd;
            color: white;
        }

        .delete-button {
            background: #dc3545;
            color: white;
        }

        .cancel-button {
            background: #6c757d;
            color: white;
            text-decoration: none;
            padding: 10px 18px;
            border-radius: 5px;
            display: inline-block;
            margin-top: 15px;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }

        th,
        td {
            padding: 12px;
            border: 1px solid #ddd;
            text-align: left;
        }

        th {
            background: #f1f1f1;
        }

        .success {
            background: #d4edda;
            color: #155724;
            padding: 12px;
            margin-bottom: 20px;
            border-radius: 5px;
        }

        .error {
            background: #f8d7da;
            color: #721c24;
            padding: 12px;
            margin-bottom: 20px;
            border-radius: 5px;
        }

        .actions {
            white-space: nowrap;
        }

    </style>

</head>

<body>


<div class="nav">

    <b>Sunrise Dental Clinic</b>

    <a href="<%= request.getContextPath() %>/dashboard">
        Dashboard
    </a>
    
    <a href="<%= request.getContextPath() %>/help"
   style="background:#2e7d32;">
    Help
</a>

    <a class="logout"
       href="<%= request.getContextPath() %>/logout">
        Logout
    </a>

</div>


<div class="container">


<%
    String successMessage =
            (String) request.getAttribute("successMessage");

    String errorMessage =
            (String) request.getAttribute("errorMessage");

    if (successMessage != null) {
%>

    <div class="success">
        <%= successMessage %>
    </div>

<%
    }

    if (errorMessage != null) {
%>

    <div class="error">
        <%= errorMessage %>
    </div>

<%
    }
%>


<%
    User editUser =
            (User) request.getAttribute("editUser");

    if (editUser != null) {
%>


<!-- UPDATE USER -->

<div class="card">

    <h2>Update User</h2>

    <form method="post"
          action="<%= request.getContextPath() %>/users">

        <input type="hidden"
               name="action"
               value="update">

        <input type="hidden"
               name="id"
               value="<%= editUser.getId() %>">


        <label>Full Name</label>

        <input type="text"
               name="fullName"
               value="<%= editUser.getFullName() %>"
               required>


        <label>Username</label>

        <input type="text"
               name="username"
               value="<%= editUser.getUsername() %>"
               required>


        <label>New Password</label>

        <input type="text"
               name="password"
               placeholder="Leave empty to keep current password">


        <label>Role</label>

        <select name="role" required>

            <option value="ADMIN"
                <%= "ADMIN".equalsIgnoreCase(editUser.getRole())
                        ? "selected" : "" %>>
                Admin
            </option>

            <option value="RECEPTIONIST"
                <%= "RECEPTIONIST".equalsIgnoreCase(editUser.getRole())
                        ? "selected" : "" %>>
                Receptionist
            </option>

            <option value="DOCTOR"
                <%= "DOCTOR".equalsIgnoreCase(editUser.getRole())
                        ? "selected" : "" %>>
                Doctor
            </option>

        </select>


        <button class="update-button"
                type="submit">

            Save Update

        </button>


        <a class="cancel-button"
           href="<%= request.getContextPath() %>/users">

            Cancel

        </a>

    </form>

</div>


<%
    } else {
%>


<!-- CREATE USER -->

<div class="card">

    <h2>Create New User</h2>

    <form method="post"
          action="<%= request.getContextPath() %>/users">

        <input type="hidden"
               name="action"
               value="create">


        <label>Full Name</label>

        <input type="text"
               name="fullName"
               required>


        <label>Username</label>

        <input type="text"
               name="username"
               required>


        <label>Password</label>

        <input type="text"
               name="password"
               required>


        <label>Role</label>

        <select name="role" required>

            <option value="">
                Select Role
            </option>

            <option value="ADMIN">
                Admin
            </option>

            <option value="RECEPTIONIST">
                Receptionist
            </option>

            <option value="DOCTOR">
                Doctor
            </option>

        </select>


        <button class="save-button"
                type="submit">

            Create User

        </button>

    </form>

</div>


<%
    }
%>


<!-- USER LIST -->

<div class="card">

    <h2>Manage Users</h2>

    <table>

        <tr>

            <th>ID</th>

            <th>Full Name</th>

            <th>Username</th>

            <th>Password</th>

            <th>Role</th>

            <th>Actions</th>

        </tr>


<%
    List<User> users =
            (List<User>)
            request.getAttribute("users");

    if (users != null && !users.isEmpty()) {

        for (User user : users) {
%>


        <tr>

            <td>
                <%= user.getId() %>
            </td>

            <td>
                <%= user.getFullName() %>
            </td>

            <td>
                <%= user.getUsername() %>
            </td>

            <td>
                <%= user.getPasswordHash() %>
            </td>

            <td>
                <%= user.getRole() %>
            </td>

            <td class="actions">

                <a href="<%= request.getContextPath() %>/users?action=edit&id=<%= user.getId() %>">

                    <button type="button"
                            class="update-button">

                        Edit

                    </button>

                </a>


                <form method="post"
                      action="<%= request.getContextPath() %>/users"
                      style="display:inline;"
                      onsubmit="return confirmDelete('<%= user.getUsername() %>');">

                    <input type="hidden"
                           name="action"
                           value="delete">

                    <input type="hidden"
                           name="id"
                           value="<%= user.getId() %>">

                    <button type="submit"
                            class="delete-button">

                        Delete

                    </button>

                </form>

            </td>

        </tr>


<%
        }

    } else {
%>

        <tr>

            <td colspan="6">
                No users found.
            </td>

        </tr>

<%
    }
%>

    </table>

</div>


</div>


<script>

function confirmDelete(username) {

    return confirm(
        "Are you sure you want to delete user '" +
        username +
        "'?"
    );
}

</script>


</body>

</html>