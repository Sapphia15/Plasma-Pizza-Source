package com.Entities;
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
	private int dx;
	private int dy;
	private int speed;
	static private int scraps;
	static private int techPoints;
	private int forceFieldLevel;
	private ArrayList<Missile> missiles;
	private long MissleShotTime;
	private long MissleShotTimeDifference;
	private boolean canBoost;
	private boolean boosted;
	private boolean isForceFieldActive;
	private techScreen t;
	private buildScreen b;
	private int HP;
	public static int shots;
	public Ship(int x, int y, int orientation){
		super(x,y,orientation);
		try {
			imgs.put(d.toString(Direction.RIGHT),new ImageIcon(new URL("https://piskel-imgstore-b.appspot.com/img/d2d3fc70-f670-11e7-ace7-153b3595bcca.gif")).getImage());
			imgs.put(d.toString(Direction.UP),new ImageIcon(new URL("https://piskel-imgstore-b.appspot.com/img/f271cb47-f670-11e7-b5c0-153b3595bcca.gif")).getImage());
			imgs.put(d.toString(Direction.LEFT),new ImageIcon(new URL("https://piskel-imgstore-b.appspot.com/img/1b0f038f-f671-11e7-906d-153b3595bcca.gif")).getImage());
			imgs.put(d.toString(Direction.DOWN),new ImageIcon(new URL("https://piskel-imgstore-b.appspot.com/img/31c1f5e6-f671-11e7-a7e3-153b3595bcca.gif")).getImage());
			imgs.put("shieldA", new ImageIcon("images/forceFieldA.gif").getImage());
			imgs.put("shieldB", new ImageIcon("images/forceFieldB.gif").getImage());
			imgs.put("shieldC", new ImageIcon("images/forceFieldC.gif").getImage());
			imgs.put("shieldD", new ImageIcon("images/forceFieldD.gif").getImage());
			imgs.put("shieldE", new ImageIcon("images/forceFieldE.gif").getImage());
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
	private class techScreen{
		JFrame f;
		public techScreen(){
			f=new JFrame();
			f.setVisible(false);
			f.setTitle("Tech");
			f.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			f.setUndecorated(true);
			f.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
			f.setLocationRelativeTo(null);
			f.setLocation(f.getX()-762, f.getY()-150);
			f.setPreferredSize(new Dimension(300,300));
			f.pack();
			f.add(new techPanel());
			
		}
		public void setVisible(boolean vis){
			f.setVisible(vis);
		}
		public boolean getVisible(){
			return f.isVisible();
		}
	}
	private class techPanel extends JPanel implements ActionListener{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		Timer timer;
		Hashtable<Integer, Image> techImages;
		ArrayList<Integer> availableTech;
		int techX;
		public techPanel(){
			setBackground(Color.GREEN);
			timer=new Timer(10, this);
			addMouseListener(new MListener());
			techImages=new Hashtable<Integer, Image>();
			availableTech=new ArrayList<Integer>();
			techImages.put(1, new ImageIcon("images/speedBoost.gif").getImage());
			techImages.put(2, new ImageIcon("images/forceFieldIcon.gif").getImage());
			timer.start();
			availableTech.add(1);
			availableTech.add(2);
			techX=0;
		}
		@Override
		public void paintComponent(Graphics g){
			super.paintComponent(g);
			g.drawRect(0, 0, 299, 299);
			g.drawRect(279, 0, 20, 20);
			g.setColor(Color.RED);
			g.drawLine(280, 19, 298, 1);
			g.drawLine(280, 1, 298, 19);
			g.setColor(Color.BLACK);
			g.drawString("Tech Points:"+techPoints, 2, 12);
			if (availableTech.contains(1+techX)){
				g.drawRect(9, 133, 33, 33);
				g.drawImage(techImages.get(1), 10, 134, this);
				g.drawString("4 tP", 10, 178);
			} else {
				g.setColor(new Color(0,123,0));
				g.fillRect(9, 133, 33, 33);
			}
			if (availableTech.contains(2+techX)){
				g.setColor(Color.BLACK);
				g.drawRect(44, 133, 33, 33);
				g.drawImage(techImages.get(2), 45, 134, this);
				g.drawString(Integer.toString(5+forceFieldLevel*2)+" tP", 45, 178);
			} else {
				g.setColor(new Color(0,123,0));
				g.fillRect(45, 133, 33, 33);
			}
		}
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			repaint();
		}
		private class MListener implements MouseListener {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				int button=e.getButton();
				int x=e.getX();
				int y=e.getY();
				if (button==MouseEvent.BUTTON1){
					if (x>279 && y<20){
						t.setVisible(false);
					} else if (x>=9 && x<=42 && y>=133 && y<=166 && availableTech.contains(1) && techPoints>=4){
						canBoost=true;
						techPoints=techPoints-4;
						availableTech.remove(new Integer(1));
					} else if (x>=45 && x<=77 && y>=133 && y<=166 && availableTech.contains(2) && techPoints>=5+forceFieldLevel*2){
						techPoints=techPoints-(5+forceFieldLevel*2);
						forceFieldLevel=forceFieldLevel+1;
						isForceFieldActive=true;
						HP=forceFieldLevel+1;
						if (forceFieldLevel==5){
							availableTech.remove(new Integer(2));
						}
					}
				}
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		}
	}
	private class buildScreen{
		JFrame f;
		public buildScreen(){
			f=new JFrame();
			f.setVisible(false);
			f.setTitle("Building");
			f.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			f.setUndecorated(true);
			f.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
			f.setLocationRelativeTo(null);
			f.setLocation(f.getX()+462, f.getY()-150);
			f.setPreferredSize(new Dimension(300,300));
			f.pack();
			f.add(new buildPanel());
		}
		public void setVisible(boolean vis){
			f.setVisible(vis);
		}
		public boolean isVisible(){
			return f.isVisible();
		}
	}
	private class buildPanel extends JPanel implements ActionListener{
		/**
		 * 
		 */
		private static final long serialVersionUID = -1701373088929340457L;
		Hashtable<Integer,Image> buildingImages;
		Timer timer;
		public buildPanel(){
			setBackground(Color.BLUE);
			setFocusable(true);
			buildingImages=new Hashtable<Integer,Image>();
			buildingImages.put(0, new ImageIcon("images/ScrapProcessor.gif").getImage());
			try {
				buildingImages.put(0, new ImageIcon(new URL("https://piskel-imgstore-b.appspot.com/img/213573f3-8f9a-11e8-b7b2-d1b5b6af4e40.gif")).getImage());
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			timer=new Timer(10, this);
		}
		@Override
		public void paintComponent(Graphics g){
			g.setColor(Color.BLUE);
			g.fillRect(0, 0, 300, 300);
			g.setColor(Color.BLACK);
			g.drawRect(279, 0, 20, 20);
			g.setColor(Color.RED);
			g.drawLine(280, 19, 298, 1);
			g.drawLine(280, 1, 298, 19);
			g.setColor(Color.BLACK);
			g.drawRect(0, 0, 299, 299);
			g.drawRect(10, 10, 33, 33);
			g.drawImage(buildingImages.get(0),11,11,this);
			g.drawRect(47, 10, 33, 33);
			g.drawImage(buildingImages.get(1),38,11,this);
		}
		
		private class MAdaper implements MouseListener{

			@Override
			public void mouseClicked(MouseEvent arg0) {
				if ((arg0.getX()>=279||arg0.getX()<=300)&&(arg0.getY()>=0&&arg0.getY()<=20)){
					setVisible(false);
				}
				
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		}
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			repaint();
			
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
