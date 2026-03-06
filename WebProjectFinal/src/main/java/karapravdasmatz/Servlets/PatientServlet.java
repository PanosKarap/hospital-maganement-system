package karapravdasmatz.Servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import karapravdasmatz.simpleClasses.Appointment;
import karapravdasmatz.simpleClasses.Doctor;
import karapravdasmatz.simpleClasses.Patient;

import java.io.IOException;
import java.sql.*;
import java.util.*;

@WebServlet("/PatientServlet")
public class PatientServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public PatientServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    // Εδώ γίνεται διαχείριση όλων των λειτουργιών που αφορούν τον ασθενή
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("viewInfo".equals(action)) { // Με την επιλογή προβολής στοιχείων στο μενού
            loadInfo(request, response);            
        } else if("viewHistory".equals(action)) { // Με την επιλογή προβολής ιστορικού στο μενού
            loadAppointmentHistory(request, response);
        } else if ("viewDoctorAvailability".equals(action)) { // Με την επιλογή διαθέσιμων γιατρών στο μενού
        	viewAvailableDoctors(request, response);   
        } else if ("viewAllDoctors".equals(action)) { // Με την επιλογή προβολής όλων των γιατρών στο μενού
        	viewAllDoctors(request, response);  
        } else if ("bookAppointmentPage".equals(action)) { // Με την επιλογή κλείσιμου ραντεβού στο μενού
            request.getRequestDispatcher("Patient/BookAppointment.jsp").forward(request, response);
        } else if ("submitAppointment".equals(action)) { // Με το πάτημα του κουμπιού Υποβολή Ραντεβού στη σελίδα BookAppointment.jsp
        	bookAppointment(request, response);  
        } else if ("cancelAppointmentPage".equals(action)) { // Με την επιλογή ακύρωσης ραντεβού στο μενού
            request.getRequestDispatcher("Patient/CancelAppointment.jsp").forward(request, response);
        } else if ("cancelAppointment".equals(action)) {  // Με το πάτημα του κουμπιού Ακύρωση Ραντεβού στη σελίδα CancelAppointment.jsp
            cancelAppointment(request, response);
        }
    }    
    
    // Forward στη σελίδα ViewInfo.jsp για προβολή των στοιχείων του ασθενή
    private void loadInfo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        
        String username = (String) session.getAttribute("username");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital", "dbuser", "password");
                 PreparedStatement ps = conn.prepareStatement(
                         "SELECT u.name, u.surname, p.amka " +
                         "FROM Users u JOIN Patient p ON u.username = p.username WHERE u.username = ?")) {
                ps.setString(1, username);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        request.setAttribute("name", rs.getString("name"));
                        request.setAttribute("surname", rs.getString("surname"));
                        request.setAttribute("amka", rs.getString("amka"));
                    }
                }
            }
        } catch (Exception e) {
            request.setAttribute("error", e.getMessage());
        }
        request.getRequestDispatcher("Patient/ViewInfo.jsp").forward(request, response);
    }    
    
    // Forward στη σελίδα ViewHistory.jsp για προβολή των παρελθοντικών ραντεβού του ασθενή
    private void loadAppointmentHistory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String username = (String) session.getAttribute("username");
        List<Appointment> historyList = new ArrayList<>();
        Patient patient = new Patient(username);
        try {
			historyList = patient.getAppointmentHistory();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
            request.setAttribute("error", "Σφάλμα: " + e.getMessage());      
        }
        
        request.setAttribute("historyList", historyList);
        request.getRequestDispatcher("Patient/ViewHistory.jsp").forward(request, response);
    }
    
    // Forward στη σελίδα AvailableDoctors.jsp για προβολή των διαθέσιμων γιατρών για ραντεβού
    private void viewAvailableDoctors(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        
        List<Doctor> doctorsList = new ArrayList<>();
        try {
        	doctorsList = Doctor.getAvailableDoctors();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
            request.setAttribute("error", "Σφάλμα: " + e.getMessage());      
        }
        request.setAttribute("doctorsList", doctorsList);
        request.getRequestDispatcher("Patient/AvailableDoctors.jsp").forward(request, response);
    }   
    
    // Forward στη σελίδα ViewDoctors.jsp για προβολή όλων των γιατρών του νοσοκομείου
    private void viewAllDoctors(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        
        List<Doctor> doctorsList = new ArrayList<>();
        try {
        	doctorsList = Doctor.getAllDoctors();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
            request.setAttribute("error", "Σφάλμα: " + e.getMessage());      
        }
        request.setAttribute("doctorsList", doctorsList);
        request.getRequestDispatcher("Patient/ViewDoctors.jsp").forward(request, response);
    }
    
    // Forward στη σελίδα BookAppointment.jsp για κλείσιμο ραντεβού από τον ασθενή
    private void bookAppointment(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        
        String username = (String) session.getAttribute("username");
        String doctorUsername = request.getParameter("doctorUsername");
        String appointmentDate = request.getParameter("appointmentDate");   
        try {
            int rowsUpdated = Appointment.bookAppointment(username, doctorUsername, appointmentDate);
            if (rowsUpdated > 0) {
            	request.setAttribute("message", "Το ραντεβού κλείστηκε επιτυχώς.");
            } else {
            	request.setAttribute("error", "Το ραντεβού δεν είναι διαθέσιμο.");
            }
        } catch (IllegalArgumentException e) {
            request.setAttribute("error", "Η ημερομηνία που δώσατε δεν είναι έγκυρη ή είναι πολύ μεγάλη.");
        } catch (Exception e) {
            request.setAttribute("error", "Σφάλμα: " + e.getMessage());
        }
        request.getRequestDispatcher("Patient/BookAppointment.jsp").forward(request, response);
    }

    // Forward στη σελίδα CancelAppointment.jsp για ακύρωση ραντεβού από τον ασθενή
    private void cancelAppointment(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String username = (String) session.getAttribute("username");
        String doctorUsername = request.getParameter("doctorUsername");
        String appointmentDate = request.getParameter("appointmentDate");
        try {
        	int rowsUpdated = Appointment.cancelAppointmentForPatient(username, doctorUsername, appointmentDate);
        	if (rowsUpdated > 0) {
        		request.setAttribute("message", "Το ραντεβού ακυρώθηκε επιτυχώς.");
        	} else {
        		request.setAttribute("error", "Το ραντεβού δεν μπορεί να ακυρωθεί. Πιθανώς είναι πολύ κοντά στην ημερομηνία ή δεν έχει κλειστεί από εσάς.");
        	}
        } catch (IllegalArgumentException e) {
            request.setAttribute("error", "Η ημερομηνία που δώσατε δεν είναι έγκυρη ή είναι πολύ μεγάλη.");
        } catch (Exception e) {
            request.setAttribute("error", "Σφάλμα: " + e.getMessage());
        }
        request.getRequestDispatcher("Patient/CancelAppointment.jsp").forward(request, response);
    }
}
