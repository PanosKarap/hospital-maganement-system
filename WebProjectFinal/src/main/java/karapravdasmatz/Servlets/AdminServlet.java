package karapravdasmatz.Servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import karapravdasmatz.simpleClasses.Admin;
import karapravdasmatz.simpleClasses.Doctor;
import karapravdasmatz.simpleClasses.Hashing;
import karapravdasmatz.simpleClasses.Patient;
import karapravdasmatz.simpleClasses.Users;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/AdminServlet")
public class AdminServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public AdminServlet() {
        super();
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	doPost(request,response);
    }

    // Εδώ γίνεται διαχείριση όλων των λειτουργιών που αφορούν τον admin
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        
        
        if ("addUser".equals(action)) { // Με την επιλογή προσθήκης χρήστη στο μενού
            response.sendRedirect("Admin/AddUser.jsp");
        } else if ("addUserSubmit".equals(action)) { // Με την επιλογή submit στην σελίδα προσθήκης χρήστη
            handleAddUser(request, response);
        } else if ("deleteUser".equals(action)) { // Με την επιλογή διαγραφής χρήστη στο μενού
            response.sendRedirect("Admin/DeleteUser.jsp");
        } else if ("deleteUserSubmit".equals(action)) { // Με την επιλογή submit στην σελίδα διαγραφής χρήστη
            handleDeleteUser(request, response);            
        } else if ("showUser".equals(action)) { // Με την επιλογή προβολής χρήστη στο μενού
            response.sendRedirect("Admin/ShowUser.jsp");
        } else if ("showDoctor".equals(action)) { // Με την επιλογή του κουμπιού Ιατροί στη σελίδα επιλογής είδους χρήστη για προβολή
            handleShowDoctors(request, response);
        } else if ("showAdmin".equals(action)) { // Με την επιλογή του κουμπιού Διαχειριστές στη σελίδα επιλογής είδους χρήστη για προβολή
            handleShowAdmins(request, response);
        } else if ("showPatient".equals(action)) { // Με την επιλογή του κουμπιού Ασθενείς στη σελίδα επιλογής είδους χρήστη για προβολή
            handleShowPatients(request, response);
        } else {
            response.sendRedirect("Admin/AdminHome.jsp");
        }
    }

    // Εκτελείται με το πάτημα του κουμπιού submit στη σελίδα προσθήκης χρήστη. Δέχεται τα στοιχεία από τα boxes της φόρμας και ανάλογα το role φτιάχνει το κατάλληλο αντικείμενο
    // και εκτελεί τη συνάρτηση saveToDatabase() για να το αποθηκεύσει στη βάση
    protected void handleAddUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String role = request.getParameter("role"); // "patient", "admin", "doctor"
        String specialty = request.getParameter("specialty");
        String amka = request.getParameter("amka");
        String finalPassword, salt;
        try {
            if (Users.usernameExists(username)) {
                request.getSession().setAttribute("errorMessage", "Το username υπάρχει ήδη.");
                response.sendRedirect("Admin/AddUser.jsp");
                return;
            } else {
            	salt = Hashing.generateSalt();
            	finalPassword = Hashing.hashPassword(password,salt);
            }

            if ("doctor".equalsIgnoreCase(role)) {
                Doctor d = new Doctor(username, finalPassword, firstName, lastName, specialty, salt);
                d.saveToDatabase();
            } else if ("patient".equalsIgnoreCase(role)) {
                Patient p = new Patient(username, finalPassword, firstName, lastName, amka, salt);
                p.saveToDatabase();
            } else if ("admin".equalsIgnoreCase(role)) {
                Admin a = new Admin(username, finalPassword, firstName, lastName, salt);
                a.saveToDatabase();
            }
            response.sendRedirect("Admin/AdminHome.jsp");

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Σφάλμα κατά την προσθήκη χρήστη: " + e.getMessage());
            request.getRequestDispatcher("Admin/AddUser.jsp").forward(request, response);
        }
    }
    
    // Εκτελείται με το πάτημα του κουμπιού submit στη σελίδα διαγραφής χρήστη. Δέχεται το username και κάνει delete από τα tables Users και
    // Admin (μέσω του Cascade που ισχύει) εάν υπάρχει ο χρήστης
    private void handleDeleteUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        try {
            boolean deleted = Users.deleteUserByUsername(username);
            if (!deleted) {
                request.getSession().setAttribute("errorMessage", "Το username δεν υπάρχει.");
                response.sendRedirect("Admin/DeleteUser.jsp");
            } else {
                response.sendRedirect("Admin/AdminHome.jsp");
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Σφάλμα κατά τη διαγραφή χρήστη: " + e.getMessage());
            request.getRequestDispatcher("Admin/DeleteUser.jsp").forward(request, response);
        }
    }
    
    // Πρόσβαση στους γιατρούς μέσω της συνάρτησης getDoctorsFromDatabase() και έπειτα εμφάνιση στο ShowDoctor.jsp
    private void handleShowDoctors(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Doctor> doctorsList = Admin.getDoctorsFromDatabase();
            request.setAttribute("doctorsList", doctorsList);
            request.getRequestDispatcher("Admin/ShowDoctor.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Σφάλμα κατά την ανάγνωση γιατρών: " + e.getMessage());
            request.getRequestDispatcher("Admin/AdminHome.jsp").forward(request, response);
        }
    }
    
    // Πρόσβαση στους ασθενείς μέσω της συνάρτησης getPatientsFromDatabase() και έπειτα εμφάνιση στο ShowPatient.jsp
    private void handleShowPatients(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Patient> patientsList = Admin.getPatientsFromDatabase();
            request.setAttribute("patientsList", patientsList);
            request.getRequestDispatcher("Admin/ShowPatient.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Σφάλμα κατά την ανάγνωση ασθενών: " + e.getMessage());
            request.getRequestDispatcher("Admin/AdminHome.jsp").forward(request, response);
        }
    }

    // Πρόσβαση στους διαχειριστές μέσω της συνάρτησης getAdminsFromDatabase() και έπειτα εμφάνιση στο ShowAdmin.jsp
    private void handleShowAdmins(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Admin> adminsList = Admin.getAdminsFromDatabase();
            request.setAttribute("adminsList", adminsList);
            request.getRequestDispatcher("Admin/ShowAdmin.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Σφάλμα κατά την ανάγνωση διαχειριστών: " + e.getMessage());
            request.getRequestDispatcher("Admin/AdminHome.jsp").forward(request, response);
        }
    }
}
