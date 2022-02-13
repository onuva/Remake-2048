import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import processing.core.PApplet;

public class DrawingSurface extends PApplet {

	private Board board;

	
	
	public DrawingSurface() {
		// Consider using the file reading code for testing purposes. Sometimes, it's nice to run the program and have some grid contents already present that you can
		// mess with to see if things are working. 
		board = new Board();    
		
	}
	
	// The statements in the setup() function 
	// execute once when the program begins
	public void setup() {
		

	}
	
	// The statements in draw() are executed until the 
	// program is stopped. Each statement is executed in 
	// sequence and after the last line is read, the first 
	// line is executed again.
	public void draw() { 
		background(255);   // Clear the screen with a white background
		fill(0);
		textAlign(LEFT);
		textSize(12);
		
		//text("Give basic instructions: ", height+20, 30);
		try {
		      File myObj = new File("src/highScoreSave.txt");
		      Scanner myReader = new Scanner(myObj);
		        String data = myReader.nextLine();
		        int n = Integer.parseInt(data);
		        if (n > board.hS()) board.hS(n);
		      myReader.close();
		      if (board.hS() > n) {
		    	  data = data.replaceAll(data, Integer.toString(board.hS()));
		    	  try {
				  FileWriter writer = new FileWriter(myObj);
				  writer.write(data);
				  writer.close();
		    	  } 
		    	  catch (IOException e) {e.printStackTrace();}
		      }
		    } catch (FileNotFoundException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
		
		board.draw(this);

	}
	
	// In general, it's better to use mouse interactions for interacting with the grid (like placing a piece).
	// Key interactions can be used if it really makes sense, such as for uncommon tasks (like resetting the game).
	// If you'd like to do basic gameplay interaction via keyboard, consider asking Mr Shelby about it.
	public void keyPressed() {
		
		if (keyCode == KeyEvent.VK_DOWN) {
			board.moveDown();
		} 
		else if (keyCode == KeyEvent.VK_UP) {
			board.moveUp();
		}
		else if (keyCode == KeyEvent.VK_RIGHT) {
			board.moveRight();
		}
		else if (keyCode == KeyEvent.VK_LEFT) {
			board.moveLeft();
		}
	}
	
	public void mousePressed() {
		if (mouseX <= (width - width/4.2f) + width/4.5f && mouseX >= width-width/4.2f && mouseY <= height/6.5f + height/12.5f && mouseY >= height/6.5f) board.reset();
		if (board.playAgain() && mouseX <= width/2 + width/4.3f && mouseX >= width/2 && mouseY <= 2*height/3 + height/15 && mouseY >= 2*height/3) board.reset();
	}

	
}










