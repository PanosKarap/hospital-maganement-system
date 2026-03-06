package karapravdasmatz.simpleClasses;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class Users {

	private String username;
	private String password;
	private String name;
	private String surname;
	private String role;
	private String salt;
	private static int usersCounter;
	
	public Users() {}
	
	public Users(String username) {
		this.username = username;
	}
	
	public Users(String username, String password, String name, String surname, String role, String salt) {
		this.username=username;
		this.password=password;
		this.name= name;
		this.surname=surname;
		this.role=role;
		this.salt=salt;
		usersCounter++;
	}

	//Getters
    public String getUsername() { return username; }
    public String getName() { return name; }
    public String getSurname() { return surname; }
    public String getPassword() {return password;}
    public String getRole() {return role;}
    public String getSalt() { return salt; }
    public static int getUsersCounter() { return usersCounter; }
    
	//Setters
	public void setUsername(String username) {this.username=username;}	
	public void setName(String name) {this.name=name;}
	public void setSurname(String surname) {this.surname=surname;}
	public void setPassword(String password) {this.password=password;}
	public void setRole(String role) {this.role=role;}
	public void setSalt(String salt) {this.salt=salt;}
	
	// Έλεγχος ύπαρξης username στη βάση
    public static boolean usernameExists(String username) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        try(Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital", "dbuser", "password")) {
            String sql = "SELECT username FROM Users WHERE username = ?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, username);
                try (ResultSet rs = ps.executeQuery()) {
                    return rs.next();
                }
            }
        }
    }
    
    // Διαγραφή χρήστη με βάση το username από Users και Admin ή Doctor ή Patient μέσω cascade
    public static boolean deleteUserByUsername(String username) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        try(Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital", "dbuser", "password")) {
            String sqlDelete = "DELETE FROM Users WHERE username = ?";
            try (PreparedStatement ps = conn.prepareStatement(sqlDelete)) {
                ps.setString(1, username);
                int affectedRows = ps.executeUpdate();
                return affectedRows > 0;
            }
        }
    }
}
