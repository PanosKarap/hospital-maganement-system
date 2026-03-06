<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Κλείσιμο Ραντεβού</title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(to right, #f0e6fa, #f5f0fc);
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }

        .container {
            background: #ffffff;
            padding: 35px 30px;
            border-radius: 12px;
            box-shadow: 0 6px 18px rgba(106, 13, 173, 0.25);
            width: 400px;
            max-height: 80vh;
            overflow-y: auto;
            border-top: 8px solid #6a0dad;
            text-align: center;
        }

        h2 {
            color: #6a0dad;
            margin-bottom: 25px;
            font-size: 26px;
        }

        input {
            width: 100%;
            padding: 12px;
            margin-top: 15px;
            border-radius: 8px;
            border: 1px solid #ccc;
            font-size: 16px;
            box-sizing: border-box;
        }
        
        .submit-button {
			border: 1px solid #ccc;
            font-size: 16px;
            box-sizing: border-box;
			background-color: #6a0dad;
            color: white;
            padding: 12px 24px;
            border-radius: 10px;
            text-decoration: none;
            font-weight: bold;
            box-shadow: 0 4px 12px rgba(106, 13, 173, 0.3);
            transition: background-color 0.3s;
            display: inline-block;
			text-align: center;
            margin: 30px auto 10px;
            width: 300px;
        }

        .back-button {
            background-color: #6a0dad;
            color: white;
            padding: 12px 24px;
            border-radius: 10px;
            text-decoration: none;
            font-weight: bold;
            box-shadow: 0 4px 12px rgba(106, 13, 173, 0.3);
            transition: background-color 0.3s;
            display: inline-block;
			text-align: center;
            margin: 30px auto 10px;
            width: 300px;
        }
    </style>
</head>
<body>
<div class="container">
    <h2>Κλείσε Ραντεβού</h2>
	<% 
	    String message = (String) request.getAttribute("message");
	    String error = (String) request.getAttribute("error");
	    if (message != null) {
	%>
	    <p style="color: green; font-weight: bold;"><%= message %></p>
	<% 
	    } else if (error != null) { 
	%>
	    <p style="color: red; font-weight: bold;"><%= error %></p>
	<% 
	    } 
	%>
    <form action="PatientServlet" method="post">
        <input type="hidden" name="action" value="submitAppointment" />        
        <input type="text" name="doctorUsername" placeholder="Username Γιατρού" required />
        <input type="date" name="appointmentDate" required />
        <button type="submit" class="submit-button">Υποβολή Ραντεβού</button>
    </form>
    <a href="Patient/PatientHome.jsp" class="back-button">← Επιστροφή στο Μενού</a> 
</div>
</body>
</html>
