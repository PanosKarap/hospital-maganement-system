<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8" />
    <title>Επιλογή Ημερομηνίας</title>
<style>
    body {
        font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        background: linear-gradient(to right, #e6f9ec, #f2fcf5);
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
        box-shadow: 0 6px 18px rgba(0, 153, 76, 0.25);
        width: 400px;
        max-height: 80vh;
        overflow-y: auto;
        border-top: 8px solid #00994d;
        text-align: center;
    }

    h2 {
        color: #00994d;
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
        background-color: #00994d;
        color: white;
        padding: 12px 24px;
        border-radius: 10px;
        text-decoration: none;
        font-weight: bold;
        box-shadow: 0 4px 12px rgba(0, 153, 76, 0.3);
        transition: background-color 0.3s;
        display: inline-block;
        text-align: center;
        margin: 30px auto 10px;
        width: 300px;
        border: none;
        font-size: 16px;
    }

    .submit-button:hover {
        background-color: #007a3d;
    }

    .back-button {
        background-color: #00994d;
        color: white;
        padding: 12px 24px;
        border-radius: 10px;
        text-decoration: none;
        font-weight: bold;
        box-shadow: 0 4px 12px rgba(0, 153, 76, 0.3);
        transition: background-color 0.3s;
        display: inline-block;
        text-align: center;
        margin: 30px auto 10px;
        width: 300px;
        font-size: 16px;
        border: none;
    }

    .back-button:hover {
        background-color: #007a3d;
    }
</style>
</head>
<body>
    <div class="container">
    	<h2>Καταχώρηση Διαθεσιμότητας Ραντεβού</h2>
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
        <h2>Επιλογή Ημερομηνίας</h2>
        <form action="DoctorServlet" method="post">
            <input type="hidden" name="action" value="showAvailabilitySubmit" />
            <input type="date" name="appointmentDate" required />
            <button type="submit" class="submit-button">Υποβολή</button>
        </form>
    <a href="Doctor/DoctorHome.jsp" class="back-button">← Επιστροφή στο Μενού</a> 
    </div>
</body>
</html>
