<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="karapravdasmatz.simpleClasses.Doctor" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8" />
    <title>Λίστα Γιατρών</title>
    <style>
    	body{
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
            width: 600px;
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
        
        .info-field {
            font-size: 18px;
            margin-bottom: 10px;
            color: black;
        }

        .back-button {
            background-color: #00994d;
            color: white;
            padding: 12px 24px;
            border-radius: 10px;
            text-decoration: none;
            font-weight: bold;
            box-shadow: 0 6px 18px rgba(0, 153, 76, 0.25);
            transition: background-color 0.3s;
            display: inline-block;
			text-align: center;
            margin: 20px auto 30px;
            width: 250px;
        }

        .back-button:hover {
            background-color: #007a3d;
			text-decoration: underline;
        }
    </style>
</head>
<body>    
    <div class="container">
    	<h2>Λίστα Γιατρών</h2>
	    <div class="info-field">
		    <%
			    List<Doctor> doctors = (List<Doctor>) request.getAttribute("doctorsList");
		    	if (!doctors.isEmpty()) {
			        for (Doctor doctor : doctors) {
			            out.print(doctor.toString() + "<br/>");
			        }
			    } else out.print("Κανένας διαθέσιμος γιατρός");
			%>
		</div>		
		<a href="Admin/ShowUser.jsp" class="back-button">← Επιστροφή στην προηγούμενη</a>
		<a href="Admin/AdminHome.jsp" class="back-button">← Επιστροφή στο Μενού</a>
    </div>
</body>
</html>