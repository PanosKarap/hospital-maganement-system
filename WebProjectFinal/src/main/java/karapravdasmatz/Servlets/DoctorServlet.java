package karapravdasmatz.Servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import karapravdasmatz.simpleClasses.Appointment;
import karapravdasmatz.simpleClasses.Doctor;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/DoctorServlet")
public class DoctorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public DoctorServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

    // Εδώ γίνεται διαχείριση όλων των λειτουργιών που αφορούν τον doctor
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    String action = request.getParameter("action");	    
	    
	   if ("viewDoctorAppointmentsPage".equals(action)) { // Με την επιλογή προβολής ραντεβού στο μενού
           loadAppointmentsPage(request, response);           
	   } else if ("RegisterationAvailabilityPage".equals(action)) {  // Με την επιλογή καταχώρηση διαθεσιμότητας για ραντεβού στο μενού
		   request.getRequestDispatcher("Doctor/RegisterationAvailabilityPage.jsp").forward(request, response);
       } else if ("showAvailabilitySubmit".equals(action)) {  // Με την επιλογή submit στην σελίδα καταχώρησης διαθεσιμότητας ραντεβού
    	   submitAppointment(request, response);
       } else if ("cancelAppointmentPage".equals(action)) {  // Με την επιλογή ακύρωσης ραντεβού στο μενού
		    request.getRequestDispatcher("Doctor/CancelAppointment.jsp").forward(request, response);
       } else if ("cancelAppointmentSubmit".equals(action)) {  // Με την επιλογή submit στην σελίδα ακύρωσης ραντεβού
    	   cancelAppointment(request, response);
       }
	}
	
	// Προσθήκη των στοιχείων του κάθε ραντεβού σε μια λίστα και έπειτα forward στην σελίδα ViewAppointments.jsp για προβολή
	private void loadAppointmentsPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    HttpSession session = request.getSession(false);
	    if (session == null || session.getAttribute("username") == null) {
	        response.sendRedirect("login.jsp");
	        return;
	    }

	    String doctorUsername = (String) session.getAttribute("username");
	    List<Appointment> appointmentsList = new ArrayList<>();
	    Doctor d = new Doctor(doctorUsername);
	    try {
	    	appointmentsList = d.getAppointments();
	    } catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
            request.setAttribute("error", "Σφάλμα: " + e.getMessage());      
        }
	    request.setAttribute("appointmentsList", appointmentsList);
	    request.getRequestDispatcher("Doctor/ViewAppointments.jsp").forward(request, response);
	}

	// Με την επιλογή καταχώρηση διαθεσιμότητας για ραντεβού στο μενού
    private void submitAppointment(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        String username = (String) session.getAttribute("username");
        String appointmentDate = request.getParameter("appointmentDate");   
        try {
            int rowsUpdated = Appointment.registerAvailableAppointment(username, appointmentDate);
            if (rowsUpdated > 0) {
            	request.setAttribute("message", "Η ημερομηνία για ραντεβού δεσμεύτηκε επιτυχώς.");
            } else {
            	request.setAttribute("error", "Η συγκεκριμένη ημερομηνία δεν είναι διαθέσιμη.");
            }
        } catch (IllegalArgumentException e) {
            request.setAttribute("error", "Η ημερομηνία που δώσατε δεν είναι έγκυρη ή είναι πολύ μεγάλη.");
        } catch (SQLIntegrityConstraintViolationException e) {
            request.setAttribute("error", "Έχετε ήδη δηλώσει διαθεσιμότητα για αυτήν την ημερομηνία.");
        } catch (Exception e) {
            request.setAttribute("error", "Σφάλμα: " + e.getMessage());
        }
        request.getRequestDispatcher("Doctor/RegisterationAvailabilityPage.jsp").forward(request, response);
    }
		
    // Με την επιλογή submit στην σελίδα ακύρωσης ραντεβού
    private void cancelAppointment(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String username = (String) session.getAttribute("username");
        String patientUsername = request.getParameter("patientUsername");
        String appointmentDate = request.getParameter("appointmentDate");
        try {
        	int rowsUpdated = Appointment.cancelAppointmentForDoctor(username, patientUsername, appointmentDate);
        	if (rowsUpdated > 0) {
        		request.setAttribute("message", "Το ραντεβού ακυρώθηκε επιτυχώς.");
        	} else {
        		request.setAttribute("error", "Το ραντεβού δεν μπορεί να ακυρωθεί. Πιθανώς είναι πολύ κοντά στην ημερομηνία ή δεν έχει κλειστεί για εσάς.");
        	}
        } catch (IllegalArgumentException e) {
            request.setAttribute("error", "Η ημερομηνία που δώσατε δεν είναι έγκυρη ή είναι πολύ μεγάλη.");
        } catch (Exception e) {
            request.setAttribute("error", "Σφάλμα: " + e.getMessage());
        }
        request.getRequestDispatcher("Doctor/CancelAppointment.jsp").forward(request, response);
    }
}
