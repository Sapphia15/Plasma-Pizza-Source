package com.Entities.Ship;

import java.awt.Image;
import java.awt.Rectangle;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import topDownGameAPI.Sprite;

public abstract class Buildable <TYPE>{
	private Image image;
	private Rectangle bounds;
	int x;
	int y;
	private int cost;
	private ArrayList<TYPE> buildings;
	
	public Buildable(String imgPath, ArrayList<TYPE> buildings,int cost) {
		image=new ImageIcon(imgPath).getImage();
		this.buildings=buildings;
		x=-1;
		y=-1;
		this.cost=cost;
	}
	
	public Buildable(String imgURL, ArrayList<TYPE> buildings,int cost,boolean URL) {
		if (URL){
			try {
				image=new ImageIcon(new URL(imgURL)).getImage();
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			image=new ImageIcon(imgURL).getImage();
		}
		this.buildings=buildings;
		x=-1;
		y=-1;
		this.cost=cost;
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
	
	public final void addBuilding(){
		if (Ship.scraps>=cost){
			Ship.scraps-=cost;
			buildings.add(building());
		}
	}
	protected abstract TYPE building();
}
