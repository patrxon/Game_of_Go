package com.example.GoGame;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JOptionPane;

public class MainServer {
	
	
	private static HashSet<String> names = new HashSet<String>();

    private static HashSet<PrintWriter> writers = new HashSet<PrintWriter>();

	private static  ArrayList<ServerSocket> clients = new ArrayList<ServerSocket>();
	
	public static void main(String[] args) throws Exception {
		
		ServerSocket listener = new ServerSocket(7171);
		
		JOptionPane.showMessageDialog(null,"Server zostal wlaczony!");
		System.out.println("Server zostal wlaczony!");
		
		try {
            while(true) {
                new Handler(listener.accept()).start();
	
            }
        } finally {
            listener.close();
        } 
    }

	private static class Handler extends Thread {
		 	private String name;
	        private Socket socket;
	        private BufferedReader in;
	        private PrintWriter out;

	        public Handler(Socket socket) {
	            this.socket = socket;
	        }

	        public void run() {
	        	
	            try {

	            	in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	            	out = new PrintWriter(socket.getOutputStream(), true);
	            	
	            	writers.add(out);
	                    
	            	while (true) {
	            		String input = in.readLine();
						String st;
						
	                    if (input == null) {
	                        return;
	                    }
	                    
	                    for (PrintWriter writer : writers) {
	                        writer.println(input);
	                    }
	            	}
					
	            } catch (IOException e) {
	                System.out.println(e);
	            } finally {
	                if (name != null) {
	                    names.remove(name);
	                }
	                if (out != null) {
	                    writers.remove(out);
	                }
	                try {
	                    socket.close();
	                } catch (IOException e) {}
	            }
	        }
	    }
	
}
