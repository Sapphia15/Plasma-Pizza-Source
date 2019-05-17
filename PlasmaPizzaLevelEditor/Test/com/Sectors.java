package com;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

import com.Entities.NPCs.AlienA;
import com.Entities.NPCs.AlienBH;
import com.Entities.NPCs.AlienBV;

import topDownGameAPI.util.*;
public class Sectors {
	int x;
	int y;
	Direction d;
	//String keys are for sector location as noted: x+"y"+y
	Hashtable<String,ArrayList<AlienA>> AlienAs;
	Hashtable<String,ArrayList<AlienBH>> AlienBHs;
	Hashtable<String,ArrayList<AlienBV>> AlienBVs;
	Hashtable<String,String> fileText; //String keys are for sector location and line number as noted: x+"y"+y+"l"+line
	//Used to determine what sectors have been loaded or cleared by testing weather the string 'x+"y"+y' is contained within the array list
	ArrayList<String> loadedSectors;
	ArrayList<String> clearedSectors;
	public Sectors(){
		x=0;
		y=0;
		fileText=new Hashtable<String,String>();
		AlienAs=new Hashtable<String,ArrayList<AlienA>>();
		AlienBHs=new Hashtable<String,ArrayList<AlienBH>>();
		AlienBVs=new Hashtable<String,ArrayList<AlienBV>>();
		d=new Direction();
		loadedSectors=new ArrayList<String>();
		clearedSectors=new ArrayList<String>();
	}
	//reads a sector if it is not already loaded 
	public void loadSector(int x, int y){
		//Read the file of the sector
		BufferedReader reader = null;
		try {
			reader=new BufferedReader(new FileReader("levels/"+x+"y"+y+".txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.err.println("Could not find file of sector: levels/"+x+"y"+y+".txt");
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
			}
			line=line+1;
		}
		fileText.put(x+"y"+y+"l"+line, "stop");
		line=0;
		int addX;
		int addY;
		int no;
		int i;
		String orientation;
		while(text!="stop"){
			line++;
			text=fileText.get(x+"y"+y+"l"+line);
			if (text.equals("AlienA")){
				AlienAs.put(x+"y"+y, new ArrayList<AlienA>());
				line++;
				no=Integer.parseInt(fileText.get(x+"y"+y+"l"+line));
				for (i=1; i<=no;i++){
					line++;
					addX=Integer.parseInt(fileText.get(x+"y"+y+"l"+line));
					line++;
					addY=Integer.parseInt(fileText.get(x+"y"+y+"l"+line));
					line++;
					orientation=fileText.get(x+"y"+y+"l"+line);
					addAlienAAtSector(x,y,addX,addY,orientation);
				}
				
			}
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
	//function for marking levels as cleared
	public void markSectorCleared(){
		clearedSectors.add(x+"y"+y);
	}
	//function for moving from sector to sector
	public void move(int direction){
		this.x=x+d.getXMod(direction);
		this.y=y+d.getYMod(direction);
	}
	//functions that add sprites to level
	//  32x32 max sprite x and y are 28
	
		//for reloading sectors *typing efficiency
	public void addAlienA(int x, int y,String orientation){
		AlienAs.get(this.x+"y"+this.y).add(new AlienA(x*32,y*32,d.getCodeFromString(orientation)));
	}
	public void addAlienBV(int x,int y,String orientation){
		AlienBVs.get(this.x+"y"+this.y).add(new AlienBV(x*32,y*32,d.getCodeFromString(orientation)));
	}
		//allows loading sectors
	public void addAlienAAtSector(int sectorX,int sectorY,int x, int y,String orientation){
		AlienAs.get(sectorX+"y"+sectorY).add(new AlienA(x*32,y*32,d.getCodeFromString(orientation)));
	}
	public void addAlienBVAtSector(int sectorX,int sectorY,int x,int y,String orientation){
		AlienBVs.get(sectorX+"y"+sectorY).add(new AlienBV(x*32,y*32,d.getCodeFromString(orientation)));
	}
	public void addAlienBHAtSector(int sectorX,int sectorY,int x,int y,String orientation){
		AlienBHs.get(sectorX+"y"+sectorY).add(new AlienBH(x*32,y*32,d.getCodeFromString(orientation)));
	}
	
	
}
