package com.prog;

import javax.swing.JFrame;

public class opgaveFrame {
    opgaveState State;
    JFrame frame;
    opgaveListe oLState;
    OpgaveManager oMState;

    public opgaveFrame() {
        frame = new JFrame("Opgave Frame");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(null);
        
        oMState = new OpgaveManager(null); // Initialize with null to create a new opgave
        oLState = new opgaveListe(); // Initialize with the default state
        State = oLState; // Set the initial state to opgaveListe
        
    }

    public void setOpgaveState(opgaveState State) {
        this.State = State;
    }

    public void update() {
       State.updateState();
    }
}
