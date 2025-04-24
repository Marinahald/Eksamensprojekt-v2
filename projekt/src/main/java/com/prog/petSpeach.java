package com.prog;

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class petSpeach extends JFrame{
   
    Container c;
    BackgroundPanel back;
    

    public petSpeach() {
        c = getContentPane();
        setUndecorated(true);
	    setBackground(new Color(0,0,0,0));

        back = new BackgroundPanel(new ImageIcon("Speech.png"));
        
        back.setOpaque(false);
        setSize(260, 260);
		setLocation(650, 350);
        
        c.add(back);

      
    }

    public class BackgroundPanel extends JPanel{
		ImageIcon icon;
		
		public BackgroundPanel(ImageIcon icon) {
			this.icon = icon;
		}

		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			if (this.icon != null) {
				g.drawImage(icon.getImage(),0,0, getWidth(), getHeight(),this);
			}
			
			
		}
} 
}

