package karapravdasmatz.simpleClasses;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Admin extends Users {

    // Λίστα με όλους τους γιατρούς
    private static ArrayList<Doctor> doctors = new ArrayList<>();

    // Constructor
    public Admin(String username, String password, String name, String surname, String salt) {
        super(username, password, name, surname, "admin", salt);
    }

    // Getter
    public static ArrayList<Doctor> getDoctors() {
        return doctors;
    }

    // Πρόσβαση στα στοιχεία των γιατρών στη βάση
    public static List<Doctor> getDoctorsFromDatabase() throws SQLException {
        List<Doctor> doctors = new ArrayList<>();

        String url = "jdbc:mysql://localhost:3306/hospital";
        String user = "dbuser";
        String pass = "password";

        String sql = "SELECT u.username, u.name, u.surname, d.specialty " +
                     "FROM Users u JOIN Doctor d ON u.username = d.username " +
                     "WHERE u.role = 'Doctor'";

        try (Connection conn = DriverManager.getConnection(url, user, pass);
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String username = rs.getString("username");
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                String specialty = rs.getString("specialty");

                Doctor d = new Doctor(username, null, name, surname, specialty, null);
                doctors.add(d);
            }
        }
        return doctors;
    }
    
    // Πρόσβαση στα στοιχεία των ασθενών στη βάση
    public static List<Patient> getPatientsFromDatabase() throws SQLException {
        List<Patient> patients = new ArrayList<>();

        String url = "jdbc:mysql://localhost:3306/hospital";
        String user = "dbuser";
        String pass = "password";

        String sql = "SELECT u.username, u.name, u.surname, p.amka " +
                     "FROM Users u JOIN Patient p ON u.username = p.username " +
                     "WHERE u.role = 'Patient'";

        try (Connection conn = DriverManager.getConnection(url, user, pass);
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String username = rs.getString("username");
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                String amka = rs.getString("amka");

                Patient p = new Patient(username, null, name, surname, amka, null);
                patients.add(p);
            }
        }
        return patients;
    }

    // Πρόσβαση στα στοιχεία των διαχειριστών στη βάση
    public static List<Admin> getAdminsFromDatabase() throws SQLException {
        List<Admin> admins = new ArrayList<>();

        String url = "jdbc:mysql://localhost:3306/hospital";
        String user = "dbuser";
        String pass = "password";

        String sql = "SELECT username, name, surname " +
                     "FROM Users " +
                     "WHERE role = 'Admin'";

        try (Connection conn = DriverManager.getConnection(url, user, pass);
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String username = rs.getString("username");
                String name = rs.getString("name");
                String surname = rs.getString("surname");

                Admin a = new Admin(username, null, name, surname, null);
                admins.add(a);
            }
        }
        return admins;
    }

    // Αποθήκευση διαχειριστή στη βάση
    public void saveToDatabase() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital", "dbuser", "password")) {

                // Εισαγωγή στον πίνακα Users
                String sqlUser = "INSERT INTO Users (username, password, name, surname, role, salt) VALUES (?, ?, ?, ?, ?, ?)";
                try (PreparedStatement psUser = conn.prepareStatement(sqlUser)) {
                    psUser.setString(1, getUsername());
                    psUser.setString(2, getPassword());
                    psUser.setString(3, getName());
                    psUser.setString(4, getSurname());
                    psUser.setString(5, "Admin");
                    psUser.setString(6, getSalt());
                    psUser.executeUpdate();
                }

                // Εισαγωγή στον πίνακα Admin
                String sqlAdmin = "INSERT INTO Admin (username) VALUES (?)";
                try (PreparedStatement psAdmin = conn.prepareStatement(sqlAdmin)) {
                    psAdmin.setString(1, getUsername());
                    psAdmin.executeUpdate();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public String toString() {
        return 
               "Διαχειριστής: Username='" + getUsername() + '\'' +
               ", Όνομα='" + getName() + '\'' +
               ", Επώνυμο='" + getSurname() + "'<br/>";
    }
}
