package com.reversi.controller;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Listener {
	private final int port = 7789;
	private ServerSocket socket;

	
	public Listener() {
		listenPort();
	}

	public int getPort() {
		return port;
	}
	
	public void listenPort() {
		
		try {
			socket = new ServerSocket(port);
			Socket client;
			
			while(true) {
				client = socket.accept();
				
			}
		} catch (IOException e){
			System.err.println(e);
		}
	}

}