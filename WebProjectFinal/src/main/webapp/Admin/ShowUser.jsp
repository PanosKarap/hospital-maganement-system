<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Προβολή Χρηστών</title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(to right, #fce0e0, #ffe5e5);
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }

        .container {
            background: #fff;
            padding: 30px 40px;
            border-radius: 12px;
            box-shadow: 0 6px 18px rgba(200, 0, 0, 0.3);
            width: 420px;
            border-top: 8px solid #b30000;
            text-align: center;
        }

        h2 {
            color: #b30000;
            margin-bottom: 30px;
            font-size: 26px;
        }

        .btn {
            display: inline-block;
            width: auto;
            min-width: 200px;
            padding: 15px 40px;
            margin-bottom: 20px;
            border: none;
            border-radius: 8px;
            font-size: 18px;
            font-weight: bold;
            cursor: pointer;
            color: white;
            box-shadow: 0 4px 10px rgba(0,0,0,0.15);
            transition: background-color 0.3s ease;
            text-decoration: none;
        }

        /* Χρώματα ανά ρόλο */
        .btn.patient {
            background-color: #7b1fa2;
        }
        .btn.patient:hover {
            background-color: #4a0072;
        }

        .btn.doctor {
            background-color: #00994d;
        }
        .btn.doctor:hover {
            background-color: #007a3d;
        }

        .btn.admin {
            background-color: #e60000;
        }
        .btn.admin:hover {
            background-color: #990000;
        }
    </style>
</head>
<body>
    <div class="container">
        <h2>Προβολή Χρηστών</h2>
        <form action="../AdminServlet" method="post" style="margin-bottom: 20px;">
            <input type="hidden" name="action" value="showPatient">
            <button type="submit" class="btn patient">Ασθενείς</button>
        </form>
        
        <form action="../AdminServlet" method="post" style="margin-bottom: 20px;">
            <input type="hidden" name="action" value="showDoctor">
            <button type="submit" class="btn doctor">Ιατροί</button>
        </form>
        
        <form action="../AdminServlet" method="post" style="margin-bottom: 20px;">
            <input type="hidden" name="action" value="showAdmin">
            <button type="submit" class="btn admin">Διαχειριστές</button>
        </form>

        <form action="AdminHome.jsp" method="get" style="margin-bottom: 0;">
            <button type="submit" class="btn admin">← Επιστροφή στο Μενού</button>
        </form>
    </div>
</body>
</html>
