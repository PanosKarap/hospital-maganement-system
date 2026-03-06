package karapravdasmatz.Servlets;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import karapravdasmatz.simpleClasses.Hashing;

import java.io.IOException;
import java.sql.*;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public LoginServlet() {
        super();
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	doPost(request,response);
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String passwordInput = request.getParameter("password");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital", "dbuser", "password");
                 PreparedStatement ps = conn.prepareStatement("SELECT * FROM Users WHERE username = ?")) {

                ps.setString(1, username);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        String storedHashedPassword = rs.getString("password");
                        String storedSalt = rs.getString("salt");
                        String role = rs.getString("role");
                        String name = rs.getString("name");

                        String inputHashedPassword = Hashing.hashPassword(passwordInput, storedSalt);

                        if (storedHashedPassword.equals(inputHashedPassword)) {
                        	
                            // Δημιουργία session
                            HttpSession session = request.getSession();
                            session.setAttribute("username", username);
                            session.setAttribute("role", role);
                            session.setAttribute("name", name);

                            // Προώθηση ανάλογα με τον ρόλο
                            switch (role.toLowerCase()) {
                                case "patient":
                                    response.sendRedirect("Patient/PatientHome.jsp");
                                    break;
                                case "doctor":
                                    response.sendRedirect("Doctor/DoctorHome.jsp");
                                    break;
                                case "admin":
                                    response.sendRedirect("Admin/AdminHome.jsp");
                                    break;
                                default:
                                    request.setAttribute("errorMessage", "Ο ρόλος χρήστη δεν αναγνωρίστηκε.");
                                    request.getRequestDispatcher("login.jsp").forward(request, response);
                                    break;
                            }
                        } else {
                            request.setAttribute("errorMessage", "Λάθος Στοιχεία!");
                            request.getRequestDispatcher("login.jsp").forward(request, response);
                        }
                    } else {
                        request.setAttribute("errorMessage", "Λάθος Στοιχεία!");
                        request.getRequestDispatcher("login.jsp").forward(request, response);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Σφάλμα: " + e.getMessage());
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}
