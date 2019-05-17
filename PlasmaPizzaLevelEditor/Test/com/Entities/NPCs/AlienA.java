package com.Entities.NPCs;

import topDownGameAPI.Sprite;
import topDownGameAPI.util.Direction;

public class AlienA extends Sprite{
	private final int SCREEN_WIDTH = 929;
	public AlienA(int x, int y, int orientation){
		super(x,y,orientation);
		loadImage("https://piskel-imgstore-b.appspot.com/img/6ef8d68c-f678-11e7-be47-153b3595bcca.gif");
		getImageDimensions();
		
	}
	public void move() {
		x=x+d.getXMod(orientation)*2;
		y=y+d.getYMod(orientation)*2;
		if (x>SCREEN_WIDTH-32 || x<2){
			if (orientation==Direction.RIGHT || orientation==Direction.LEFT){
				orientation=d.getOpposite(orientation);
			}
		} else if(y<2 || y>870){
			if (orientation==Direction.UP || orientation==Direction.DOWN){
				orientation=d.getOpposite(orientation);
			}
			
		}
    }
}
