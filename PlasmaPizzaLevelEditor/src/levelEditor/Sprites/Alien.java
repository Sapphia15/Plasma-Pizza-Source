package levelEditor.Sprites;

import java.awt.Image;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Hashtable;

import javax.swing.ImageIcon;

import topDownGameAPI.Sprite;
import topDownGameAPI.util.Direction;

public class Alien extends Sprite{
	protected String type;
	protected Hashtable<Integer,Image> ArrowImage;
	public Alien(int x, int y, int orientation, String type) {
		super(x, y, orientation);
		this.type=type;
		ArrowImage=new Hashtable<Integer, Image>();
		ArrowImage.put(Direction.RIGHT, new ImageIcon("images/ArrowRight.gif").getImage());
		ArrowImage.put(Direction.LEFT, new ImageIcon("images/ArrowLeft.gif").getImage());
		ArrowImage.put(Direction.UP, new ImageIcon("images/ArrowUp.gif").getImage());
		ArrowImage.put(Direction.DOWN, new ImageIcon("images/ArrowDown.gif").getImage());
		try {
			imgs.put("A", new ImageIcon(new URL("https://piskel-imgstore-b.appspot.com/img/6ef8d68c-f678-11e7-be47-153b3595bcca.gif")).getImage());
			imgs.put("BH", new ImageIcon(new URL("https://piskel-imgstore-b.appspot.com/img/85ed0f28-35c7-11e8-9a46-23d7616e2df6.gif")).getImage());
			imgs.put("BV", new ImageIcon(new URL("https://piskel-imgstore-b.appspot.com/img/85ed0f28-35c7-11e8-9a46-23d7616e2df6.gif")).getImage());
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		image=imgs.get(type);
	}
	public String getType(){
		return type;
	}
	public Image getArrowImage(){
		return ArrowImage.get(orientation);
	}
	
}
