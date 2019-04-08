/**
* This class converts strings from the server to workable lists, maps or strings
* and converts the message header to a valid command
* 
* @author Thom van Dijk
* @version 1.0
* @since 08-04-2019
*/

package com.reversi.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class Parser {
	
	public enum ServerCommand {
		ERR, 
		OK, 
		SVR_HELP, 
		SVR_GAME_MATCH, 	// Nu bezig met een match, de inschrijving voor een speltype is vervallen
		SVR_GAME_YOURTURN, 	// Nu mogelijkheid een zet te doen
		SVR_GAME_MOVE, 		// Er is een zet gedaan, dit bericht geeft aan wie deze gezet heeft, wat de
							// reactie van het spel erop is
		SVR_GAME_CHALLENGE, // Nu mogelijkheid de uitdaging te accepteren.
		SVR_GAME_CHALLENGE_CANCELLED, // De uitdaging is vervallen. Mogelijke oorzaken: speler heeft een 
							// andere uitdaging gestart, speler is een match begonnen, speler heeft de 
							// verbinding verbroken. 
		SVR_GAME,  			// De match is afgelopen, <speler resultaat> kan de waarde 'WIN', 'LOSS' of
							// 'DRAW' bevatten.
		SVR_GAMELIST,		// Lijst met spellen ontvangen.
		SVR_PLAYERLIST	 	// List of online players
	}
	
	public enum ArgumentKey {
		DEFAULT,
		GAMETYPE,		// "<game type>"
		PLAYERTOMOVE,	// "<name player who has turn>"
		OPPONENT,		// "<name opponent>"
		PLAYER,			// "<player name>"
		DETAILS,		// "<reaction on game setup>"
		MOVE,			// "<move>"
		PLAYERONESCORE, // "<score player1>" 
		PLAYERTWOSCORE, // "<score player2>" 
		COMMENT, 		// "<comments on result>"
		CHALLENGER, 	// "<name of challenger>"
		CHALLENGENUMBER	// "<number of challenge>"
	}

	public Parser() {
		
	}
	
	/**
	* This function converts a server message to a map with ServerCommand and arguments.
	* Help messages from the server are not allowed and will not be captured!
	* 
	* @param message A message from the server
	* @return HashMap<ServerCommand, String> This returns a map with 
	* correct ServerCommand and its arguments
	*/
	public HashMap<ServerCommand, String> getCommand(String message) {
		HashMap<ServerCommand, String> map = new HashMap<>();
		
		// Filter useless server messages
		if(message.equals("OK")) {
			return map;
		}
		
		if(message.equals("Strategic Game Server Fixed [Version 1.1.0]")) {
			return map;
		}
		
		if(message.equals("(C) Copyright 2015 Hanzehogeschool Groningen")) {
			return map;
		}
		
		// Split the message in two parts ServerCommand and arguments
		// Try the split in a curly bracket
		String[] parts = message.split(Pattern.quote("{"));

		if(parts.length == 1) {
			
			// No map return so check for list return on a bracket
			parts = message.split(Pattern.quote("["));
			
			if(parts.length == 1) {
				
				// Also no list return so split the message after the server message
				// this can only be ERR and SVR HELP
				parts = message.split("(?<=ERR) ");
				
				if(parts.length == 1) {
					// exception
				}
			}
		}

		map.put(stringToCommand(parts[0]), parts[1]);	
		return map;
	}
	
	public ServerCommand stringToCommand(String commandString) {
		// parts[0] = parts[0].substring(0, parts[0].length() - 1); // Remove last char
		commandString = commandString.trim(); // Remove last whitespace
		// Match the ServerCommand format
		commandString = commandString.replaceAll(" ", "_");

		ServerCommand command = null;

		// Pick the right ServerCommand to return
		for (ServerCommand cmd : ServerCommand.values()) {
			if (cmd.name().equals(commandString)) {
				command = cmd;
				break;
			}
		}

		// Should not happen
		if (command == null) {
			return null;
		}
		
		return command;
	}
	
	public List<String> stringToList(String listString) {
		// Create a new list
		List<String> list = new ArrayList<>();

		// Split the elements up
		String[] elements = listString.split(",");

		// Add the keys as ArgumentKey and the values as String
		for (int i = 0; i < elements.length; i++) {
			// Remove unwanted stuff
			elements[i] = elements[i].trim();
			elements[i] = elements[i].replaceAll(Pattern.quote("["), "");
			elements[i] = elements[i].replaceAll(Pattern.quote("]"), "");
			
			list.add(elements[i]);
		}

		return list;
	}
	
	public HashMap<ArgumentKey, String> stringToMap(String mapString) {
		HashMap<ArgumentKey, String> keyValueMap = new HashMap<>();
		
		// Split the arguments up
		String[] arguments = mapString.split(",");		
		
		// Add the keys as ArgumentKey and the values as String
		for(int i = 0; i < arguments.length; i++) {
			ArgumentKey tempKey = ArgumentKey.DEFAULT;
			
			// Make pair [key, value]
			String[] keyValue = arguments[i].split(":");
			
			// Remove unwanted string stuff
			keyValue[0] = keyValue[0].trim();
			keyValue[1] = keyValue[1].replace("\"", "");
			keyValue[1] = keyValue[1].replaceAll("\\}", " ");
			keyValue[1] = keyValue[1].trim();

			// Match the right ArgumentKey to return
			for(ArgumentKey key: ArgumentKey.values()) {
				if(key.name().equals(keyValue[0])) {
					keyValueMap.put(key, keyValue[1]);
					break;
				}
			}
		}

		return keyValueMap;
	}

}
