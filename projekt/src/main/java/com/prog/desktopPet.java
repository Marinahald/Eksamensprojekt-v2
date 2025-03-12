package com.prog;

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;




public class desktopPet extends JFrame{
   // private JFrame f;
   
    Container c;
    BackgroundPanel back;
    

    public desktopPet() {
        c = getContentPane();
        setUndecorated(true);
	    setBackground(new Color(0,0,0,0));

        back = new BackgroundPanel(new ImageIcon("tempcat.png"));
        //back.setBackground(new Color(0,0,0,0));
        //back.setOpaque(false);

        setSize(260, 260);
		setLocation(650, 350);
       
      c.add(back);
      
    }

    public void setIcon(ImageIcon imageIcon) {
        System.out.println("setIcon called with: " + imageIcon);
        back.setIcon(imageIcon);
        
        
    }

   

    public class BackgroundPanel extends JPanel{
		ImageIcon icon;
		
		public BackgroundPanel(ImageIcon icon) {
			this.icon = icon;
		}

		public void setIcon(ImageIcon icon) {
			this.icon = icon;
            System.out.println("setIcon called with: " + icon + "v2");
            
            repaint();   
		}

        @Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
            //g.setColor(new Color(0, 0, 0, 0));
            //g.fillRect(0, 0, 260, 260);
			if (this.icon != null) {
				g.drawImage(icon.getImage(),0,0, getWidth(), getHeight(),this);
			}
			
			
		}
		
		
	}


}