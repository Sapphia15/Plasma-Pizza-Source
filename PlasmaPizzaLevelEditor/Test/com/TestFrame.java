package com;

import java.awt.Dimension;
import java.net.MalformedURLException;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
public class TestFrame{
	public static JFrame f;
	public static TestPanel board;
	public TestFrame(){
		f=new JFrame();
		f.setPreferredSize(new Dimension(929,929));//maximum visible value is 927 minimum visible is 0
		f.setResizable(false);
		f.pack();
		f.setTitle("Plasma Pizza");
		
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setLocationRelativeTo(null);
	}
	public void setVisible(boolean vis){
		f.setVisible(vis);
		if (vis){
			//reset board if one was previously loaded
			if (board!=null){
				f.remove(board);
			}
			try {
				board=new TestPanel();
				f.add(board);
				board.load();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
