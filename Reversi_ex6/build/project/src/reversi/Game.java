package reversi;
import javafx.util.Pair;

/**
* class name: Game
* description: runs the game
*/
public class Game {
	private Player player;
	private Player rival;
	private GameLogic logic;
	private Board board;
	private boolean gameOn;//true if the game is still on
	private Display display;
	
	/**
	 * constructor name:Game
	 * @param player1
	 * @param player2
	 * @param board
	 * @param logic
	 * @param display
	 * initializes the fields
	 */
	public Game(Player player1,Player player2,Board board, GameLogic logic,Display display) {
		gameOn=true;
	    player=player1;//player is the player currently playing
	    rival=player2;
	    this.board=board;
	    this.logic=logic;
	    this.display=display;
	}

	/**
	 *  function name: play
	 *  runs the game
	 */
	public void play() {
	    while (gameOn) {
	    	display.printMessage("Current board:");
	    	display.printBoard(board);
	        logic.possibleMoves();
	        player.preMovePrint(board);
	        if (player.isNoMoves()  && !rival.isNoMoves())
	        	display.printMessage("No moves available, hence your turn is passed ");
	        else if (player.isNoMoves() && rival.isNoMoves() ) {
	        	display.noMoves(player.getType());
	        	display.printEnd(player.getType(),player.getScore(),rival.getScore());
	            break;
	        }
	        else {
	        	Pair<Integer,Integer> chosenMove=player.chooseMove(board,logic);
	            logic.makeMove(chosenMove.getKey(), chosenMove.getValue());
                logic.resetPossibleMoves();
	        }
	        if (player.getScore() + rival.getScore() == board.getBoardSize()*board.getBoardSize()) {
	        	display.printMessage("Final board:");
	            display.printBoard(board);
	            display.printEnd(player.getType(),player.getScore(),rival.getScore());
	            break;
	        }
	        swap();
	        logic.switchPlayer();

	    }
	}

	/**
	 * function name: swap
	 * flips the playing and "watching" players
	 */
	private void swap()
	{
	    Player pSwap = this.player;
	    this.player = this.rival;
	    this.rival = pSwap;
	}
}
