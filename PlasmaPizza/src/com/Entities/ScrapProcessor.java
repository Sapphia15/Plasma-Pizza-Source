package com.Entities;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import com.Entities.Ship.Ship;

import topDownGameAPI.Sprite;

public class ScrapProcessor extends Sprite{
	Ship ship;
	public ScrapProcessor(int x, int y, int orientation) {
		super(x, y, orientation);
		image=new ImageIcon("src/images/ScrapProcessor.gif").getImage();
		ship=new Ship(0,0,-1);
	}
	public void interact(){
		String[] Options={"Yes","No"};
		int choice=JOptionPane.showOptionDialog(null, "Would you like to convert scraps to tech points(5:1)?", "Scrap Processor", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, Options, 1);
		if (ship.getScrapCount()>=5 && choice==1){
			ship.addScrap(-5);
			ship.addTechPoints(1);
		}
	}
	
}
