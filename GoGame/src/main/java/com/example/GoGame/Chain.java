package com.example.GoGame;

import java.util.ArrayList;

public class Chain {
	private ArrayList<Piece> pieces = new ArrayList<Piece>();
	
	Chain(Piece piece) {
		pieces.add(piece);
	}
	
	public ArrayList<Piece> getChain() {
		return pieces;
	}
	
	public Piece getPiece(int i) {
		return pieces.get(i);
	}
	
	public int getSize() {
		return pieces.size();
	}
	
	
	
}
