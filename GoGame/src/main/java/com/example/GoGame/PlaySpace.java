package com.example.GoGame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class PlaySpace extends JPanel{

	public static final int HMAX = 805;
	public static final int WMAX = 825;
	public static final int boardSize = 760;
	private JFrame f = new JFrame("GRA");
	
	Piece[][] pieces;
	int dimention=0;
	int spacing=0;
	
	public PlaySpace(){			
		set_window();						
		repaint();						
	}
	
	public void setBorad(int dimention,Piece[][] pieces) {
		this.dimention = dimention;
		this.pieces = pieces;
		spacing = boardSize/dimention;
	}
	
	protected void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		
		g2d.setColor(new Color(210,105,30));
		
		g2d.fillRect(20,20,spacing*dimention,spacing*dimention);
		
		if(dimention>0)	{
			
			g2d.setColor(new Color(0,0,0));
			
			for(int i=0; i<dimention; i++) {
				
				int dis = 20+spacing/2+i*spacing;
				int beg = 20+spacing/2;
				int end = 20+spacing/2+spacing*(dimention-1);
				
				g2d.drawLine(dis, beg, dis, end);
				g2d.drawLine(beg, dis, end, dis);
			}
			
			
			for(int i=1; i<=dimention;i++) {
				for(int j=1; j<=dimention;j++) {
					
					if(pieces[j][i].getState() == 0) continue;
					else if(pieces[j][i].getState() == 1) g2d.setColor(new Color(0,0,0));
					else if(pieces[j][i].getState() == 2) g2d.setColor(new Color(255,255,255));
					
					g2d.fillOval(20+(j-1)*spacing+2,20+(i-1)*spacing+2, spacing-4, spacing-4);	
					
				}	
			}
		}
					
	}
	
	public void get_keyListener(KeyListener k) {
		f.addKeyListener(k);
	}
	
 	public void set_window() {
 		
		f.setPreferredSize(new Dimension(HMAX,WMAX ));
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(HMAX, WMAX);
		f.setVisible(true);
		f.setResizable(false);
		setBackground(Color.BLACK);
		f.add(this);
	}

}
