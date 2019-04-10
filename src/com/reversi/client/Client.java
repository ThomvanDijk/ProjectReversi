package com.reversi.client;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
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

//		System.out.println("Client started and connecting... \n");

//		listener = new Listener(this, "localhost");
//		Thread listenerThread = new Thread(listener);
//
//		listenerThread.setDaemon(true);
//		listenerThread.start();

//		consoleInput();
	}
	
	public void login(String[] arguments) {
		arguments[0] = "login " + arguments[0];
		
		listener = new Listener(this, arguments[1]);
		Thread listenerThread = new Thread(listener);

		listenerThread.setDaemon(true);
		listenerThread.start();
		
		try {
			// Send command to the server
			listener.sendMessage(arguments[0]);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Display text to the text area
		System.out.println("Client: " + arguments[1] + " " + arguments[0] + "\n");
	}
	
	/**
	 * sendCommand is called from model (GameModel) every time there are new messages...
	 * 
	 * @param arguments Command + arguments to send to the server.
	 */
	public void sendCommand(String command) {
		try {
			// Send command to the server
			listener.sendMessage(command);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Display text to the text area
		System.out.println("Client: " + command + "\n");
	}

	// Send commands to model via serverController
	public void notifyController(String message) {
		System.out.println("Server: " + message);
		
		HashMap<ServerCommand, String> commandMap = parser.getCommand(message);
		
		// Display the raw first map
		//System.out.println("Map: " + commandMap); 

		// If ERR just use the value with this key because the value is just a single string
		if(!commandMap.containsKey(ServerCommand.ERR) && !commandMap.isEmpty()) {
			
			// Parse to a list if ServerCommand == SVR_GAMELIST or SVR_PLAYERLIST
			if(commandMap.containsKey(ServerCommand.SVR_GAMELIST) || 
			   commandMap.containsKey(ServerCommand.SVR_PLAYERLIST)) {
				HashMap.Entry<ServerCommand, String> entry = commandMap.entrySet().iterator().next();
				
				List<String> elemenstsList = parser.stringToList(commandMap.get(entry.getKey()));
				//System.out.println("Elemensts list: " + elemenstsList);
				
			} else { // Parse to a map
				HashMap.Entry<ServerCommand, String> entry = commandMap.entrySet().iterator().next();
//				ServerCommand key = entry.getKey();
//				String value = entry.getValue();
				 
				HashMap<ArgumentKey, String> keyValueMap = parser.stringToMap(commandMap.get(entry.getKey()));
				//System.out.println("Map key: " + entry.getKey()); 
				//System.out.println("Key value map: " + keyValueMap);
				
				// Input the first element of the server command
				switch(entry.getKey()) {
				case SVR_GAME_MATCH:
					break;
				case SVR_GAME_YOURTURN:
					break;
				case SVR_GAME_MOVE:
					break;
				case SVR_GAME_CHALLENGE:
					break;
				case SVR_GAME:
					break;
				default:
					throw new IllegalStateException();
				}
			}
		} 
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
				System.out.println("Client: " + textToSend + "\n");

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
