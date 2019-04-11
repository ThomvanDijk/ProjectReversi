package com.reversi.model;

import com.reversi.client.Client;
import com.reversi.view.GameView;

public abstract class Model {
	
	protected GameView gameView;
	protected Client client;

	
	public void setView(GameView view) {
		if (this.gameView!=null) {
			throw new IllegalStateException("View already set.");
		}
		this.gameView = view;
	}
	
	public void setClient(Client client) {
		if (this.client!=null) {
			throw new IllegalStateException("Client already set.");
		}
		this.client = client;
	}
	
	// Call notify view every time something is updated
	public void notifyView() {
		gameView.notify(this);
	}
	
}
