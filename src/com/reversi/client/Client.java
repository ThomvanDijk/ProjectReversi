package com.reversi.client;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import com.reversi.client.Parser.ArgumentKey;
import com.reversi.client.Parser.ServerCommand;
import com.reversi.controller.ClientController;
import com.reversi.controller.Controller;

public class Client {

	private ClientController clientController;
	private Listener listener;
	private Parser parser;

	private String currentPlayer;

	public Client(ClientController clientController) {
		this.clientController = clientController;
		
		parser = new Parser();
		
		currentPlayer = "";
	}
	
	public void login(String[] arguments) {
		currentPlayer = arguments[0];
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
		System.out.println("Client: " + arguments[0] + " address: " + arguments[1]);
	}
	
	/**
	 * sendCommand is called from model (GameModel) every time there are new messages...
	 * 
	 * @param arguments Command + arguments to send to the server.
	 */
	public void sendCommand(String command) {
		System.out.println("Client: " + command);
		
		try {
			// Send command to the server
			listener.sendMessage(command);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Send commands to model via serverController
	public void notifyController(String message) {
		System.out.println("Server: " + message);
		
		HashMap<ServerCommand, String> commandMap = parser.getCommand(message);

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
				//ServerCommand key = entry.getKey();
				//String value = entry.getValue();
				 
				HashMap<ArgumentKey, String> keyValueMap = parser.stringToMap(commandMap.get(entry.getKey()));
				//System.out.println("Map key: " + entry.getKey()); 
				//System.out.println("Key value map: " + keyValueMap);
				
				// Input the first element of the server command
				switch(entry.getKey()) {
				case SVR_GAME_MATCH:
					// Make new string array to use as argument
					String[] typeArguments = new String[2];
					typeArguments[0] = keyValueMap.get(ArgumentKey.GAMETYPE);
					
					clientController.notifyModel(Controller.START_ONLINE_GAME, typeArguments);
					break;
				case SVR_GAME_YOURTURN: // Request a move from the ai
					clientController.notifyModel(Controller.REQUEST_MOVE, null);
					break;
				case SVR_GAME_MOVE: // Other player did a move
					// Make new string array to use as argument
					String[] moveArguments = new String[2];
					moveArguments[0] = keyValueMap.get(ArgumentKey.MOVE);
					
					// Only notifyModel if it is not our own move!
					if(!keyValueMap.get(ArgumentKey.PLAYER).equals(currentPlayer)) {
						clientController.notifyModel(Controller.OTHER_DID_MOVE, moveArguments);
					}
					break;
				case SVR_GAME_CHALLENGE:
					// Make new string array to use as argument
					String[] chalArguments = new String[3];
					chalArguments[0] = keyValueMap.get(ArgumentKey.CHALLENGER);
					chalArguments[1] = keyValueMap.get(ArgumentKey.CHALLENGENUMBER);
					chalArguments[2] = keyValueMap.get(ArgumentKey.GAMETYPE);
					
					clientController.notifyModel(Controller.PROCESS_NEW_CHALLENGE, chalArguments);
					break;
				case SVR_GAME_WIN:
					// Make new string array to use as argument
					String[] winArguments = new String[2];
					winArguments[0] = keyValueMap.get(ArgumentKey.PLAYERONESCORE);
					winArguments[1] = keyValueMap.get(ArgumentKey.PLAYERTWOSCORE);
					
					clientController.notifyModel(Controller.END_ONLINE_GAME, winArguments);
					break;
				case SVR_GAME_LOSS:
					// Make new string array to use as argument
					String[] loseArguments = new String[2];
					loseArguments[0] = keyValueMap.get(ArgumentKey.PLAYERONESCORE);
					loseArguments[1] = keyValueMap.get(ArgumentKey.PLAYERTWOSCORE);
					
					clientController.notifyModel(Controller.END_ONLINE_GAME, loseArguments);
					break;
				case SVR_GAME:
					break;
				default:
					throw new IllegalStateException();
				}
			}
		} 
	}

}
