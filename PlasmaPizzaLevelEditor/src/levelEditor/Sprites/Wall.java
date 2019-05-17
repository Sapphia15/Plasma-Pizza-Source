package levelEditor.Sprites;

import java.awt.Image;
import java.util.Hashtable;

import javax.swing.ImageIcon;

import topDownGameAPI.Sprite;
import topDownGameAPI.util.Direction;

public class Wall extends Sprite{

	public Wall(int x, int y, int orientation) {
		super(x, y, orientation);
		loadImage("https://piskel-imgstore-b.appspot.com/img/213573f3-8f9a-11e8-b7b2-d1b5b6af4e40.gif");
		// TODO Auto-generated constructor stub
	}
}
