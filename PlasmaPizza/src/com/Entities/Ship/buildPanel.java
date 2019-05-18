package com.Entities.Ship;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Hashtable;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

import com.Frame;
import com.Panel;
import com.Entities.ScrapProcessor;
import com.Entities.Ship.Buildables.*;


class buildPanel extends JPanel implements ActionListener{
	/**
	 * 
	 */
	protected static final long serialVersionUID = -1701373088929340457L;
	Hashtable<Integer,Buildable> buildings;
	Timer timer;
	
	int selection;
	
	public buildPanel(){
		setBackground(Color.BLUE);
		setFocusable(true);
		buildings=new Hashtable<Integer,Buildable>();
		buildings.put(0, new ScrapProcessorB(12,11));
		buildings.put(1, new WallB(50,11));
		addMouseListener(new MAdaper());
		timer=new Timer(10, this);
		selection=0;
	}
	@Override
	public void paintComponent(Graphics g){
		g.setColor(Color.BLUE);
		g.fillRect(0, 0, 300, 300);
		g.setColor(Color.BLACK);
		g.fillRect(279, 0, 20, 20);
		g.setColor(Color.RED);
		g.drawLine(280, 19, 298, 1);
		g.drawLine(280, 1, 298, 19);
		g.setColor(Color.BLACK);
		g.drawRect(0, 0, 299, 299);
		//g.drawRect(10, 10, 34, 33);
		//g.drawRect(48, 10, 34, 33);
		for (int k:buildings.keySet()){
			g.drawImage(buildings.get(k).getImage(),buildings.get(k).x,buildings.get(k).y,this);
			g.drawRect(buildings.get(k).x-2, buildings.get(k).y-1, buildings.get(k).getBounds().width+2, buildings.get(k).getBounds().height);
		}
		//g.drawImage(buildings.get(1).getImage(),38,11,this);
	}
	
	private class focusListener implements FocusListener{

		@Override
		public void focusGained(FocusEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void focusLost(FocusEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	protected class MAdaper implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent arg0) {
			
			
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
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub
			if (arg0.getButton()==MouseEvent.BUTTON1) {
				if ((arg0.getX()>=279&&arg0.getX()<=300)&&(arg0.getY()>=0&&arg0.getY()<=20)){
					Ship.b.setVisible(false);
				}
				for (int k:buildings.keySet()){
					if (buildings.get(k).getBounds().contains(arg0.getPoint())){
						buildings.get(k).addBuilding();
					}
				}
			}
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		repaint();
		
	}
}
