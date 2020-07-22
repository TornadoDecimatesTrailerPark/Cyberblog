package com.cyberblogger.util;

import java.util.Arrays;

/**
 * A utility class which contains methods allowing one to iterate through all possible passwords of a particular maximum
 * length, containing characters in a given character set.
 * <p>
 * To use the methods, first initialize a new int[] array using the {@link #getNewPasswordData(int)} method, supplying the
 * maximum password length to generate. Then, in a loop, repeatedly call the {@link #getNextPassword(String[], int[])}
 * method, supplying the set of possible password characters along with the array you initialized earlier. Each time you
 * call the method, it will return the next password in the sequence. It will return null if there are no more passwords
 * left.
 */
public class PasswordIterator {

    public static int[] getNewPasswordData(int maxPwLen) {
        int[] passwordData = new int[maxPwLen];
        Arrays.fill(passwordData, -1);
        return passwordData;
    }

    public static String getNextPassword(String[] characterSet, int[] charIndex) {

        for (int i = 0; i < charIndex.length; i++) {
            charIndex[i] = charIndex[i] + 1;

            if (charIndex[i] >= characterSet.length) {
                if ((i + 1) == charIndex.length) {
                    return null;
                }

                charIndex[i] = 0;
            } else {
                break;
            }
        }

        StringBuilder passwordBuilder = new StringBuilder();

        for (int aCharIndex : charIndex) {
            if (aCharIndex != -1) {
                passwordBuilder.insert(0, characterSet[aCharIndex]);
            }
        }

        return passwordBuilder.toString();
    }
}
