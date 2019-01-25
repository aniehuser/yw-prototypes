package org.yesworkflow.save;

import java.io.FileInputStream;
import java.net.URI;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;



public class URIHash {

    private MessageDigest md;

    public URIHash(String hashAlgorithm) throws NoSuchAlgorithmException{

        this.md = MessageDigest.getInstance(hashAlgorithm);

    }

    public String getHash(Path filePath) throws IOException {

        InputStream inputStream = new FileInputStream(filePath.toString());
        byte[] buffer = new byte[1024];
        int inputRead;
        while ((inputRead = inputStream.read(buffer)) != -1) {
            md.update(buffer, 0, inputRead);
        }

        StringBuilder hexString = new StringBuilder();
        for(byte b: md.digest()) {
            hexString.append(String.format("%02x", b));
        }

        return hexString.toString();
    }

    public String getHash(String filePath) throws IOException {

        InputStream inputStream = new FileInputStream(filePath) ;
        byte[] buffer = new byte[1024];
        int inputRead;
        while ((inputRead = inputStream.read(buffer)) != -1) {
            md.update(buffer, 0, inputRead);
        }
        

        StringBuilder hexString = new StringBuilder();
        for(byte b: md.digest()) {
            hexString.append(String.format("%02x", b));
        }

        return hexString.toString();
    }

    public String getHash(URI filePath) throws IOException {
        InputStream inputStream = new FileInputStream(filePath.getPath());
        byte[] buffer = new byte[1024];
        int inputRead;
        while ((inputRead = inputStream.read(buffer)) != -1) {
            md.update(buffer, 0, inputRead);
        }
        

        StringBuilder hexString = new StringBuilder();
        for(byte b: md.digest()) {
            hexString.append(String.format("%02x", b));
        }

        return hexString.toString();
    }


}
