package com.prog;

import javax.swing.ImageIcon;

import org.json.JSONObject;

public class Main{
public static void main(String[] args) {    
      
        JSONObject nyOpgave = new JSONObject();
        desktopPet pet = new desktopPet();
        pet.setVisible(true);
        //AssignmentReminder ar = new AssignmentReminder();

        pet.setIcon(new ImageIcon("angycat.png"));

}}