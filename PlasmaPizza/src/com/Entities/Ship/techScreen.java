package com.Entities.Ship;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JRootPane;

class techScreen{
	JFrame f;
	techPanel t;
	public techScreen(){
		f=new JFrame();
		f.setVisible(false);
		f.setTitle("Tech");
		f.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		f.setUndecorated(true);
		f.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
		f.setLocationRelativeTo(null);
		f.setLocation(f.getX()-762, f.getY()-150);
		f.setPreferredSize(new Dimension(300,300));
		f.pack();
		t=new techPanel();
		f.add(t);
		
	}
	public void setVisible(boolean vis){
		f.setVisible(vis);
	}
	public boolean getVisible(){
		return f.isVisible();
	}
	
}
