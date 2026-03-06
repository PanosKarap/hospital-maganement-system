<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Μενού Ιατρού</title>
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

        .doctor-dashboard {
            background: #ffffff;
            padding: 35px 30px;
            border-radius: 12px;
            box-shadow: 0 6px 18px rgba(0, 153, 76, 0.25);
            width: 400px;
            border-top: 8px solid #00994d;
            text-align: center;
        }

        h2 {
            color: #00994d;
            margin-bottom: 25px;
            font-size: 26px;
        }

        .dashboard-button {
            display: inline-block;
            width: 80%;
            background-color: #00994d;
            color: white;
            padding: 12px 0;
            margin: 10px 0;
            border: none;
            border-radius: 6px;
            font-weight: bold;
            font-size: 17px;
            text-decoration: none;
            transition: background-color 0.3s ease;
            cursor: pointer;
        }

        .dashboard-button:hover {
            background-color: #007a3d;
        }

        .logout {
            margin-top: 20px;
            color: #555;
            font-size: 14px;
        }

        .logout a {
            color: #00994d;
            text-decoration: none;
        }

        .logout a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
    <div class="doctor-dashboard">
		<h2><%= session.getAttribute("role") %></h2>
        <h2>Καλώς ήρθες, <%= session.getAttribute("name")%>!</h2>

        <form action="../DoctorServlet" method="post">
            <input type="hidden" name="action" value="viewDoctorAppointmentsPage" />
            <button type="submit" class="dashboard-button">Προβολή Ραντεβού</button>
        </form>
        
		<form action="../DoctorServlet" method="post">
            <input type="hidden" name="action" value="RegisterationAvailabilityPage" />
            <button type="submit" class="dashboard-button">Καταχώρηση Διαθεσιμότητας Ραντεβού</button>
        </form>

		<form action="../DoctorServlet" method="post">
		    <input type="hidden" name="action" value="cancelAppointmentPage">
		    <button type="submit" class="dashboard-button">Ακύρωση Ραντεβού</button>
		</form>

        <div class="logout">
            <p><a href="../LogoutServlet">Αποσύνδεση</a></p>
        </div>
    </div>
</body>
</html>
