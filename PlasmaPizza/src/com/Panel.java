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
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.FocusListener;
import java.awt.geom.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Random;

import javax.swing.Timer;

import com.Entities.Scrap;
import com.Entities.ScrapProcessor;
import com.Entities.NPCs.*;
import com.Entities.Projectiles.*;
import com.Entities.Ship.Ship;
import com.Foreground.Wall;
import com.Effects.*;
import  sun.audio.*;
import topDownGameAPI.util.Direction;
import topDownGameAPI.util.Sounds;

import  java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
@SuppressWarnings("unused")
public class Panel extends JPanel implements /*Runnable,*/ ActionListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Ship ship;
	protected Timer timer;
	public static ArrayList<AlienA> alienAs;
	public static ArrayList<AlienBV> alienBVs;
	public static ArrayList<AlienBH> alienBHs;
	public static ArrayList<Explosion> explosions;
	public static ArrayList<Wall> walls;
	public static ArrayList<Scrap> scraps;
	public static ArrayList<ScrapProcessor> scrapProcessors;
	private static Hashtable<String,Image>loadedImages;
    private static boolean ingame;
    private static final int ICRAFT_X = 40;
    private static final int ICRAFT_Y = 60;
    public static final int B_WIDTH = 929;
    public static final int B_HEIGHT = 902;
    private static final int DELAY = 10;
    private static boolean init;
    private static boolean win;
    private static boolean loading;
    private static boolean timerWasRunning;
    private static boolean paused;
    private static boolean musicEnabled;
    private static Sectors sectors;
    static Graphics g;
    private static Random Rand=new Random();
    public static int sectorsCleared;
    public static String saveDir;
    final static long DURATION_EXPLOSION=(long)1714;
    Sounds sound;
    //private Thread animator;
    
    
    
    
    
    //~~initialization~~
    
    public Panel() throws InterruptedException{
    	setBackground(Color.BLACK);
    	loading=true;
        loadedImages=new Hashtable<String, Image>();
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
        scraps=new ArrayList<>();
        scrapProcessors=new ArrayList<>();
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
		sound.playSoundOnLoop("PlasmaPizzaSongs.wav", 100);
        System.out.println("Arrays Loaded");
		System.out.println("Loading Images...");
		loadedImages.put("musicIconA",new ImageIcon("images/musicIconA.gif").getImage());
		loadedImages.put("musicIconB",new ImageIcon("images/musicIconB.gif").getImage());
		repaint();
        System.out.println("Images Loaded");
        String[] saveFiles= {"Save1","Save2","Save3","Save4","Save5","Save6","temp"};
        int fileNo=JOptionPane.showOptionDialog(this, "What save file would you like to load?", "Save Files", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, saveFiles, saveFiles[1]);
        
        if (saveFiles[fileNo].equals("temp")){
        	saveDir="temp/";
        } else {
        	saveDir="saves/"+saveFiles[fileNo]+"/";
        }
        //load player data
        System.out.println("Loading Player Data...");
        
        //make directories
        {
        File saveFolder=new File(saveDir);
        saveFolder.mkdirs();
        File sectorFolder=new File(saveDir+"sectors");
        sectorFolder.mkdir();
        }
        ship=new Ship(423, 423, Direction.RIGHT);
        System.out.println("Launching Game");
		addKeyListener(new TAdapter());
		addMouseListener(new MAdapter());
		addFocusListener(new focusListener());
		BufferedReader reader;
			try {
				reader= new BufferedReader(new FileReader(saveDir+"/"+"panelInf.txt"));
				String text="start";
				int line=0;
				Hashtable<Integer,String> fileText=new Hashtable<>();
				while (text!=null) {
					try {
						fileText.put(line,text);
						text=reader.readLine();
					} catch (IOException e) {
						
					}
					line=line+1;
					
				}
				fileText.put(line, "stop");
				sectors=new Sectors(saveDir+"sectors/",Integer.parseInt(fileText.get(1)),Integer.parseInt(fileText.get(2)));
				sectorsCleared=sectors.clearedSectors.size();
				musicEnabled=fileText.get(3).equals("true");
				if (!musicEnabled){
					sound.pauseSound("The-Happy-New.wav");
				}
				
			} catch (FileNotFoundException e1) {
				sectors=new Sectors(saveDir+"sectors/");
				musicEnabled=true;
			}
		lvInit();
		timer = new Timer(DELAY, this);
		loading=false;
		ingame=false;
		paused=false;
		repaint();
		ingame=true;
		MessageBox("Galactic Coordinates: X-"+sectors.x+" Y-"+sectors.y,"SHIP NAVIGATION SYSTEM");
        timer.start();
	}
	
	/**Loads from a test sector
	 * 
	 * @param x
	 * @param y
	 * @throws InterruptedException
	 */
	public void load(int x,int y) throws InterruptedException{
		init=true;
		ingame=false;
		repaint();
		System.out.println("Test Level ("+x+","+y+") loading...");
		saveDir="temp/";
		win=false;
		sectorsCleared=0;
		System.out.println("Loading Arrays...");
		alienAs = new ArrayList<>();
        alienBVs = new ArrayList<>();
        alienBHs = new ArrayList<>();
        explosions=new ArrayList<>();
        walls=new ArrayList<>();
        scraps=new ArrayList<>();
        scrapProcessors=new ArrayList<>();
        ArrayList<String> soundPaths=new ArrayList<>();
        System.out.println("Arrays Loaded");
        soundPaths.add("ExplosionA.wav");
        soundPaths.add("LaserA.wav");
        soundPaths.add("LaserB.wav");
        soundPaths.add("The-Happy-New.wav");
		//load sounds
		System.out.println("Loading Sounds...");
		sound=new Sounds();
		sound.setSourcePath("sounds/");
		sound.initialize(soundPaths);
		System.out.println("Playing The-Happy-New.wav");
		sound.playSoundOnLoop("The-Happy-New.wav", 100);
        System.out.println("Sounds Loaded");
		System.out.println("Loading Images...");
		loadedImages.put("musicIconA",new ImageIcon("images/musicIconA.gif").getImage());
		loadedImages.put("musicIconB",new ImageIcon("images/musicIconB.gif").getImage());
        System.out.println("Images Loaded");
        //make directories
        {
            File saveFolder=new File(saveDir);
            saveFolder.mkdir();
            File sectorFolder=new File(saveDir+"sectors");
            sectorFolder.mkdir();
        }
        System.out.println("Launching Game");
		ship=new Ship(423, 423, Direction.RIGHT);
		ship.addScrap((int)Math.round(Point2D.distance(x, y, 0, 0))*5);
		ship.addTechPoints((int)Math.round(Point2D.distance(x, y, 0, 0))*5);
		ship.addShots((int)Math.round(Point2D.distance(x, y, 0, 0))*2);
		sectorsCleared=(int)Math.round(Point2D.distance(x, y, 0, 0));
		addKeyListener(new TAdapter());
		addMouseListener(new MAdapter());
		sectors=new Sectors(x,y);
		lvInit();
		timer = new Timer(DELAY, this);
		loading=false;
		paused=false;
		musicEnabled=true;
		repaint();
		ingame=true;
		MessageBox("Test Initialization Protocol Complete","SHIP ITERATION SYSTEM");
		MessageBox("Galactic Coordinates: X - ("+sectors.x+") Y - ("+sectors.y+")","SHIP NAVIGATION SYSTEM");
        timer.start();
	}
	
	
	
	
	
	//~~painting methods
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		if (ingame){
			if (!paused){
				doInGameDrawing(g);
			} else {
				doMenuDrawing(g);
			}
		
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
	
	
	
	
	public void doMenuDrawing(Graphics g){
		Graphics2D g2d=(Graphics2D) g;
		g2d.setColor(Color.white);
		g2d.drawString("Save", 2, 17);
		if (musicEnabled){
			g2d.drawImage(loadedImages.get("musicIconA"),900,860, this);
		} else {
			g2d.drawImage(loadedImages.get("musicIconB"),900,860, this);
		}
	}
	
	
	
	public void doInGameDrawing(Graphics g){
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
        for (Scrap s:scraps){
        	g.drawImage(s.getImage(), s.getX(), s.getY(), this);
        }
        for (Explosion ex: explosions){
        	if (ex.isVisible()){
        		g.drawImage(ex.getImage(), ex.getX(), ex.getY(), this);
        	}
        }
        for (Wall w:walls){
        	g.drawImage(w.getImage(),w.getX(),w.getY(), this);
        }
        for (ScrapProcessor s:scrapProcessors){
        	g.drawImage(s.getImage(), s.getX(), s.getY(), this);
        }
        g.setColor(Color.WHITE);
        int Enemies=alienAs.size()+alienBVs.size()+alienBHs.size();
        g.drawString("Aliens left: " + Enemies, 5, 15);
        g.drawString("Level: "+sectorsCleared, 5,35);
        g.drawString("Plasma Pizza Projectiles:"+Ship.shots, 5, 865);
        g.drawString("Pieces of Scrap:"+ship.getScrapCount(), 5, 877);
        g2d.drawImage(ship.getImg(), ship.getX(), ship.getY(), this);
        Image shield=ship.getForceFieldImage();
        if (shield!=null && ship.getForceFieldActive()){
        	g2d.drawImage(shield, ship.getX(), ship.getY(), this);
        }
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
	
	public void lvInit(){
		if (!sectors.loadedSectors.contains(sectors.x+"y"+sectors.y)){
			sectors.loadSector(sectors.x, sectors.y);
		}
		for(AlienA aA:sectors.getAlienAs()){
			alienAs.add(aA);
		}
		for (AlienBV aBV:sectors.getAlienBVs()){
			alienBVs.add(aBV);
		}
		for (AlienBH aBH:sectors.getAlienBHs()){
			alienBHs.add(aBH);
		}
		
		for (Wall w:sectors.getWalls()){
			walls.add(w);
		}
		for (ScrapProcessor s:sectors.getScrapProcessors()){
			scrapProcessors.add(s);
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
        	ship.addTechPoints(5);
        	ship.activateForceField();
            sectorsCleared++;
            if (win){
            	ingame=false;
            	return;
            }
            MessageBox("Sector X - ("+sectors.x+") Y - ("+sectors.y+") cleared!","Congratulational Systems");
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
	
	
	
	
	
	public void updateOtherEntities(){
		for (int i = 0; i < scraps.size(); i++){
			Scrap s=scraps.get(i);
			if (!s.isVisible()){
				scraps.remove(s);
			}
		}
	}
	
	
	
	
	
	//~~update effects~~
	
	private void updateExplosions(){
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
        			alien.setOrientation(Direction.getOpposite(alien.orientation));
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
        for (Scrap s:scraps){
        	Rectangle r8=s.getBounds();
        	if (r8.intersects(r3)){
        		s.setVisible(false);
        		ship.addScrap();
        	}
        }
        for (AlienA alien : alienAs) {
            Rectangle r2 = alien.getBounds();

            if (r3.intersects(r2)) {
                ship.damage(1);
                alien.setVisible(false);
                sound.playSound("ExplosionA.wav", 0);
            }
        }
        for (AlienBV alienB : alienBVs){
        	Rectangle r4 = alienB.getBounds();
        	if (r3.intersects(r4)) {
        		ship.damage(1);;
        		alienB.setVisible(false);
        		sound.playSound("ExplosionA.wav", 0);
        	}
        }
        for (AlienBH alienB : alienBHs){
        	Rectangle r5 = alienB.getBounds();
        	if (r3.intersects(r5)) {
        		ship.damage(1);
        		alienB.setVisible(false);
        		sound.playSound("ExplosionA.wav", 0);
        	}
        }
        ArrayList<Laser> lasers=AlienBV.lasers;
        for (Laser ls : lasers){
        	Rectangle r5=ls.getBounds();
        	if (r3.intersects(r5)){
        		ship.damage(1);
        		ls.setVisible(false);
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
                    int i=Rand.nextInt(3);
            		for (int s=0; s<i; s++){
            			scraps.add(new Scrap(alien.getX()+11,alien.getY()+11,Direction.UNDEFINED));
            		}
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
			int key=e.getKeyCode();
			ship.keyPressed(key);
			if (key==KeyEvent.VK_ESCAPE){
				if (paused){
					paused=false;
				} else {
					paused=true;
				}
			}
		}
	}
	private class MAdapter extends MouseAdapter{
		@Override
		public void mousePressed(MouseEvent e){
			if (paused){
				if (e.getX()>=900 && e.getX()<927 && e.getY()>=860 && e.getY()<927){
					if (musicEnabled){
						musicEnabled=false;
						sound.pauseSound("The-Happy-New.wav");
					} else {
						musicEnabled=true;
						sound.resumeSound("The-Happy-New.wav");
					}
				}
				if (e.getX()>=2 && e.getX()<=28 && e.getY()>=6 && e.getY()<=17){
					System.out.println("Saving...");
					sectors.save();
					ship.save();
					save();
					System.out.println("Game Saved");
				}
			} else {
				Rectangle r9;
				for (ScrapProcessor s:scrapProcessors){
					r9=s.getBounds();
					if (r9.contains(e.getPoint())){
						s.interact();
					}
				}
			}
		}
	}
	
	private class focusListener implements FocusListener{

		@Override
		public void focusGained(FocusEvent arg0) {
			
		}

		@Override
		public void focusLost(FocusEvent arg0) {
			ship.stop();
			
		}
		
	}
	
	public void save(){
		BufferedWriter writer;
		try {
			File f=new File(Panel.saveDir+"paneInf.txt");
			if (f.exists()){
				f.delete();
			}
			if (!f.exists()) {
				f=new File(Panel.saveDir+"panelInf.txt");
			}
			writer=new BufferedWriter(new FileWriter(f));
			writer.write(String.valueOf(sectors.x));
			writer.newLine();
			writer.write(String.valueOf(sectors.y));
			writer.newLine();
			writer.write(String.valueOf(musicEnabled));
			
			writer.close();
			MessageBox("Data saved successfully.","Ship Memory Systems");
		} catch (IOException e) {
			e.printStackTrace();
			MessageBox("MEMORY ERROR: failed to save data", "Ship Memory Systems");
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (!paused){
			ship.move();
			updateProjectiles();
			try {
				updateAliens();
			} catch (InterruptedException e1) {
				
				e1.printStackTrace();
			}
			updateOtherEntities();
			checkCollisions();
			if (!ship.isVisible()){
				ingame=false;
			}
		}
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
	
	
	
	public void dispose(){
		AlienBV.lasers.clear();
	}
	
	//function to move sector from ship class
	
	public void moveSector(int direction){
		if (sectors.clearedSectors.contains(sectors.x+"y"+sectors.y)){
			sectors.archiveLevel(alienAs, alienBHs, alienBVs,walls,scrapProcessors);
			sectors.move(direction);
			walls.clear();
			scrapProcessors.clear();
			lvInit();
			if (direction==Direction.RIGHT){
				ship.setLocation(0, ship.getY());
			} else if (direction==Direction.LEFT){
				ship.setLocation(895, ship.getY());
			} else if (direction==Direction.UP){
				ship.setLocation(ship.getX(), 870);
			} else if (direction==Direction.DOWN){
				ship.setLocation(ship.getX(), 0);
			}
		} else {
			ship.setLocation(ship.getX()+Direction.getXMod(Direction.getOpposite(direction)), ship.getY()+Direction.getYMod(Direction.getOpposite(direction)));
		}
	}
}
