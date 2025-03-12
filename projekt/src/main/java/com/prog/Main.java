package com.prog;

import javax.swing.ImageIcon;

public class Main{
public static void main(String[] args) {    
      
        //JSONObject nyOpgave = new JSONObject();
        OpgaveManager opgaveManager = new OpgaveManager();
        opgaveManager.main(args);

        desktopPet pet = new desktopPet();
        pet.setVisible(true);
        reader Reader = new reader();
        Reader.main(args);


        pet.setIcon(new ImageIcon("tempcat.png"));

}}