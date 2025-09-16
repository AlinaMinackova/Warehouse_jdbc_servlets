<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="org.example.entity.User" %>
<%@ page import="java.util.List" %>
<%
    List<User> users = (List<User>) request.getAttribute("users");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Users</title>
</head>
<body>
<h1>Users</h1>
<table border="1" cellpadding="6">
    <tr><th>ID</th><th>Name</th><th>Email</th></tr>
    <%
        if (users != null) {
            for (User u : users) {
    %>
    <tr>
        <td><%= u.getId() %></td>
        <td><%= u.getName() %></td>
        <td><%= u.getEmail() %></td>
    </tr>
    <%
            }
        }
    %>
</table>

<h2>Add user</h2>
<form method="post" action="users">
    Name: <input name="name"/> <br/>
    Email: <input name="email"/> <br/>
    <button type="submit">Add</button>
</form>
</body>
</html>