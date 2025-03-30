
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

public class OpgaveManager implements opgaveState {

    private static final String FILE_PATH = "opgaver.json";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");

    public OpgaveManager(JSONObject opgave) {
        JFrame frame = new JFrame("Opgave Manager");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new GridLayout(4, 2));

        JLabel navnLabel = new JLabel("Opgavens navn:");
        JTextField navnField = new JTextField(opgave != null ? opgave.optString("navn", "") : ""); //hvis der står noget i feltet skriver den det ellers skriver den ingenting ""
        JLabel datoLabel = new JLabel("Afleveringsdato (DD-MM-YYYY):");
        JTextField datoField = new JTextField(opgave != null ? opgave.optString("dato", "") : ""); //hvis der står noget i feltet skriver den det ellers skriver den ingenting ""
        JLabel elevTidLabel = new JLabel("Estimeret elevtid (timer):");
        JTextField elevTidField = new JTextField(opgave != null ? String.valueOf(opgave.optInt("elevTid", 0)) : ""); //hvis der står noget i feltet skriver den det ellers skriver den ingenting ""
        JButton saveButton = new JButton(opgave != null ? "Opdater Opgave" : "Gem Opgave");

        saveButton.addActionListener(e -> {
            String navn = navnField.getText();
            String dato = datoField.getText();
            try {
                int elevTid = Integer.parseInt(elevTidField.getText());
                Date parsedDate = DATE_FORMAT.parse(dato);
                String formattedDate = DATE_FORMAT.format(parsedDate);

                JSONObject nyOpgave = new JSONObject();
                nyOpgave.put("navn", navn);
                nyOpgave.put("dato", formattedDate);
                nyOpgave.put("elevTid", elevTid);

                JSONArray opgaver = hentOpgaver();

                if (opgave != null) {
                    // Update existing opgave
                    for (int i = 0; i < opgaver.length(); i++) {
                        JSONObject existingOpgave = opgaver.getJSONObject(i);
                        if (existingOpgave.optString("navn").equals(opgave.optString("navn"))) {
                            opgaver.put(i, nyOpgave); //Opdatere feltet til det nye info
                            break;
                        }
                    }
                } else {
                    // Add ny opgave
                    opgaver.put(nyOpgave);
                }

                gemOpgaver(opgaver);

                JOptionPane.showMessageDialog(frame, opgave != null ? "Opgaven er opdateret!" : "Opgaven er gemt!");
                frame.dispose();
            } catch (ParseException ex) {
                JOptionPane.showMessageDialog(frame, "Ugyldigt datoformat. Brug venligst DD-MM-YYYY.");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Ugyldigt talformat for elevtid. Indtast et heltal.");
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
            if (!file.exists()) {
                return new JSONArray();
            }
            String content = new String(Files.readAllBytes(Paths.get(FILE_PATH)));
            return new JSONArray(content);
        } catch (IOException e) {
            e.printStackTrace();
            return new JSONArray();
        }
    }

    private static void gemOpgaver(JSONArray opgaver) {
        try (FileWriter file = new FileWriter(FILE_PATH)) {
            file.write(opgaver.toString(4)); // Formatted JSON with indentation
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
