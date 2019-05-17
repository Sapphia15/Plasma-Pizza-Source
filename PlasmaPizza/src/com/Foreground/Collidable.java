package com.Foreground;
import java.awt.Image;
import java.awt.Rectangle;
import java.net.MalformedURLException;
import java.net.URL;
import javax.swing.ImageIcon;

import topDownGameAPI.util.Direction;

import java.util.Hashtable;
public class Collidable {
	protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected boolean vis;
    protected Image image;
    public Collidable(int x, int y) {
        this.x = x;
        this.y = y;
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
    public Hashtable<String,Integer> getRawBounds(Rectangle bounds){
    	Hashtable<String,Integer> rawBounds=new Hashtable<String,Integer>();
    	rawBounds.put("xm", (int)bounds.getMinX());
    	rawBounds.put("xM", (int)bounds.getMaxX());
    	rawBounds.put("ym", (int)bounds.getMinY());
    	rawBounds.put("yM", (int)bounds.getMaxY());
    	return rawBounds;
    }
    public Hashtable<String, Integer> getDistanceIn(Rectangle r,int orientation){ //r is colliding object; orientation is the direction of colliding entity
    	Hashtable<String, Integer> b1=getRawBounds(getBounds());
    	Hashtable<String, Integer> b2=getRawBounds(r);
    	int xDif=0;//difference in x to push colliding entity
    	int yDif=0;
    	if (orientation==Direction.RIGHT){
			xDif=b1.get("xm")-b2.get("xM");
		}
		if (orientation==Direction.LEFT){
			xDif=b1.get("xM")-b2.get("xm");
		}
		if (orientation==Direction.DOWN){
			yDif=b1.get("ym")-b2.get("yM");
		}
		if (orientation==Direction.UP){
			yDif=b1.get("yM")-b2.get("ym");
		}
		Hashtable<String, Integer>dif=new Hashtable<String, Integer>();
		dif.put("x", xDif);
		dif.put("y", yDif);
		return dif;
    	
    }
    public void setVisible(Boolean visible) {
        vis = visible;
    }
    
}
