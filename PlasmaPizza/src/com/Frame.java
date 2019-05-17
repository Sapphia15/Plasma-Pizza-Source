package com;

import java.awt.Dimension;
import java.net.MalformedURLException;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
public class Frame{
	public static JFrame f;
	public static Panel board;
	
	/**
	 * Initiates a level test of the specified coordinates
	 * @param levelX
	 * @param levelY
	 */
	public Frame(int levelX,int levelY) {
		System.out.println("Test level initializing...");
		String[] args = {String.valueOf(levelX),String.valueOf(levelY)};
		main(args);
	}
	
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
			if (args.length<1) {
				board.load();
			} else {
				board.load(Integer.parseInt(args[0]),Integer.parseInt(args[1]));
			}
		} catch (InterruptedException e) {
			
			e.printStackTrace();
			
		}
		
	}
	
}
