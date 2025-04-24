package com.prog;

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;



public class desktopPet extends JFrame{
    private Point initialClick;
   
    Container c;
    BackgroundPanel back;
    

    public desktopPet() {
        c = getContentPane();
        //Fjerner bar i toppen af vinduet
        setUndecorated(true);
        //Laver baggrunden usynlig
	    setBackground(new Color(0,0,0,0));
        //Laver baggrunden uden en kat som så kan få en kat på
        back = new BackgroundPanel(new ImageIcon("tempcat.png"));
        
        back.setOpaque(false);
        setSize(260, 260);
		setLocation(650, 350);
        
        //mouseListener til at trække desktopPet rundt
        mouseListener(this);
        c.add(back);
      
    }
    //Vælger hvilken kat der bliver tegnet
    public void setIcon(ImageIcon imageIcon) {
        System.out.println("setIcon called with: " + imageIcon);
        back.setIcon(imageIcon);
        
        
    }
   
    //Laver baggrunden usynlig og sætter en kat på
    public class BackgroundPanel extends JPanel{
		ImageIcon icon;
		
		public BackgroundPanel(ImageIcon icon) {
			this.icon = icon;
		}

		public void setIcon(ImageIcon icon) {
			this.icon = icon;
            System.out.println("setIcon called with: " + icon + "v2");
            //kalder paintComponent for at tegne katten
            repaint();   
		}
        ///Sørger for at katten bliver tegnet i baggrunden
        @Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			if (this.icon != null) {
				g.drawImage(icon.getImage(),0,0, getWidth(), getHeight(),this);
			}
			
			
		}
		
		
	}
  
    //MouseListener der tjekker hvor man klikker mm
    public static void mouseListener(JFrame frame) {
        frame.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent m) {
                ((desktopPet) frame).initialClick = m.getPoint();
                frame.getComponentAt(((desktopPet) frame).initialClick);
            }
        });

        frame.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent m) {
                if (m.getClickCount() == 2) {   
                    new OpgaveFrame();
                }

            }
        });

        frame.addMouseMotionListener(new MouseMotionAdapter() {
            //@Override
            public void mouseDragged(MouseEvent m) {
                // get location of Window
                int thisX = frame.getLocation().x;
                int thisY = frame.getLocation().y;

                // Determine how much the mouse moved since the initial click
                int xMoved = m.getX() - ((desktopPet) frame).initialClick.x;
                int yMoved = m.getY() - ((desktopPet) frame).initialClick.y;

                // Move window to this position
                int X = thisX + xMoved;
                int Y = thisY + yMoved;
                frame.setLocation(X, Y);
            }
        });
    }

}