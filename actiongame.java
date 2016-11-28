package battleship;
import java.util.Scanner;

public class actiongame {

	gameboard gameboard = new gameboard();
	
	Scanner input = new Scanner(System.in);
	int size;
	int length;
	
	//Initialized game (Creates ships, PLayer Board, and Sets Turns).
	public String[][] StartGame(){
		gameboard.MakeShips();
		size = gameboard.SetDifficulty();
		String[][] Player_Board = new String[size][size];
		length = (size - 1);
		Player_Board[length][length] = gameboard.PopulateBoard(Player_Board, length);
		gameboard.TurnArray[0].SetupTurns();
		return Player_Board;
	}
	
	//Create Board containing answer key.
	public String[][] Ship_Board(){
		String[][] Ship_Board = gameboard.Ship_Board(length);
		gameboard.PlaceShips(Ship_Board,length);
		return Ship_Board;
	}

	//TROUBLESHOOTING ONLY!  Not in final code.
	public void print(String Player_Board[][]){
		gameboard.PrintBoard(Player_Board, length);
	}
	
	//Execute game. End once turns = 0 (Work in Progress).
	public void TakeTurn(String Player_Board[][], String Ship_Board[][]){
		for (int a = 0; a < gameboard.TurnArray[0].remaining; gameboard.TurnArray[0].EndTurn())
		{
			gameboard.PrintBoard(Player_Board, length);
			int row = MoveRow();
			int col = MoveCol();
			UpdateBoard(row,col,Player_Board,Ship_Board);
		}
	}
	
	//Get User's move.
	public int MoveRow(){
		System.out.print("Make your move:\n Row: ");
		int row = input.nextInt();
		return row;
	}	
	public int MoveCol(){
		System.out.print(" Col: ");
		int row = input.nextInt();
		return row;
	}
	
	//Update Player_Board (Still WIP).
	public String[][] UpdateBoard(int row, int col, String[][] Player_Board, String[][] Ship_Board){
		if (Ship_Board[row][col] == gameboard.SPACE_EMPTY)
		{
			Player_Board[row][col] = gameboard.SPACE_MISS;
			System.out.println("Wow, (INSULT)");		
		}
		if (Ship_Board[row][col] == gameboard.ShipsArray[0].icon)
		{
			System.out.print("You hit a ship!");
			Player_Board[row][col] = Ship_Board[row][col];
		}
	return Player_Board;		
	}
}
