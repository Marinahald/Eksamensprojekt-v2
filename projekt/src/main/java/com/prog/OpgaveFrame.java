package com.prog;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.json.JSONArray;

public class OpgaveFrame{
    JFrame frame;
    private static final String FILE_PATH = "opgaver.json";
  

    public OpgaveFrame()  {
        // Constructor for OpgaveFrame
        // Initialize the JFrame and set its properties
        frame = new JFrame("Opgave List");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(null);
        setVeiw(new opgaveListe());
        frame.setVisible(true);
    }

    // Method to set the strategy for handling tasks / what class to veiw
    public void setVeiw(opgaverVeiw veiw) {
        JPanel Cveiw = veiw.getVeiw(this);
        frame.setContentPane(Cveiw);
        frame.revalidate(); 
        frame.repaint();
    }

    public JSONArray hentOpgaver() throws IOException {
        File file = new File("opgaver.json");
        if (!file.exists()) return new JSONArray();
        String content = new String(Files.readAllBytes(Paths.get("opgaver.json")));
        return new JSONArray(content);
    }

    public void gemOpgaver(JSONArray opgaver) throws IOException {
        try (FileWriter file = new FileWriter("opgaver.json")) {
            file.write(opgaver.toString(4)); // Save the updated JSON array with indentation
        }
    }
}
