package com.reversi.client;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import com.reversi.client.Parser.ArgumentKey;
import com.reversi.client.Parser.ServerCommand;
import com.reversi.controller.ClientController;
import com.reversi.controller.Controller;

/**
 * The Client manages the connection with the sever. This class has a listener
 * and a parser. The parser is used to breakdown server messages and the
 * listener passes new messages from the server to the client and sends messages
 * to the server via the client.
 * 
 * @author Thom van Dijk
 * @version 1.0
 * @since 16-04-2019
 */
public class Client {

	private ClientController clientController;
	private Listener listener;
	private Parser parser;

	private String loginName;

	/**
	 * The constructor sets the clientController which is used by Client. This can
	 * be done because there is only one clientController for the Client to talk to.
	 * 
	 * @param clientController The ClientController is a Controller which handles
	 *                         messages to GameModel.
	 */
	public Client(ClientController clientController) {
		this.clientController = clientController;
		parser = new Parser();
	}

	/**
	 * This login function is specifically made to login to the server and is called
	 * by GameModel.
	 * 
	 * @param arguments Is a list of argument Strings with first the login name and
	 *                  second the server address.
	 */
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
	 * The sendCommand function is called from GameModel every time there are new
	 * messages ready to be send.
	 * 
	 * @param command Is a String consisting of the exact command the listener has
	 *                to send to the server.
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

	/**
	 * This function is called by the listener and processes server messages so that
	 * they will be send correctly to the ClientController. After the parser makes
	 * the messages usable this function will call ClientController according to the
	 * content of the message.
	 * 
	 * @param message Is the raw message from the server.
	 */
	public void notifyController(String message) {
		System.out.println("Server: " + message);

		HashMap<ServerCommand, String> commandMap = parser.getCommand(message);

		// TODO catch ERR messages
		// ERR messages are ignored for now
		if (!commandMap.containsKey(ServerCommand.ERR) && !commandMap.isEmpty()) {

			// Parse to a list if ServerCommand == SVR_GAMELIST or SVR_PLAYERLIST
			if (commandMap.containsKey(ServerCommand.SVR_GAMELIST)
					|| commandMap.containsKey(ServerCommand.SVR_PLAYERLIST)) {
				
				HashMap.Entry<ServerCommand, String> entry = commandMap.entrySet().iterator().next();
				List<String> elemenstsList = parser.stringToList(commandMap.get(entry.getKey()));
				
				// TODO send elemenstsList to ClientController
				
			} else { // Parse to a map
				HashMap.Entry<ServerCommand, String> entry = commandMap.entrySet().iterator().next();
				HashMap<ArgumentKey, String> keyValueMap = parser.stringToMap(commandMap.get(entry.getKey()));

				// Make string array to send to the controller
				String[] arguments = new String[3];

				// Input the first element of the server command
				switch (entry.getKey()) {
				case SVR_GAME_MATCH:
					arguments[0] = keyValueMap.get(ArgumentKey.PLAYERTOMOVE);
					arguments[1] = keyValueMap.get(ArgumentKey.OPPONENT);
					arguments[2] = keyValueMap.get(ArgumentKey.GAMETYPE);
					clientController.notifyModel(Controller.START_ONLINE_GAME, arguments);
					break;
				case SVR_GAME_YOURTURN: // Request a move from the AI
					clientController.notifyModel(Controller.REQUEST_MOVE, null);
					break;
				case SVR_GAME_MOVE: // Other player did a move
					arguments[0] = keyValueMap.get(ArgumentKey.MOVE);

					// Only notifyModel if it is not our own move!
					if (!keyValueMap.get(ArgumentKey.PLAYER).equals(loginName)) {
						clientController.notifyModel(Controller.SET_MOVE, arguments);
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
					clientController.notifyModel(Controller.END_GAME, null);
					break;
				case SVR_GAME_LOSS:
					clientController.notifyModel(Controller.END_GAME, null);
					break;
				case SVR_GAME_DRAW:
					clientController.notifyModel(Controller.END_GAME, null);
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
