package com.Entities;


import javax.swing.ImageIcon;

import topDownGameAPI.Sprite;

public class Scrap extends Sprite{

	public Scrap(int x, int y, int orientation) {
		super(x, y, orientation);
		image=new ImageIcon("src/images/scrap.gif").getImage();
		getImageDimensions();
	}

}
