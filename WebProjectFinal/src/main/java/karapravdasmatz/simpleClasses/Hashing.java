package karapravdasmatz.simpleClasses;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

public class Hashing {

    public static String generateSalt() {
		SecureRandom random = new SecureRandom();
		byte bytes[]= new byte[20];
		random.nextBytes(bytes);
        return Base64.getEncoder().encodeToString(bytes);
    }

    public static String hashPassword(String password, String salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        final String toHash = salt + password + salt;
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException ex) {
            return "00000000000000000000000000000000";
        }
        messageDigest.update(toHash.getBytes(), 0, toHash.length());
        String hashed = new BigInteger(1, messageDigest.digest()).toString(16);
        if (hashed.length() < 32) {
            hashed = "0" + hashed;
        }
        return hashed.toUpperCase();
    }
}