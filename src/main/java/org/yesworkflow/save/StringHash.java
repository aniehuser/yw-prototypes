package org.yesworkflow.save;

public class StringHash {

    public static String getHash(String hashString) {
        return Integer.toString(hashString.hashCode());
    }
}
