package com.example.GoGame;

public class GoBot {
	
	private Piece[][] pieces;
	private int size;
	private int newX=0;
	private int newY=0;
	
	public GoBot(Piece[][] pieces, int size) {
		this.pieces = pieces;
		this.size = size;
	}
	
	public void FindPos(int player) {
		
		if(player == 1) player = 2;
		else player = 1;
		
		int positions=0;
		
		for(int i=1; i<size-1; i++) {
			for(int j=1; j<size-1; j++) {
				if(hasNeighbour(j,i,player)) positions++;			
			}
		}
		
		int putAt=0;
		if(positions>0) putAt = (int) (Math.random() * (positions-1))+1;
		
		int iter=0;
		
		for(int i=1; i<size-1; i++) {
			for(int j=1; j<size-1; j++) {
				if(hasNeighbour(j,i,player)) {
					iter++;
					if(putAt == iter)  {
						newX = j;
						newY = i;
					}
				}
			}
		}
		

		if(positions == 0) {
			newX = 0;
			newY = 0;
		}
		
	}
	
	private Boolean hasNeighbour(int x, int y, int type) {
		
		if(pieces[x][y].getState() != 0) return false;
		if(pieces[x][y+1].getState() == type) return true;
		if(pieces[x][y-1].getState() == type) return true;
		if(pieces[x+1][y].getState() == type) return true;
		if(pieces[x-1][y].getState() == type) return true;
		
		return false;
	}
	
	public int getX() {
		return newX;
	}
	
	public int getY() {
		return newY;
	}
	
}
