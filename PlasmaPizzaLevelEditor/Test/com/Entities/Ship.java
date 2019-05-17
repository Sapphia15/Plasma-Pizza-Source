package com.Entities;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;

import com.TestFrame;
import com.Entities.Projectiles.Missile;

import topDownGameAPI.Sprite;
import topDownGameAPI.util.Direction;
public class Ship extends Sprite{
	private int dx;
	private int dy;
	private ArrayList<Missile> missiles;
	private long MissleShotTime;
	private long MissleShotTimeDifference;
	public static int shots;
	public Ship(int x, int y, int orientation){
		super(x,y,orientation);
		try {
			imgs.put(d.toString(Direction.RIGHT),new ImageIcon(new URL("https://piskel-imgstore-b.appspot.com/img/d2d3fc70-f670-11e7-ace7-153b3595bcca.gif")).getImage());
			imgs.put(d.toString(Direction.UP),new ImageIcon(new URL("https://piskel-imgstore-b.appspot.com/img/f271cb47-f670-11e7-b5c0-153b3595bcca.gif")).getImage());
			imgs.put(d.toString(Direction.LEFT),new ImageIcon(new URL("https://piskel-imgstore-b.appspot.com/img/1b0f038f-f671-11e7-906d-153b3595bcca.gif")).getImage());
			imgs.put(d.toString(Direction.DOWN),new ImageIcon(new URL("https://piskel-imgstore-b.appspot.com/img/31c1f5e6-f671-11e7-a7e3-153b3595bcca.gif")).getImage());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		missiles = new ArrayList<Missile>();
		shots=40;
		MissleShotTime=System.currentTimeMillis()-250;
		image=imgs.get(d.toString(orientation));
		getImageDimensions();
	}
	public void move(){
		x+=dx;
		y+=dy;
		if (x<0 || x>897 || y<0 || y>870){
			TestFrame.board.moveSector(orientation);
		}
	}
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	public Image getImg(){
		return imgs.get(d.toString(orientation));
	}
	public void addShots(int no){
		shots=shots+no;
	}
	public void fire() {
		MissleShotTimeDifference=System.currentTimeMillis()-MissleShotTime;
		if (MissleShotTimeDifference>=300 && shots>0){
			shots--;
			MissleShotTime=System.currentTimeMillis();
			missiles.add(new Missile(x , y, orientation));
			sound.playSound("LaserA.wav",0);
		}
    }
	public ArrayList<Missile> getMissiles() {
        return missiles;
    }
	
	public void keyPressed(KeyEvent e) {
		int key=e.getKeyCode();
		
		if (key == KeyEvent.VK_SPACE) {
				fire();
        }
		if (key == KeyEvent.VK_A) {
            orientation=Direction.LEFT;
        }
        if (key == KeyEvent.VK_D) {
            orientation=Direction.RIGHT;
        }

        if (key == KeyEvent.VK_W) {
            orientation=Direction.UP;
        }

        if (key == KeyEvent.VK_S) {
            orientation=Direction.DOWN;
        }
        dx=d.getXMod(orientation);
        dy=d.getYMod(orientation);
	}
public void keyReleased(KeyEvent e) {
        
        int key = e.getKeyCode();
        dx=0;
        dy=0;
    }
}
