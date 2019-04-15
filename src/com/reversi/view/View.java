/**
* Abstract class for GameView to hide some functions that GameView doesn't need to know. 
* 
* @author Thom van Dijk
* @version 1.0
* @since 08-04-2019
*/

package com.reversi.view;

import com.reversi.controller.UserController;
import com.reversi.model.Model;

import javafx.application.Platform;

public abstract class View {

	protected final UserController userController;

	public View(UserController userController) {
		this.userController = userController;
	}

	/**
	 * Notify is called from model (GameModel) every time something is updated...
	 * 
	 * @param model Model
	 */
	public void notify(Model model) {
		Platform.runLater(() -> update(model));
	}

	/**
	 * This update function must be implemented and needs to be used to get the new
	 * values from model (GameModel).
	 *
	 * @param model Model
	 */
	protected abstract void update(Model model);

	public UserController getUserController() {
		return userController;
	}

}