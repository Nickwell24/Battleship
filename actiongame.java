package battleship;
import java.util.Scanner;

public class actiongame extends gameboard {

	Scanner input = new Scanner(System.in);

	//Initialized game (Creates ships, PLayer Board, and Sets Turns).
	public String[][] StartGame(){
		MakeShips();
		size = SetDifficulty();
		String[][] Player_Board = new String[size][size];
		length = (size -1);		
		Player_Board[length][length] = PopulateBoard(Player_Board);
		TurnArray[0].SetupTurns();
		return Player_Board;
	}
	
	//Create Board containing answer key.
	public String[][] Ship_Board(){
		String[][] Ship_Board = Create_Ship_Board();
		PlaceShips(Ship_Board);
		return Ship_Board;
	}

	//Execute game. End once turns = 0
	public void PlayGame(String Player_Board[][], String Ship_Board[][]){
		for (int a = 0; a < TurnArray[0].turns_remaining; TurnArray[0].EndTurn())
		{
			PrintBoard(Player_Board);
			int row = MoveRow();
			int col = MoveCol();
			UpdateBoard(row,col,Player_Board,Ship_Board);
			if (Winner() == true)
				break;
			if (Loser() == true)
				break;
			}
	}
	
	//Get User's move.
	public int MoveRow(){

		int row = 0;
		do {
		System.out.print("Make your move:\n Letter: ");
		String letter = input.next();
		letter = letter.toUpperCase();
		for (int i = 1; i <= 12; i++)
		if (Board_Letters[i].equals(letter))
			row = i;
			
		} while (row <= 0 || row >= length);
		return row;
	}	
	public int MoveCol(){
		int col = 0;
		do {
		System.out.print("Make your move:\n Number: ");
		String number = input.next();
		for (int i = 1; i <= 12; i++)
		if (Board_Numbers[i].equals(number))
			col = i;
			
		} while (col <= 0 || col >= length);
		return col;
	}
	
	//Update Player_Board.
	public String[][] UpdateBoard(int row, int col, String[][] Player_Board, String[][] Ship_Board){
		
		
		// Previously hit space
		if (Player_Board[row][col] != SPACE_EMPTY)
				System.out.println("\nAre you so cocky you think you can waste your shots? You already tried here!");	
		
		// Miss
		if (Ship_Board[row][col] == SPACE_EMPTY && Player_Board[row][col] == SPACE_EMPTY)
			{
			Player_Board[row][col] = SPACE_MISS;
			System.out.println("\nWhat an awful miss, I've seen blind kids aim better than you!");
			}

		for (int i = 0; i < 5; i++)
			
			// Hit
		if (Ship_Board[row][col] == ShipsArray[i].icon && Player_Board[row][col] != ShipsArray[i].icon)
		{
			ShipsArray[i].status += 1;
			TurnArray[0].hits++;
			if (ShipsArray[i].status != 0)
			System.out.println("\nYou hit my " + ShipsArray[i].name + "!\n");
			else
				//Sunk Ship
			{	TurnArray[0].ships_remaining--;
				System.out.println("\nYou sunk my " + ShipsArray[i].name + "! I still have " + TurnArray[0].ships_remaining + " ship remaining though.\n");
				}
			Player_Board[row][col] = Ship_Board[row][col];
		}
	return Player_Board;		
	}
	
	//Player Wins
	public boolean Winner(){
		if (TurnArray[0].ships_remaining == 0)
		{	System.out.println("\n\nWell.... it looks like you won. I guess a blind squirrel can find a nut on occasion.");
				return true;
		}	else
				return false;
	}
	
	//Player Loses
	public boolean Loser(){
		if (TurnArray[0].turns_remaining == 1 && TurnArray[0].ships_remaining != 0)
			{System.out.println("\n\n\n\nIn the famous words of Ace Ventura... You are a Luh-Who, Zuh-Hur!");
			return true;
			}
		else
			return false;
	}
}