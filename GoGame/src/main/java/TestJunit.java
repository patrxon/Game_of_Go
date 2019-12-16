import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.example.GoGame.Board;
import com.example.GoGame.GoBot;
import com.example.GoGame.Piece;

public class TestJunit {
	
	@Test
	public void equelSetUps() throws Exception {

		Board board = new Board(7);
		Piece[][] pieces = new Piece[9][9];
		
		fillPieces(pieces);
	
		for(int i=0; i<9; i++) {
			for(int j=0; j<9; j++) {
				assertEquals(0,pieces[j][i].getState(),board.getBoard()[j][i].getState());
			}
		}
		
	}
	
	private void fillPieces(Piece[][] pieces) {
		for(int i=0; i<9; i++) {
			for(int j=0; j<9; j++) {
				if(j==0 || j==8 || i ==0 || i == 8) pieces[j][i] = new Piece(j,i,3);
				else pieces[j][i] = new Piece(j,i,0);
			}
		}
	}
	
	@Test
	public void equelMoves() {
		
		Board board = new Board(7);
		Piece[][] pieces = new Piece[9][9];
		
		fillPieces(pieces);
		
		makeMoves(pieces,board,1,1,1);
		makeMoves(pieces,board,1,2,2);
		makeMoves(pieces,board,4,1,1);
		makeMoves(pieces,board,5,6,2);
		makeMoves(pieces,board,5,7,1);
		makeMoves(pieces,board,8,2,2);
		
		for(int i=0; i<9; i++) {
			for(int j=0; j<9; j++) {
				assertEquals(0,pieces[j][i].getState(),board.getBoard()[j][i].getState());
			}
		}
		
	}
	
	private void makeMoves(Piece[][] pieces,Board board, int x, int y, int p) {
		pieces[x][y].setState(p);
		board.makeMove(x, y, p);
	}
	
	@Test
	public void illegalMove() {
		
		Board board = new Board(7);
		
		assertTrue(board.makeMove(1, 2, 1));
		assertTrue(board.makeMove(2, 1, 1));
		assertTrue(board.makeMove(2, 3, 1));
		assertTrue(board.makeMove(3, 2, 1));
		assertFalse(board.makeMove(2, 2, 2)); //Suicide move
		assertTrue(board.makeMove(1, 3, 2));
		assertTrue(board.makeMove(2, 4, 2));
		assertTrue(board.makeMove(3, 3, 2));
		assertTrue(board.makeMove(2, 2, 2)); //Suicide with capture
		assertFalse(board.makeMove(3, 2, 1)); //Ko
	}
	
	@Test
	public void botMove() {
		
		Board board = new Board(7);
		GoBot bot = new GoBot(board.getBoard(),7);
		
		assertTrue(board.makeMove(3, 3, 1));
		
		int x;
		int y;
		
		for(int i=3; i>0; i--) {
			bot.FindPos(2);
			x = bot.getX();
			y = bot.getY();
			assertTrue(board.makeMove(x, y, 2));
			assertEquals(0,board.getBoard()[x][y].getBreaths(),3);
			assertEquals(0,board.getBoard()[3][3].getBreaths(),i);
		}
		
		bot.FindPos(2);
		x = bot.getX();
		y = bot.getY();
		assertTrue(board.makeMove(x, y, 2));
		assertEquals(0,board.getBoard()[x][y].getBreaths(),4);
		assertEquals(0,board.getBoard()[3][3].getBreaths(),4);
			
	}
	
	
}
