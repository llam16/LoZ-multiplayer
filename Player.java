import java.io.IOException;

import javax.imageio.ImageIO;
import java.util.Stack;
import java.io.Serializable;

public class Player implements Serializable {
	private String name;
	private Location location;
	private int items;
	private Stack<Integer> health;
	
	public Player(String name) {
		this.name = name;
		location = new Location(4, 5);
		
		items = 0;
		
		health = new Stack<Integer>();
		for(int i = 0; i < 3; i++) {
			health.push(1);
		}
		
	}
	
	public String getName() {return name;}
	public Location getLocation() {return location;}
	public void setLocation(Location loc) {this.location = loc;}
	public int getHealthSize() {return health.size();}
	public int getNumItems() {return items;}
	
	public void loseHealth() {health.pop();}
	public void collectedItem() {items++;}
	
	public void right() {
		location.right();
	}
	
	public void left() {
		location.left();
	}
	
	public void up() {
		location.up();
	}
	
	public void down() {
		location.down();
	}
	
	public void stay() {
		this.location = new Location(location.getR(), location.getC());
	}
}