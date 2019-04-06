package com.reversi.client;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.reversi.client.Parser.ArgumentKey;
import com.reversi.client.Parser.ServerCommand;
import com.reversi.controller.ClientController;

public class Client {

	private ClientController clientController;
	private Listener listener;
	private Parser parser;

	private Scanner scanInput;

	public Client(ClientController clientController) {
		this.clientController = clientController;
		
		parser = new Parser();

		scanInput = new Scanner(System.in);

		System.out.println("Client started and connecting... \n");

		listener = new Listener(this);
		Thread listenerThread = new Thread(listener);

		listenerThread.setDaemon(true);
		listenerThread.start();

		consoleInput();
	}

	// Send commands to model via serverController
	public void notifyController(String message) {
		System.out.println("Server: " + message);
		
		//Map<ServerCommands, Map<ArgumentKey, String>>
		HashMap<ServerCommand, HashMap<ArgumentKey, String>> map = parser.parseMessage(message);

//			if() {
//				clientController.notify(Notification.SET_MOVE_TICTACTOE, message);
//			}	
		
		// Input the first element of the server command
//				switch(parts[0]) {
//				case ERR:
//					break;
//				case OK:
//					break;
//				case SVR_HELP:
//					break;
//				case SVR_GAME_MATCH:
//					break;
//				case SVR_GAME_YOURTURN,:
//					break;
//				case SVR_GAME_MOVE:
//					break;
//				case SVR_GAME_CHALLENGE:
//					break;
//				case SVR_GAME:
//					break;
//				default:
//					throw new IllegalStateException();
//				}

	}

	// Send commands to server
	public void consoleInput() {
		String textToSend;

		while (scanInput.hasNextLine() && !Thread.currentThread().isInterrupted()) {
			try {
				textToSend = scanInput.nextLine();

				// Send the text to the server
				listener.sendMessage(textToSend);

				// Display text to the text area
				System.out.println("You: " + textToSend + "\n");

				if (textToSend.equals("exit")) {
					break;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		scanInput.close();
	}

}
