package karapravdasmatz.simpleClasses;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class Patient extends Users {
    private String amka;

    public Patient(String username, String password, String name, String surname, String amka, String salt) {
        super(username, password, name, surname, "patient",salt);
        this.amka = amka;
    }
    
    public Patient(String username) {
    	super(username);
    }

    public Patient() {}
    

	public String getAMKA() {
        return amka;
    }

    public void setAmka(String amka) {
        this.amka = amka;
    }

    // Αποθήκευση ασθενή στη βάση
    public void saveToDatabase() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital", "dbuser", "password")) {

            // Εισαγωγή στον πίνακα Users
            String sqlUser = "INSERT INTO Users (username, password, name, surname, role, salt) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement psUser = conn.prepareStatement(sqlUser)) {
                psUser.setString(1, getUsername());
                psUser.setString(2, getPassword());
                psUser.setString(3, getName());
                psUser.setString(4, getSurname());
                psUser.setString(5, "Patient");
                psUser.setString(6, getSalt());
                psUser.executeUpdate();
            }

            // Εισαγωγή στον πίνακα Patient
            String sqlPatient = "INSERT INTO Patient (username, amka) VALUES (?, ?)";
            try (PreparedStatement psPatient = conn.prepareStatement(sqlPatient)) {
                psPatient.setString(1, getUsername());
                psPatient.setString(2, getAMKA());
                psPatient.executeUpdate();
            }
        }
    }
    
    // Πρόσβαση και αποθήκευση των παρελθοντικών ραντεβού σε λίστα για προβολή στο ViewHistory.jsp
    public List<Appointment> getAppointmentHistory() throws SQLException, ClassNotFoundException {
        List<Appointment> historyList = new ArrayList<>();
        Class.forName("com.mysql.cj.jdbc.Driver");

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital", "dbuser", "password");
             PreparedStatement ps = conn.prepareStatement(
                 "SELECT CONCAT(u.name, ' ', u.surname) AS doctor_full_name, a.appointment_date " +
                 "FROM Appointment a " +
                 "JOIN Users u ON a.doctor_username = u.username " +
                 "WHERE a.patient_username = ? AND a.appointment_date < CURDATE() " + // Έξτρα έλεγχος ώστε η ημερομηνία να είναι πιο παλιά για να ανήκει στο ιστορικό
                 "ORDER BY a.appointment_date"
             )) {
            ps.setString(1, getUsername());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String doctorName = rs.getString("doctor_full_name");
                    Date appointmentDate = rs.getDate("appointment_date");

                    Appointment ap = new Appointment(doctorName, appointmentDate);
                    historyList.add(ap);
                }
            }
        }
        return historyList;
    }
    
    @Override
    public String toString() {
        return "Ασθενής: Username='" + getUsername() + '\'' +
               ", Όνομα='" + getName() + '\'' +
               ", Επώνυμο='" + getSurname() + '\'' +
               ", ΑΜΚΑ='" + amka + "'<br/>";
    }
}
