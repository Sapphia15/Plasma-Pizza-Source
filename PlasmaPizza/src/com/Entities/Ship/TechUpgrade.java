package com.Entities.Ship;

import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

abstract class TechUpgrade {
	private Image image;
	private Rectangle bounds;
	private int x;
	private int y;
	
	public TechUpgrade(String imgPath) {
		image=new ImageIcon(imgPath).getImage();
	}
	
	public Image getImage() {
		return image;
	}
	
	public Rectangle getBounds() {
		return new Rectangle(x,y,image.getWidth(null),image.getHeight(null));
	}
	
	public void setLoc(int x,int y) {
		this.x=x;
		this.y=y;
	}
	
	abstract void Upgrade();
}
