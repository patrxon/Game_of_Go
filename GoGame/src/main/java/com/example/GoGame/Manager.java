package com.example.GoGame;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class Manager implements MouseListener, KeyListener{
	
	private Board board;
	private PlaySpace playSpace;
	private Piece[][] pieces;
	private int size=9;
	private int turn=0;
	private int passCount=0;
	private boolean yourTurn = false;
	private boolean gameStarted =false;
	
	BufferedReader in;
    PrintWriter out;
    private Socket socket;
    
	Scanner myObj = new Scanner(System.in);
	
	public Manager() throws Exception {
		
		board = new Board(size);
		playSpace = new PlaySpace();
		pieces = board.getBoard();
		
		playSpace.setBorad(size,pieces);
		playSpace.addMouseListener(this);	
		playSpace.get_keyListener(this);
		
		runMove();
	}
	
	public void run(int x, int y) {	
	
		if(yourTurn)
		if(gameStarted)
		if(board.makeMove(x+1,y+1,turn)) {	
			yourTurn=false;
			out.println("NEXT_TURN" + turn + "" + x + ":" + y);
		}
			
		playSpace.repaint();		
	}

	public void keyPressed(KeyEvent e) {
		
		if(gameStarted) {
			if(yourTurn) {
				if(e.getKeyCode() == 83) {
					out.println("PASSED" + turn);
					yourTurn = false;	
				}	
				
				if(e.getKeyCode() == 66) {
					//TO:DO turnBotOn();
				}
			}
			
			if(e.getKeyCode() == 81) {
				out.println("FORFIT" + turn);
			}
		}
	}

	public void mousePressed(MouseEvent e) {
		
		int Px = e.getX();
		int Py = e.getY();
		
		int x = (int) Math.floor((Px - 20)*size/PlaySpace.boardSize);
		int y = (int) Math.floor((Py - 20)*size/PlaySpace.boardSize);
		
		run(x,y);
		
	}
	
	private void runMove() throws IOException {
	
        Socket socket = new Socket("127.0.0.1", 7171);
        
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        
        out = new PrintWriter(socket.getOutputStream(), true);
        
    	out.println("NEW_PLAYER");
    	
        while (true) {
    
        	String line = in.readLine();
                     
        	if(line.startsWith("NEW_PLAYER") && !gameStarted) {
        		turn++;
        		if(turn==2) {
            		out.println("GAME_STARTED");
            	}
        	}  
        	
        	if(line.startsWith("GAME_STARTED")) {
        		gameStarted = true;
        		if(turn==1) {
        			yourTurn=true;
        			JOptionPane.showMessageDialog(null,"Zaczynasz!");
        		}	
        	}
        	
        	if(gameStarted) {
	        	if(line.startsWith("PASSED")) {
	        		passCount++;
	        		
	        		if(line.charAt(6)-48 != turn) yourTurn=true;
	        			
	        		if(passCount>1) {
	        			board.endGame();
	        			playSpace.repaint();
	        			gameStarted = false;
	        		}
	        	}
	        	
	        	if(line.startsWith("FORFIT")) {
	        		gameStarted = false;
	        		
	        		if(line.charAt(6)-48 != turn) {
	        			JOptionPane.showMessageDialog(null,"Your opponent surrendered!");
	        		}
	        	}
				
	        	if(line.startsWith("NEXT_TURN")) {
	        		
	        		int opT = line.charAt(9)-48;
	        		
	        		if(opT != turn) {
		        		String[] parts = line.substring(10).split(":");
		        		
		        		int opX = Integer.parseInt(parts[0]);
		        		int opY = Integer.parseInt(parts[1]);
		        		
		        		board.makeMove(opX+1,opY+1,opT);
		        		yourTurn = true;
		        		playSpace.repaint();
		        		passCount=0;
	        		}
	        	}
        	}
  
		}		
	}
	
	public void keyTyped(KeyEvent e) {}
	
	public void keyReleased(KeyEvent e) {}

	public void mouseClicked(MouseEvent e) {}

	public void mouseReleased(MouseEvent e) {}

	public void mouseEntered(MouseEvent e) {}

	public void mouseExited(MouseEvent e) {}
}
