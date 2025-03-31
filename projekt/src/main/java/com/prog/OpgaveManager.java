
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
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.json.JSONArray;
import org.json.JSONObject;

public class OpgaveManager implements opgaverVeiw {

    private JSONObject opgave;
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");

    public OpgaveManager(JSONObject opgave) {
        this.opgave = opgave;
    }

    @Override
    public JPanel getVeiw(OpgaveFrame frame) {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setLayout(new GridLayout(4, 2));

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

                JSONArray opgaver;
                try {
                    opgaver = frame.hentOpgaver();
                } catch (IOException ioException) {
                    JOptionPane.showMessageDialog(panel, "Der opstod en fejl under hentning af opgaver: " + ioException.getMessage());
                    return;
                }

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

                try {
                    frame.gemOpgaver(opgaver);
                    frame.setVeiw(new opgaveListe());
                    JOptionPane.showMessageDialog(panel, opgave != null ? "Opgaven er opdateret!" : "Opgaven er gemt!");
                } catch (IOException ioException) {
                    JOptionPane.showMessageDialog(panel, "Der opstod en fejl under gemning af opgaven: " + ioException.getMessage());
                }

            } catch (ParseException ex) {
                JOptionPane.showMessageDialog(panel, "Ugyldigt datoformat. Brug venligst DD-MM-YYYY.");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(panel, "Ugyldigt talformat for elevtid. Indtast et heltal.");
            }
        });

        panel.add(navnLabel);
        panel.add(navnField);
        panel.add(datoLabel);
        panel.add(datoField);
        panel.add(elevTidLabel);
        panel.add(elevTidField);
        panel.add(new JLabel()); // Empty cell
        panel.add(saveButton);

        return panel;
    }
}
