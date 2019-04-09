package com.reversi.model;

import com.reversi.view.GameView;

public abstract class Model implements Runnable {

	protected GameView gameView;

	public void setView(GameView view) {
		if (this.gameView != null) {
			throw new IllegalStateException("View already set.");
		}
		this.gameView = view;
	}

	// Call notify view every time something is updated
	public void notifyView() {
		gameView.notify(this);
	}

}
