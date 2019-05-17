package com.Entities.Ship;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JRootPane;


class buildScreen{
	JFrame f;
	public buildScreen(){
		f=new JFrame();
		f.setVisible(false);
		f.setTitle("Building");
		f.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		f.setUndecorated(true);
		f.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
		f.setLocationRelativeTo(null);
		f.setLocation(f.getX()+462, f.getY()-150);
		f.setPreferredSize(new Dimension(300,300));
		f.pack();
		f.add(new buildPanel());
	}
	public void setVisible(boolean vis){
		f.setVisible(vis);
	}
	public boolean isVisible(){
		return f.isVisible();
	}
}