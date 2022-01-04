package rami.adel.abdulrazek.application.services.Threads;

import rami.adel.abdulrazek.application.ai.NeuralNetwork;

/**
 * @author Rami Adel
 *
 */

public class RunningThreads {
	
	/**
	 * Train of the medium model
	 */
	public static NeuralNetwork MEDIUM_TRAIN;
	
	/**
	 * Train of the hard model
	 */
	public static NeuralNetwork HARD_TRAIN;

	/**
	 * The thread for the medium training
	 */
	public static Thread THREAD_MEDIUM;
	
	/**
	 * The thread for the hard training
	 */
	public static Thread THREAD_HARD;
	
}
