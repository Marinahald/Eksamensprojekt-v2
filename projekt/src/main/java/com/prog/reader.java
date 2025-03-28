package com.prog;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalDateTime;

public class reader {
    private static final String FILE_PATH = "opgaver.json";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");


    public reader() {
        try {
            JSONArray opgaver = hentOpgaver();
            JSONObject closestOpgave = findClosestOpgave(opgaver);
            if (closestOpgave != null) {
                System.out.println("Nærmeste opgave: " + closestOpgave.toString(4));
            } else {
                System.out.println("Ingen opgaver fundet.");
            }
        } catch (IOException | ParseException e) {
        }
    }

    private static JSONArray hentOpgaver() throws IOException {
        File file = new File(FILE_PATH);
        if (!file.exists()) return new JSONArray();
        String content = new String(Files.readAllBytes(Paths.get(FILE_PATH)));
        return new JSONArray(content);
    }

    private static JSONObject findClosestOpgave(JSONArray opgaver) throws ParseException {
        JSONObject closestOpgave = null;
        Date closestDate = null;

        for (int i = 0; i < opgaver.length(); i++) {
            JSONObject opgave = opgaver.getJSONObject(i);
            String datoStr = opgave.getString("dato");
            Date dato = DATE_FORMAT.parse(datoStr);

            if (closestDate == null || dato.before(closestDate)) {
                closestDate = dato;
                closestOpgave = opgave;
            }
        }
        return closestOpgave;
    }

    public void getMood() {
        String cl = null;
        LocalDateTime now = LocalDateTime.now();
        System.err.println(now);
        try {
        JSONArray opgaver = hentOpgaver();
        JSONObject closestOpgave = findClosestOpgave(opgaver);
        
        cl = closestOpgave.toString();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        

        String datoStr = (now.getDayOfMonth() + "-" + now.getMonthValue() + "-" + now.getYear());
        System.err.println(datoStr);
        System.err.println(cl);
        

        // JSONObject closestOpgave = findClosestOpgave(opgaver);
       // LocalDateTime now = LocalDateTime.now();

       

        /*
        int timeTillSend;

        switch (timeTillSend) {
            case value:
                
                break;
        
            default:
                break;
        }
        */
    }
}