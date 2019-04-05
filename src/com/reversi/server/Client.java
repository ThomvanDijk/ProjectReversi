package com.reversi.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

import com.reversi.main.Main;

public class Client {

	// IO streams
	private BufferedWriter toServer;
	private BufferedReader fromServer;

	private Socket socket;

	private Scanner scanInput;

	public Client() {

		scanInput = new Scanner(System.in);

		System.out.println("Client started and connecting... \n");

		Thread thread = new Thread(() -> {
			// Create a socket to connect to the server
			// Socket socket = new Socket("130.254.204.36", 8000);
			// Socket socket = new Socket("drake.Armstrong.edu", 8000);
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
					String textFromServer = fromServer.readLine();

					System.out.println("Other: " + textFromServer + "\n");
				}
			} catch (IOException ex) {
				System.out.println(ex.toString() + "\n");
			}
		});

		thread.setDaemon(true);
		thread.start();

//		primaryStage.setOnCloseRequest(e -> {
//			if (socket != null) {
//				if (!socket.isClosed()) {
//					try {
//						socket.close();
//					} catch (IOException e1) {
//						e1.printStackTrace();
//					}
//				}
//			}
//		});

		consoleInput();
	}

	public void consoleInput() {
		String input;

		while (scanInput.hasNextLine()) {
			try {
				input = scanInput.nextLine();
				System.out.println(input);

				// Get the text from the text field
				String textToSend = input;
				// window.clearTextField();

				// Send the text to the server
				toServer.write(textToSend);
				toServer.newLine();
				toServer.flush();
				
				// Display text to the text area
				System.out.println("You: " + textToSend + "\n");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if (!Main.running) {
			scanInput.close();
		}
	}
}
