package com.prog;

import java.awt.Font;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.json.JSONArray;
import org.json.JSONObject;

public class opgaveListe implements opgaverVeiw {
    private JSONArray opgaver; // Keep the JSON array in memory
    
    
    @Override
    public JPanel getVeiw(OpgaveFrame frame) {
        JPanel panel = new JPanel();
        panel.setLayout(null);

        // Create a table model with column names
        DefaultTableModel tableModel = new DefaultTableModel(new String[] { "Navn", "Dato", "Tid (timer)" }, 0);

        try {
            opgaver = frame.hentOpgaver(); // Load the JSON array once
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
        opgaveTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION); // Allow single row selection

        // Add the table to a scroll pane
        JScrollPane scrollPane = new JScrollPane(opgaveTable);
        scrollPane.setBounds(10, 10, 560, 300); // Set bounds for the scroll pane
        panel.add(scrollPane);

        // Create and add the "Tilføj opgave" button
        JButton tilfoj = new JButton("Tilføj opgave");
        tilfoj.setBounds(10, 320, 150, 30); // Set proper bounds for the button
        panel.add(tilfoj);

        // Add ActionListener to the "Tilføj opgave" button
        tilfoj.addActionListener(e -> {
            frame.setVeiw(new OpgaveManager(null)); // Open the OpgaveManager for adding a new task
        });

        // Create and add the "Slet opgave" button
        JButton slet = new JButton("Slet opgave");
        slet.setBounds(220, 320, 150, 30); // Set proper bounds for the button
        panel.add(slet);

        // Add ActionListener to the "Slet opgave" button
        slet.addActionListener(e -> {
            int selectedRow = opgaveTable.getSelectedRow(); // Get the selected row index
            if (selectedRow != -1) { // Ensure a row is selected
                tableModel.removeRow(selectedRow); // Remove the row from the table
                opgaver.remove(selectedRow); // Remove the corresponding object from the JSON array
                try {
                    frame.gemOpgaver(opgaver); // Save the updated JSON array back to the file
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        JButton rediger = new JButton("Rediger opgave");
        rediger.setBounds(420, 320, 150, 30); // Set proper bounds for the button
        

        rediger.addActionListener(e -> {

            int selectedRow = opgaveTable.getSelectedRow(); // Get the selected row index

            JSONObject selectedOpgave = opgaver.getJSONObject(selectedRow);
            System.out.println(selectedOpgave);
            frame.setVeiw(new OpgaveManager(selectedOpgave));
            
        });
        panel.add(rediger);

        return panel; 
    }


}
