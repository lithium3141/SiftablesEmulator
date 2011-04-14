package com.lithium3141.SifteoCubes;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.lithium3141.SifteoCubes.Games.*;

public class Emulator {

	private static EmulatorFrame emulatorFrame;
	
	private static Set<Cube> cubes;
	
	private static Game activeGame;
	private static List<Game> games;
	
	public static enum InteractionMode {
		Normal,
		Removing
	};
	
	private static InteractionMode interactionMode;
	
	/**
	 * @param args Command-line arguments. Ignored.
	 */
	public static void main(String[] args) {
		// Instantiate cube variables
		cubes = new HashSet<Cube>();
		interactionMode = InteractionMode.Normal;
		
		// Game variables
		games = new ArrayList<Game>();
		
		// Create the JFrame
		emulatorFrame = new EmulatorFrame();
		emulatorFrame.setVisible(true);
		
		load();
	}
	
	/**
	 * Do post-initialization startup. Commonly used to register games.
	 */
	public static void load() {
		registerGame(new NullGame());
		registerGame(new RGBStripeGame());
		registerGame(new ColorBlockGame());
	}
	
	/**
	 * Add a new cube to active emulation.
	 */
	public static void addCube() {
		Cube cube = new Cube();
		cubes.add(cube);
		emulatorFrame.addedCube(cube);
	}
	
	public static void removeCube(Cube cube) {
		cubes.remove(cube);
		emulatorFrame.removedCube(cube);
	}
	
	/**
	 * @return the set of cubes in play.
	 */
	public static Set<Cube> getCubes() {
		return cubes;
	}
	
	/**
	 * Clean up and exit the Emulator.
	 */
	public static void quit() {
		System.exit(0);
	}

	public static void setInteractionMode(InteractionMode interactionMode) {
		Emulator.interactionMode = interactionMode;
	}

	public static InteractionMode getInteractionMode() {
		return interactionMode;
	}

	public static void setActiveGame(Game game) {
		if(Emulator.activeGame != null) {
			Emulator.activeGame.unload();
		}
		Emulator.activeGame = game;
		if(game != null) {
			Emulator.activeGame.load();
			for(Cube cube : cubes) {
				cube.needsRefresh();
			}
		}
	}

	public static Game getActiveGame() {
		return activeGame;
	}

	public static List<Game> getGames() {
		return games;
	}
	
	public static void registerGame(Game game) {
		if(activeGame == null) {
			setActiveGame(game);
		}
		
		games.add(game);
		emulatorFrame.registeredGame(game);
	}
	
	public static void unregisterGame(Game game) {
		if(game == activeGame) {
			setActiveGame(null);
		}
		
		games.remove(game);
		emulatorFrame.unregisteredGame(game);
	}
	
	public static void foundAdjacent(Cube c1, int e1, Cube c2, int e2) {
		System.out.println("Found adjacent: " + c1 + " to " + c2);
		if(activeGame != null && c1.adjacencyAlong(e1) == null && c2.adjacencyAlong(e2) == null) {
			activeGame.cubesJoined(c1, e1, c2, e2);
		}
		if(c1.adjacencyAlong(e1) == null) {
			c1.joinedCube(c2, e1);
		}
		if(c2.adjacencyAlong(e2) == null) {
			c2.joinedCube(c1, e2);			
		}
	}
	
	public static void foundSeparate(Cube c1, int e1, Cube c2, int e2) {
		System.out.println("Found separate: " + c1 + " to " + c2);
		if(activeGame != null && c1.adjacencyAlong(e1).equals(c2) && c2.adjacencyAlong(e2).equals(c1)) {
			activeGame.cubesSeparated(c1, e1, c2, e2);
		}
		if(c1.adjacencyAlong(e1) != null && c1.adjacencyAlong(e1).equals(c2)) {
			c1.separatedCube(c2, e1);
		}
		if(c2.adjacencyAlong(e2) != null && c2.adjacencyAlong(e2).equals(c1)) {
			c2.separatedCube(c1, e2);			
		}
	}

}
