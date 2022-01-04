package rami.adel.abdulrazek.application.models;

import rami.adel.abdulrazek.application.configurations.Colors;
import rami.adel.abdulrazek.application.configurations.Settings;
import rami.adel.abdulrazek.application.configurations.Sounds;
import rami.adel.abdulrazek.application.configurations.Textures;
import rami.adel.abdulrazek.application.gui.main.MainController;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.StackPane;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.Event;
import rami.adel.abdulrazek.application.models.JoyStickThread;
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;

/**
 * A Tile
 * @author Rami Adel
 */

public class Tile extends StackPane {
	
	/**
	 * The player piece
	 */
	public ImageView contentImg;
	
	/**
	 * Rectangle
	 */
	public Rectangle shape;
	
	/**
	 * Current state
	 */
	public String currentShape = Settings.BLANK;
        

        Controller[] controllers;
        Component comp;
	Event ev;
        StringBuffer buffer;
        Robot r ;

	/**
	 * Constructor
	 */
	public Tile() {


		// Piece image
		this.contentImg = new ImageView();
		
		// Rectangle
		this.shape = new Rectangle();
		this.shape.widthProperty().bind(Settings.TILE_WIDTH);
		this.shape.heightProperty().bind(Settings.TILE_HEIGHT);
		
		// Fill colors and stroke
		this.shape.setFill(Color.web(Colors.TILE_FILL));
		this.shape.setStroke(Color.web(Colors.TILE_STROKE));
		
		// Rounded corners
		this.shape.setArcWidth(Settings.RADIUS);
		this.shape.setArcHeight(Settings.RADIUS);
				
		// Align
		setAlignment(Pos.CENTER);
		
		// Add all the elements to the pane
		getChildren().addAll(this.shape, this.contentImg);

                try {
                    r = new Robot();
                } catch (AWTException ex) {

                }

                
                this.setOnMouseMoved(event -> {

                    this.shape.setFill(Color.web("#ff2267"));
                    
                    if(JoyStickThread.Click=="Yes"){
                        
                        
                        r.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                        r.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                        r.delay(1000);
                        r.mouseMove(JoyStickThread.curso.x, JoyStickThread.curso.y);
                        System.out.println(JoyStickThread.curso.x+ "+" +JoyStickThread.curso.y);
                        //r.createScreenCapture(screenRect)
                    }
                    
                });
        
                /*
                this.setOnKeyPressed(event -> {
                System.out.println("Ramiiii");
                });
                */
                
                
                this.setOnMouseExited(event -> {
                    JoyStickThread.curso = JoyStickThread.pi.getLocation();
                    this.shape.setFill(Color.web(Colors.TILE_FILL));
                    
                });

       
                
		this.setOnMouseClicked(event -> {
				
               
			// If left hand click
			if (event.getButton() == MouseButton.PRIMARY)
			{
				
				// If it's the end of the game, clear it
				if (rami.adel.abdulrazek.application.models.Game.STATUS.get() == false) {

					// New game
					rami.adel.abdulrazek.application.models.Game.getInstance().clear();
					
					return;
				}
				// If it's not empty
				else if (!this.currentShape.equals(Settings.BLANK)) {
					return;
				}
				/**
				 * If AI VS HUMAN but it's the player round = can play
				 * <br>
				 * If HUMAN VS HUMAN = can play
				 */
				else if ((rami.adel.abdulrazek.application.models.Game.GAME_MODE.equals(Settings.HUMAN_VS_AI) && 
							rami.adel.abdulrazek.application.models.Game.CURRENT_PLAYER.getValue().equals(Settings.CIRCLE)) ||
							rami.adel.abdulrazek.application.models.Game.GAME_MODE.equals(Settings.MULTIPLAYERS)
				) {

					// The next player
					String nextPlayer = Settings.CIRCLE;
					
					// If the are currently playing with the cross
                                        
					if (rami.adel.abdulrazek.application.models.Game.CURRENT_PLAYER.get().equals(Settings.CROSS)) {
						
						// Draw the cross
						this.drawCross();

						// The next player
						nextPlayer = Settings.CIRCLE;
						
					} 
            
                                        else {
                                                
						// Draw the circle
						this.drawCircle();		
						
						// The next player
						nextPlayer = Settings.CROSS;
					}
					
					// Increment the hit counter
					rami.adel.abdulrazek.application.models.Game.HIT.set(rami.adel.abdulrazek.application.models.Game.HIT.get() + 1);
					
					// And change to the cross
					rami.adel.abdulrazek.application.models.Game.CURRENT_PLAYER.set(nextPlayer);
				}
			}
		});
	}

	/**
	 * Draw a cross
	 */
	public void drawCross() {		

		// Insert the image
		this.contentImg.setImage(new Image(getClass().getResourceAsStream(Textures.CROSS)));
		// Image anti-aliasing
		this.contentImg.setSmooth(true);

		// Play a sound effect
		if (MainController.STATUS_SOUND.get() == true) {
			AudioClip sound = new AudioClip(this.getClass().getResource(Sounds.CLICK_1).toString());
			sound.play();			
		}
		
		// Set he size of it
		this.contentImg.fitWidthProperty().bind(Settings.TILE_WIDTH.divide(Settings.PIECE_RATIO));
		this.contentImg.fitHeightProperty().bind(Settings.TILE_HEIGHT.divide(Settings.PIECE_RATIO));
		
		// Animation
		MainController.fadeOut(this.contentImg, 400);

		// Change the current shape on the tile
		this.currentShape = Settings.CROSS;
	}

	/**
	 * Draw a cross
	 */
	private void drawCircle() {	

		// Insert the image
		this.contentImg.setImage(new Image(getClass().getResourceAsStream(Textures.CIRCLE)));
		// Image anti-aliasing
		this.contentImg.setSmooth(true);

		// Play a sound effect
		if (MainController.STATUS_SOUND.get() == true) {
			AudioClip sound = new AudioClip(this.getClass().getResource(Sounds.CLICK_2).toString());
			sound.play();
		}
		
		// Set he size of it
		this.contentImg.fitWidthProperty().bind(Settings.TILE_WIDTH.divide(Settings.PIECE_RATIO));
		this.contentImg.fitHeightProperty().bind(Settings.TILE_HEIGHT.divide(Settings.PIECE_RATIO));
		
		// Animation
		MainController.fadeOut(this.contentImg, 400);
		
		// Change the current shape on the tile
		this.currentShape = Settings.CIRCLE;
	}
	
	@Override
	public String toString() {		
		return this.currentShape;
	}

	/**
	 * Return the current value as {@code Integer}
	 * @return {@code Integer}
	 */
	public Integer asInteger() {
		
		if (this.currentShape.equals(Settings.CROSS)) {
			return 1;			
		}
		else if (this.currentShape.equals(Settings.CIRCLE)) {
			return -1;			
		}
		else if (this.currentShape.equals(Settings.BLANK)) {
			return 0;			
		}
		
		return 0;
	}


        
   
}
