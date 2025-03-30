package com.prog;

import javax.swing.ImageIcon;

public class Main{
public static void main(String[] args) {       
        desktopPet pet = new desktopPet();
        pet.setVisible(true);

        opgaveFrame frame = new opgaveFrame();
        

        reader Reader = new reader();
        
        pet.setIcon(new ImageIcon(Reader.getMood()+"cat.png"));

        
        
    }
        

}