import static javax.swing.JOptionPane.*;

public class SimpleGame extends EasyGraphics {
	
	public static void main(String[] args) {
		launch(args);
	}

	public void run() {
		boolean exit = false;
		
		while (!exit) { //Loop for game
		//Variables
		final int SIZE_WIDTH = 1200;
		final int SIZE_HIGHT = 800;
		int boxMiddleWidth = (SIZE_WIDTH/7)/2;
		int boxMiddleHight = (SIZE_HIGHT/7)/2;
		int[][] board = new int[7][7];
		String spiller = "spiller 1";
		int roundCounter = 0;
		boolean gameRunning = true;
		int startGame = 0;
		int replay = 1;
		
  		makeWindow("GrafikkSpill", SIZE_WIDTH, SIZE_HIGHT);
  		
  		setColor(0, 0, 0);
  		//Introduction
  		setFont("Arial", 50);
  		drawString("How to play:", boxMiddleWidth/3, boxMiddleHight);
  		setFont("Arial", 40);
  		drawString("1. You need 4 in a row to win the game.", boxMiddleWidth/2, boxMiddleHight*2);
  		drawString("2. You can win vertically, horizontally or diagonally.", boxMiddleWidth/2, boxMiddleHight*3);
  		drawString("3. The circle will always fall down in the column.", boxMiddleWidth/2, boxMiddleHight*4);
  		drawString("4. Enjoy!", boxMiddleWidth/2, boxMiddleHight*5);
  		
  		startGame = Integer.parseInt(getText("Do you wanna start the game? Enter '1' to start.")) - 1;
  		setColor(255, 255, 255);
  		fillRectangle(0, 0, SIZE_WIDTH, SIZE_HIGHT);

  		//Draw board
  		setColor(0, 0, 0);
  		for (int i = 0; i < 6; i++) {
  			drawLine(SIZE_WIDTH/7 + i*SIZE_WIDTH/7, 0, SIZE_WIDTH/7 + i*SIZE_WIDTH/7, SIZE_HIGHT);
  		}

  		for (int i = 0; i < 6; i++) {
  			drawLine(0, SIZE_HIGHT/7 + i*SIZE_HIGHT/7, SIZE_WIDTH, SIZE_HIGHT/7 + i*SIZE_HIGHT/7);
  		}
  		
  		//Main event
  		do {
  			int playerInput;
  			//Checks for valid number
  			do {
  				playerInput = Integer.parseInt(getText("Det er " + spiller + " sin tur, velg plassering [Kolonne 1-7]")) - 1;
  			} while(playerInput < 0 || playerInput > 6);
  			//Counts number of rounds
			roundCounter++;
			//Sets correct player
			if(roundCounter % 2 == 0) {
				setColor(255, 0, 0);
				spiller = "spiller 1";
			} else {
				setColor(0,0,255);
				spiller = "spiller 2";
			}
			//Draw circle
			for(int i = 6; i >= 0; i--) {
				if (board[playerInput][i] == 0) {
					if (spiller == "spiller 1") 
						board[playerInput][i] = 1;
					else
						board[playerInput][i] = 2;
					int sid = fillCircle((SIZE_WIDTH/7*(playerInput))+boxMiddleWidth,((SIZE_HIGHT/7*i)+boxMiddleHight) - SIZE_HIGHT, boxMiddleHight);
					setSpeed(5);
					moveCircle(sid,(SIZE_WIDTH/7*(playerInput))+boxMiddleWidth,(SIZE_HIGHT/7*(i))+boxMiddleHight);
					gameRunning = !checkBoard(playerInput, i, board, roundCounter % 2 + 1);
					break;
				}
			}
  		} while (gameRunning);
  		win(roundCounter);
  		replay = Integer.parseInt(showInputDialog("Wanna play again? [0] or [1]"));
		if (replay == 1) {
			resetBoard(SIZE_WIDTH, SIZE_HIGHT);
			exit = false;
		} else
			exit = true;
		}
	}
	
	private static boolean checkBoard(int column, int index, int [][] array, int playerNumber) {
		//Variables
		boolean gameWon = false;
		int winScore = 0;
		int winScore2 = 0;
		int initialXd2 = 0;
		int initialYd2 = column + index;
		int initialXd1 = 0;
		int initialYd1 = Math.abs(index - column);
		int lastIndex = array.length - 1;
		
		//Vertical
		for (int i = 6; i >= 0; i--) {
			if (array[column][i] == playerNumber) {
				winScore++;
			} else {
				winScore = 0;
			}
			if (winScore == 4) {
				gameWon = true;
			}
		}
		
		//Horizontal
		winScore = 0;
		for (int i = 0; i <= 6; i++) {
			if (array[i][index] == playerNumber) {
				winScore++;
			} else {
				winScore = 0;
			}
			if (winScore == 4) {
				gameWon = true;
			}
		}
		
		//Diagonally
		if (initialYd2 > lastIndex) {
			initialXd2 = initialYd2 % lastIndex;
			initialYd2 = lastIndex;
		}
		if (Math.signum(index - column) == -1.0) {
		  initialXd1 = initialYd1;
		  initialYd1 = 0;
		}
		winScore = 0;
		for (int i = 0; i < array.length-initialYd1; i++) {
			if (array[initialXd1 + i][initialYd1 + i] == playerNumber) {
				winScore++;
			}
			if (array[initialXd2 + i][initialYd2 - i] == playerNumber) {
				winScore2++;
			}
			if (winScore == 4 || winScore2 == 4) {
				gameWon = true;
			}
		}
		return (gameWon);
	}
	
	//Display win and correct player message
	private static void win(int round) {
		String spiller = "spiller 1";
		if(round % 2 == 0) {
			spiller = "spiller 2";
		} else {
			spiller = "spiller 1";
		}
		showMessageDialog(null, "Congratulation " + spiller + ", you won the game! The game took: " + round + " rounds");
	}
	//Resets the board
	private void resetBoard(int SIZE_WIDTH, int SIZE_HIGHT) {
		setColor(255, 255, 255);
  		fillRectangle(0, 0, SIZE_WIDTH, SIZE_HIGHT);
	}
}
