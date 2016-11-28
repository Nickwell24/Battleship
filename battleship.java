package battleship;

public class battleship {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		actiongame game = new actiongame();

		String[][] Player_Board = game.StartGame();
		String[][] Ship_Board = game.Ship_Board();
		
		game.print(Ship_Board);
		game.TakeTurn(Player_Board, Ship_Board);
		
		
	}

}
