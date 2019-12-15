package com.example.GoGame;

import java.util.ArrayList;

import javax.swing.JOptionPane;

public class Board {

	private int size=11; // 9x9 + 2 warden layers
	
	private Piece[][] pieces;
	private ArrayList<Chain> blackChains = new ArrayList<Chain>();
	private ArrayList<Chain> whiteChains = new ArrayList<Chain>();
	private int blackPoints=0;
	private int whitePoints=0;
	private int cdPieceX=0;
	private int cdPieceY=0;
	
	public Board(int size) {
		this.size = size+2;
		
		SetUpBoard();
		
	}
	
	private void SetUpBoard() {
		
		pieces = new Piece[size][size];
		
		for(int i=1; i<size-1; i++) {
			for(int j=1; j<size-1; j++) {
				pieces[j][i] = new Piece(j,i,0);
			}
		}
		
		for(int i=0; i<size; i++) {
			pieces[i][0] = new Piece(i,0,3);
			pieces[i][size-1] = new Piece(i,size-1,3);
			pieces[0][i] = new Piece(0,i,3);
			pieces[size-1][i] = new Piece(size-1,i,3);
		}
				
	}
	
	public Boolean makeMove(int px, int py, int player) {
		
		if(pieces[px][py].getState()!=0) return false;
		if(cdPieceX == px && cdPieceY == py) return false;
		
		pieces[px][py].setState(player);
		if(player == 1) {
			setPawn(blackChains, px, py);
			countBreaths(whiteChains);
			
			if(checkChainBreaths(blackChains)) {
				pieces[px][py].resetPiece();
				return false;
			}	
		}
		else {
			setPawn(whiteChains, px, py);
			countBreaths(blackChains);
			
			if(checkChainBreaths(whiteChains)) {
				pieces[px][py].resetPiece();
				return false;
			}	
		}		
		
		cdPieceX=0;
		cdPieceY=0;
		//printChains();
		return true;
	}
	
	public void setPawn(ArrayList<Chain> chains, int px, int py) {
		chains.add(new Chain(pieces[px][py])); 
		sumUpChains(chains);
	}
	
	public void sumUpChains(ArrayList<Chain> chains) {
		
		Piece newPiece = chains.get(chains.size()-1).getPiece(0);
		
		for(int i=0; i < chains.size()-1; i++) {
			
			for(int j=0; j < chains.get(i).getSize(); j++) {
				
				Piece somePiece = chains.get(i).getPiece(j);
			
				if(isNeighbour(newPiece, somePiece)) {
					
					for(int k=0; k < chains.get(i).getSize(); k++) {
						chains.get(chains.size()-1).getChain().add(chains.get(i).getPiece(k));
					}				
					chains.remove(i);
					i--;
					break;
				}
			}
		}
		
	}
	
	public boolean checkChainBreaths(ArrayList<Chain> chains) {

		Chain chain=chains.get(chains.size()-1);
		
		int breaths=0;
		
		for(int i=0; i<chain.getSize(); i++) {
			
			checkBreaths(chain.getPiece(i).getX(), chain.getPiece(i).getY());
			breaths += chain.getPiece(i).getBreaths();
		}
		
		if(breaths==0) {
			
			chains.remove(chains.size()-1);
			chain.getChain().remove(0);
			
			for(int i=0; i<chain.getSize(); i++) {
				setPawn(chains,chain.getPiece(i).getX(),chain.getPiece(i).getY());
			}
			
			return true;
		}
		
		return false;
	}
	
	public void countBreaths(ArrayList<Chain> chains) {
	
		int breaths=0;
		Piece somePiece;
		int Kox=0;
		int Koy=0;
		int isKo=0;
		
		for(int i=0; i < chains.size(); i++) {
			
			for(int j=0; j < chains.get(i).getSize(); j++) {
				
				somePiece = chains.get(i).getPiece(j);
			
				checkBreaths(somePiece.getX(), somePiece.getY());
				breaths += somePiece.getBreaths();
			}
			
			if(breaths == 0) {
				if( chains.get(i).getSize()==1 ) {
					Kox=chains.get(i).getPiece(0).getX();
					Koy=chains.get(i).getPiece(0).getY();
					isKo++;
				}
				
				deleteChain(chains, i);
				i--;
			}
			else breaths = 0;
		}
		
		if(isKo==1) {
			cdPieceX=Kox;
			cdPieceY=Koy;
		}
		
	}
	
	public void deleteChain(ArrayList<Chain> chains, int n) {
		
		if(chains.get(0).getPiece(0).getState() == 1) whitePoints+=chains.get(n).getSize();
		else blackPoints+=chains.get(n).getSize();
			
		for(int i=0; i< chains.get(n).getSize(); i++) {
			pieces[chains.get(n).getPiece(i).getX()][chains.get(n).getPiece(i).getY()].resetPiece();
		}
		
		chains.remove(n);
	}
	
