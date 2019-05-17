package com;
import java.awt.Image;
import java.sql.Time;
import java.util.Hashtable;
import java.util.Timer;
public class Effect {
	int x;
	int y;
	Hashtable<String, Image> imgs;
	boolean vis;
	long Starttime;
	long length;
	public Effect(int x,int y, long length){
		this.x=x;
		this.y=y;
		this.length=length;
		vis=true;
		Starttime=System.currentTimeMillis();
	}
	public boolean isVisible(){
		if (vis==false || System.currentTimeMillis()-Starttime>length){
			vis=false;
		}
		return vis;
	}
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	
}
