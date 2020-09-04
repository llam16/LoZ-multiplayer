import java.io.Serializable;
public class Location implements Serializable{
	private int r;
	private int c;
	
	public Location(int r, int c) {
		this.r = r;
		this.c = c;
	}
	
	public int getR() {return r;}
	public int getC() {return c;}
	
	public void setR(int r) {this.r = r;}
	public void setC(int c) {this.c = c;}
	
	public void up() {
		if (r > 0)
			r--;
	}
	
	public void down() {
		if (r < 9)
			r++;
	}
	
	public void left() {
		if (c > 0)
			c--;
	}
	
	public void right() {
		if (c < 9)
			c++;
	}
	
	public boolean equals(Object o) {
		Location loc = (Location) o;
		return (this.r == loc.getR() && this.c == loc.getC());
	}
	
	public int hashCode() {
		return (r*10) + c;
	}
	
	public String toString() {
		return "r: " + r + "c: " + c;
	}
}