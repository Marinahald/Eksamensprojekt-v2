package com.prog;

import javax.swing.ImageIcon;

public class Main{
public static void main(String[] args) {    
      

        desktopPet pet = new desktopPet();
        pet.setVisible(true);


        reader Reader = new reader();
        
        while (true) {
            //pet.setIcon(new ImageIcon("gladcat.png"));
            pet.setIcon(new ImageIcon(Reader.getMood()+"cat.png"));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                
            }
        }
    
        
    }
        

}