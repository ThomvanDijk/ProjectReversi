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

	private String loginName;

	public Client(ClientController clientController) {
		this.clientController = clientController;		
		parser = new Parser();
	}
	
	public void login(String[] arguments) {
		loginName = arguments[0];
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
				
				// Make string array to send to the controller
				String[] arguments = new String[3];
				
				// Input the first element of the server command
				switch(entry.getKey()) {
				case SVR_GAME_MATCH:
					arguments[0] = keyValueMap.get(ArgumentKey.PLAYERTOMOVE);
					arguments[1]= keyValueMap.get(ArgumentKey.OPPONENT);
					arguments[2]= keyValueMap.get(ArgumentKey.GAMETYPE);
					clientController.notifyModel(Controller.START_ONLINE_GAME, arguments);
					break;
				case SVR_GAME_YOURTURN: // Request a move from the AI
					clientController.notifyModel(Controller.REQUEST_MOVE, null);
					break;
				case SVR_GAME_MOVE: // Other player did a move
					arguments[0] = keyValueMap.get(ArgumentKey.MOVE);
					
					// Only notifyModel if it is not our own move!
					if(!keyValueMap.get(ArgumentKey.PLAYER).equals(loginName)) {
						clientController.notifyModel(Controller.SERVER_DID_MOVE, arguments);
					}
					break;
				case SVR_GAME_CHALLENGE:
					arguments[0] = keyValueMap.get(ArgumentKey.CHALLENGER);
					arguments[1] = keyValueMap.get(ArgumentKey.CHALLENGENUMBER);
					arguments[2] = keyValueMap.get(ArgumentKey.GAMETYPE);		
					clientController.notifyModel(Controller.PROCESS_NEW_CHALLENGE, arguments);					
					break;
				case SVR_GAME_CHALLENGE_CANCELLED:
					arguments[0] = keyValueMap.get(ArgumentKey.CHALLENGENUMBER);
					clientController.notifyModel(Controller.CANCEL_CHALLENGE, arguments);
					break;
				case SVR_GAME_WIN:
					arguments[0] = keyValueMap.get(ArgumentKey.PLAYERONESCORE);
					arguments[1] = keyValueMap.get(ArgumentKey.PLAYERTWOSCORE);
					clientController.notifyModel(Controller.END_GAME, arguments);
					break;
				case SVR_GAME_LOSS:
					arguments[0] = keyValueMap.get(ArgumentKey.PLAYERONESCORE);
					arguments[1] = keyValueMap.get(ArgumentKey.PLAYERTWOSCORE);
					clientController.notifyModel(Controller.END_GAME, arguments);
					break;
				case SVR_GAME_DRAW:
					arguments[0] = keyValueMap.get(ArgumentKey.PLAYERONESCORE);
					arguments[1] = keyValueMap.get(ArgumentKey.PLAYERTWOSCORE);
					clientController.notifyModel(Controller.END_GAME, arguments);
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
