package com.Entities.Ship;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Hashtable;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;


class buildPanel extends JPanel implements ActionListener{
	/**
	 * 
	 */
	protected static final long serialVersionUID = -1701373088929340457L;
	Hashtable<Integer,Image> buildingImages;
	Timer timer;
	public buildPanel(){
		setBackground(Color.BLUE);
		setFocusable(true);
		buildingImages=new Hashtable<Integer,Image>();
		buildingImages.put(0, new ImageIcon("src/images/ScrapProcessor.gif").getImage());
		try {
			buildingImages.put(0, new ImageIcon(new URL("https://piskel-imgstore-b.appspot.com/img/213573f3-8f9a-11e8-b7b2-d1b5b6af4e40.gif")).getImage());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		addMouseListener(new MAdaper());
		timer=new Timer(10, this);
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
		g.drawRect(10, 10, 33, 33);
		g.drawImage(buildingImages.get(0),11,11,this);
		g.drawRect(47, 10, 33, 33);
		g.drawImage(buildingImages.get(1),38,11,this);
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
