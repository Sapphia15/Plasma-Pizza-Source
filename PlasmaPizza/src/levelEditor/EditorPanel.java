package levelEditor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import com.Frame;

import levelEditor.Sprites.Alien;
import levelEditor.Sprites.Wall;
import topDownGameAPI.util.*;

public class EditorPanel extends JPanel implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ArrayList<Alien> aliens;
	ArrayList<Wall> walls;
	Alien selectedAlien;
	Wall selectedWall;
	String selectionType;
	Timer timer;
	BufferedWriter writer;
	ScheduledExecutorService s;
	
	boolean mouseInComponent;
	
	public EditorPanel(){
		this.setBackground(Color.WHITE);
		this.setDoubleBuffered(true);
		mouseInComponent=false;
		addMouseListener(new MListener());
		selectedAlien=null;
		selectedWall=null;
		selectionType="";
		timer=new Timer(10, this);
		walls=new ArrayList<Wall>();
		aliens=new ArrayList<Alien>();
		timer.start();
		s=Executors.newScheduledThreadPool(1);
	}
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2d=(Graphics2D) g;
		for (Alien a:aliens){
			g2d.drawImage(a.getImage(), a.getX(), a.getY(), this);
			g2d.drawImage(a.getArrowImage(), a.getX(), a.getY(), this);
		}
		for (Wall w:walls){
			g2d.drawImage(w.getImage(), w.getX(), w.getY(), this);
		}
		if (selectionType.equals("alien")){
			try {
				g2d.drawImage(selectedAlien.getImage(), getMousePosition().x, getMousePosition().y, this);
				g2d.drawImage(selectedAlien.getArrowImage(), getMousePosition().x, getMousePosition().y, this);
			} catch (NullPointerException e) {
				//mouse out of bounds
			}
		} else if (selectionType.equals("wall")){
			try {
				g2d.drawImage(selectedWall.getImage(), getMousePosition().x, getMousePosition().y, this);
				
			} catch (NullPointerException e){
				//mouse out of bounds
			}
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		repaint();
		
	}
	
	public void test() {
		save("temp/");
		s.schedule(new Runnable(){
			public void run(){
				new Frame(EditorFrame.x,EditorFrame.y);
			}
		}, 10, TimeUnit.MILLISECONDS);
		
	}
	
	public void save(){
		try {
			File temp=new File("temp/");
			for (File tempFile:temp.listFiles()){
				tempFile.delete();
			}
			File f=new File("levels/"+EditorFrame.coords+".txt");
			/*if (f.exists()){
				f.delete();
			}*/
			//if (!f.exists()) {
				f=new File("levels/"+EditorFrame.coords+".txt");
			//}
			writer=new BufferedWriter(new FileWriter(f));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (Alien a:aliens){
			
			try {
				writer.write("Alien"+a.getType());
				writer.newLine();
				writer.write(Integer.toString(1));
				writer.newLine();
				writer.write(Integer.toString(a.orientation));
				writer.newLine();
				writer.write(Integer.toString(a.getX()));
				writer.newLine();
				writer.write(Integer.toString(a.getY()));
				writer.newLine();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		if (!walls.isEmpty()){
			try {
				writer.write("Wall");
				writer.newLine();
				writer.write(Integer.toString(walls.size()));
				for (Wall w:walls){
					writer.newLine();
					writer.write(Integer.toString(w.getX()));
					writer.newLine();
					writer.write(Integer.toString(w.getY()));
				}
			} catch (IOException e){
				e.printStackTrace();
			}
		}
		try {
				
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void save(String dir){
		try {
			File f=new File(dir+EditorFrame.coords+".txt");
			System.out.println(f.delete());
			if (!f.exists()) {
				f=new File(dir+EditorFrame.coords+".txt");
			}
			writer=new BufferedWriter(new FileWriter(f));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (Alien a:aliens){
			
			try {
				writer.write("Alien"+a.getType());
				writer.newLine();
				writer.write(Integer.toString(1));
				writer.newLine();
				writer.write(Integer.toString(a.orientation));
				writer.newLine();
				writer.write(Integer.toString(a.getX()));
				writer.newLine();
				writer.write(Integer.toString(a.getY()));
				writer.newLine();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		if (!walls.isEmpty()){
			try {
				writer.write("Wall");
				writer.newLine();
				writer.write(Integer.toString(walls.size()));
				for (Wall w:walls){
					writer.newLine();
					writer.write(Integer.toString(w.getX()));
					writer.newLine();
					writer.write(Integer.toString(w.getY()));
				}
			} catch (IOException e){
				e.printStackTrace();
			}
		}
		try {
				
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private class MListener implements MouseListener{
		@Override
		public void mouseClicked(MouseEvent arg0) {
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
			mouseInComponent=true;
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			mouseInComponent=false;
		}

		@Override
		public void mousePressed(MouseEvent e) {
			int button=e.getButton();
			if (button==MouseEvent.BUTTON1){
				if (selectedAlien!=null){
					if (selectionType.equals("alien")){
						selectedAlien.setLocation(getMousePosition().x, getMousePosition().y);
						aliens.add(selectedAlien);
						selectionType="";
						System.out.println("Alien added to array");
					}
					selectedAlien=null;
				
				} else if (selectedWall!=null){
					if (selectionType.equals("wall")){
						selectedWall.setLocation(getMousePosition().x,getMousePosition().y);
						walls.add(selectedWall);
						selectionType="";
						System.out.println("Wall added to array");
					}
					selectedWall=null;
				}
			} else if (button==MouseEvent.BUTTON3){
				String[] options={"Add Sprite","Save","Test"};
				int option=JOptionPane.showOptionDialog(null, "What would you like to do?", "Menu", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
				if (option==0){
					String[] optionsSprite={"Alien","Wall"};
					int Sprite=JOptionPane.showOptionDialog(null,"Choose sprite","Add Sprite",JOptionPane.DEFAULT_OPTION,JOptionPane.QUESTION_MESSAGE,null,optionsSprite,optionsSprite[0]);
					if (Sprite==0){
						String[] optionsType={"A","B"};
						int Type=JOptionPane.showOptionDialog(null, "Choose Alien Type", "Add Sprite", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, optionsType, optionsType[0]);
						String[] optionsOrientation={"right","left","up","down"};
						int Orientation=JOptionPane.showOptionDialog(null, "Choose Alien Orientation", "Add Sprite", JOptionPane.DEFAULT_OPTION,JOptionPane.QUESTION_MESSAGE,null,optionsOrientation,optionsOrientation[0]);
						select("alien"+optionsType[Type], Orientation);
						System.out.println("alien"+optionsType[Type]+" selected");
					} else if (Sprite==1){
						select("wall",Direction.UNDEFINED);
						System.out.println("Wall selected");
					}
				} else if (option==1){
					save();
				} else if (option==2){
					test();
				}
			}
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
	}
	public void select(String type, int orientation){
		if (type.equals("alienA")){
			selectedAlien=new Alien(this.getMousePosition().x,this.getMousePosition().y,orientation,"A");
			selectionType="alien";
		} else if (type.equals("alienB") && (orientation==Direction.UP || orientation==Direction.DOWN)){
			selectedAlien=new Alien(this.getMousePosition().x,this.getMousePosition().y,orientation,"BV");
			selectionType="alien";
		} else if (type.equals("alienB") && (orientation==Direction.LEFT || orientation==Direction.RIGHT)){
			selectedAlien=new Alien(this.getMousePosition().x,this.getMousePosition().y,orientation,"BH");
			selectionType="alien";
		} else if (type.equals("wall")){
			selectedWall=new Wall(getMousePosition().x,getMousePosition().y,Direction.UNDEFINED);
			selectionType="wall";
		}
	}
}
