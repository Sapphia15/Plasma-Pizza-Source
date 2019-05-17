package com;

import java.awt.Dimension;
import java.net.MalformedURLException;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
public class Frame{
	public static JFrame f;
	public static Panel board;
	public static void main(String[] args){
		f=new JFrame();
		f.setPreferredSize(new Dimension(929,929));//maximum visible value is 927 minimum visible is 0
		f.setResizable(false);
		f.pack();
		f.setTitle("Plasma Pizza");
		
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setLocationRelativeTo(null);
		try {
			board=new Panel();
			f.add(board);
			f.setVisible(true);
			board.load();
		} catch (InterruptedException e) {
			
			e.printStackTrace();
			
		}
		
	}
	
}
