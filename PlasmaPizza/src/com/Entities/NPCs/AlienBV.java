package com.Entities.NPCs;

import java.util.ArrayList;

import com.Entities.Projectiles.*;

import topDownGameAPI.util.Direction;
public class AlienBV extends topDownGameAPI.Sprite {
	public static ArrayList<Laser> lasers = new ArrayList<Laser>();
	private long LaserBurstTime;
	private long LaserShotTime;
	private long LaserBurstTimeDifference;
	private long LaserShotTimeDifference;
	private int shots;
	public AlienBV(int x, int y, int orientation) {
		super(x, y, orientation);
		loadImage("https://piskel-imgstore-b.appspot.com/img/85ed0f28-35c7-11e8-9a46-23d7616e2df6.gif");
		getImageDimensions();
		LaserBurstTime=System.currentTimeMillis()-2500;
		LaserShotTime=System.currentTimeMillis()-250;
	}
	public void fire(int orientation){
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
				lasers.add(new Laser(x , y+    10, orientation));
				LaserShotTime=System.currentTimeMillis();
				
			}
			
		}
	}
	public ArrayList<Laser> getLasers(){
		return lasers;
	}
	public void AddLaser(Laser ls){
		lasers.add(ls);
	}
	public void move(int sX,int sY){
		if (y<sY-10){
			orientation=Direction.DOWN;
			y=y+2;
		}else if (y>sY+10){
			orientation=Direction.UP;
			y=y-2;
		} else if (x>sX){
			this.fire(Direction.LEFT);
		} else{
			this.fire(Direction.RIGHT);
		}
	}

}
