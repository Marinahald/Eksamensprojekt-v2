package com.prog;

import java.awt.GridLayout;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.json.JSONArray;
import org.json.JSONObject;

public class OpgaveManager {
    private static final String FILE_PATH = "opgaver.json";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");

        public static void main(String[] args) {
            JFrame frame = new JFrame("Opgave Manager");
            //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 300);
            frame.setLayout(new GridLayout(4, 2));

            JLabel navnLabel = new JLabel("Opgavens navn:");
            JTextField navnField = new JTextField();
            JLabel datoLabel = new JLabel("Afleveringsdato (DD-MM-YYYY):");
            JTextField datoField = new JTextField();
            JLabel elevTidLabel = new JLabel("Estimeret elevtid (timer):");
            JTextField elevTidField = new JTextField();
            JButton saveButton = new JButton("Gem Opgave");

            saveButton.addActionListener(e -> {
                String navn = navnField.getText();
                String dato = datoField.getText();
                int elevTid = Integer.parseInt(elevTidField.getText());
                try {
                Date parsedDate = DATE_FORMAT.parse(dato);
                String formattedDate = DATE_FORMAT.format(parsedDate);

                JSONObject nyOpgave = new JSONObject();
                nyOpgave.put("navn", navn);
                nyOpgave.put("dato", formattedDate);
                nyOpgave.put("elevTid", elevTid);

                JSONArray opgaver = hentOpgaver();
                opgaver.put(nyOpgave);
                gemOpgaver(opgaver);

                JOptionPane.showMessageDialog(frame, "Opgaven er gemt!");
            } catch (ParseException ex) {
                JOptionPane.showMessageDialog(frame, "Ugyldigt datoformat. Brug venligst DD-MM-YYYY.");
            }
            
            });

            frame.add(navnLabel);
            frame.add(navnField);
            frame.add(datoLabel);
            frame.add(datoField);
            frame.add(elevTidLabel);
            frame.add(elevTidField);
            frame.add(new JLabel()); // Empty cell
            frame.add(saveButton);

            frame.setVisible(true);
        }
        private static JSONArray hentOpgaver() {
            try {
            File file = new File(FILE_PATH);
            if (!file.exists()) return new JSONArray();
            String content = new String(Files.readAllBytes(Paths.get(FILE_PATH)));
            return new JSONArray(content);
            } catch (IOException e) {
            return new JSONArray();
            }
        }
        private static void gemOpgaver(JSONArray opgaver) {
            try (FileWriter file = new FileWriter(FILE_PATH)) {
                file.write(opgaver.toString(4)); // Formateret JSON med indryk
            } catch (IOException e) {
            }
        }
    }

    