/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rami.adel.abdulrazek.application.models;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;
import net.java.games.input.Event;
import net.java.games.input.EventQueue;

/**
 *
 * @author Rami Adel
 */

public class JoyStickThread extends Thread{
        
        public static String Click ="";
        public static StringBuffer buffer;
          
        int x,y;
        public static PointerInfo pi = MouseInfo.getPointerInfo();;
        public static Point curso = pi.getLocation();;
        
        public void run(){

            
            while (true) {
                
			/* Get the available controllers */
			Controller[] controllers = ControllerEnvironment
					.getDefaultEnvironment().getControllers();
			if (controllers.length == 0) {
				System.out.println("Found no controllers.");
				System.exit(0);
			}

			for (int i = 0; i < controllers.length; i++) {
				/* Remember to poll each one */
				controllers[i].poll();

				/* Get the controllers event queue */
				EventQueue queue = controllers[i].getEventQueue();

				/* Create an event object for the underlying plugin to populate */
				Event event = new Event();

				/* For each object in the queue */
				while (queue.getNextEvent(event)) {

				
				        buffer = new StringBuffer(controllers[i]
							.getName());
					buffer.append(" at ");
					buffer.append(event.getNanos()).append(", ");
					Component comp = event.getComponent();
					buffer.append(comp.getName());
					float value = event.getValue();

				
                                        if(comp.getName()=="2"){
                                            Click = "Yes";
                                           
                                        }
                                        else{
                                            Click = "No";
                                        }
                                       
                                       // System.out.println(buffer);
					
					
				}
			}


		}
        }
}
