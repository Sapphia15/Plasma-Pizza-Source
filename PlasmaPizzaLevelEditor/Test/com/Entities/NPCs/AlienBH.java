package com.Entities.NPCs;

import java.util.ArrayList;

import com.Entities.Projectiles.Laser;

import topDownGameAPI.Sprite;
import topDownGameAPI.util.*;

import com.Entities.NPCs.AlienBV;
public class AlienBH extends Sprite{
	private long LaserBurstTime;
	private long LaserShotTime;
	private long LaserBurstTimeDifference;
	private long LaserShotTimeDifference;
	private int shots;
	private static AlienBV Parent=new AlienBV(0,0,Direction.UNDEFINED);
	public AlienBH(int x, int y, int orientation) {
		super(x, y, orientation);
		loadImage("https://piskel-imgstore-b.appspot.com/img/85ed0f28-35c7-11e8-9a46-23d7616e2df6.gif");
		getImageDimensions();
		LaserBurstTime=System.currentTimeMillis()-2500;
		LaserShotTime=System.currentTimeMillis()-250;
	}
	public void fire(int direction){
		LaserBurstTimeDifference=System.currentTimeMillis()-LaserBurstTime;
		if (LaserBurstTimeDifference>=3000 && shots<1){
			shots=3;
			LaserBurstTime=System.currentTimeMillis();
			sound.playSound("LaserB.wav", 0);
		}
		if (shots>0){
			LaserShotTimeDifference=System.currentTimeMillis()-LaserShotTime;
			if (LaserShotTimeDifference>200){
				shots=shots-1;
				Parent.AddLaser(new Laser(x+10 , y, direction));
				LaserShotTime=System.currentTimeMillis();
			}
			
		}
	}
	public void move(int sX,int sY){
		if (x<sX-10){
			orientation=Direction.RIGHT;
			x=x+2;
		}else if (x>sX+10){
			orientation=Direction.LEFT;
			x=x-2;
			
		} else if (y>sY){
			this.fire(Direction.DOWN);
		} else{
			this.fire(Direction.UP);
		}
	}
}
