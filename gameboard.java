package battleship;
import java.util.Scanner;
import java.util.Random;

public class gameboard {
	Random random = new Random();
	Scanner input = new Scanner (System.in);

	turns[] TurnArray = new turns[1];
	Ships[] ShipsArray = new Ships[5];
	String[] Board_Letters = { "","A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L"};
	String[] Board_Numbers = { "","1", "2", "3", "4", "5", "6", "7", "8","9" , "10", "11", "12"};
	
	int size;
	static int length;	
	String Difficulty = "";				//Difficulty Level Variable
	int BoardSize;						//Score board Info

	//Difficulty Arrays  (Board Size + 2, Missiles)
	private int[] BEGINNER = {8,30};  //6x6 Board
	private int[] STANDARD = {11,50}; //9x9 Board
	private int[] ADVANCED = {14,75}; //12x12 Board
	
	String SPACE_EMPTY = "-\t";
	String SPACE_MISS = "o\t";
	
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
		ShipsArray[4].name = "Patrol Ship";
		
		ShipsArray[0].spaces = 5;
		ShipsArray[1].spaces = 4;
		ShipsArray[2].spaces = 4;
		ShipsArray[3].spaces = 3;
		ShipsArray[4].spaces = 2;
		
		ShipsArray[0].icon = "C\t";
		ShipsArray[1].icon = "B\t";
		ShipsArray[2].icon = "D\t";
		ShipsArray[3].icon = "S\t";
		ShipsArray[4].icon = "P\t";
		
