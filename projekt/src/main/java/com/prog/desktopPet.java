package com.prog;

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

//import org.w3c.dom.events.MouseEvent;




public class desktopPet extends JFrame{
    private Point initialClick;
   // private JFrame f;
   
    Container c;
    BackgroundPanel back;
    

    public desktopPet() {
        c = getContentPane();
        setUndecorated(true);
	    setBackground(new Color(0,0,0,0));

        back = new BackgroundPanel(new ImageIcon("angycat.png"));
        
        back.setOpaque(false);
        setSize(260, 260);
		setLocation(650, 350);
        
        //mouseListener ml = new mouseListener(this);
        mouseListener(this);
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
    /*
    public static void mouseListener(JFrame frame) {
        frame.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                System.out.println("Mouse clicked");
                frame.setLocation(evt.getXOnScreen(), evt.getYOnScreen());
            }
        });
        
    }
    */

    public static void mouseListener(JFrame frame) {
        frame.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                ((desktopPet) frame).initialClick = e.getPoint();
                frame.getComponentAt(((desktopPet) frame).initialClick);
            }
        });

        frame.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                // get location of Window
                int thisX = frame.getLocation().x;
                int thisY = frame.getLocation().y;

                // Determine how much the mouse moved since the initial click
                int xMoved = e.getX() - ((desktopPet) frame).initialClick.x;
                int yMoved = e.getY() - ((desktopPet) frame).initialClick.y;

                // Move window to this position
                int X = thisX + xMoved;
                int Y = thisY + yMoved;
                frame.setLocation(X, Y);
            }
        });
    }

}