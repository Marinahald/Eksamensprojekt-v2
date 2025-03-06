package com.prog;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

public class OpgaveManager {
    private static final String FILE_PATH = "opgaver.json";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Opgavens navn: ");
        String navn = scanner.nextLine();
        System.out.print("Afleveringsdato (YYYY-MM-DD): ");
        String dato = scanner.nextLine();
        System.out.print("Estimeret elevtid (timer): ");
        int elevTid = scanner.nextInt();
        scanner.nextLine();
        
        JSONObject nyOpgave = new JSONObject();
        nyOpgave.put("navn", navn);
        nyOpgave.put("dato", dato);
        nyOpgave.put("elevTid", elevTid);
        
        JSONArray opgaver = hentOpgaver();
        opgaver.put(nyOpgave);
        gemOpgaver(opgaver);
        
        System.out.println("Opgaven er gemt!");
    }

    private static JSONArray hentOpgaver() {
        try {
            File file = new File(FILE_PATH);
            if (!file.exists()) return new JSONArray();
            String content = new String(Files.readAllBytes(Paths.get(FILE_PATH)));
            return new JSONArray(content);
        } catch (IOException e) {
            e.printStackTrace();
            return new JSONArray();
        }
    }

    private static void gemOpgaver(JSONArray opgaver) {
        try (FileWriter file = new FileWriter(FILE_PATH)) {
            file.write(opgaver.toString(4)); // Formateret JSON med indryk
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
