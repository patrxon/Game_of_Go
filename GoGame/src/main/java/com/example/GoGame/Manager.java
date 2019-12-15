package com.example.GoGame;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Scanner;

public class Manager implements MouseListener, KeyListener{
	
	private Board board;
	private PlaySpace playSpace;
	private Piece[][] pieces;
	private int size=9;
	private int turn=1;
	private boolean blackPass = false;
	private boolean whitePass = false;
	
	Scanner myObj = new Scanner(System.in);
	
	public Manager() {
		
		board = new Board(size);
		playSpace = new PlaySpace();
		pieces = board.getBoard();
		
		playSpace.setBorad(size,pieces);
		playSpace.addMouseListener(this);	
		playSpace.get_keyListener(this);
	}
	
	public void run(int x, int y) {	
	
		if(board.makeMove(x+1,y+1,turn))		
		if(turn == 1)turn=2;
		else turn=1;
		playSpace.repaint();
			
	}

	public void keyPressed(KeyEvent e) {
			
		if(e.getKeyCode() == 83) {
			if( turn == 1) {
				blackPass = true;
				turn = 2;
			}
			else {
				whitePass = true;
				turn = 1;
			}	
		}	
		
		if(whitePass && blackPass) {
			board.endGame();
			playSpace.repaint();
		}
	}

	public void mousePressed(MouseEvent e) {
		
		int Px = e.getX();
		int Py = e.getY();
		
		int x = (int) Math.floor((Px - 20)*size/PlaySpace.boardSize);
		int y = (int) Math.floor((Py - 20)*size/PlaySpace.boardSize);
		
		System.out.println(x + "-" + y);
		
		run(x,y);
		
	}
	public void keyTyped(KeyEvent e) {}
	
	public void keyReleased(KeyEvent e) {}

	public void mouseClicked(MouseEvent e) {}

	public void mouseReleased(MouseEvent e) {}

	public void mouseEntered(MouseEvent e) {}

	public void mouseExited(MouseEvent e) {}
	
	
	
}
