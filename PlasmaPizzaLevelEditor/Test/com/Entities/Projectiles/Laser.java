package com.Entities.Projectiles;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Hashtable;

import javax.swing.ImageIcon;

import topDownGameAPI.Sprite;
import topDownGameAPI.util.Direction;
public class Laser extends Sprite{
	private final int BOARD_WIDTH = 900;
	private final int BOARD_HEIGHT = 900;
	static Hashtable<Integer, Integer> msx;
    static Hashtable<Integer, Integer> msy;
	public Laser(int x, int y,int orientation){
		super(x,y,orientation);
        msx=new Hashtable<Integer, Integer>();
        msy=new Hashtable<Integer, Integer>();
		try {
			imgs.put("Horizontal",new ImageIcon(new URL("https://piskel-imgstore-b.appspot.com/img/f0776d17-354d-11e8-a52a-394883ebe37a.gif")).getImage());
			imgs.put("Vertical",new ImageIcon(new URL("https://piskel-imgstore-b.appspot.com/img/7580ba73-360b-11e8-be5d-23d7616e2df6.gif")).getImage());
		} catch (MalformedURLException e) {
			
			e.printStackTrace();
		}
		if (orientation==Direction.LEFT || orientation==Direction.RIGHT){
			image=imgs.get("Horizontal");
		} else {
			image=imgs.get("Vertical");
		}
		msx.put(Direction.LEFT, -3);
        msx.put(Direction.RIGHT, 3);
        msx.put(Direction.UP,0);
        msx.put(Direction.DOWN, 0);
        msy.put(Direction.RIGHT, 0);
        msy.put(Direction.LEFT,0);
        msy.put(Direction.UP,3);
        msy.put(Direction.DOWN,-3);
        getImageDimensions();
	}
	public void move(){
		x += msx.get(orientation);
        y += msy.get(orientation);
        if (x > BOARD_WIDTH || x<1-width || y>BOARD_HEIGHT || y<1-height) {
            vis = false;
        }
	}
}
