package com.reversi.server;

public class Event {

	
	private Handlers event;
	private String message;

	Events(Handlers event, String msg){
		this.event = event;
        this.message = msg;
	}
	
	public Handlers getEvent() { return event; }
	public String getMessage() { return message; }
}


	

