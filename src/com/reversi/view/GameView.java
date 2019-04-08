/**
* Abstract class for GameView to hide some functions that GameView doesn't need to know. 
* 
* @author Thom van Dijk
* @version 1.0
* @since 08-04-2019
*/

package com.reversi.view;

import com.reversi.controller.UserController;
import com.reversi.model.GameModel;
import com.reversi.model.Model;

public class GameView extends View {

	public GameView(UserController userController, String[] args) {
		super(userController);

		// userController.GiveUserCommando("commando")

		Window window = new Window();
		window.rmain(args);
	}
	
	/**
	 * This run function is used to refresh pages e.d.
	 *
	 * @param model Model
	 */
	@Override
	public void run() {
		// If needed this function keeps running until the thread is terminated
	}
	
	/**
	 * This update function must be used to get the new values from model (GameModel).
	 * Update is only called when model had some changes.
	 *
	 * @param model Model
	 */
	@Override
	protected void update(Model model) {
		GameModel gameModel = (GameModel) model; // cast
	}

}
