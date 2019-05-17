package com.Entities.Projectiles;

import java.util.Hashtable;

import topDownGameAPI.Sprite;
import topDownGameAPI.util.Direction;

public class Missile extends Sprite{

	private final int BOARD_WIDTH = 900;
	private final int BOARD_HEIGHT = 900;
	    static Hashtable<Integer, Integer> msx;
	    static Hashtable<Integer, Integer> msy;
	    public Missile(int x, int y, int orientation) {
	        super(x, y, orientation);
	        msx=new Hashtable<Integer, Integer>();
	        msy=new Hashtable<Integer, Integer>();
	        initMissile();
	    }
	    
	    private void initMissile() {
	        
	        loadImage("https://piskel-imgstore-b.appspot.com/img/93ce5211-f58f-11e7-a208-1fd8fe757045.gif");  
	        getImageDimensions();
	        msx.put(Direction.LEFT, -2);
	        msx.put(Direction.RIGHT, 2);
	        msx.put(Direction.UP,0);
	        msx.put(Direction.DOWN, 0);
	        msy.put(Direction.RIGHT, 0);
	        msy.put(Direction.LEFT,0);
	        msy.put(Direction.UP,-2);
	        msy.put(Direction.DOWN,2);
	    }
	
	
	    public void move() {
	        
	        x += msx.get(orientation);
	        y += msy.get(orientation);
	        if (x > BOARD_WIDTH || x<1-width || y>BOARD_HEIGHT || y<1-height) {
	            vis = false;
	        }
	        
	    }
	
}
