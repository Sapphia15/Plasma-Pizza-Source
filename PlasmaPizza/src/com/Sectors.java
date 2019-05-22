package com;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

import com.Entities.ScrapProcessor;
import com.Entities.NPCs.AlienA;
import com.Entities.NPCs.AlienBH;
import com.Entities.NPCs.AlienBV;
import com.Foreground.Wall;

import levelEditor.EditorFrame;
import levelEditor.Sprites.Alien;
import topDownGameAPI.util.*;
public class Sectors {
	int x;
	int y;
	Direction d;
	BufferedWriter writer;
	//String keys are for sector location as noted: x+"y"+y
	Hashtable<String,ArrayList<AlienA>> AlienAs;
	Hashtable<String,ArrayList<AlienBH>> AlienBHs;
	Hashtable<String,ArrayList<AlienBV>> AlienBVs;
	Hashtable<String,ArrayList<Wall>> Walls;
	Hashtable<String,ArrayList<ScrapProcessor>> scrapProcessors;
	Hashtable<String,String> fileText; //String keys are for sector location and line number as noted: x+"y"+y+"l"+line
	//Used to determine what sectors have been loaded or cleared by testing weather the string 'x+"y"+y' is contained within the array list
	ArrayList<String> loadedSectors;
	ArrayList<String> clearedSectors;
	String sectorDir;
	public Sectors(String saveDir){
		x=0;
		y=0;
		fileText=new Hashtable<String,String>();
		AlienAs=new Hashtable<String,ArrayList<AlienA>>();
		AlienBHs=new Hashtable<String,ArrayList<AlienBH>>();
		AlienBVs=new Hashtable<String,ArrayList<AlienBV>>();
		Walls=new Hashtable<String,ArrayList<Wall>>();
		scrapProcessors=new Hashtable<String,ArrayList<ScrapProcessor>>();
		loadedSectors=new ArrayList<String>();
		clearedSectors=new ArrayList<String>();
		
		this.sectorDir=saveDir;
	}
	
	public Sectors(int x,int y){
		this.x=x;
		this.y=y;
		fileText=new Hashtable<String,String>();
		AlienAs=new Hashtable<String,ArrayList<AlienA>>();
		AlienBHs=new Hashtable<String,ArrayList<AlienBH>>();
		AlienBVs=new Hashtable<String,ArrayList<AlienBV>>();
		Walls=new Hashtable<String,ArrayList<Wall>>();
		scrapProcessors=new Hashtable<String,ArrayList<ScrapProcessor>>();
		loadedSectors=new ArrayList<String>();
		clearedSectors=new ArrayList<String>();
		sectorDir="temp/";
		loadTestSector(x,y);
	}
	
