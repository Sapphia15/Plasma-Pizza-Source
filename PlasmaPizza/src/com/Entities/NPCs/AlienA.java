package com.Entities.NPCs;

import java.util.ArrayList;
import java.util.Random;

import com.Entities.Scrap;

import topDownGameAPI.Sprite;
import topDownGameAPI.util.Direction;

public class AlienA extends Sprite{
	private final int SCREEN_WIDTH = 929;
	ArrayList<Scrap> scraps;
	Random rand;
	public AlienA(int x, int y, int orientation){
		super(x,y,orientation);
		loadImage("https://piskel-imgstore-b.appspot.com/img/6ef8d68c-f678-11e7-be47-153b3595bcca.gif");
		getImageDimensions();
		rand=new Random();
		scraps=new ArrayList<Scrap>();
	}
	public ArrayList<Scrap> makeAndGetScraps(){
		int i=rand.nextInt(3);
		for (int s=0; s<=i; s++){
			scraps.add(new Scrap(x,y,Direction.UNDEFINED));
		}
		return scraps;
	}
	public void move() {
		x=x+Direction.getXMod(orientation)*2;
		y=y+Direction.getYMod(orientation)*2;
		if (x>SCREEN_WIDTH-32 || x<2){
			if (orientation==Direction.RIGHT || orientation==Direction.LEFT){
				orientation=Direction.getOpposite(orientation);
			}
		} else if(y<2 || y>870){
			if (orientation==Direction.UP || orientation==Direction.DOWN){
				orientation=Direction.getOpposite(orientation);
			}
			
		}
    }
}
