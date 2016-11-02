package de.benedikt_werner.UNO;

import java.util.Scanner;

import de.benedikt_werner.UNO.Game.GameOverListener;

public class Simulation implements GameOverListener {
	private int[] wins;
	private int gamesCount;
	private Player[] players;
	private Game game;
	
	public static void main(String[] args) {
		Simulation sim = new Simulation();
		Scanner in = new Scanner(System.in);

		System.out.print("BOT count: ");
		int botCount = in.nextInt();
		System.out.print("AI count: ");
		int aiCount = in.nextInt();
		System.out.print("Games count: ");
		int gamesCount = in.nextInt();
		in.close();
		
		sim.setup(botCount, aiCount);
		sim.simulate(gamesCount);
		sim.printResults();
	}
	
	public void setup(int botCount, int aiCount) {
		players = new Player[botCount + aiCount];
		
		for (int i = 0; i < botCount; i++) {
			players[i] = new Player("BOT_" + i + "", BasicAIController.class);
		}
		for (int i = botCount; i < aiCount; i++) {
			players[i] = new Player("AI_" + i + "", AdvancedAIController.class);
		}
		
		game = new Game(players);
	}
	
	public void simulate(int count) {
		for (int i = 0; i < count; i++) {
			simulate();
		}
	}
	
	public void simulate() {
		game.init();
		game.start();
	}

	@Override
	public void onGameOver(int winner) {
		wins[winner]++;
	}
	
	public void printResults() {
		for (int i = 0; i < wins.length; i++) {
			Log.print(i + ": " + wins[i] + " - " + Math.round(wins[i] * 100.0 / gamesCount) + "%");
		}
	}
}
