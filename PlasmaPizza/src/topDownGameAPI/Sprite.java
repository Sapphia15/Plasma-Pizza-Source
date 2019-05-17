package topDownGameAPI;
import java.awt.Image;
import java.awt.Rectangle;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Hashtable;
import javax.swing.ImageIcon;


import topDownGameAPI.util.*;
public class Sprite {
	protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected boolean vis;
    protected Image image;
	protected Direction d;
	public Sounds sound;
    public int orientation;
    protected Hashtable<String, Image> imgs;
    public Sprite(int x, int y, int orientation) {
    	d=new Direction();
    	sound=new Sounds();
    	imgs=new Hashtable<String, Image>();
        this.x = x;
        this.y = y;
        this.orientation=orientation;
        vis = true;
    }

    protected void loadImage(String imageName) {

        ImageIcon ii = null;
		try {
			ii = new ImageIcon(new URL(imageName));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
        image = ii.getImage();
    }
    
    protected void getImageDimensions() {
    	if (image==null){
    		System.err.println("image is null");
    	}
        width = image.getWidth(null);
        height = image.getHeight(null);
    }    
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
    public Image getImage() {
        return image;
    }

    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }

    public boolean isVisible() {
        return vis;
    }

    public void setVisible(Boolean visible) {
        vis = visible;
    }
    public void setLocation(int x,int y){
    	this.x=x;
    	this.y=y;
    }
    public void setOrientation(int orientation){
    	this.orientation=orientation;
    }
    public void interact(){

    }
}
