
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PFont;

/**

	Represents a Game Of Life grid.

	Coded by: Onuva Ekram
	Modified on: 11/16

*/

public class Board {

	// Add a 2D array field to represent the Game Of Life grid.
	private int[][] grid;
	private final int DEFAULTNUM = 4;
	private boolean poss = false, freeze = false, win = false;
	private int pad = 45, goCount = 0, score = 0, hs = 0;
	private float num = 0;
	PFont f, fSmol;
	//2048 or pacmaze
	
	
	/**
	 * Initialized the Game of Life grid to an empty grid.
	 */
	public Board() {
		grid = new int[DEFAULTNUM][DEFAULTNUM];
		step();
		step();
		freeze = false;
	}

	
	/**
	 * Initializes the Game of Life grid to a grid with given dimensions.
	 * 
	 * @param x The number of columns visible in the grid.
	 * @param y The number of rows visible in the grid.
	 */
	public Board(int x, int y) {
		grid = new int[y][x];
		step();
		step();
		freeze = false;
	}
	
	
	/**
	 * Finds if given values can be a cell located in bounds of the grid. 
	 * @param i The x coordinate of the cell in the grid.
	 * @param j The y coordinate of the cell in the grid.
	 * @return If cell grid[i][j] is in bounds of grid.
	 */
	public boolean inBounds(int i, int j) {
		if (i >= 0 && i<grid.length && j >= 0 && j < grid[i].length) return true;
		return false;
	}
	
	
	/**
	 * Formats this Life grid as a String to be printed (one call to this method returns the whole multi-line grid)
	 * 
	 * @return The grid formatted as a String.
	 */
	public String toString() {
		String s = "";
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				if (grid[i][j]==0) s += "*";
				else s += " ";
			}
			s += "\n";
		}
		return s;
	}
	
	/**
	 * Runs a single step within the Game of Life by applying the Game of Life rules on
	 * the grid.
	 */
	public void step() {
		int x = grid[0].length;
		int y = grid.length;
		int i = y, j = x;
		i *= Math.random();
		j *= Math.random();
		while (grid[i][j] != 0) {
			i = (int) (Math.random() * y);
			j = (int) (Math.random() * x);
		}
		if (Math.random() < 0.75) grid[i][j] = 2;
		else grid[i][j] = 4;
			
	}
	
	
	/**
	 * (Graphical UI)
	 * Draws the grid on a PApplet.
	 * The specific way the grid is depicted is up to the coder.
	 * 
	 * @param marker The PApplet used for drawing.
	 * @param x The x pixel coordinate of the upper left corner of the grid drawing. 
	 * @param y The y pixel coordinate of the upper left corner of the grid drawing.
	 * @param width The pixel width of the grid drawing.
	 * @param height The pixel height of the grid drawing.
	 * @post The text has been aligned to the mode CENTER.
	 */
	public void draw(PApplet marker) {
		marker.background(255, 250, 240);
		pad = marker.width/45;
		float w = marker.width, h = marker.height;
		float wb = (float)(w - (pad * (grid[0].length+2)))/grid[0].length;
		float hb = (float)(h/1.6f - (pad * (grid.length+1)))/grid.length;
		float backX = pad/2,  backY = h/4;
		int size = (int) (Math.pow(hb * wb, 0.45)), x = pad, y = (int) (backY + pad);
		f = marker.createFont("src/ClearSans-Bold.ttf", size);
		fSmol = marker.createFont("src/ClearSans-Medium.ttf", size/4f);
		
		marker.textFont(f);
		marker.fill(110, 102, 90);
		marker.textSize(size * 1.5f);
		marker.text("2048", 3, size * 1.3f); //Title
		
		marker.textSize(size/3.5f);
		marker.text("Play 2048 Game Online", w/35, h/5.5f); //Subtitle
		marker.textFont(fSmol);
		marker.text("Join the numbers and get to the ", w/35, h/4.75f); //Intro1
		marker.text("Use your ", 7.5f * (size/4), h - h/13);//Instruc2
		marker.text("to move the tiles.", 7.5f * (size/4) + 3.75f * (size/3.5f) + 5.2f * (size/4), h - h/13);//Instruc4
		marker.text("When two tiles with the same number touch, they ", w/35, h - h/21);//Instruc5
		marker.textFont(f);
		marker.textSize(size/3.5f);
		marker.text("2048 tile!", 13.32f * (size/3.5f), h/4.75f); //Intro2
		marker.textSize(size/4);
		marker.text("HOW TO PLAY: ", w/35, h - h/13); //Instruc1
		marker.text("arrow keys ", 7.4f * (size/4) + 3.75f * (size/3.5f), h - h/13); //Instruc3
		marker.text("merge into one!", w/35, h - h/45);//Instruc6
		
		
		marker.textSize(size);
		marker.textAlign(PConstants.CENTER);
		
		marker.fill(160, 145, 130);
		marker.noStroke();
		marker.rect(backX, backY, w - pad, h/1.6f, 7); //Background Box
		
		marker.rect(w - w/2.25f, h/20, w/5, h/12, 3); //Score Box
		marker.rect(w - w/4.5f, h/20, w/5, h/12, 3); //High Score Box
		marker.fill(130, 115, 100);
		marker.rect(w - w/4.2f, h/6.5f, w/4.5f, h/12.5f, 3); //New Game Box
		marker.fill(205, 195, 185);
		marker.textSize(size/3.5f);
		marker.text("SCORE", w - w/2.25f + w/10, h/13);
		marker.textSize(size/4.5f);
		marker.text("HIGH SCORE", w - w/4.5f + w/10, h/13);
		marker.fill(255);
		marker.textSize(size/3.4f);
		marker.text("NEW GAME", w - w/4.2f + w/9, h/4.9f);
		marker.textSize(size/2);
		marker.text(score, w - w/2.25f + w/10, h/8.5f); //Score Number Display
		marker.text(hs, w - w/4.5f + w/10, h/8.5f); //High Score Number Display
		
		
		marker.textFont(f);
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				if (grid[i][j] == 2) {
					marker.fill(238, 228, 218);
					marker.textSize(size);
				}
				else if (grid[i][j] == 4) {
					marker.fill(237, 224, 200);
					marker.textSize(size);
				}
				else if (grid[i][j] == 8) {
					marker.fill(242, 177, 121);
					marker.textSize(size);
				}
				else if (grid[i][j] == 16) {
					marker.fill(245, 149, 99);
					marker.textSize(size);
				}
				else if (grid[i][j] == 32) {
					marker.fill(246, 124, 95);
					marker.textSize(size);
				}
				else if (grid[i][j] == 64) {
					marker.fill(246, 94, 59);
					marker.textSize(size);
				}
				else if (grid[i][j] == 128) {
					marker.fill(237, 207, 114);
					marker.textSize(size/1.2f);
				}
				else if (grid[i][j] == 256) {
					marker.fill(230, 185, 25, 50);
					marker.rect((backX + x + j * wb) - wb * 0.05f, (y + i * hb) - hb * 0.05f, wb * 1.09f, hb * 1.09f, 3);
					marker.fill(237, 204, 97);
					marker.textSize(size/1.2f);
				}
				else if (grid[i][j] == 512) {
					marker.fill(230, 185, 25, 65);
					marker.rect((backX + x + j * wb) - wb * 0.062f, (y + i * hb) - hb * 0.07f, wb * 1.1f, hb * 1.1f, 3);
					marker.fill(237, 200, 80);
					marker.textSize(size/1.2f);
				}
				else if (grid[i][j] == 1024) {
					marker.fill(230, 185, 25, 80);
					marker.rect((backX + x + j * wb) - wb * 0.062f, (y + i * hb) - hb * 0.07f, wb * 1.1f, hb * 1.1f, 3);
					marker.fill(237, 197, 63);
					marker.textSize(size/1.5f);
				}
				else if (grid[i][j] == 2048){
					marker.fill(230, 185, 25, 95);
					marker.rect((backX + x + j * wb) - wb * 0.1f, (y + i * hb) - hb * 0.1f, wb * 1.2f, hb * 1.2f, 3);
					marker.fill(205, 195, 185);
					marker.textSize(size/1.5f); 
					win = true;
				}
				else {
					marker.fill(205, 195, 185); 
				}
				marker.rect(backX + x + j * wb, y + i * hb, wb, hb, 3);
				if (grid[i][j] != 0) {
					if (grid[i][j] <= 4) marker.fill(95, 90, 85);
					else marker.fill(255, 250, 245);
					marker.text(grid[i][j], backX + x + j * wb + wb/2, y + i * hb + hb/1.45f);
				}
				x += pad;
			}
			x = pad;
			y += pad;
		}
		if ((filled() && gameOver()) || (win)) {
			if (freeze == false) {
				freeze = true;
				num = 0;
				marker.delay(240);
			}
		}
		
		if (freeze && !win) {
				if (num < 150) num += 1.5;
				marker.fill(255, 255, 255, num);
				marker.rect(backX, backY, w - pad, h/1.6f, 10);
				marker.fill(110, 102, 90);
				marker.textSize((int)(size*1.25));
				if (num >= 150) {
					marker.text("Game Over!", w/2, h/2);
					marker.fill(130, 115, 100);
					marker.rectMode(PConstants.CENTER);
					marker.rect(w/2, 2*h/3, w/4.3f, h/15, 3); //Try Again Box
					marker.rectMode(PConstants.CORNER);
					marker.fill(255);
					marker.textFont(fSmol);
					marker.textSize(size/2.9f);
					marker.text("Try again", w/2, h * 0.675f);
				}
		}
		else if (win) {
			if (num < 100) num += 0.75;
			marker.fill(250, 203, 35, num);
			marker.rect(backX, backY, w - pad, h/1.6f, 10);
			marker.fill(255);
			marker.textSize((int)(size*1.25));
			if (num >= 100) {
				marker.text("You Win!", w/2, h/2);
				marker.fill(130, 115, 100);
				marker.rectMode(PConstants.CENTER);
				marker.rect(w/2, 2*h/3, w/4.3f, h/15, 3); //Try Again Box
				marker.rectMode(PConstants.CORNER);
				marker.fill(255);
				marker.textFont(fSmol);
				marker.textSize(size/2.9f);
				marker.text("Play again", w/2, h * 0.675f);
			}
		}
	}

	private boolean filled() {
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				if (grid[i][j] == 0) return false;
			}
		}
		return true;
	}
	public void moveLeft() {
		if (!freeze) {
			//System.out.println("left");
			int temp = 0;
			for (int i = 0; i < grid.length; i++) {
				for (int j = 0; j < grid[0].length-1; j++) {
					temp = j;
					while (inBounds(i, j) && grid[i][j] == 0) {
						grid[i][j] = grid[i][j+1];
						grid[i][j+1] = 0;
						poss = true;
						j--;
					}
					j = temp;	
				}
				
				for (int j = 0; j < grid[0].length-1; j++) {
					if (grid[i][j] == grid[i][j+1]) {
						grid[i][j] *= 2;
						score += grid[i][j];
						if(score > hs) hs = score;
						grid[i][j+1] = 0;
						poss = true;
						temp = j;
						j++;
						while (inBounds(i, j+1) && grid[i][j+1] != 0) {
							grid[i][j] = grid[i][j+1];
							grid[i][j+1] = 0;
							j++;
						}
						j = temp;	
					}
				}
			}
			if (poss) {
				step();
				poss = false;
			}
			else goCount++;
		}
	}
	
	public void moveRight() {
		if (!freeze) {
			//System.out.println("right");
			int temp = 0;
			for (int i = 0; i < grid.length; i++) {
				for (int j = grid[0].length-1; j > 0; j--) {
					temp = j;
					while (inBounds(i, j) && grid[i][j] == 0) {
						grid[i][j] = grid[i][j-1];
						grid[i][j-1] = 0;
						poss = true;
						j++;
					}
					j = temp;	
				}
				
				for (int j = grid[0].length-1; j > 0; j--) {
					if (grid[i][j] == grid[i][j-1]) {
						grid[i][j] *= 2;
						score += grid[i][j];
						if(score > hs) hs = score;
						grid[i][j-1] = 0;
						poss = true;
						temp = j;
						j--;
						while (inBounds(i, j-1) && grid[i][j-1] != 0) {
							grid[i][j] = grid[i][j-1];
							grid[i][j-1] = 0;
							j--;
						}
						j = temp;	
					}
				}
			}
			if (poss) {
				step();
				poss = false;
			}
			else goCount++;
		}
	}
	
	public void moveDown() {
		if (!freeze) {
			//System.out.println("down");
			int temp = 0;
			for (int j = 0; j < grid.length; j++) {
				for (int i = grid.length-1; i > 0; i--) {
					temp = i;
					while (inBounds(i, j) && grid[i][j] == 0) {
						grid[i][j] = grid[i-1][j];
						grid[i-1][j] = 0;
						poss = true;
						i++;
					}
					i = temp;	
				}
				
				for (int i = grid.length-1; i > 0; i--) {
					if (grid[i][j] == grid[i-1][j]) {
						grid[i][j] *= 2;
						score += grid[i][j];

						if(score > hs) hs = score;
						grid[i-1][j] = 0;
						poss = true;
						temp = i;
						i--;
						while (inBounds(i-1, j) && grid[i-1][j] != 0) {
							grid[i][j] = grid[i-1][j];
							grid[i-1][j] = 0;
							i--;
						}
						i = temp;	
					}
				}
			}
			if (poss) {
				step();
				poss = false;
			}
			else goCount++;
		}
	}
	
	public void moveUp() {
		if (!freeze) {
			//System.out.println("up");
			int temp = 0;
			for (int j = 0; j < grid.length; j++) {
				for (int i = 0; i < grid.length - 1; i++) {
					temp = i;
					while (inBounds(i, j) && grid[i][j] == 0) {
						grid[i][j] = grid[i+1][j];
						grid[i+1][j] = 0;
						poss = true;
						i--;
					}
					i = temp;	
				}
				
				for (int i = 0; i < grid.length - 1; i++) {
					if (grid[i][j] == grid[i+1][j]) {
						grid[i][j] *= 2;
						score += grid[i][j];
						if(score > hs) hs = score;
						grid[i+1][j] = 0;
						poss = true;
						temp = i;
						i++;
						while (inBounds(i+1, j) && grid[i+1][j] != 0) {
							grid[i][j] = grid[i+1][j];
							grid[i+1][j] = 0;
							i++;
						}
						i = temp;	
					}
				}
			}
			if (poss) {
				step();
				poss = false;
			}
			else goCount++;
		}
	}
	
	private boolean gameOver() {
		boolean go = false;
		goCount = 0;
		int temp = score;
		
		int[][] test = new int[grid.length][grid[0].length];
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				test[i][j] = grid[i][j];
			}
		}
		moveUp();
		moveDown();
		moveLeft();
		moveRight();
		if (goCount == 4) go = true;
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				grid[i][j] = test[i][j];
			}
		}
		score = temp;
		return go;
	}
	
	public void reset() {
		score = 0;
		freeze = false;
		win = false;
		grid = new int[DEFAULTNUM][DEFAULTNUM];
		step();
		step();
	}
	
	public boolean playAgain () {
		if (freeze) return true;
		return false;
	}
	
	
	public void hS(int hs) {
		this.hs = hs;
	}
	
	public int hS() {
		return hs;
	}


	
	
}