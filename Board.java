import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class Board {
	private Hex[][] hexArr = new Hex[7][];
	private int boardWidth,boardHeight,boardXLoc;
	private int hexWidth;
	private int hexHeight;
	private int xGap;
	private int yGap;
	private ArrayList<TerrainHex.Resource> availablePorts = new ArrayList<TerrainHex.Resource>();
	private ArrayList<TerrainHex.Resource> availableTiles = new ArrayList<TerrainHex.Resource>();
	private ArrayList<Integer> availableNumbers = new ArrayList<Integer>();
	private ArrayList<Location> spaces = new ArrayList<Location>();
	private boolean isDesert = false;

	public Board(int pw, int ph) {
		for(int i = 0;i < hexArr.length/2+1;i++) {
			hexArr[i] = new Hex[i+4];
			hexArr[6-i] = new Hex[i+4];
		}
		for(int i = 0;i < hexArr.length;i++) {
			for(int j = 0;j < hexArr[i].length;j++) {
				System.out.print("H");
			}
			System.out.println();
		}

		boardWidth = pw/2;
		boardHeight = (3*ph)/4;
		boardXLoc = pw/4;
		hexWidth = boardWidth/7;
		hexHeight = (int)(2*(hexWidth/Math.sqrt(3)));
		xGap = boardWidth - 7*hexWidth;
		yGap = boardHeight - (11*hexHeight)/2;

		for(int i = 0;i < 5;i++) {
			availablePorts.add(TerrainHex.Resource.values()[i]);
		}
		for(int i = 0;i < 4;i++) {
			availablePorts.add(null);

			availableTiles.add(TerrainHex.Resource.Wood);
			availableTiles.add(TerrainHex.Resource.Sheep);
			availableTiles.add(TerrainHex.Resource.Wheat);
		}
		for(int i = 0;i < 3;i++) {
			availableTiles.add(TerrainHex.Resource.Brick);
			availableTiles.add(TerrainHex.Resource.Rock);
		}
		availableTiles.add(TerrainHex.Resource.Desert);

		availableNumbers.add(2);
		for(int i = 0;i < 4;i++) {
			for(int n = 0;n < 2;n++) {
				availableNumbers.add(i+3);
				availableNumbers.add(11-i);
			}
		}
		availableNumbers.add(12);
		System.out.println("Numbers: "+availableNumbers);
		System.out.println("Tiles: "+availableTiles);
		System.out.println("Ports: "+availablePorts);

		setTileLocs();
		setUpPorts();
	}

	public void setTileLocs() {
		int y = yGap/2 + hexHeight/2;
		int x = boardXLoc;
		for(int r = 0; r < hexArr.length; r++) {
			if(hexArr[r].length == 4) {
				x = boardXLoc + xGap/2 + 2*hexWidth;
			}
			else if(hexArr[r].length == 5) {
				x = boardXLoc + xGap/2 + (3*hexWidth)/2;
			}
			else if(hexArr[r].length == 6) {
				x = boardXLoc + xGap/2 + hexWidth;
			}
			else if(hexArr[r].length == 7) {
				x = boardXLoc + xGap/2 + hexWidth/2;
			}
			for(int c = 0; c < hexArr[r].length; c++) {
				if(r == 0 || r == hexArr.length - 1 || c == 0 || c == hexArr[r].length - 1) {
					hexArr[r][c] = new OceanHex(x, y, hexWidth);
				}
				else {
					hexArr[r][c] = new TerrainHex(randomTile(), x, y, hexWidth, randomNumber());
					for(int i = 0; i < hexArr[r][c].getVertices().size(); i++) {
						if(!spaces.contains(hexArr[r][c].getVertices().get(i))) {
							spaces.add(hexArr[r][c].getVertices().get(i));
						}
					}
				}
				x += hexWidth;
			}
			y += (3*hexHeight)/4;
		}
	}

	public void setUpPorts() {
		System.out.println("PORTS");
		TerrainHex.Resource currentPort;
		int randIndex;

		int[][] megaArr = {
				{0,0}, {0,2}, {1,4}, {2,0}, {3,6},
				{4,0}, {5,4}, {6,0}, {6,2}
		};

		for(int i = 0;i < megaArr.length;i++) {
			System.out.println(i);
			randIndex = (int)(Math.random()*availablePorts.size());
			currentPort = availablePorts.get(randIndex);
			availablePorts.remove(randIndex);
			hexArr[megaArr[i][0]][megaArr[i][1]]
					= new OceanHex(
							currentPort,
							hexArr[megaArr[i][0]][megaArr[i][1]].getXLoc(),
							hexArr[megaArr[i][0]][megaArr[i][1]].getYLoc(),
							hexWidth
							);
		}
	}

	public TerrainHex.Resource randomTile() {
		int randIndex = (int)(Math.random()*availableTiles.size());
		TerrainHex.Resource temp = availableTiles.get(randIndex);
		if(temp == TerrainHex.Resource.Desert) {
			isDesert = true;
		}
		availableTiles.remove(randIndex);
		return temp;
	}

	public int randomNumber() {
		System.out.println(availableTiles.size()+". Tiles: "+availableTiles);
		if(isDesert == true) {
			isDesert = false;
			return 0;
		} else {
			int randIndex = (int)(Math.random()*availableNumbers.size());
			int temp = availableNumbers.get(randIndex);
			availableNumbers.remove(randIndex);
			return temp;
		}
	}

	public void draw(Graphics g) {
		System.out.println("Draw");
		g.setColor(new Color(153,255,255));
		g.fillRect(boardXLoc, 0, boardWidth, boardHeight);

		for(int r = 0; r < hexArr.length; r++) {
			for(int c = 0; c < hexArr[r].length; c++) {
				hexArr[r][c].draw(g);
			}
		}
	}

}
