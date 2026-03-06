<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Μενού Διαχειριστή</title>
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

        .admin-dashboard {
            background: #ffffff;
            padding: 35px 30px;
            border-radius: 12px;
            box-shadow: 0 6px 18px rgba(200, 0, 0, 0.3);
            width: 400px;
            border-top: 8px solid #b30000;
            text-align: center;
        }

        h2 {
            color: #b30000;
            margin-bottom: 25px;
            font-size: 26px;
        }

        .dashboard-button {
            display: inline-block;
            width: 80%;
            background-color: #e60000;
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
            background-color: #990000;
        }

        .logout {
            margin-top: 20px;
            color: #555;
            font-size: 14px;
        }

        .logout a {
            color: #b30000;
            text-decoration: none;
        }

        .logout a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
    <div class="admin-dashboard">
        <h2><%= session.getAttribute("role") %></h2>
        <h2>Καλώς ήρθες, <%= session.getAttribute("name") %>!</h2>

        <form action="../AdminServlet" method="post">
            <input type="hidden" name="action" value="addUser">
            <button type="submit" class="dashboard-button">Εισαγωγή Νέου Χρήστη</button>
        </form>

        <form action="../AdminServlet" method="post">
            <input type="hidden" name="action" value="deleteUser">
            <button type="submit" class="dashboard-button">Διαγραφή Χρήστη</button>
        </form>
        
        <form action="../AdminServlet" method="post">
            <input type="hidden" name="action" value="showUser">
            <button type="submit" class="dashboard-button">Προβολή Χρηστών</button>
        </form>

        <div class="logout">
            <p><a href="../LogoutServlet">Αποσύνδεση</a></p>
        </div>
    </div>
</body>
</html>
