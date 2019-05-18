package com.Entities.Ship.Buildables;



import com.Frame;
import com.Entities.Ship.Buildable;
import com.Foreground.Wall;

import topDownGameAPI.util.Direction;

public class WallB extends Buildable<Wall>{

	public WallB(int x,int y) {
		super("https://piskel-imgstore-b.appspot.com/img/213573f3-8f9a-11e8-b7b2-d1b5b6af4e40.gif", Frame.board.walls, 4,true);
		setLoc(x,y);
	}

	@Override
	protected Wall building() {
		// TODO Auto-generated method stub
		return new Wall(Direction.getXMod(Frame.board.ship.orientation)*(int)Frame.board.ship.getBounds().getWidth()+Frame.board.ship.getX(),Direction.getYMod(Frame.board.ship.orientation)*(int)Frame.board.ship.getBounds().getHeight()+Frame.board.ship.getY());
	}

}
