package com.Entities.Ship;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

import com.Entities.Ship.techPanel.MListener;

class techPanel extends JPanel implements ActionListener{
	/**
	 * 
	 */
	protected static final long serialVersionUID = 1L;
	Timer timer;
	Hashtable<Integer, Image> techImages;
	ArrayList<Integer> availableTech;
	int techX;
	public techPanel(){
		setBackground(Color.GREEN);
		timer=new Timer(10, this);
		addMouseListener(new MListener());
		techImages=new Hashtable<Integer, Image>();
		availableTech=new ArrayList<Integer>();
		techImages.put(1, new ImageIcon("src/images/speedBoost.gif").getImage());
		techImages.put(2, new ImageIcon("src/images/forceFieldIcon.gif").getImage());
		timer.start();
		availableTech.add(1);
		availableTech.add(2);
		techX=0;
	}
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawRect(0, 0, 299, 299);
		g.fillRect(279, 0, 20, 20);
		g.setColor(Color.RED);
		g.drawLine(280, 19, 298, 1);
		g.drawLine(280, 1, 298, 19);
		g.setColor(Color.BLACK);
		g.drawString("Tech Points:"+Ship.techPoints, 2, 12);
		if (availableTech.contains(1+techX)){
			g.drawRect(9, 133, 33, 33);
			g.drawImage(techImages.get(1), 10, 134, this);
			g.drawString("4 tP", 10, 178);
		} else {
			g.setColor(new Color(0,123,0));
			g.fillRect(9, 133, 33, 33);
		}
		if (availableTech.contains(2+techX)){
			g.setColor(Color.BLACK);
			g.drawRect(44, 133, 33, 33);
			g.drawImage(techImages.get(2), 45, 134, this);
			g.drawString(Integer.toString(5+Ship.forceFieldLevel*2)+" tP", 45, 178);
		} else {
			g.setColor(new Color(0,123,0));
			g.fillRect(45, 133, 33, 33);
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		repaint();
	}
	protected class MListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			int button=e.getButton();
			int x=e.getX();
			int y=e.getY();
			if (button==MouseEvent.BUTTON1){
				if (x>279 && y<20){
					Ship.t.setVisible(false);
				} else if (x>=9 && x<=42 && y>=133 && y<=166 && availableTech.contains(1) && Ship.techPoints>=4){
					Ship.canBoost=true;
					Ship.techPoints=Ship.techPoints-4;
					availableTech.remove(new Integer(1));
				} else if (x>=45 && x<=77 && y>=133 && y<=166 && availableTech.contains(2) && Ship.techPoints>=5+Ship.forceFieldLevel*2){
					Ship.techPoints=Ship.techPoints-(5+Ship.forceFieldLevel*2);
					Ship.forceFieldLevel=Ship.forceFieldLevel+1;
					Ship.isForceFieldActive=true;
					Ship.HP=Ship.forceFieldLevel+1;
					if (Ship.forceFieldLevel==5){
						availableTech.remove(new Integer(2));
					}
				}
			}
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}
}
