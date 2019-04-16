package com.reversi.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

/**
 * This class converts strings from the server to workable lists, maps or
 * strings and converts the message header to a valid command.
 * 
 * @author Thom van Dijk
 * @version 1.0
 * @since 08-04-2019
 */
public class Parser {
	
	/**
	 * Contains all the different commands the sever may send to the client.
	 */
	public enum ServerCommand {
		ERR,
		OK,
		SVR_HELP,
		SVR_GAME_MATCH,
		SVR_GAME_YOURTURN,
		SVR_GAME_MOVE,
		SVR_GAME_CHALLENGE,
		SVR_GAME_CHALLENGE_CANCELLED,
		SVR_GAME,
		SVR_GAMELIST,
		SVR_PLAYERLIST,
		SVR_GAME_WIN,
		SVR_GAME_LOSS,
		SVR_GAME_DRAW
	}

	/**
	 * Contains all the different keys inside the map data structure that the server
	 * may send to the client.
	 */
	public enum ArgumentKey {
		DEFAULT,
		GAMETYPE,
		PLAYERTOMOVE,
		OPPONENT,
		PLAYER,
		DETAILS,
		MOVE,
		PLAYERONESCORE,
		PLAYERTWOSCORE,
		COMMENT,
		CHALLENGER,
		CHALLENGENUMBER
	}
	
	/**
	 * This function converts a server message to a map with ServerCommand and
	 * arguments. Help messages from the server are not allowed and will not be
	 * captured!
	 * 
	 * @param  message Raw String message from the server.
	 * @return         Returns a map with correct ServerCommand as the key and
	 *                 arguments as value.
	 * @see            HashMap
	 * @see			   ServerCommand
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
	
	/**
	 * Converts a string to a ServerCommand.
	 * 
	 * @param  commandString A string which contains a command.
	 * @return               Returns a ServerCommand.
	 * @see                  ServerCommand
	 */
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
	
	/**
	 * Converts a string to a list containing the values of the requested data from
	 * the server.
	 * 
	 * @param  listString A string formatted as a list.
	 * @return            Returns a list containing the values of the requested data
	 *                    from the server.
	 * @see               List
	 */
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
	
	/**
	 * Converts a string to a map. The incoming string is already formatted as a
	 * map and comes directly from the server.
	 * 
	 * @param  mapString A string formatted as a map.
	 * @return           Returns a map with key as ArgumentKey and value as a
	 *                   string.
	 * @see              HashMap
	 * @see              ArgumentKey
	 */
	public HashMap<ArgumentKey, String> stringToMap(String mapString) {
		HashMap<ArgumentKey, String> keyValueMap = new HashMap<>();
		
		// Split the arguments up
		String[] arguments = mapString.split(",");		
		
		// Add the keys as ArgumentKey and the values as a string
		for(int i = 0; i < arguments.length; i++) {
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
