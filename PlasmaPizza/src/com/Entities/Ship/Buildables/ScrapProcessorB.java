package com.Entities.Ship.Buildables;

import java.util.ArrayList;

import com.Frame;
import com.Entities.ScrapProcessor;
import com.Entities.Ship.Buildable;

public class ScrapProcessorB extends Buildable<ScrapProcessor> {
	public ScrapProcessorB(int x,int y){
		super("images/ScrapProcessor.gif",Frame.board.scrapProcessors,6);
		setLoc(x,y);
	}

	@Override
	protected ScrapProcessor building() {
		// TODO Auto-generated method stub
		return new ScrapProcessor(Frame.board.ship.getX(),Frame.board.ship.getY(),Frame.board.ship.orientation);
	}
}
