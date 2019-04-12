package com.reversi.model;

import java.util.ArrayList;

import com.reversi.client.Client;
import com.reversi.view.View;

public abstract class Model {
	
	private ArrayList<View> views = new ArrayList<>();
	protected Client client;

	public void addView(View view) {
		views.add(view);
	}
	
	public void setClient(Client client) {
		if (this.client!=null) {
			throw new IllegalStateException("Client already set.");
		}
		this.client = client;
	}
	
	// Call notify view every time something is updated
	public void notifyViews() {
		for (View view : views) {
			view.notify(this);
		}
	}
	
}
