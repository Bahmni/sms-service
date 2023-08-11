package org.bahmni.sms;

import java.io.*;

public class TokenStorage {
    private static final String TOKEN_FILE_PATH = "/../home/token.txt";

    public static String readToken() {
        try (BufferedReader reader = new BufferedReader(new FileReader(TOKEN_FILE_PATH))) {
            return reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void writeToken(String token) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(TOKEN_FILE_PATH))) {
            writer.write(token);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}