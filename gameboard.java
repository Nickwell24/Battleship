package battleship;
import java.util.Scanner;
import java.util.Random;

public class gameboard {
	Random random = new Random();
	Scanner input = new Scanner (System.in);
	
	turns[] TurnArray = new turns[1];
	Ships[] ShipsArray = new Ships[5];
	
	int length;
	int size; 						//Board Size + 2
	int diff;						//Difficulty Level Variable
	
	String SPACE_EMPTY = "-\t";
	String SPACE_HIT = "x\t";
	String SPACE_MISS = "O\t";
	
	public class Ships {
		public String name = "";
		public String icon = "";
		public int spaces = 0;

		public String GetName(){
			return name;
		}
		
		public String GetIcon(){
			return icon;
		}
		
		public int GetSpaces(){
			return spaces;
		}
	}
	public class turns {
		public int turns;
		public int moves;
		public int remaining;
		public int hits;
		public double accuracy;
		public void SetupTurns(){
			TurnArray[0].remaining = turns;
		}
		public int GetTurns(){
			return turns;
		}
		public void EndTurn(){
			moves++;
		}
		public int GetMoves(){
			return moves;
		}
		public int GetRem(){
			remaining = (turns - moves);
			return remaining;
		}
		public void AddHit(){
			hits++;
		}
		public void Accuracy(){
			accuracy = (hits/moves);
		}
		public double GetAccuracy(){
			return accuracy;
		}
	}

	//Difficulty Arrays  (Board Size + 2, Missiles)
	private int[] BEGINNER = {8,30};  //6x6 Board
	private int[] STANDARD = {11,50}; //9x9 Board
	private int[] ADVANCED = {14,75}; //12x12 Board

	//Score board Info
	int BoardSize;
	
	public int SetDifficulty(){
		
		//Getting Player input for Game Type.
		do {
		System.out.println("Please select your difficulty level. \n\n   Level:\tGrid:\tMissles:");
		System.out.println("1. Beginner\t6x6\t30");
		System.out.println("2. Standard\t9x9\t50");
		System.out.println("3. Advanced\t12x12\t75\n");
		System.out.print("Enter 1 for Beginner, 2 for Standard, 3 for Advanced: ");
		diff = input.nextInt();
		System.out.println();
		} while (diff != 1 && diff != 2 && diff != 3);
		
		//Array to hold turn details
		TurnArray[0] = new turns();
		
		//Setting Variable Size to Difficulty level
		if (diff == 1){
			System.out.println("Playing as a beginner? Are you scared?\n");	
			size = BEGINNER[0];
			TurnArray[0].turns = BEGINNER[1];
			}
		if (diff == 2)
		{	System.out.println("A Standard game? Boring... but okay.\n");
			size = STANDARD[0];
			TurnArray[0].turns = STANDARD[1];
			}
		if (diff == 3){
			System.out.println("Finally, a real competitor. LETS GO!\n");
			size = ADVANCED[0];
			TurnArray[0].turns = ADVANCED[1];
			}

		return size;
		}
	
	// Fills board with BLANK_EMPTY
	public String PopulateBoard(String[][] BOARD, int length){
		for (int row = 0; row < length; row++)
		{
			for (int col = 0; col < length; col++)
				BOARD[row][col] = SPACE_EMPTY;
		}
		
		//Setting Labels for Top and Side Row
		for (int row = 1; row < length; row++)
			BOARD[row][0] = (String.valueOf(row) + "\t");
		
		//Clearing Top-Left space.
		for (int col = 1; col < length; col++)
			BOARD[0][col] = (String.valueOf(col) + "\t");
		BOARD[0][0] = " \t";
		
		return BOARD[length][length];
	}
	
	// Fills X and Y Axis with appropriate labels
	public void PrintBoard(String BOARD[][], int length){
		System.out.println("\t\tBATTLESHIP:\n"
				+ "\tTotal Turns:  \t\t" + TurnArray[0].GetTurns() + "\n"
				+ "\tRemaining Turns: \t" + TurnArray[0].GetRem() + "\n"
				+ "\tAccuracy:  \t\t" + TurnArray[0].GetAccuracy() + "%\n");
		for (int row = 0; row < length; row++)
		{
			for (int col = 0; col < length; col++)
			{
				System.out.print(BOARD[row][col]);
			}
			System.out.println();
		}
		System.out.println();
	}
	
	// Creates a second board to hold ship placement.
	public String[][] Ship_Board(int length){
		String[][] Ship_Board = new String[size][size];
		length = size -1 ;
		Ship_Board[length][length] = PopulateBoard(Ship_Board, length);
		return Ship_Board;
	}
	
