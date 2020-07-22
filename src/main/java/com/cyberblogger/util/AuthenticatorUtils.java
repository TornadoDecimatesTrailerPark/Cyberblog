package com.cyberblogger.util;


import com.cyberblogger.model.LocalAuth;

public class AuthenticatorUtils {

    public static LocalAuth createLocalAuthAccount(String username, String password, int userId) {

        // TODO Generate hashed, salted password and return a new User object with the appropriate information
        int saltLength = (int)(Math.random() * 64);
        int iteNum = (int)(Math.random() * 64);
        byte[] salts = Passwords.getNextSalt(saltLength);
        byte[] hashVal = Passwords.hash(password.toCharArray(), salts, iteNum);
      return new LocalAuth(username,
        Passwords.base64Encode(hashVal),
        Passwords.base64Encode(salts),
        saltLength, iteNum, userId);

    }

    public static boolean authenticate(LocalAuth localAuth, String password) {

        // TODO Return a value indicating whether the given plaintext password matches the information containe in the given User object.
        byte[] expectHash = Passwords.base64Decode(localAuth.getPassword());
        byte[] salts = Passwords.base64Decode(localAuth.getSalt());
        if (Passwords.isExpectedPassword(password.toCharArray(), salts, localAuth.getIterationNum(), expectHash)) {
          return true;
        }
        return false;
    }

}
