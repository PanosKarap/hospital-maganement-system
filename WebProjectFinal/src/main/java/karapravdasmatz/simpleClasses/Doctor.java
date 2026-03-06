package karapravdasmatz.simpleClasses;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class Doctor extends Users {
	private String specialty;
	private Date availabilityDate;
	
	//Constructor
	public Doctor(String username, String password, String name, String surname,String specialty, String salt) {
		super(username,password,name,surname,"doctor", salt);
		this.specialty=specialty;
	}
	
	public Doctor(String username, String password, String name, String surname,String specialty, String salt,Date availabilityDate) {
		super(username,password,name,surname,"doctor", salt);
		this.specialty=specialty;
		this.availabilityDate = availabilityDate;
	}
	
	public Doctor(String username) {
		super(username);
	}

    public Doctor() {}

	//Getters - Setters
	public String getSpecialty() { return specialty; }
    public void setSpecialty(String specialty) { this.specialty = specialty; }
    public Date getAvailabilityDate() { return availabilityDate; }
    public void setAvailabilityDate(Date availabilityDate) { this.availabilityDate = availabilityDate; }

    // Αποθήκευση γιατρού στη βάση
    public void saveToDatabase() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        try(Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital", "dbuser", "password")) {

            // Εισαγωγή στον πίνακα Users
            String sqlUser = "INSERT INTO Users (username, password, name, surname, role, salt) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement psUser = conn.prepareStatement(sqlUser)) {
                psUser.setString(1, getUsername());
                psUser.setString(2, getPassword());
                psUser.setString(3, getName());
                psUser.setString(4, getSurname());
                psUser.setString(5, "Doctor");
                psUser.setString(6, getSalt());
                psUser.executeUpdate();
            }
            
            // Εισαγωγή στον πίνακα Doctor
            String sqlDoctor = "INSERT INTO Doctor (username, specialty) VALUES (?, ?)";
            try (PreparedStatement psDoctor = conn.prepareStatement(sqlDoctor)) {
                psDoctor.setString(1, getUsername());
                psDoctor.setString(2, getSpecialty());
                psDoctor.executeUpdate();
            }
        }
    }    
    
    // Πρόσβαση στη βάση και επιστροφή λίστας με γιατρούς και ημερομηνίες των διαθέσιμων ραντεβού τους
    public static List<Doctor> getAvailableDoctors() throws SQLException, ClassNotFoundException {
        List<Doctor> doctorsList = new ArrayList<>();
        Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital", "dbuser", "password");
                 PreparedStatement ps = conn.prepareStatement(
                     "SELECT u.username, u.name, u.surname, d.specialty, a.appointment_date " +
                     "FROM Appointment a " +
                     "JOIN Doctor d ON a.doctor_username = d.username " +
                     "JOIN Users u ON d.username = u.username " +
                     "WHERE a.patient_username IS NULL AND a.appointment_date > CURDATE()")) {

                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        String doctorUsername = rs.getString("username");
                        String name = rs.getString("name");
                        String surname = rs.getString("surname");
                        String specialty = rs.getString("specialty");
                        Date availabilityDate = rs.getDate("appointment_date");

                        Doctor doctor = new Doctor(doctorUsername, null, name, surname, specialty, null, availabilityDate);
                        doctorsList.add(doctor);
                    }
                }
        }
        return doctorsList;
    }
    
    // Πρόσβαση στη βάση και επιστροφή λίστας με όλους τους γιατρούς και τα στοιχεία τους
    public static List<Doctor> getAllDoctors() throws SQLException, ClassNotFoundException {
        List<Doctor> doctorsList = new ArrayList<>();
        Class.forName("com.mysql.cj.jdbc.Driver");
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital", "dbuser", "password");
                PreparedStatement ps = conn.prepareStatement(
                    "SELECT u.username, u.name, u.surname, d.specialty " +
                    "FROM Users u JOIN Doctor d ON u.username = d.username")) {

               try (ResultSet rs = ps.executeQuery()) {
                   while (rs.next()) {
                       String doctorUsername = rs.getString("username");
                       String name = rs.getString("name");
                       String surname = rs.getString("surname");
                       String specialty = rs.getString("specialty");
                       Doctor doctor = new Doctor(doctorUsername, null, name, surname, specialty, null);
                       doctorsList.add(doctor);
                   }
               }
        }
        return doctorsList;
    }    
    
    public List<Appointment> getAppointments() throws SQLException, ClassNotFoundException {
    	List<Appointment> patientList = new ArrayList<>();
        Class.forName("com.mysql.cj.jdbc.Driver");
        
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital", "dbuser", "password");
                PreparedStatement ps = conn.prepareStatement(
                		"SELECT a.patient_username, CONCAT(u.name, ' ', u.surname) AS patient_full_name, a.appointment_date " +
   	                         "FROM Appointment a JOIN Users u ON a.patient_username = u.username " +
   	                         "WHERE a.doctor_username = ? AND a.appointment_date >= CURDATE() " + // Έξτρα έλεγχος ώστε η ημερομηνία να μην είναι παλιότερη
   	                         "ORDER BY a.appointment_date"
                )) {
               ps.setString(1, getUsername());
               try (ResultSet rs = ps.executeQuery()) {
                   while (rs.next()) {
                   	String patientName = rs.getString("patient_full_name");
                    Date appointmentDate = rs.getDate("appointment_date");
                    Appointment ap = new Appointment(patientName, appointmentDate);
                    patientList.add(ap);
                   }
               }
           }
           return patientList;
    }
    
    // toString για προβολή στοιχείων γιατρών στο ViewDoctors.jsp
    @Override
    public String toString() {
        return 
               "Γιατρός: Username='" + getUsername() + '\'' +
               ", Όνομα='" + getName() + '\'' +
               ", Επώνυμο='" + getSurname() + '\'' +
               ", Ειδικότητα='" + specialty + "'<br/>";
    }

    // toString για προβολή στοιχείων γιατρών και διαθέσιμων ραντεβού στο AvailableDoctors.jsp
    public String toStringAvailability() {
        return 
            "Γιατρός: Username='" + getUsername() + '\'' +
            ", Όνομα='" + getName() + '\'' +
            ", Επώνυμο='" + getSurname() + '\'' +
            ", Ειδικότητα='" + specialty +
            ", Ημερομηνία='" + getAvailabilityDate() + "'<br/>";
    }    
}
