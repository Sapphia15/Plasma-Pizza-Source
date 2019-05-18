package topDownGameAPI.util;

import java.util.Hashtable;

public class Direction {
	static Hashtable<String, Integer> directions;
	private static Direction d=new Direction();
	//direction codes
	public static final int UNDEFINED=-1;
	public static final int RIGHT=0;
	public static final int LEFT=1;
	public static final int UP=2;
	public static final int DOWN=3;
	private Direction(){
		directions=new Hashtable<String,Integer>();
		directions.put(LEFT+"X", -1);
		directions.put(LEFT+"Y", 0);
		directions.put(RIGHT+"X", 1);
		directions.put(RIGHT+"Y", 0);
		directions.put(UP+"X", 0);
		directions.put(UP+"Y", -1);
		directions.put(DOWN+"X",0);
		directions.put(DOWN+"Y",1);
	}
	/** 
	 * Attempts to find the direction opposite of an input direction.
	 * @param direction an integer representing a direction; use direction codes such as <code>Direction.RIGHT</code>
	 * @return if <code>direction</code> is a valid direction code the opposite direction is returned. Otherwise <code>Direction.UNDEFINED</code> is returned and error message will be sent to the console.
	 */
	public static int getOpposite(int direction){
		
		if (direction==LEFT){
			return RIGHT;
		} else if (direction==RIGHT){
			return LEFT;
		} else if (direction==UP){
			return DOWN;
		} else if (direction==DOWN){
			return UP;
		} else {
			System.err.println("direction input not valid");
			return UNDEFINED;
		}
	}
	/**
	 * Gets the base number that should modify x when movement in specific direction occurs  
	 * @param direction an integer representing a direction; use direction codes such as <code>Direction.RIGHT</code>
	 * @return an integer from -1 to 1 that can be used to modify the x value of an entity that is moving and multiplied to it's speed if desired
	 */
	public static int getXMod(int direction){
		return directions.get(direction+"X");
	}
	/**
	 * Gets the base number that should modify y when movement in specific direction occurs  
	 * @param direction an integer representing a direction; use direction codes such as <code>Direction.RIGHT</code>
	 * @return an integer from -1 to 1 that can be used to modify the y value of an entity that is moving
	 */
	public static int getYMod(int direction){
		return directions.get(direction+"Y");
	}
	/**
	 * Converts a direction code to a string
	 * @param direction an integer representing a direction; use direction codes such as <code>Direction.Right</code>
	 * @return the number of the direction code as a string
	 */
	public static String toString(int direction){
		if (direction==RIGHT){
			return "0";
		} else if (direction==LEFT) {
			return "1";
		} else if (direction==UP){
			return "2";
		} else if (direction==DOWN){
			return "3";
		} else {
			return "-1";
		}
	}
	/**
	 *
	 * Converts a string of the name of a direction code to the integer code
	 * @param direction a string representing a direction; a lower case form of a direction code such as <code>"right"</code>
	 * @return a direction code such as <code>Direction.RIGHT</code>
	 */
	public static int getCodeFromString(String direction){
		if (direction.equals("right")){
			return RIGHT;
		} else if (direction.equals("left")) {
			return LEFT;
		} else if (direction.equals("up")){
			return UP;
		} else if (direction.equals("down")){
			return DOWN;
		} else {
			return UNDEFINED;
		}
	}
}
