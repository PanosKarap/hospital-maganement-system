<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Εισαγωγή Χρήστη</title>
    
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(to right, #fce0e0, #ffe5e5);
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }

        .form-container {
            background: #fff;
            padding: 25px 35px 30px 35px;
            border-radius: 12px;
            box-shadow: 0 6px 18px rgba(200, 0, 0, 0.3);
            width: 420px;
            border-top: 8px solid #b30000;
        }

        h2 {
            text-align: center;
            color: #b30000;
            margin-top: 5px;
            margin-bottom: 25px;
            font-size: 24px;
        }

        label {
            display: block;
            margin-bottom: 6px;
            font-weight: bold;
            font-size: 15px;
        }

        input[type="text"], input[type="email"], input[type="password"] {
            width: 100%;
            padding: 12px;
            margin-bottom: 18px;
            border: 1px solid #ccc;
            border-radius: 6px;
            font-size: 15px;
            box-sizing: border-box;
        }

        input[type="submit"] {
            width: 100%;
            background-color: #e60000;
            color: white;
            padding: 12px;
            border: none;
            border-radius: 6px;
            font-weight: bold;
            font-size: 17px;
            cursor: pointer;
        }

        input[type="submit"]:hover {
            background-color: #990000;
        }

        .back-button {
		background-color: #e60000;
        color: white;
        padding: 12px 24px;
        border-radius: 10px;
        text-decoration: none;
        font-weight: bold;
		box-shadow: 0 4px 10px rgba(200, 0, 0, 0.3);
        transition: background-color 0.3s;
		display: block;
        text-align: center;
		margin: 30px auto 10px auto;
        width: 300px;
        font-size: 16px;
        border: none;
        }

        .back-button:hover {
            background-color: #990000;
        }
    </style>
</head>
<body>
    <div class="form-container">
        <h2>Εισαγωγή Νέου Χρήστη</h2>
        <%
		    String error = (String) session.getAttribute("errorMessage");
		    if (error != null) {
		%>
		    <div style="color:red; text-align:center; margin-bottom:15px;">
		        <%= error %>
		    </div>
		<%
		        session.removeAttribute("errorMessage"); // Διαγραφή για να μην ξαναεμφανιστεί
		    }
		%>
		<form action="../AdminServlet" method="post">
		    <input type="hidden" name="action" value="addUserSubmit" />
		    
		    <label>Username</label>
		    <input type="text" name="username" required>
		    
		    <label>Password</label>
		    <input type="password" name="password" required>
		    
		    <label>Όνομα</label>
		    <input type="text" name="firstName" required>
		    
		    <label>Επώνυμο</label>
		    <input type="text" name="lastName" required>
		    
			<label>Ρόλος</label>
			<select name="role" required>
			    <option value="">--Επιλέξτε Ρόλο--</option>
			    <option value="patient">Ασθενής</option>
			    <option value="doctor">Ιατρός</option>
			    <option value="admin">Διαχειριστής</option>
			</select>
			
			<label>Ειδικότητα (για Ιατρό)</label>
			<input type="text" name="specialty">			

			<label>ΑΜΚΑ (για Ασθενή)</label>
			<input type="text" name="amka">
			
			<div style="margin-top: 25px;">
			    <input type="submit" value="Προσθήκη">
			</div>
		</form>
    </div>
	<a href="AdminHome.jsp" class="back-button">← Επιστροφή στο Μενού</a>
</body>
</html>
