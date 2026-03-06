package karapravdasmatz.simpleClasses;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class Appointment {
    private String fullName;
    private Date appointmentDate;

    // Constructor
    public Appointment(String fullName, Date appointmentDate) {
    	this.fullName = fullName;
        this.appointmentDate = appointmentDate;
    }    
    
    public Appointment() {}
    
    // Συνάρτηση που επιστρέφει 1 άμα έγινε το κλείσιμο του ραντεβού και 0 αν όχι, προκειμένου να εμφανιστεί το ανάλογο μήνυμα
    public static int bookAppointment(String username, String doctorUsername, String appointmentDate) throws SQLException, ClassNotFoundException {
    	int rowsUpdated = 0;
    	Class.forName("com.mysql.cj.jdbc.Driver");

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital", "dbuser", "password");
             PreparedStatement ps = conn.prepareStatement(
                 "UPDATE Appointment SET patient_username = ? " +
                 "WHERE doctor_username = ? AND appointment_date = ? AND patient_username IS NULL")) {

            ps.setString(1, username);
            ps.setString(2, doctorUsername);
            ps.setDate(3, java.sql.Date.valueOf(appointmentDate));
            rowsUpdated = ps.executeUpdate();
        }
            return rowsUpdated;
    }
    
    // Συνάρτηση που επιστρέφει 1 άμα έγινε η δέσμευση της ημερομηνίας για ραντεβού και 0 αν όχι, προκειμένου να εμφανιστεί το ανάλογο μήνυμα
    public static int registerAvailableAppointment(String username, String appointmentDate) throws SQLException, ClassNotFoundException {
    	int rowsUpdated = 0;
    	Class.forName("com.mysql.cj.jdbc.Driver");

    	// Έλεγχος ότι η ημερομηνία είναι μελλοντική
    	if (LocalDate.parse(appointmentDate).isAfter(LocalDate.now())) {
	        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital", "dbuser", "password");
	             PreparedStatement ps = conn.prepareStatement(
	                 "INSERT INTO Appointment (doctor_username, patient_username, appointment_date) VALUES (?, ?, ?)")) {
	
	            ps.setString(1, username);
	            ps.setNull(2, java.sql.Types.VARCHAR);
	            ps.setDate(3, java.sql.Date.valueOf(appointmentDate));
	            rowsUpdated = ps.executeUpdate();
	        }
    	}
            return rowsUpdated;
    }
    
    // Λειτουργία ασθενή: Συνάρτηση που επιστρέφει 1 άμα έγινε η ακύρωση του ραντεβού και 0 αν όχι, προκειμένου να εμφανιστεί το ανάλογο μήνυμα
    public static int cancelAppointmentForPatient(String username, String doctorUsername, String appointmentDate) throws SQLException, ClassNotFoundException {
    	int rowsUpdated = 0;
    	Class.forName("com.mysql.cj.jdbc.Driver");

    	try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital", "dbuser", "password");
    			PreparedStatement ps = conn.prepareStatement(
    					"UPDATE Appointment SET patient_username = NULL " +
    					"WHERE doctor_username = ? " +
	                    "AND appointment_date = ? " +
	                    "AND patient_username = ? " +
	                    "AND appointment_date >= CURDATE() + INTERVAL 3 DAY")) {
	
	        ps.setString(1, doctorUsername);
	        ps.setDate(2, java.sql.Date.valueOf(appointmentDate));
	        ps.setString(3, username);
	        rowsUpdated = ps.executeUpdate();
    	}
    	return rowsUpdated;
    }   
    
    // Λειτουργία γιατρού: Συνάρτηση που επιστρέφει 1 άμα έγινε η ακύρωση του ραντεβού και 0 αν όχι, προκειμένου να εμφανιστεί το ανάλογο μήνυμα
    public static int cancelAppointmentForDoctor(String username, String patientUsername, String appointmentDate) throws SQLException, ClassNotFoundException {
    	int rowsUpdated = 0;
    	Class.forName("com.mysql.cj.jdbc.Driver");

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital", "dbuser", "password");
             PreparedStatement ps = conn.prepareStatement(
            		 "UPDATE Appointment SET patient_username = NULL " +
                             "WHERE doctor_username = ? " +
                             "AND appointment_date = ? " +
                             "AND patient_username = ? " +
                             "AND appointment_date >= CURDATE() + INTERVAL 3 DAY")) {

            ps.setString(1, username);
            ps.setDate(2, java.sql.Date.valueOf(appointmentDate));
            ps.setString(3, patientUsername);
            rowsUpdated = ps.executeUpdate();
        }
            return rowsUpdated;
    }   

    @Override
    public String toString() {
        return "Ραντεβού με τον/την " + fullName + " στις " + appointmentDate;
    }    
}
