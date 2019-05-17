package com;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Random;

import javax.swing.Timer;

import com.Entities.Ship;
import com.Entities.NPCs.*;
import com.Entities.Projectiles.*;
import com.Foreground.Wall;
import com.Effects.*;
import  sun.audio.*;
import topDownGameAPI.util.Direction;
import topDownGameAPI.util.Sounds;

import  java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
@SuppressWarnings("unused")
public class TestPanel extends JPanel implements /*Runnable,*/ ActionListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Ship ship;
	private Timer timer;
	private ArrayList<AlienA> alienAs;
	private ArrayList<AlienBV> alienBVs;
	private ArrayList<AlienBH> alienBHs;
	private ArrayList<Explosion> explosions;
	private ArrayList<Wall> walls;
	private ArrayList<Image>loadedImages;
    private boolean ingame;
    private final int ICRAFT_X = 40;
    private final int ICRAFT_Y = 60;
    public final int B_WIDTH = 929;
    public final int B_HEIGHT = 902;
    private final int DELAY = 10;
    private boolean init;
    private boolean win;
    private boolean loading;
    private boolean timerWasRunning;
    private Sectors sectors;
    private Direction d;
    Graphics g;
    private Random Rand=new Random();
    public int sectorsCleared;
    final long DURATION_EXPLOSION=(long)1714;
    Sounds sound;
    //private Thread animator;
    
    
    
    
    
    //~~initialization~~
    
    public TestPanel() throws InterruptedException{
    	setBackground(Color.BLACK);
    	loading=true;
        loadedImages=new ArrayList<>();
    	setFocusable(true);
		setDoubleBuffered(true);
	}
	public void load() throws InterruptedException{
		init=true;
		repaint();
		win=false;
		sectorsCleared=0;
		System.out.println("Loading Arrays...");
		alienAs = new ArrayList<>();
        alienBVs = new ArrayList<>();
        alienBHs = new ArrayList<>();
        explosions=new ArrayList<>();
        walls=new ArrayList<>();
        ArrayList<String> soundPaths=new ArrayList<>();
        soundPaths.add("ExplosionA.wav");
        soundPaths.add("LaserA.wav");
        soundPaths.add("LaserB.wav");
        soundPaths.add("The-Happy-New.wav");
		//load sounds
		System.out.println("Loading Sounds...");
		sound=new Sounds();
		sound.setSourcePath("sounds/");
		sound.initialize(soundPaths);
		//Sounds.PlayRepeatingSound("The-Happy-New.wav", 100); TODO uncomment this when you export it
		

        System.out.println("Arrays Loaded");
		System.out.println("Loading Images...");
		loadedImages.add(new Wall(0,0).getImage());
		loadedImages.add(new Explosion(0,0,1740).getImage());
		loadedImages.add(new AlienBH(0,0,Direction.UNDEFINED).getImage());
		loadedImages.add(new AlienBV(0,0,Direction.UNDEFINED).getImage());
		loadedImages.add(new AlienA(0,0,Direction.UNDEFINED).getImage());
		loadedImages.add(new Ship(0,0,Direction.RIGHT).getImage());
		loadedImages.add(new Ship(0,0,Direction.LEFT).getImage());
		loadedImages.add(new Ship(0,0,Direction.DOWN).getImg());
		loadedImages.add(new Ship(0,0,Direction.UP).getImg());
		repaint();
        System.out.println("Images Loaded");
        d=new Direction();
        System.out.println("Launching Game");
		ship=new Ship(423, 423, Direction.RIGHT);
		addKeyListener(new TAdapter());
		
		sectors=new Sectors();
		lvInitAliens();
		timer = new Timer(DELAY, this);
		loading=false;
		ingame=false;
		repaint();
		ingame=true;
		MessageBox("Galactic Coordinates: X-"+sectors.x+" Y-"+sectors.y,"SHIP NAVIGATION SYSTEM");
        timer.start();
	}
	
	
	
	
	
	//~~painting methods
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		if (ingame){
			doDrawing(g);
		
		}
		else if (loading){
			Graphics2D g2d=(Graphics2D) g;
			this.g=g;
			try {
				g2d.drawImage(new ImageIcon(new URL("https://piskel-imgstore-b.appspot.com/img/1a796c7d-8fa0-11e8-9153-e3ceed00db74.gif")).getImage(),420,410,this);
			} catch (MalformedURLException e) {
				System.out.println("Failed to fetch loading symbol from URL.");
				e.printStackTrace();
			}
			/*for (Image i:loadedImages){
				//g2d.drawImage(i, 929, 0, this);
			}*/
		}
		else if (win){
			timer.stop();
			drawText(g, "~YOU WIN~");
			
			
		} else{
			timer.stop();
			drawText(g, "--GAME OVER--");
		}
		Toolkit.getDefaultToolkit().sync();
	}

	public void doDrawing(Graphics g){
		Graphics2D g2d=(Graphics2D) g;
		
		ArrayList<Missile> ms = ship.getMissiles();
		ArrayList<Laser> lasers=AlienBV.lasers;
        for (Missile m : ms) {
            g2d.drawImage(m.getImage(), m.getX(),m.getY(), this);
        }
        for (Laser ls : lasers){
        	g2d.drawImage(ls.getImage(),ls.getX(),ls.getY(), this);
        }
        for (AlienA a1 : alienAs) {
            if (a1.isVisible()) {
                g.drawImage(a1.getImage(), a1.getX(), a1.getY(), this);
                
            }
        }
        for (AlienBV alB : alienBVs) {
        	if (alB.isVisible()){
        		g.drawImage(alB.getImage(), alB.getX(), alB.getY(), this);
        	}
        }
        for (AlienBH alB : alienBHs) {
        	if (alB.isVisible()){
        		g.drawImage(alB.getImage(), alB.getX(), alB.getY(), this);
        	}
        }
        for (Explosion ex: explosions){
        	if (ex.isVisible()){
        		g.drawImage(ex.getImage(), ex.getX(), ex.getY(), this);
        	}
        }
        for (Wall w:walls){
        	g.drawImage(w.getImage(),w.getX(),w.getY(), this);
        }
        g.setColor(Color.WHITE);
        int Enemies=alienAs.size()+alienBVs.size()+alienBHs.size();
        g.drawString("Aliens left: " + Enemies, 5, 15);
        g.drawString("Level: "+sectorsCleared, 5,35);
        g.drawString("Plasma Pizza Projectiles:"+Ship.shots, 5, 865);
        g2d.drawImage(ship.getImg(), ship.getX(), ship.getY(), this);
	}
	 /*@Override
	    public void addNotify() {
	        super.addNotify();

	        animator = new Thread(this);
	        animator.start();
	    }
	public void run(){
		long beforeTime, timeDiff, sleep;

        beforeTime = System.currentTimeMillis();

        while (true) {
            repaint();

            timeDiff = System.currentTimeMillis() - beforeTime;
            sleep = DELAY - timeDiff;

            if (sleep < 0) {
                sleep = 2;
            }

            try {
                Thread.sleep(sleep);
            } catch (InterruptedException e) {
                System.out.println("Interrupted: " + e.getMessage());
            }

            beforeTime = System.currentTimeMillis();
        }
		
	}
	*/
	
	private Image loadImageIcon(String img){
		ImageIcon ii=new ImageIcon(img);
		Image image=ii.getImage();
		return image;
	}

	private void drawText(Graphics g, String msg) {
	        Font small = new Font("Helvetica", Font.BOLD, 30);
	        FontMetrics fm = getFontMetrics(small);

	        g.setColor(Color.white);
	        g.setFont(small);
	        g.drawString(msg, (B_WIDTH - fm.stringWidth(msg)) / 2,
	                B_HEIGHT / 2);
	    }
	
	
	
	
	
	//~~level initialization~~
	
	public void lvInitAliens(){
		sectors.loadSector(sectors.x, sectors.y);
		for(AlienA aA:sectors.getAlienAs()){
			alienAs.add(aA);
		}
	}
	
	
	
	
	
	//~~update entities~~
	
	private void updateProjectiles() {

        ArrayList<Missile> ms = ship.getMissiles();
        ArrayList<Laser> lasers=AlienBV.lasers;
        for (int i = 0; i < ms.size(); i++) {
        	Missile m=(Missile) ms.get(i);
            if (m.isVisible()) {

                m.move();
            } else {

                ms.remove(m);
            }
        }
        for (int i = 0; i < lasers.size(); i++){
        	Laser ls=(Laser) lasers.get(i);
        	if (ls.isVisible()){
        		ls.move();
        	} else {
        		lasers.remove(ls);
        	}
        }
    }
	private void updateAliens() throws InterruptedException {
		
		
		//level complete protocol and checking
		
        if (alienAs.isEmpty() && alienBVs.isEmpty() && alienBHs.isEmpty() && !sectors.clearedSectors.contains(sectors.x+"y"+sectors.y)) {
        	ship.addShots(10);
            sectorsCleared++;
            if (win){
            	ingame=false;
            	return;
            }
            MessageBox("Sector X-"+sectors.x+" Y-"+sectors.y+" cleared!","Congratulational Systems");
            sectors.markSectorCleared();
        }
        
        
        //continued updating of aliens
        
        for (int i = 0; i < alienAs.size(); i++) {

            AlienA a = alienAs.get(i);
            if (a.isVisible()) {
                a.move();
            } else {
                alienAs.remove(i);
            }
        }
        for (int i = 0; i < alienBVs.size(); i++){
        	AlienBV aB=alienBVs.get(i);
        	if (aB.isVisible()) {
        		aB.move(ship.getX(), ship.getY());
        	} else {
        		alienBVs.remove(aB);
        	}
        }
        for (int i = 0; i < alienBHs.size(); i++){
        	AlienBH aB=alienBHs.get(i);
        	if (aB.isVisible()) {
        		aB.move(ship.getX(), ship.getY());
        	} else {
        		alienBHs.remove(aB);
        	}
        }
    }
	
	
	
	
	
	//~~update effects~~
	
	public void updateExplosions(){
		for (Explosion ex:explosions){
			if (!ex.isVisible()){
				explosions.remove(ex);
			}
		}
	}
	
	
	
	
	
	//~~check entity collisions~~
	
	public void checkCollisions() {

        Rectangle r3 = ship.getBounds();
        for (Wall w:walls){
        	Rectangle r7=w.getBounds();
        	if (r7.intersects(r3)){
        		Hashtable<String, Integer> distanceIn=w.getDistanceIn(r3, ship.orientation);
        		ship.setLocation(ship.getX()+distanceIn.get("x"), ship.getY()+distanceIn.get("y"));
        	}
        	for (AlienA alien:alienAs){
        		Rectangle r2=alien.getBounds();
        		if (r7.intersects(r2)){
        			alien.setOrientation(d.getOpposite(alien.orientation));
        		}
        	}
        	for (AlienBV alienB:alienBVs){
        		Rectangle r4=alienB.getBounds();
        		if (r7.intersects(r4)){
        			Hashtable<String, Integer> distanceIn=w.getDistanceIn(r4, alienB.orientation);
        			alienB.setLocation(alienB.getX()+distanceIn.get("x"), alienB.getY()+distanceIn.get("y"));
        		}
        	}
        	for (AlienBH alienB:alienBHs){
        		Rectangle r5=alienB.getBounds();
        		if (r7.intersects(r5)){
        			Hashtable<String, Integer> distanceIn=w.getDistanceIn(r5, alienB.orientation);
        			alienB.setLocation(alienB.getX()+distanceIn.get("x"), alienB.getY()+distanceIn.get("y"));
        		}
        	}
        }
        for (AlienA alien : alienAs) {
            Rectangle r2 = alien.getBounds();

            if (r3.intersects(r2)) {
                ship.setVisible(false);
                alien.setVisible(false);
                ingame = false;
                sound.playSound("ExplosionA.wav", 0);
            }
        }
        for (AlienBV alienB : alienBVs){
        	Rectangle r4 = alienB.getBounds();
        	if (r3.intersects(r4)) {
        		ship.setVisible(false);
        		alienB.setVisible(false);
        		ingame=false;
        		sound.playSound("ExplosionA.wav", 0);
        	}
        }
        for (AlienBH alienB : alienBHs){
        	Rectangle r5 = alienB.getBounds();
        	if (r3.intersects(r5)) {
        		ship.setVisible(false);
        		alienB.setVisible(false);
        		ingame=false;
        		sound.playSound("ExplosionA.wav", 0);
        	}
        }
        ArrayList<Laser> lasers=AlienBV.lasers;
        for (Laser ls : lasers){
        	Rectangle r5=ls.getBounds();
        	if (r3.intersects(r5)){
        		ship.setVisible(false);
        		ls.setVisible(false);
        		ingame=(false);
        		sound.playSound("ExplosionA.wav", 0);
        	}
        	for (Wall w : walls){
            	Rectangle r6=w.getBounds();
            	if (r5.intersects(r6)){
            		sound.playSound("ExplosionA.wav", 0);
            		explosions.add(new Explosion(ls.getX(),ls.getY(),DURATION_EXPLOSION));
            		ls.setVisible(false);
            	}
            }
        }
        ArrayList<Missile> ms = ship.getMissiles();

        for (Missile m : ms) {

            Rectangle r1 = m.getBounds();

            for (AlienA alien : alienAs) {

                Rectangle r2 = alien.getBounds();

                if (r1.intersects(r2)) {
                    m.setVisible(false);
                    
                    alien.setVisible(false);
                    explosions.add(new Explosion(alien.getX(),alien.getY(),DURATION_EXPLOSION));
                    sound.playSound("ExplosionA.wav", 0);
                }
            }
            for (AlienBV alienB : alienBVs){
            	
            	Rectangle r4 = alienB.getBounds();
            	
            	if (r1.intersects(r4)) {
            		sound.playSound("ExplosionA.wav", 0);
            		m.setVisible(false);
            		explosions.add(new Explosion(alienB.getX(),alienB.getY(),DURATION_EXPLOSION));
            		alienB.setVisible(false);
            	}
            }
            for (AlienBH alienB : alienBHs){
            	Rectangle r5 = alienB.getBounds();
            	
            	if (r1.intersects(r5)) {
            		sound.playSound("ExplosionA.wav", 0);
            		explosions.add(new Explosion(alienB.getX(),alienB.getY(),DURATION_EXPLOSION));
            		m.setVisible(false);
            		alienB.setVisible(false);
            	}
            }
            for (Wall w : walls){
            	Rectangle r6=w.getBounds();
            	if (r1.intersects(r6)){
            		sound.playSound("ExplosionA.wav", 0);
            		explosions.add(new Explosion(m.getX(),m.getY(),DURATION_EXPLOSION));
            		m.setVisible(false);
            	}
            }
        }
        
    }
	
	
	
	
	
	//~~event handling~~
	
	private class TAdapter extends KeyAdapter {
		@Override
		public void keyReleased(KeyEvent e) {
			ship.keyReleased(e);
		}
		
		@Override
		public void keyPressed(KeyEvent e){
			ship.keyPressed(e);
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		ship.move();
		updateProjectiles();
		try {
			updateAliens();
		} catch (InterruptedException e1) {
			
			e1.printStackTrace();
		}
		checkCollisions();
		repaint();
		
	}
	
	
	
	
	
	//~~sprite adding convenience methods (leads to slightly less typing time)~~
	
	public void addWall(int x,int y){
		walls.add(new Wall(x,y));
	}
	public void addAlienA(int x,int y, int orientation){
		alienAs.add(new AlienA(x,y,orientation));
	}
	public void addAlienBH(int x,int y, int orientation){
		alienBHs.add(new AlienBH(x,y,orientation));
	}
	public void addAlienBV(int x,int y, int orientation){
		alienBVs.add(new AlienBV(x,y,orientation));
	}
	
	
	
	
	
	//~~external windows~~
	
	public void MessageBox(String msg, String title){
		JOptionPane.showMessageDialog(null, msg, title, JOptionPane.PLAIN_MESSAGE);
	}
	
	
	
	
	
	//function to move sector from ship class
	
	public void moveSector(int direction){
		if (sectors.clearedSectors.contains(sectors.x+"y"+sectors.y)){
			sectors.move(direction);
		} else {
			ship.setLocation(ship.getX()+d.getXMod(d.getOpposite(direction)), ship.getY()+d.getYMod(d.getOpposite(direction)));
		}
	}
}
