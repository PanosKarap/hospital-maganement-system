<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*" %>
<%@ page import="karapravdasmatz.simpleClasses.Doctor" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Διαθέσιμοι Γιατροί</title>
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
            width: 600px;
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

        a {
            color: #7b1fa2;
            text-decoration: none;
            font-weight: bold;
        }

        a:hover {
            text-decoration: underline;
        }
        
        .info-field {
            font-size: 18px;
            margin-bottom: 10px;
            color: black;
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
            margin: 40px auto 30px;
            width: 400px;
        }

        .back-button:hover {
            background-color: #4a0072;
        }
    </style>
</head>
<body>
	<div class="container">
	    <h2>Διαθέσιμοι γιατροί</h2>
	    <div class="info-field">
		    <%
			    List<Doctor> doctors = (List<Doctor>) request.getAttribute("doctorsList");
		    	if (!doctors.isEmpty()) {
			        for (Doctor doctor : doctors) {
			            out.print(doctor.toStringAvailability() + "<br/>");
			        }
			    } else out.print("Κανένας διαθέσιμος γιατρός");
			%>
		</div>
		<a href="Patient/PatientHome.jsp" class="back-button">← Επιστροφή στο Μενού</a>
	</div>
</body>
</html>
