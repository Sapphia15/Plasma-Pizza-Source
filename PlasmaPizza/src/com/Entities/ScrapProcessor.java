package com.Entities;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import com.Frame;
import com.Entities.Ship.Ship;

import topDownGameAPI.Sprite;

public class ScrapProcessor extends Sprite{
	public ScrapProcessor(int x, int y, int orientation) {
		super(x, y, orientation);
		image=new ImageIcon("images/ScrapProcessor.gif").getImage();
		getImageDimensions();
	}
	
	public void interact(){
		String[] Options={"Yes","No"};
		int choice=JOptionPane.showOptionDialog(Frame.board, "Would you like to convert scraps to tech points(5:1)?", "Scrap Processor", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, Options, 1);
		if (Frame.board.ship.getScrapCount()>=5 && choice==0){
			Frame.board.ship.addScrap(-5);
			Frame.board.ship.addTechPoints(1);
		}
	}
	
}
