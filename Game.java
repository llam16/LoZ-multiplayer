import java.util.HashMap;
import java.util.Map;
import java.io.Serializable;

public class Game implements Serializable {
	Map<Location, Integer> itemMap;
	private Player p1;
	private Player p2;
	private int winner;

	public Game() {


		p1 = new Player("Player 1");
		p2 = new Player("Player 2");


		itemMap = new HashMap<Location, Integer>();

		addItem(11, 1);
		addItem(10, 2);
		addItem(2, 3);
		addItem(2, 4);
		addItem(2, 5);

	}

	public void addItem(int num, int val) {
		int count = 0;
		int v1 = (int) (Math.random()*10);
		int v2 = (int) (Math.random()*10);

		while (count < num) {
			Location loc = new Location(v1, v2);
			if (itemMap.get(loc) == null) {
				if (v1 == 4 && v2 == 5) {
					itemMap.put(loc, 0);
				}
				else {
					itemMap.put(loc, val);
					count++;
				}
			}

			v1 = (int) (Math.random()*10);
			v2 = (int) (Math.random()*10);
		}
	}

	public Map<Location,Integer> getItemMap() {return itemMap;}
	public Player getP1() {return p1;}
	public Player getP2() {return p2;}

	public void checkWinner() {
		if (p1.getNumItems() == 6)
			winner = 1;
		else if (p2.getNumItems() == 6)
			winner = 2;
		else
			winner = 0;
	}

	public void setWinner(int winner) {this.winner = winner;}
	public int getWinner() {return winner;}

}