	public Boolean isNeighbour(Piece p1, Piece p2) {
		
		if(p1.getX() == p2.getX() && p1.getY() == p2.getY()+1) return true;
		if(p1.getX() == p2.getX() && p1.getY() == p2.getY()-1) return true;
		if(p1.getX() == p2.getX()+1 && p1.getY() == p2.getY()) return true;
		if(p1.getX() == p2.getX()-1 && p1.getY() == p2.getY()) return true;
		
		return false;
	}
	
	public void checkBreaths(int px, int py) {
		
		int n=4;
		
		if(0 < pieces[px+1][py].getState()) n--;
		if(0 <  pieces[px][py+1].getState()) n--;
		if(0 <  pieces[px-1][py].getState()) n--;
		if(0 <  pieces[px][py-1].getState()) n--;
		
		pieces[px][py].setBreath(n);
		
	}
	
	public void endGame() {
		
		int foundChange=1;
		
		while(foundChange>0) {
			foundChange=0;
			
			for(int i=1; i<size-1; i++) {
				for(int j=1; j<size-1; j++) {
					
					if(pieces[j][i].getState()==0)
					if(countNeighbours(j,i) ) {
						foundChange++;
					}		
				}
			}	
		}
		
		for(int i=1; i<size-1; i++) {
			for(int j=1; j<size-1; j++) {
				if(pieces[j][i].getBlackNeighbour() == 0 && pieces[j][i].getWhiteNeighbour() > 0) pieces[j][i].setState(2);
				if(pieces[j][i].getBlackNeighbour() > 0 && pieces[j][i].getWhiteNeighbour() == 0) pieces[j][i].setState(1);
			}
		}
		
		for(int i=1; i<size-1; i++) {
			for(int j=1; j<size-1; j++) {
				if(pieces[j][i].getState()==1) blackPoints++;
				if(pieces[j][i].getState()==2) whitePoints++;
			}
		}
		
		if(blackPoints>whitePoints) JOptionPane.showMessageDialog(null,"Black has won with "+ blackPoints + " points to " + whitePoints + " points.");
		else JOptionPane.showMessageDialog(null,"White has won with "+ whitePoints + " points to " + blackPoints + " points.");
		
		System.out.println("Someone has won");
	}
	
	public Boolean countNeighbours(int x, int y) {
		
		int blackNeighbour = 0;
		int whiteNeighbour = 0;
		
		if(pieces[x+1][y].getState() == 1 || pieces[x+1][y].getBlackNeighbour() > 0) blackNeighbour++;
		if(pieces[x-1][y].getState() == 1 || pieces[x-1][y].getBlackNeighbour() > 0) blackNeighbour++;
		if(pieces[x][y+1].getState() == 1 || pieces[x][y+1].getBlackNeighbour() > 0) blackNeighbour++;
		if(pieces[x][y-1].getState() == 1 || pieces[x][y-1].getBlackNeighbour() > 0) blackNeighbour++;
		
		if(pieces[x+1][y].getState() == 2 || pieces[x+1][y].getWhiteNeighbour() > 0) whiteNeighbour++;
		if(pieces[x-1][y].getState() == 2 || pieces[x-1][y].getWhiteNeighbour() > 0) whiteNeighbour++;
		if(pieces[x][y+1].getState() == 2 || pieces[x][y+1].getWhiteNeighbour() > 0) whiteNeighbour++;
		if(pieces[x][y-1].getState() == 2 || pieces[x][y-1].getWhiteNeighbour() > 0) whiteNeighbour++;
		
		if(pieces[x][y].getBlackNeighbour() == blackNeighbour && pieces[x][y].getWhiteNeighbour() == whiteNeighbour) return false;
		else {
			pieces[x][y].setNeighbours(blackNeighbour, whiteNeighbour);
			return true;
		}
	}
	
	public void printChains() {
		
		System.out.println("Points: "+ blackPoints);
		for(int i=0; i < blackChains.size(); i++) {
			for(int j=0; j < blackChains.get(i).getSize(); j++) {
				
				Piece someBPiece = blackChains.get(i).getPiece(j);
				System.out.println(someBPiece.getX() + " " + someBPiece.getY() + "|" + i + " " + j + "-" + someBPiece.getBreaths());
			}
		}
		
		System.out.println("----------");
		System.out.println("Points: "+ whitePoints);			
		for(int i=0; i < whiteChains.size(); i++) {
			for(int j=0; j < whiteChains.get(i).getSize(); j++) {
				
				Piece someWPiece = whiteChains.get(i).getPiece(j);
				System.out.println(someWPiece.getX() + " " + someWPiece.getY() + "|" + i + " " + j + "-" + someWPiece.getBreaths());
			}
		}	
		
		System.out.println("==========");
	}
	
	public Piece[][] getBoard() {
		return pieces;
	}
	
}