	public Sectors(String saveDir,int x,int y){
		this.x=x;
		this.y=y;
		fileText=new Hashtable<String,String>();
		AlienAs=new Hashtable<String,ArrayList<AlienA>>();
		AlienBHs=new Hashtable<String,ArrayList<AlienBH>>();
		AlienBVs=new Hashtable<String,ArrayList<AlienBV>>();
		Walls=new Hashtable<String,ArrayList<Wall>>();
		scrapProcessors=new Hashtable<String,ArrayList<ScrapProcessor>>();
		loadedSectors=new ArrayList<String>();
		clearedSectors=new ArrayList<String>();
		this.sectorDir=saveDir;
		BufferedReader reader;
		try {
			reader= new BufferedReader(new FileReader(saveDir+"/"+"sectorInf.txt"));
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
			line=1;
			text=fileText.get(line);
			while (text!="stop"){
				clearedSectors.add(text);
				line++;
				text=fileText.get(line);
			}
			try {
				reader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e1) {
			
		}
		loadSector(x,y);
	}
	
	//reads a sector if it is not already loaded 
	public void loadSector(int x, int y){
		loadSector("levels/",x,y);
	}
	
	//reads a sector if it is not already loaded 
	public void loadTestSector(int x, int y){
		loadSector("temp/",x,y);
	}
	
	public void loadSector(String path,int x,int y) {
		//Read the file of the sector
		BufferedReader reader = null;
		try {
			reader=new BufferedReader(new FileReader(sectorDir+x+"y"+y+".txt"));
		} catch (FileNotFoundException loadAsNewLevelInstance) {
			try {
				reader=new BufferedReader(new FileReader(path+x+"y"+y+".txt"));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				System.err.println("Could not find file of sector: "+path+x+"y"+y+".txt");
			}
		}
		String text="start";
		int line=0;
		while (text!=null) {
			try {
				fileText.put(x+"y"+y+"l"+line,text);
				text=reader.readLine();
			} catch (IOException e) {
				e.printStackTrace();
				System.err.println("Could not read text of sector: levels/"+x+"y"+y+".txt");
				return;
			}
			line=line+1;
		}
		fileText.put(x+"y"+y+"l"+line, "stop");
		line=0;
		int addX;
		int addY;
		int no;
		int i;
		int orientation;
		AlienAs.put(x+"y"+y, new ArrayList<AlienA>());
		AlienBHs.put(x+"y"+y, new ArrayList<AlienBH>());
		AlienBVs.put(x+"y"+y, new ArrayList<AlienBV>());
		Walls.put(x+"y"+y, new ArrayList<Wall>());
		scrapProcessors.put(x+"y"+y,new ArrayList<ScrapProcessor>());
		while(text!="stop"){
			
			line++;
			text=fileText.get(x+"y"+y+"l"+line);
			if (text.equals("AlienA")){
				
				line++;
				no=Integer.parseInt(fileText.get(x+"y"+y+"l"+line));
				for (i=1; i<=no;i++){
					line++;
					orientation=Integer.parseInt(fileText.get(x+"y"+y+"l"+line));
					line++;
					addX=Integer.parseInt(fileText.get(x+"y"+y+"l"+line));
					line++;
					addY=Integer.parseInt(fileText.get(x+"y"+y+"l"+line));
					addAlienAAtSector(x,y,addX,addY,orientation);
				}
				
			}
			if (text.equals("AlienBV")){
				line++;
				no=Integer.parseInt(fileText.get(x+"y"+y+"l"+line));
				for (i=1; i<=no;i++){
					line++;
					orientation=Integer.parseInt(fileText.get(x+"y"+y+"l"+line));
					line++;
					addX=Integer.parseInt(fileText.get(x+"y"+y+"l"+line));
					line++;
					addY=Integer.parseInt(fileText.get(x+"y"+y+"l"+line));
					addAlienBVAtSector(x,y,addX,addY,orientation);
				}
				
			}
			if (text.equals("AlienBH")){
				
				line++;
				no=Integer.parseInt(fileText.get(x+"y"+y+"l"+line));
				for (i=1; i<=no;i++){
					line++;
					orientation=Integer.parseInt(fileText.get(x+"y"+y+"l"+line));
					line++;
					addX=Integer.parseInt(fileText.get(x+"y"+y+"l"+line));
					line++;
					addY=Integer.parseInt(fileText.get(x+"y"+y+"l"+line));
					addAlienBHAtSector(x,y,addX,addY,orientation);
				}
				
			}
			if (text.equals("Wall")){
				
				line++;
				no=Integer.parseInt(fileText.get(x+"y"+y+"l"+line));
				for (i=1; i<=no;i++){
					line++;
					addX=Integer.parseInt(fileText.get(x+"y"+y+"l"+line));
					line++;
					addY=Integer.parseInt(fileText.get(x+"y"+y+"l"+line));
					addWallAtSector(x,y,addX,addY);
				}
				
			}
			if (text.equals("ScrapProcessor")){
				
				line++;
				no=Integer.parseInt(fileText.get(x+"y"+y+"l"+line));
				for (i=1; i<=no;i++){
					line++;
					addX=Integer.parseInt(fileText.get(x+"y"+y+"l"+line));
					line++;
					addY=Integer.parseInt(fileText.get(x+"y"+y+"l"+line));
					addScrapProcessorAtSector(x,y,addX,addY);
				}
				
			}
		}		
	}
	
	public void save(int x,int y){
		try {
			File f=new File(sectorDir+x+"y"+y+".txt");
			System.out.println(f.delete());
			if (!f.exists()) {
				f=new File(sectorDir+x+"y"+y+".txt");
			}
			writer=new BufferedWriter(new FileWriter(f));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (!AlienAs.get(x+"y"+y).isEmpty()) {
			try {
				writer.write("AlienA");
				writer.newLine();
				writer.write(Integer.toString(AlienAs.get(x+"y"+y).size()));
				writer.newLine();
				for (AlienA a:AlienAs.get(x+"y"+y)){
					
					
					writer.write(Integer.toString(a.orientation));
					writer.newLine();
					writer.write(Integer.toString(a.getX()));
					writer.newLine();
					writer.write(Integer.toString(a.getY()));
					writer.newLine();
			
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		if (!AlienBVs.get(x+"y"+y).isEmpty()) {
			try {
				writer.write("AlienBV");
				writer.newLine();
				writer.write(Integer.toString(AlienBVs.get(x+"y"+y).size()));
				writer.newLine();
				for (AlienBV a:AlienBVs.get(x+"y"+y)){
					
					
					writer.write(Integer.toString(a.orientation));
					writer.newLine();
					writer.write(Integer.toString(a.getX()));
					writer.newLine();
					writer.write(Integer.toString(a.getY()));
					writer.newLine();
			
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		if (!AlienBHs.get(x+"y"+y).isEmpty()) {
			try {
				writer.write("AlienBH");
				writer.newLine();
				writer.write(Integer.toString(AlienBHs.get(x+"y"+y).size()));
				writer.newLine();
				for (AlienBH a:AlienBHs.get(x+"y"+y)){
					
					
					writer.write(Integer.toString(a.orientation));
					writer.newLine();
					writer.write(Integer.toString(a.getX()));
					writer.newLine();
					writer.write(Integer.toString(a.getY()));
					writer.newLine();
			
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
		if (!Walls.get(x+"y"+y).isEmpty()){
			try {
				writer.write("Wall");
				writer.newLine();
				writer.write(Integer.toString(Walls.get(x+"y"+y).size()));
				for (Wall w:Walls.get(x+"y"+y)){
					writer.newLine();
					writer.write(Integer.toString(w.getX()));
					writer.newLine();
					writer.write(Integer.toString(w.getY()));
				}
			} catch (IOException e){
				e.printStackTrace();
			}
		}
		
		if (!scrapProcessors.get(x+"y"+y).isEmpty()){
			try {
				writer.write("ScrapProcessor");
				writer.newLine();
				writer.write(Integer.toString(scrapProcessors.get(x+"y"+y).size()));
				for (ScrapProcessor sp:scrapProcessors.get(x+"y"+y)){
					writer.newLine();
					writer.write(Integer.toString(sp.getX()));
					writer.newLine();
					writer.write(Integer.toString(sp.getY()));
				}
			} catch (IOException e){
				e.printStackTrace();
			}
		}
		
	}
	
	public void save() {
		archiveLevel(Frame.board.alienAs, Frame.board.alienBHs, Frame.board.alienBVs,Frame.board.walls,Frame.board.scrapProcessors);
		for (String key:loadedSectors) {
			int yIndex=key.lastIndexOf("y");
			save(Integer.parseInt(key.substring(0, yIndex)),Integer.parseInt(key.substring(yIndex+1)));
		}
		try {
			
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			File f=new File(sectorDir+"sectorInf.txt");
			f.delete();
			f=new File(sectorDir+"sectorInf.txt");
			writer=new BufferedWriter(new FileWriter(f));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			
			for (String s:clearedSectors){
				writer.write(s);
				writer.newLine();
			}
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
			
			
	
	//functions for Panel to retrieve level info
	public ArrayList<AlienA> getAlienAs(){
		return AlienAs.get(x+"y"+y);
	}
	public ArrayList<AlienBH> getAlienBHs(){
		return AlienBHs.get(x+"y"+y);
	}
	public ArrayList<AlienBV> getAlienBVs(){
		return AlienBVs.get(x+"y"+y);
	}
	public ArrayList<Wall> getWalls(){
		return Walls.get(x+"y"+y);
	}
	public ArrayList<ScrapProcessor> getScrapProcessors(){
		return scrapProcessors.get(x+"y"+y);
	}
	//saves the current state of the level temporarily to reloaded later
	public void archiveLevel(ArrayList<AlienA> aAs, ArrayList<AlienBH> aBHs, ArrayList<AlienBV> aBVs, ArrayList<Wall> ws, ArrayList<ScrapProcessor> sPs){
		AlienAs.get(x+"y"+y).clear();
		AlienBHs.get(x+"y"+y).clear();
		AlienBVs.get(x+"y"+y).clear();
		Walls.get(x+"y"+y).clear();
		scrapProcessors.get(x+"y"+y).clear();
		AlienAs.get(x+"y"+y).addAll(aAs);
		AlienBHs.get(x+"y"+y).addAll(aBHs);
		AlienBVs.get(x+"y"+y).addAll(aBVs);
		Walls.get(x+"y"+y).addAll(ws);
		scrapProcessors.get(x+"y"+y).addAll(sPs);
		loadedSectors.add(x+"y"+y);
	}
	//function for marking levels as cleared
	public void markSectorCleared(){
		clearedSectors.add(x+"y"+y);
	}
	//function for moving from sector to sector
	public void move(int direction){
		this.x=x+Direction.getXMod(direction);
		this.y=y+Direction.getYMod(direction);
	}
	//functions that add sprites to level
	
		//for reloading sectors *typing efficiency
	public void addAlienA(int x, int y,int orientation){
		AlienAs.get(this.x+"y"+this.y).add(new AlienA(x,y,orientation));
	}
	public void addAlienBV(int x,int y,int orientation){
		AlienBVs.get(this.x+"y"+this.y).add(new AlienBV(x,y,orientation));
	}
		//allows loading sectors
	public void addAlienAAtSector(int sectorX,int sectorY,int x, int y,int orientation){
		AlienAs.get(sectorX+"y"+sectorY).add(new AlienA(x,y,orientation));
	}
	public void addAlienBVAtSector(int sectorX,int sectorY,int x,int y,int orientation){
		AlienBVs.get(sectorX+"y"+sectorY).add(new AlienBV(x,y,orientation));
	}
	public void addAlienBHAtSector(int sectorX,int sectorY,int x,int y,int orientation){
		AlienBHs.get(sectorX+"y"+sectorY).add(new AlienBH(x,y,orientation));
	}
	public void addWallAtSector(int sectorX,int sectorY,int x, int y){
		Walls.get(sectorX+"y"+sectorY).add(new Wall(x,y));
	}
	public void addScrapProcessorAtSector(int sectorX,int sectorY,int x, int y){
		scrapProcessors.get(sectorX+"y"+sectorY).add(new ScrapProcessor(x,y,0));
	}
	
	
}
