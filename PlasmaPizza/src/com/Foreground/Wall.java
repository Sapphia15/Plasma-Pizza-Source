package com.Foreground;

public class Wall extends Collidable{

	public Wall(int x, int y) {
		super(x, y);
		loadImage("https://piskel-imgstore-b.appspot.com/img/213573f3-8f9a-11e8-b7b2-d1b5b6af4e40.gif");
		getImageDimensions();
	}
	
}
