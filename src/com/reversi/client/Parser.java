package com.reversi.client;

import java.util.Arrays;
import java.util.HashMap;
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
	
	public HashMap<ServerCommand, HashMap<ArgumentKey, String>> parseMessage(String message) {
		// Create a new map
		HashMap<ServerCommand, HashMap<ArgumentKey, String>> map = new HashMap<>();
		
		// Split the message in two parts ServerCommand and Map<ArgumentKey, String>
		String[] parts = message.split(Pattern.quote("{"));
			
		// First part ServerCommand
		//parts[0] = parts[0].substring(0, parts[0].length() - 1); // Remove last char
		parts[0] = parts[0].trim(); // Remove last whitespace
		parts[0] = parts[0].replaceAll(" ", "_");
		
		ServerCommand tempCommand = null;
		
		// Pick the right ServerCommand to return
		for(ServerCommand cmd: ServerCommand.values()) {
			if(cmd.name().equals(parts[0])) {
				tempCommand = cmd;
				break;
			}
		}
		
		if(tempCommand == null) {
			return null;
		}

		// If the ServerCommand has no arguments return null
		if(tempCommand.equals(ServerCommand.OK)) {
			map.put(tempCommand, null);
			return map;
		}
		
		// Second part HashMap<ArgumentKey, String>
		// Split the arguments up
		String[] arguments = parts[1].split(",");
		HashMap<ArgumentKey, String> keyValueMap = new HashMap<>();
		
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
		
		//System.out.println(keyValueMap.toString());

		map.put(tempCommand, keyValueMap);
		return map;
	}

}
