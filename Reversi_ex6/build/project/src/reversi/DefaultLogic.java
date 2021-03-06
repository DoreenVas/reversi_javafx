package reversi;

/**
* class name: DefaultLogic
* contains the default rules of the game
*/
public class DefaultLogic extends GameLogic{

	/**
	 * constructor name: DefaultLogic
	 * @param player1p
	 * @param player2p
	 * @param board
	 * calls GameLogic constructor
	 */
	public DefaultLogic(Player player1p, Player player2p, Board board) {
		super(player1p, player2p, board);
	}

	/**
	 * function name:  makeMove
	 * @param row
	 * @param col
	 * puts the players disk in place and +1 to his score,than goes throw
	 * the cell flipOption and changes "sum" disks in each direction and changes accordingly the score
	 */
	@Override
	public void makeMove(int row, int col) {
		 board.cellAt(row,col).setContains(player.getType());
		    player.setScore(1);
		    for (int i=0;i<3;i++) {
		        for (int j=0;j<3;j++){
		            int sum= board.cellAt(row,col).getFlipOptions(i,j);
		            while(sum!=0){
		                board.cellAt(row+sum*(i-1),col+sum*(j-1)).setContains(player.getType());
		                player.setScore(1);
		                rival.setScore(-1);
		                sum--;
		            }
		        }
		    }
	}

	/**
	 * function name:  possibleMoves
	 *  goes throw the Cells in board and if sees a player disk calls checkCell for that cell
	 */
	@Override
	public void possibleMoves() {
		player.setNoMoves(true);
	    for (int row = 0; row < board.getBoardSize() ; row++) {
	        for (int col = 0; col < board.getBoardSize(); col++) {
	            if (board.cellAt(row,col).getContains()==player.getType())
	                checkCell(row,col);
	        }
	    }
	}
	
	/**
	 * function name:  checkCell
	 * @param row
	 * @param col
	 * checks the close cell in each direction to see if it
	 * contains the rival type(Black/White). if so sends it to checkDirection
	 */
	 private void checkCell(int row, int col) {
		 for(int i=row-1;i<=row+1;i++){
		        for(int j=col-1;j<=col+1;j++){
		            if(i>=0 && i<board.getBoardSize() && j>=0 && j<board.getBoardSize() ){
		                if (board.cellAt(i,j).getContains()==rival.getType())
		                    checkDirection(row,col,i-row,j-col);
		            }
		        }
		    }
	}

	 /**
	  * function name:  checkDirection
	  * @param row
	  * @param col
	  * @param deltaRow
	  * @param deltaCol
	  * while we see a rival type disk we continue in that way,if it ends with a space we
* set the cell option to true and update the changeFlipOptions with the sum we reached.
	  */
	 private void checkDirection(int row,int col,int deltaRow,int deltaCol) {
		 int i = row + deltaRow;
		    int j = col + deltaCol;
		    int sum=0;
		    while (i>=0 && i<board.getBoardSize()  && j>=0 && j<board.getBoardSize()  && board.cellAt(i,j).getContains() == rival.getType()){
		        i += deltaRow;
		        j += deltaCol;
		        sum++;
		    }
		    if(i>=0 && i<board.getBoardSize()  && j>=0 && j<board.getBoardSize()  && board.cellAt(i,j).getContains()==Contains.Empty) {
		        board.cellAt(i,j).setOption(true);
		        board.cellAt(i,j).changeFlipOptions(1-deltaRow,1-deltaCol,sum);// we save the sum in the direction we came from
		        player.setNoMoves(false);
		    }
	}
}
