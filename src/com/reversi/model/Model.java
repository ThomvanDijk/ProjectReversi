package com.reversi.model;

import java.util.ArrayList;

import com.reversi.client.Client;
import com.reversi.view.View;

/**
 * This class is an abstract class, that gameModel uses.
 * 
 * @author Thom van Dijk
 * @version 1.0
 * @since 12-04-2019
 */
public abstract class Model {

	private ArrayList<View> views = new ArrayList<>();
	protected Client client;

	/**
	 * Adds a view to Model so that the Views can be updated.
	 * 
	 * @param view The View that is used to display items and needs to be updated.
	 */
	public void addView(View view) {
		views.add(view);
	}

	/**
	 * This function sets the given Client. There is only one client and it must be
	 * called.
	 * 
	 * @param client The Client that handles connection to the server.
	 */
	public void setClient(Client client) {
		if (this.client != null) {
			throw new IllegalStateException("Client already set.");
		}
		this.client = client;
	}

	/**
	 * A function to notify all Views. This function is called when the model has
	 * updates.
	 */
	public void notifyViews() {
		for (View view : views) {
			view.notify(this);
		}
	}

}