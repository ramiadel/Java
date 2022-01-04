package rami.adel.abdulrazek.application.services.Threads;

import rami.adel.abdulrazek.application.configurations.Settings;
import rami.adel.abdulrazek.application.models.Board;
import rami.adel.abdulrazek.application.models.Game;
import javafx.concurrent.Task;

/**
 * @author Rami Adel
 *
 */

public class BotPlayEasy extends Task<Void> {

	int randomX = 0;
	int randomY = 0;
	

	@Override
	protected Void call() throws Exception {
		
		/**
		 * While the current player is the bot, the game is AI vs HUMAN and the game wasn't stopped
		 */
		while(Game.CURRENT_PLAYER.get().equals(Settings.BOT_PIECE) == true && Game.STATUS.get() == true && Game.GAME_MODE == Settings.HUMAN_VS_AI) {
						
			// Generate coordinates
			this.randomX = (int) (Math.random() * (Settings.TILES_NBR_WIDTH-0));
			this.randomY = (int) (Math.random() * (Settings.TILES_NBR_WIDTH-0));
			
			// Check if the tile is empty
			if (Board.getBoard().get(this.randomX).get(this.randomY).currentShape.equals(Settings.BLANK)) {	
                                Thread.sleep(500);
				break;	
			}			
		}
		
		return null;
	}
	
	@Override
	protected void succeeded() {
		
		try {
			Thread.sleep(250);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		// Draw the cross
		Board.getBoard().get(this.randomX).get(this.randomY).drawCross();
		
		// And change to the circle
		Game.CURRENT_PLAYER.set(Settings.CIRCLE);

		// Increment the hit counter
		Game.HIT.set(Game.HIT.get() + 1);
		
		super.succeeded();
	}

}
