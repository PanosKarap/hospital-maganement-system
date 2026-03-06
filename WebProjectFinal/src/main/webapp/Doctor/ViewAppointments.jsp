<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="karapravdasmatz.simpleClasses.Appointment" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Προβολή Ραντεβού</title>
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
            box-shadow: 0 4px 12px rgba(0, 153, 76, 0.3);
            transition: background-color 0.3s;
            display: inline-block;
            text-align: center;
            margin: 40px auto 30px;
            width: 400px;
        }

        .back-button:hover {
            background-color: #007a3d;
        }
    </style>
</head>
<body>
    <div class="container">
        <h2>Προβολή Ραντεβού</h2>
        <div class="info-field">
            <%
                List<Appointment> appointmentList = (List<Appointment>) request.getAttribute("appointmentsList");
                if (appointmentList != null && !appointmentList.isEmpty()) {
                    for (Appointment appointment : appointmentList) {
                        out.print(appointment.toString() + "<br/>");
                    }
                } else {
                    out.print("Δεν υπάρχουν ραντεβού για προβολή.");
                }
            %>
        </div>
        <a href="Doctor/DoctorHome.jsp" class="back-button">← Επιστροφή στο Μενού</a>
    </div>
</body>
</html>
