import javax.swing.JPanel;
import javax.imageio.ImageIO;
import javax.swing.JButton;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.*;
import java.net.*;

import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
public class ServerScreen extends JPanel implements KeyListener {
	private Game game;
	private int mouseX, mouseY;
	private BufferedImage background, link, zelda, enemy, fullHeart, emptyHeart, water, rock, tree;

	private ObjectOutputStream out;

	public ServerScreen() {
    	this.setLayout(null);
    	this.setFocusable(true);
    	this.addKeyListener(this);

		game = new Game();


		try {
			background = ImageIO.read(new File("background.png"));
			link = ImageIO.read(new File("link.gif"));
			zelda = ImageIO.read(new File("zelda.gif"));
			enemy = ImageIO.read(new File("enemy.png"));
			fullHeart = ImageIO.read(new File("fullHeart.png"));
			emptyHeart = ImageIO.read(new File("emptyHeart.png"));
			water = ImageIO.read(new File("water.png"));
			rock = ImageIO.read(new File("rock.png"));
			tree = ImageIO.read(new File("tree.png"));
    	} catch (IOException e) {
    		e.printStackTrace();
    	}



    }
	public Dimension getPreferredSize() {

        return new Dimension(800,1000);
    }




    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_UP) {
        	game.getP1().up();
			checkMap("up");
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
        	game.getP1().down();
        	checkMap("down");
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
        	game.getP1().left();
        	checkMap("left");
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
        	game.getP1().right();
					checkMap("right");
        }

		game.getP2().stay();
		repaint();
		try {
			out.reset();
			out.writeObject(game);
		} catch (IOException e1) {
			System.err.println("Game failed to send");
		}


    }

	public void checkMap(String dir) {
		if (game.getItemMap().get(game.getP1().getLocation()) == null) {
    			System.out.println("empty");
    		}
        	else if ((int) (game.getItemMap().get(game.getP1().getLocation())) == 1) {
        		game.getP1().collectedItem();
						//Sound.playSound("LOZ_Get_Rupee.wav");
						//Sound.stopSound("LOZ_Get_Rupee.wav");
						game.checkWinner();
        		game.getItemMap().put(game.getP1().getLocation(), 0);
        	}
			else if ((int) (game.getItemMap().get(game.getP1().getLocation())) == 2) {
        		game.getP1().loseHealth();
						//Sound.playSound("LOZ_Link_Hurt.wav");
						//Sound.stopSound("LOZ_Link_Hurt.wav");
						game.getItemMap().remove(game.getP1().getLocation());
						if (game.getP1().getHealthSize() == 0) {
							game.setWinner(2);
						}
						else {
	        		game.getP1().setLocation(new Location(4,5));
						}

        	}
			else if ((int) (game.getItemMap().get(game.getP1().getLocation())) == 3 || (int) (game.getItemMap().get(game.getP1().getLocation())) == 4 || (int) (game.getItemMap().get(game.getP1().getLocation())) == 5) {
        		if (dir.equals("up")) {
					game.getP1().down();
				}
				else if (dir.equals("down")){
					game.getP1().up();
				}
				else if (dir.equals("left")){
					game.getP1().right();
				}
				else if (dir.equals("right")) {
					game.getP1().left();
				}
        	}
	}

    public void keyReleased(KeyEvent e2) {}
    public void keyTyped(KeyEvent e3) {}


	public void poll() throws IOException {
        int portNumber = 1024;
        ServerSocket serverSocket = new ServerSocket(portNumber);
        Socket clientSocket = serverSocket.accept(); //wait for a connection
        System.out.println("Connection Successful!");//Will print after connection is made

		out = new ObjectOutputStream(clientSocket.getOutputStream());
		ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
		out.writeObject("Connection Successful!");

		String message;
		try {
			message = (String) in.readObject();
			System.out.println(message);
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			out.reset();
			out.writeObject(game);
		} catch (IOException e1) {
			System.err.println("Game failed to send");
		}

		try {
			while (true) {
				game = (Game) in.readObject();
				repaint();
			}
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}


	}

	public void paintComponent(Graphics g) {

	super.paintComponent(g);
	g.drawImage(background, 0, 0, 800, 1000, null);

	g.setColor(new Color(5, 168, 32));

	int xPos = 50;
	int yPos = 280;

	for (int i = 0; i < 11; i++) {
		g.drawLine(xPos, yPos, xPos + 700, yPos);
		yPos += 70;
	}

	xPos = 50;
	yPos = 280;

	for (int i = 0; i < 11; i++) {
		g.drawLine(xPos, yPos, xPos, yPos + 700);
		xPos += 70;
	}

	g.drawImage(link, game.getP1().getLocation().getC()*70 + 55, game.getP1().getLocation().getR()*70 + 280, 60, 70, null);
	g.drawImage(zelda, game.getP2().getLocation().getC()*70 + 55, game.getP2().getLocation().getR()*70 + 280, 65, 70, null);

	BufferedImage img = null;

	for (Location key : game.getItemMap().keySet()) {
		img = null;
		try {
			if (game.getItemMap().get(key) == null) {
				img = null;
			}
			else if (game.getItemMap().get(key) == 1) {
				img = ImageIO.read(new File("rupee.png"));
			}
			else if (game.getItemMap().get(key) == 2) {
				img = ImageIO.read(new File("enemy.png"));
			}
			else if (game.getItemMap().get(key) == 3) {
				img = ImageIO.read(new File("water.png"));
			}
			else if (game.getItemMap().get(key) == 4) {
				img = ImageIO.read(new File("rock.png"));
			}
			else if (game.getItemMap().get(key) == 5) {
				img = ImageIO.read(new File("tree.png"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		g.drawImage(img, key.getC()*70 + 50, key.getR()*70 + 280, 70, 70, null);
	}

	g.setColor(Color.black);
	g.setFont(new Font("Helvetica", Font.BOLD, 50));
	g.drawString("PLAYER 1", 500, 60);
	g.setColor(Color.white);
	g.setFont(new Font("Helvetica", Font.BOLD, 50));
	g.drawString("PLAYER 1", 503, 60);
	g.setColor(Color.black);
	g.setFont(new Font("Helvetica", Font.BOLD, 20));
	try {
		img = ImageIO.read(new File("rupee.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	g.drawImage(img, 550, 210, 30, 30,null);
	g.drawString(" x " + game.getP1().getNumItems(), 575, 232);

	xPos = 550;
	yPos = 170;

	try {
		img = ImageIO.read(new File("fullHeart.png"));
	} catch (IOException e) {
			e.printStackTrace();
	}

	for (int i = 0; i < game.getP1().getHealthSize(); i++) {
		g.drawImage(img, xPos, yPos, 30, 30, null);
		xPos += 30;
	}

	try {
		img = ImageIO.read(new File("emptyHeart.png"));
	} catch (IOException e) {
			e.printStackTrace();
	}

	for (int i = 0; i < (3 - game.getP1().getHealthSize()); i++) {
		g.drawImage(img, xPos, yPos, 30, 30, null);
		xPos += 30;
	}

	g.setFont(new Font("Helvetica", Font.BOLD, 70));

	if (game.getWinner() == 2) {
		g.setColor(new Color(255,255,255, 170));
		g.fillRect(0,0,800,1000);
		g.setColor(Color.black);
		g.drawString("You Lost!",250,480);
		g.drawString("P2 Wins", 270, 570);
		this.removeKeyListener(this);
	}
	else if (game.getWinner() == 1) {
		g.setColor(new Color(255,255,255,170));
		g.fillRect(0,0,800,1000);
		g.setColor(Color.black);
		g.drawString("You Won!",250,480);
		g.drawString("P1 Wins", 270, 570);
		this.removeKeyListener(this);
	}
}

}
