<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Μενού Ασθενή</title>
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

        .patient-dashboard {
            background: #ffffff;
            padding: 35px 30px;
            border-radius: 12px;
            box-shadow: 0 6px 18px rgba(106, 13, 173, 0.25);
            width: 400px;
            border-top: 8px solid #6a0dad;
            text-align: center;
        }

        h2 {
            color: #6a0dad;
            margin-bottom: 25px;
            font-size: 26px;
        }

        .dashboard-button {
            display: inline-block;
            width: 80%;
            background-color: #7b1fa2;
            color: white;
            padding: 12px 0;
            margin: 10px 0;
            border: none;
            border-radius: 6px;
            font-weight: bold;
            font-size: 17px;
            text-decoration: none;
            transition: background-color 0.3s ease;
        }

        .dashboard-button:hover {
            background-color: #4a0072;
        }

        .logout {
            margin-top: 20px;
            color: #555;
            font-size: 14px;
        }

        .logout a {
            color: #6a0dad;
            text-decoration: none;
        }

        .logout a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
    <div class="patient-dashboard">
        <h2><%= session.getAttribute("role") %></h2>
        <h2>Καλώς ήρθες, <%= session.getAttribute("name")%>!</h2>
        
		<form action="../PatientServlet" method="post">
		    <input type="hidden" name="action" value="viewInfo">
			<button type="submit" class="dashboard-button">Προβολή Στοιχείων</button>
		</form>
		
		<form action="../PatientServlet" method="post">
		    <input type="hidden" name="action" value="viewHistory">
		    <button type="submit" class="dashboard-button">Προβολή Ιστορικού</button>
		</form>	
		
		<form action="../PatientServlet" method="post">
		    <input type="hidden" name="action" value="viewDoctorAvailability">
		    <button type="submit" class="dashboard-button">Διαθέσιμοι γιατροί</button>
		</form>
		
		<form action="../PatientServlet" method="post">
        	<input type="hidden" name="action" value="viewAllDoctors" />
            <button type="submit" class="dashboard-button">Προβολή Όλων των Γιατρών</button>
    	</form>

		<form action="../PatientServlet" method="post">
		    <input type="hidden" name="action" value="bookAppointmentPage">
		    <button type="submit" class="dashboard-button">Κλείσιμο Ραντεβού</button>
		</form>

		<form action="../PatientServlet" method="post">
		    <input type="hidden" name="action" value="cancelAppointmentPage">
		    <button type="submit" class="dashboard-button">Ακύρωση Ραντεβού</button>
		</form>

        <div class="logout">
            <p><a href="../LogoutServlet">Αποσύνδεση</a></p>
        </div>
    </div>
</body>
</html>