	//Fills Ship Arrays with ship details.
	public void MakeShips(){

		ShipsArray[0] = new Ships();
		ShipsArray[1] = new Ships();
		ShipsArray[2] = new Ships();
		ShipsArray[3] = new Ships();
		ShipsArray[4] = new Ships();
		
		ShipsArray[0].name = "Carrier";
		ShipsArray[1].name = "Battleship";
		ShipsArray[2].name = "Destroyer";
		ShipsArray[3].name = "Submarine";
		ShipsArray[4].name = "Patrol";
		
		ShipsArray[0].spaces = 5;
		ShipsArray[1].spaces = 4;
		ShipsArray[2].spaces = 4;
		ShipsArray[3].spaces = 3;
		ShipsArray[4].spaces = 2;
		
		ShipsArray[0].icon = "C";
		ShipsArray[1].icon = "B";
		ShipsArray[2].icon = "D";
		ShipsArray[3].icon = "S";
		ShipsArray[4].icon = "P";
		}
	
	//Randomly Placing Ships
	public void PlaceShips(String[][] ShipBoard, int length){
		int row = 0;
		int col = 0;
		int LastRow = row;
		int LastCol = col;
		int Direction;
		int Pass = 0;

				for (int ship = 0; ship <5; ship++)
				{
					do {		
						//Determine if First and Last Cell are valid in the array.
						do {	Direction = 0;					
								Pass = 0;
								row = random.nextInt(length);
								col = random.nextInt(length);					
								
								LastRow = row;
								LastCol = col;
								//Direction Modifiers
								// 0 = UP, 1 = RIGHT, 2 = DOWN, 3 = LEEFT
								Direction = random.nextInt(4);
				
								
								//Set Last Space Based on Direction
								if (Direction == 0)
									LastRow -= ShipsArray[ship].spaces;
								if (Direction == 1)
									LastCol += ShipsArray[ship].spaces;
								if (Direction == 2)
									LastRow += ShipsArray[ship].spaces;
								if (Direction == 3)
									LastCol -=ShipsArray[ship].spaces;
								
								} while ((OutOfBounds(row, length) == true) || (OutOfBounds(col, length) == true) || 
										(OutOfBounds(LastRow, length) == true) || (OutOfBounds(LastCol, length) == true));
							
							if (row > LastRow)		//Assigning the smaller value to row/col.
							{
								int a = row;
								row = LastRow;
								LastRow = a;
							}
							
							if (col > LastCol)
							{
								int b = col;
								col = LastCol;
								LastCol = b;
							}
							
							if (Direction == 0 || Direction == 2)
								{
								Direction = 0;
								if (CheckIfVertArrayEmpty(ShipBoard,row,col,ShipsArray[ship].spaces) == true)
									Pass = 1;
								}
							
							if (Direction == 1 || Direction == 3)
								{	
								Direction = 1;
								if (CheckIfSideArrayEmpty(ShipBoard,row,col,ShipsArray[ship].spaces) == true)
									Pass = 1;
								}		
							
						} while (Pass == 0);
					
					//Add Ship to Ship_Board
					if (Direction == 0)
						AddShipToBoard(ShipBoard,row,col,ship);
					if (Direction == 1)
						AddShipToBoardSide(ShipBoard,row,col,ship);
					}
			}
	//Determine is space is empty	
	public boolean CheckEmpty(String[][] ShipBoard, int row, int col){
		if (ShipBoard[row][col] == SPACE_EMPTY)
			return true;
		else
			return false;
	}
	
	//Cycles through array to see if any spaces are occupied.
	public boolean CheckIfVertArrayEmpty(String[][] ShipBoard, int row, int col, int length){
		int check = 0;	
		for (int i = 0; i < length; i++ )
			if ((CheckEmpty(ShipBoard, row, col) == true))
				{row++;
				check = i + 1;
				}
			else
				break;
		if (check == length)
			return true;
		else	
			return false;
		}
	public boolean CheckIfSideArrayEmpty(String[][] ShipBoard, int row, int col, int length){
		int check = 0;
		for (int i = 0; i < length; i++ )
			if ((CheckEmpty(ShipBoard, row, col) == true))
				{col++;
				check = i + 1;
				}
			else
				break;

		if (check == length)
			return true;
		else
			return false;
		}
	
	//Adds ship to Ship_Board
	public void AddShipToBoard(String[][] ShipBoard, int row, int col, int ship)
	{
		
		for (int i = 1; i <= ShipsArray[ship].spaces; i++)
		{
			ShipBoard[row][col] = ShipsArray[ship].icon + "\t";
			row++;
		}
	}
	public void AddShipToBoardSide(String[][] ShipBoard, int row, int col, int ship)
	{
		for (int i = 1; i <= ShipsArray[ship].spaces; i++)
		{
			ShipBoard[row][col] = (ShipsArray[ship].icon + "\t");
			col++;
		}
	}
	
	//Checks if first/last ship point is off the Array.
	public boolean OutOfBounds(int a, int length) {
		length -= 1;
		if (a > length || a <= 0)

			return true;
		else
			return false;
	}
	
}
