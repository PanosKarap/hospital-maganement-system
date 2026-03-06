<%@ page import="java.sql.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Προβολή Στοιχείων Ασθενούς</title>
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

        .info-field {
            font-size: 18px;
            margin-bottom: 15px;
            color: black;
        }

        .info-field strong {
            color: #6a0dad;
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
            margin: 20px auto 10px;
            width: 300px;
        }

        .back-button:hover {
            background-color: #4a0072;
        }
    </style>
</head>
<body>
    <div class="container">
        <h2>Τα Στοιχεία Μου</h2>
        <div class="info-field"><strong>Όνομα:</strong> <%= request.getAttribute("name") %></div>
        <div class="info-field"><strong>Επώνυμο:</strong> <%= request.getAttribute("surname") %></div>
        <div class="info-field"><strong>AMKA:</strong> <%= request.getAttribute("amka") %></div>
		<a href="Patient/PatientHome.jsp" class="back-button">← Επιστροφή στο Μενού</a>
    </div>
</body>
</html>