		//Each hit will be status++.  Once status = 0 ship = sunk.
		ShipsArray[0].status = -5;
		ShipsArray[1].status = -4;
		ShipsArray[2].status = -4;
		ShipsArray[3].status = -3;
		ShipsArray[4].status = -2;
		}

	public int SetDifficulty(){
		
		//Getting Player input for Game Type.
		do {
		System.out.println("Please select your difficulty level. \n\n   Level:\tGrid:\tMissles:");
		System.out.println("1. Beginner\t6x6\t30");
		System.out.println("2. Standard\t9x9\t50");
		System.out.println("3. Advanced\t12x12\t75\n");
		System.out.print("Enter 1 for Beginner, 2 for Standard, 3 for Advanced: ");
		Difficulty = input.nextLine();
		System.out.println();

		} while ((!Difficulty.equals("1")) && (!Difficulty.equals("2")) && (!Difficulty.equals("3")));
		
		//Array to hold turn details
		TurnArray[0] = new turns();
		
		//Setting Variable Size to Difficulty level
		if (Difficulty.equals("1")){
			System.out.println("Playing as a beginner? Are you scared?\n");	
			size = BEGINNER[0];
			TurnArray[0].turns = BEGINNER[1];
			}
		if (Difficulty.equals("2")){
			System.out.println("A Standard game? Boring... but okay.\n");
			size = STANDARD[0];
			TurnArray[0].turns = STANDARD[1];
			}
		if (Difficulty.equals("3")){
			System.out.println("Finally, a real competitor. LETS GO!\n");
			size = ADVANCED[0];
			TurnArray[0].turns = ADVANCED[1];
			}

		return size;
		}
	
	// Fills board with BLANK_EMPTY
	public String PopulateBoard(String[][] BOARD){
		for (int row = 0; row < length; row++)
		{
			for (int col = 0; col < length; col++)
				BOARD[row][col] = SPACE_EMPTY;
		}
		
		//Setting Labels for Top and Side Row
		for (int row = 1; row < length; row++)
			BOARD[row][0] = (Board_Letters[row] + "\t");
		
		//Clearing Top-Left space.
		for (int col = 1; col < length; col++)
			BOARD[0][col] = (String.valueOf(col) + "\t");
		BOARD[0][0] = " \t";
		
		return BOARD[length][length];
	}
	
	// Creates a second board to hold ship placement.
	public String[][] Create_Ship_Board(){
		String[][] Ship_Board = new String[size][size];
		Ship_Board[length][length] = PopulateBoard(Ship_Board);
		return Ship_Board;
	}

	// Fills X and Y Axis with appropriate labels.
	public void PrintBoard(String BOARD[][]){
		System.out.print("\n\n\n\t\tBATTLESHIP:\n"
				+ "\tTotal Turns:  \t\t" + TurnArray[0].GetTurns() + "\n"
				+ "\tRemaining Turns: \t" + TurnArray[0].GetRem() + "\n"
				+ "\tShips Remaining: \t" + TurnArray[0].ships_remaining + "\n"
				+ "\tAccuracy: \t\t");
		System.out.printf("%2.2f%%", TurnArray[0].GetAcc());
		System.out.println("\n");
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
	//Randomly Placing Ships
	public void PlaceShips(String[][] ShipBoard){
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
								
								//Direction Modifier
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
								
								} while ((OutOfBounds(row) == true) || (OutOfBounds(col) == true) || 
										(OutOfBounds(LastRow) == true) || (OutOfBounds(LastCol) == true));
							
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
							
							//Checking if any part of the ship will be position in an occupied space.
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
	public boolean CheckIfVertArrayEmpty(String[][] ShipBoard, int row, int col, int Ship_Length){
		int check = 0;	
		for (int i = 0; i < Ship_Length; i++ )
			if ((CheckEmpty(ShipBoard, row, col) == true))
				{row++;
				check = i + 1;
				}
			else
				break;
		if (check == Ship_Length)
			return true;
		else	
			return false;
		}
	public boolean CheckIfSideArrayEmpty(String[][] ShipBoard, int row, int col, int Ship_Length){
		int check = 0;
		for (int i = 0; i < Ship_Length; i++ )
			if ((CheckEmpty(ShipBoard, row, col) == true))
				{col++;
				check = i + 1;
				}
			else
				break;

		if (check == Ship_Length)
			return true;
		else
			return false;
		}
	
	//Adds ship to Ship_Board
	public void AddShipToBoard(String[][] ShipBoard, int row, int col, int ship)
	{
		
		for (int i = 1; i <= ShipsArray[ship].spaces; i++)
		{
			ShipBoard[row][col] = ShipsArray[ship].icon;
			row++;
		}
	}
	public void AddShipToBoardSide(String[][] ShipBoard, int row, int col, int ship)
	{
		for (int i = 1; i <= ShipsArray[ship].spaces; i++)
		{
			ShipBoard[row][col] = (ShipsArray[ship].icon);
			col++;
		}
	}
	
	//Checks if first/last ship point is off the Array.
	public boolean OutOfBounds(int a) {
		if (a > (length - 1) || a <= 0)
			return true;
		else
			return false;
	}
	

public class Ships {
		public String name = "";	//Ship Type
		public String icon = "";	//Represents ship type on the board. 
		public int spaces = 0;		//length of the ship
		public int status = 0;  	// 0 = previously sunk, 1 = Remaining, 2 = sunk this turn.
		

		public String GetName(){
			return name;
		}
		
		public String GetIcon(){
			return icon;
		}
		
		public int GetSpaces(){
			return spaces;
		}
		public int GetStatus(){
			return status;
		}
	}
public class turns {
	public int turns;						//Total Turns
	public int moves = 0;					//Moves Taken
	public int turns_remaining = turns;
	public int ships_remaining = 5;
	public double hits = 0;					//# of hits
	public double accuracy;					//Accuracy Percentage
	
	public void SetupTurns(){
		turns_remaining = turns;
	}
	public int GetTurns(){
		return turns;
	}
	public void EndTurn(){
		moves++;
		turns_remaining--;
	}
	public int GetRem(){
		return turns_remaining;
	}
	public double GetAcc(){
		if (moves == 0)
			accuracy = 0;
		else
		accuracy = ((hits/moves)*100);
		return accuracy;
	}


	}
}