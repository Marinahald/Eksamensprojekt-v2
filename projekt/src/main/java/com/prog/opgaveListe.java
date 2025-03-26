package com.prog;

import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.json.JSONArray;
import org.json.JSONObject;

public class opgaveListe {
    JFrame frame;

    public opgaveListe() {
        frame = new JFrame("Opgave List");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(null);

        // Create a table model with column names
        DefaultTableModel tableModel = new DefaultTableModel(new String[] { "Navn", "Dato", "Tid (timer)" }, 0);

        try {
            JSONArray opgaver = hentOpgaver();
            for (int i = 0; i < opgaver.length(); i++) {
                JSONObject opgave = opgaver.getJSONObject(i);
                String navn = opgave.getString("navn");
                String dato = opgave.getString("dato");
                int elevTid = opgave.getInt("elevTid");
                tableModel.addRow(new Object[] { navn, dato, elevTid });
            }
        } catch (IOException e) {
            e.printStackTrace();
            tableModel.addRow(new Object[] { "Kunne ikke indlæse opgaver.", "", "" });
        }

        // Create a JTable to display the assignments
        JTable opgaveTable = new JTable(tableModel);
        opgaveTable.setFont(new Font("Arial", Font.PLAIN, 16)); // Set font size
        opgaveTable.setRowHeight(25); // Set row height for better readability

// Add the table to a scroll pane
JScrollPane scrollPane = new JScrollPane(opgaveTable);
scrollPane.setBounds(10, 10, 560, 300); // Set bounds for the scroll pane

// Create and add the "Tilføj opgave" button
JButton tilfoj = new JButton("Tilføj opgave");
tilfoj.setBounds(10, 320, 150, 30); // Set proper bounds for the button

        frame.add(scrollPane);
        frame.add(tilfoj);
        frame.setVisible(true);
        tilfoj.addActionListener(e -> {
            new OpgaveManager(); // Create a new instance of OpgaveManager
        });
    }
   

    private JSONArray hentOpgaver() throws IOException {
        File file = new File("opgaver.json");
        if (!file.exists()) return new JSONArray();
        String content = new String(Files.readAllBytes(Paths.get("opgaver.json")));
        return new JSONArray(content);
    }
    
}