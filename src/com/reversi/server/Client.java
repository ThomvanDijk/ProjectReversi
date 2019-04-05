package com.reversi.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

import com.reversi.controller.ClientController;

public class Client {

	// IO streams
	private BufferedWriter toServer;
	private BufferedReader fromServer;

	private Socket socket;
	private Scanner scanInput;
	private boolean running;

	public Client(ClientController serverController) {
		scanInput = new Scanner(System.in);
		running = true;

		System.out.println("Client started and connecting... \n");

		Thread thread = new Thread(() -> {
			String messageServer;
			
			try {
				Socket socket = new Socket("localhost", 7789);

				toServer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
				fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));

				System.out.println("Client connected to " + socket.getRemoteSocketAddress() + "\n");
			} catch (IOException ex) {
				if (socket != null) {
					if (!socket.isClosed()) {
						try {
							socket.close();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}
				System.out.println(ex.toString() + "\n");
			}

			try {
				while (true) {
					// Receive text from the server
					messageServer = fromServer.readLine();
					serverMessage(messageServer);
				}
			} catch (IOException ex) {
				System.out.println(ex.toString() + "\n");
			}
		});

		thread.setDaemon(true);
		thread.start();

		consoleInput();
	}
	
	// Send commands to model via serverController
	public void serverMessage(String message) {
		System.out.println("Other: " + message + "\n");
	}

	// Send commands to server
	public void consoleInput() {
		String textToSend;

		while (scanInput.hasNextLine() && running) {
			try {
				textToSend = scanInput.nextLine();
				System.out.println(textToSend);

				// Send the text to the server
				toServer.write(textToSend);
				toServer.newLine();
				toServer.flush();

				// Display text to the text area
				System.out.println("You: " + textToSend + "\n");

				if (textToSend.equals("exit")) {
					running = false;
					
					scanInput.close();
					
					try {
						socket.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}
