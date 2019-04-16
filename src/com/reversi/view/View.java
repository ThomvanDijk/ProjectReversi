package com.reversi.view;

import com.reversi.controller.ViewController;
import com.reversi.model.Model;

import javafx.application.Platform;

/**
 * Abstract class for GameView to hide some functions that GameView doesn't need
 * to know.
 * 
 * @author  Thom van Dijk
 * @version 1.0
 * @since   08-04-2019
 */
public abstract class View {

	protected final ViewController viewController;

	/**
	 * This function sets the assigned ViewController so that it can be used to
	 * update the Model.
	 * 
	 * @param viewController The Controller that needs to be called when a View has
	 *                       user input.
	 */
	public View(ViewController viewController) {
		this.viewController = viewController;
	}

	/**
	 * Notify is called from Model every time something is updated in Model.
	 * 
	 * @param model Reference to model to retrieve data from for the Views.
	 */
	public void notify(Model model) {
		Platform.runLater(() -> update(model));
	}

	/**
	 * This update function must be implemented and is used to get the new
	 * values from the Model.
	 *
	 * @param model Reference to model to retrieve data from for the Views.
	 */
	protected abstract void update(Model model);

	public ViewController getViewController() {
		return viewController;
	}

}