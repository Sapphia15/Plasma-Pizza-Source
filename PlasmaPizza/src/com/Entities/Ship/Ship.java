package com.Entities.Ship;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.Timer;

import com.Frame;
import com.Panel;
import com.Entities.Projectiles.Missile;

import topDownGameAPI.Sprite;
import topDownGameAPI.util.Direction;
public class Ship extends Sprite{
	protected static int dx;
	protected static int dy;
	protected static int speed;
	protected static int scraps;
	protected static int techPoints;
	protected static int forceFieldLevel;
	protected static ArrayList<Missile> missiles;
	protected static long MissleShotTime;
	protected static long MissleShotTimeDifference;
	protected static boolean canBoost;
	protected static boolean boosted;
	protected static boolean isForceFieldActive;
	protected static techScreen t;
	protected static buildScreen b;
	protected static int HP;
	public static int shots;
	public Ship(int x, int y, int orientation){
		super(x,y,orientation);
		try {
			imgs.put(d.toString(Direction.RIGHT),new ImageIcon(new URL("https://piskel-imgstore-b.appspot.com/img/d2d3fc70-f670-11e7-ace7-153b3595bcca.gif")).getImage());
			imgs.put(d.toString(Direction.UP),new ImageIcon(new URL("https://piskel-imgstore-b.appspot.com/img/f271cb47-f670-11e7-b5c0-153b3595bcca.gif")).getImage());
			imgs.put(d.toString(Direction.LEFT),new ImageIcon(new URL("https://piskel-imgstore-b.appspot.com/img/1b0f038f-f671-11e7-906d-153b3595bcca.gif")).getImage());
			imgs.put(d.toString(Direction.DOWN),new ImageIcon(new URL("https://piskel-imgstore-b.appspot.com/img/31c1f5e6-f671-11e7-a7e3-153b3595bcca.gif")).getImage());
			imgs.put("shieldA", new ImageIcon("src/images/forceFieldA.gif").getImage());
			imgs.put("shieldB", new ImageIcon("src/images/forceFieldB.gif").getImage());
			imgs.put("shieldC", new ImageIcon("src/images/forceFieldC.gif").getImage());
			imgs.put("shieldD", new ImageIcon("src/images/forceFieldD.gif").getImage());
			imgs.put("shieldE", new ImageIcon("src/images/forceFieldE.gif").getImage());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		isForceFieldActive=false;
		missiles = new ArrayList<Missile>();
		shots=40;
		MissleShotTime=System.currentTimeMillis()-250;
		image=imgs.get(d.toString(orientation));
		getImageDimensions();
		t=new techScreen();
		b=new buildScreen();
		speed=1;
		boosted=false;
		forceFieldLevel=0;
		HP=1;
		scraps=0;
	}
	public void move(){
		x+=dx;
		y+=dy;
		if (x<0 || x>897 || y<0 || y>870){
			Frame.board.moveSector(orientation);
		}
	}
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	public int getScrapCount(){
		return scraps;
	}
	public Image getImg(){
		return imgs.get(d.toString(orientation));
	}
	public void addTechPoints(int no){
		techPoints=techPoints+no;
	}
	public void addShots(int no){
		shots=shots+no;
	}
	public void addScrap(){
		scraps++;
	}
	public void addScrap(int no){
		scraps+=no;
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
	
	public void keyPressed(int key) {
		boolean changeSpeed=false;
		if (key == KeyEvent.VK_SPACE) {
				fire();
        }
		if (key == KeyEvent.VK_A) {
			changeSpeed=true;
            orientation=Direction.LEFT;
        }
        if (key == KeyEvent.VK_D) {
        	changeSpeed=true;
            orientation=Direction.RIGHT;
        }

        if (key == KeyEvent.VK_W) {
        	changeSpeed=true;
            orientation=Direction.UP;
        }

        if (key == KeyEvent.VK_S) {
        	changeSpeed=true;
            orientation=Direction.DOWN;
        }
        if (key == KeyEvent.VK_SHIFT && canBoost){
        	if (boosted){
        		speed=1;
        		boosted=false;
        	} else {
        		speed=2;
        		boosted=true;
        	}
        	if (dx!=0 || dy !=0){
        		changeSpeed=true;
        	}
        }
        if (key == KeyEvent.VK_U){
        	if (t.getVisible()){
        		t.setVisible(false);
        	} else {
        		t.setVisible(true);
        	}
        }
        if (key == KeyEvent.VK_B){
        	if (b.isVisible()){
        		b.setVisible(false);
        	} else {
        		b.setVisible(true);
        	}
        }
        if (changeSpeed){
        	dx=d.getXMod(orientation)*speed;
        	dy=d.getYMod(orientation)*speed;
        }
	}
	public Image getForceFieldImage(){
		if (HP==1){
			return null;
		} else if (HP==2){
			return imgs.get("shieldA");
		} else if (HP==3){
			return imgs.get("shieldB");
		} else if (HP==4){
			return imgs.get("shieldC");
		} else if (HP==5){
			return imgs.get("shieldD");
		} else {
			return imgs.get("shieldE");
		}
	}
	
	public int getForceFieldLevel(){
		return forceFieldLevel;
	}
	
	public boolean getForceFieldActive(){
		return isForceFieldActive;
	}
	
	public void activateForceField(){
		HP=forceFieldLevel+1;
		isForceFieldActive=true;
	}
	
	public void damage(int no){
		HP=HP-no;
		if (isForceFieldActive){
			if (HP<2){
				isForceFieldActive=false;
			}
		}
		if (HP<=0){
			setVisible(false);
		}
	}
	public void keyReleased(KeyEvent e) {
        
        int key = e.getKeyCode();
        if (key==KeyEvent.VK_A || key==KeyEvent.VK_D){
        	dx=0;
        } else if (key==KeyEvent.VK_W || key==KeyEvent.VK_S){
        	dy=0;
        }
        
    }
}
