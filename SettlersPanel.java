import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.UIManager;

public class SettlersPanel extends JPanel {
	public static SettlersOfCatanGame frame;
	private final int PANEL_WIDTH = 1200;
	private final int PANEL_HEIGHT = 800;
	private Board b;
	private Player player1;
	private Player player2;
	private Player player3;
	private Player player4;
	private ArrayList<Player> playerList = new ArrayList<Player>();
	private PlayerBox box1;
	private PlayerBox box2;
	private PlayerBox box3;
	private PlayerBox box4;
	private Player currentPlayer;
	private int turn;
	
	public static void main(String[] args) {
		JFrame frame = new JFrame("Catan");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(new SettlersPanel());
		frame.pack();
		frame.setVisible(true);
		frame.setResizable(false);
	}
	
	public SettlersPanel() {
		this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
		setUpGameObjects();
		startGame();
	}
	public SettlersPanel(MainMenu settings) {
		this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
		setUpGameObjects(settings.getPlayers());
		startGame();
	}
	
	public void setUpGameObjects() {
		b = new Board(PANEL_WIDTH, PANEL_HEIGHT);
		player1 = new Player(b, "Alan", Color.BLUE);
		playerList.add(player1);
		player2 = new Player(b, "Devon", Color.RED);
		playerList.add(player2);
		player3 = new Player(b, "Payton", Color.PINK);
		playerList.add(player3);
		player4 = new Player(b, "Maz", Color.YELLOW);
		playerList.add(player4);
		box1 = new PlayerBox(PANEL_WIDTH, PANEL_HEIGHT, 0, 0, player1);
		box2 = new PlayerBox(PANEL_WIDTH, PANEL_HEIGHT, 0, PANEL_HEIGHT/2, player2);
		box3 = new PlayerBox(PANEL_WIDTH, PANEL_HEIGHT, (3*PANEL_WIDTH)/4, 0, player3);
		box4 = new PlayerBox(PANEL_WIDTH, PANEL_HEIGHT, (3*PANEL_WIDTH)/4, PANEL_HEIGHT/2, player4);
	}
	public void setUpGameObjects(ArrayList<PlayerOptions> players) {
		b = new Board(PANEL_WIDTH, PANEL_HEIGHT);
		for(int n = 0;n < players.size();n++) {
			playerList.add(new Player(b,players.get(n).getName(),players.get(n).getColor()));
		}
		box1 = new PlayerBox(PANEL_WIDTH, PANEL_HEIGHT, 0, 0, playerList.get(0));
		box2 = new PlayerBox(PANEL_WIDTH, PANEL_HEIGHT, 0, PANEL_HEIGHT/2, playerList.get(1));
		box3 = new PlayerBox(PANEL_WIDTH, PANEL_HEIGHT, (3*PANEL_WIDTH)/4, 0, playerList.get(2));
		box4 = new PlayerBox(PANEL_WIDTH, PANEL_HEIGHT, (3*PANEL_WIDTH)/4, PANEL_HEIGHT/2, playerList.get(3));
	}
	
	public void startGame() {
		turn = 1;
		pickStartingPlayer();
		pickStartingSettlements();
	}
	
	public void pickStartingPlayer() {
		int start = (int)(Math.random()*4 + 1);
		for(Player p : playerList) {
			if(p.getPlayerNumber() == start) {
				currentPlayer = p;
				currentPlayer.setTurn(true);
			}
		}
	}
	
	public void nextTurn() {
		turn++;
		currentPlayer.setTurn(false);
		int current = currentPlayer.getPlayerNumber();
		if(current < 4) {
			current++;
		}
		else {
			current = 1;
		}
		for(Player p : playerList) {
			if(p.getPlayerNumber() == current) {
				currentPlayer = p;
				currentPlayer.setTurn(true);
			}
		}
	}
	
	public void pickStartingSettlements() {
		MouseListener settlementPicker = new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int x = e.getX();
				int y = e.getY();
				System.out.println(x + " " + y);
				if(b.closestLoc(x,y) != null) {
					if(b.checkAdjacentLocs(b.closestLoc(x,y))) {
						b.closestLoc(x,y).makeSettlement(currentPlayer);
						repaint();
						nextTurn();
					}
				}
			}
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub	
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub	
			}
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub	
			}
		};
		this.addMouseListener(settlementPicker);
	}
	
	public boolean gameOver() {
		for(Player p : playerList) {
			if(p.getPoints() >= 10) {
				return true;
			}
		}
		return false;
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		b.draw(g);
		box1.draw(g);
		box2.draw(g);
		box3.draw(g);
		box4.draw(g);
	}

}
