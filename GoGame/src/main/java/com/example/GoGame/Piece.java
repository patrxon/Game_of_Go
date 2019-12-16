package com.example.GoGame;

public class Piece {
	
	private int x = 0;
	private int y = 0;
	private int state = 0; //0=EMPTY, 1=BLACK, 2=WHITE, 3=WARDEN
	private int breaths = 4;
	private int neighbourWhite = 0;
	private int neighbourBlack = 0;
	
	public Piece(int x, int y, int state) {
		this.y = y;
		this.x = x;
		this.state = state;
	}

	void resetPiece() {
		state = 0;
		breaths = 4;
	}
	
	public int getState() {
		return state;
	}
	
	public void setState(int state) {
		this.state = state;
	}

	public void setBreath(int breaths) {
		this.breaths = breaths;
		
	}

	public int getX() {
		return  x;
	}
	
	public int getY() {
		return y;
	}

	public int getBreaths() {
		return breaths;
	}

	public int getBlackNeighbour() {
		return neighbourBlack;
	}
	
	public int getWhiteNeighbour() {
		return neighbourWhite;
	}

	public void setNeighbours(int b, int w) {
		neighbourBlack = b;
		neighbourWhite = w;
	}
	
}
