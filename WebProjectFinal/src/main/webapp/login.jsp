<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Hospital Patient Login</title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(to right, #e6f2f9, #f2f9fc);
            display: flex;
            justify-content: center;
            align-items: center;
            height: 87vh;
            font-size: 18px;
        }
        .patientlogin-container {
            background: #ffffff;
            padding: 40px 35px;
            border-radius: 12px;
            box-shadow: 0 6px 18px rgba(0, 123, 153, 0.25);
            width: 380px;
            border-top: 8px solid #007b99;
        }
        .patientlogin-container h2 {
            text-align: center;
            color: #007b99;
            margin-bottom: 25px;
            font-size: 26px;
        }
        label {
            font-weight: bold;
            color: #333;
            display: block;
            margin-bottom: 6px;
            font-size: 18px;
        }
        input[type=text], input[type=password] {
            width: 100%;
            padding: 14px;
            margin: 10px 0 20px 0;
            box-sizing: border-box;
            border: 1px solid #ccc;
            border-radius: 6px;
            font-size: 17px;
        }
        input[type=submit] {
            width: 100%;
            background-color: #007b99;
            color: white;
            padding: 14px;
            border: none;
            border-radius: 6px;
            font-weight: bold;
            font-size: 18px;
            cursor: pointer;
        }
        input[type=submit]:hover {
            background-color: #005f73;
        }
        .message {
            color: red;
            margin-top: 14px;
            text-align: center;
            font-size: 16px;
        }
        .logo {
            display: block;
            margin: 0 auto 20px auto;
            width: 80px;
        }
    </style>
</head>
<body>
    <div class="patientlogin-container">
        <img src="images/hospital.png" alt="Hospital Logo" class="logo">

        <h2>Login</h2>
        <form action="LoginServlet" method="post">
            <label style="text-align: center" for="username">Username</label>
            <input style="text-align: center" type="text" name="username" placeholder="Πληκτρολογήστε το username σας" required>

            <label style="text-align: center" for="password">Password</label>
            <input style="text-align: center" type="password" name="password" placeholder="Πληκτρολογήστε τον κωδικό σας" required>

            <input type="submit" value="Login">
        </form>
        <div class="message">
            <%= request.getAttribute("errorMessage") != null ? request.getAttribute("errorMessage") : "" %>
        </div>
    </div>
</body>
</html>
