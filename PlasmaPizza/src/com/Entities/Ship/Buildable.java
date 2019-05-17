package com.Entities.Ship;

import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

import topDownGameAPI.Sprite;

public class Buildable {
	private Image image;
	private Rectangle bounds;
	private int x;
	private int y;
	private Sprite building;
	
	public Buildable(String imgPath,Sprite building) {
		image=new ImageIcon(imgPath).getImage();
		this.building=building;
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
}
