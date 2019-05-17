package com.Effects;

import java.awt.Image;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.ImageIcon;

import com.Effect;

public class Explosion extends Effect {
	Image img;
	public Explosion(int x, int y, long length) {
		super(x, y, length);
		try {
			img=new ImageIcon(new URL("https://piskel-imgstore-b.appspot.com/img/ceaa5300-8af5-11e8-9d35-f5d92446ad27.gif")).getImage();
		} catch (MalformedURLException e) {
			
			e.printStackTrace();
		}
	}
	public Image getImage(){
		return img;
	}
}
