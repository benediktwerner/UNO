package de.benedikt_werner.UNO;

import java.util.Scanner;

public class HumanPlayer extends PlayerController {
	private Scanner in = new Scanner(System.in);
	
	public HumanPlayer(Player player) {
		super(player);
	}

	@Override
	public Card requestCard() {
		Log.print("-------------------------");
		Log.print("It's your turn " + player.getName());
		Log.print(player.getHand());
		Log.print("Stack: " + player.getTopCard());
		
		int input;
		while (true) {
			Log.print("Choose a card to play: ");
			input = in.nextInt();
			
			if (0 <= input && input < player.getHand().size() && player.isPlayable(player.getHand().get(input))) {
				return player.getHand().get(input);
			}
			else {
				Log.print("Invalid card!");
			}
		}
	}

	@Override
	public Card requestTakeCards(int count) {
		Log.print("-------------------------");
		Log.print("It's your turn " + player.getName());
		Log.print("You have to draw " + count + " cards or play a TAKE TWO or WILD TAKE FOUR card:");
		
		CardStack takeCards = new CardStack();
		for (Card card : player.getHand()) {
			if (card.number == Card.TAKE_TWO || card.number == Card.WILD_FOUR) {
				takeCards.addCard(card);
			}
		}
		Log.print("-1: Take cards");
		Log.print(takeCards);
		
		if (takeCards.isEmpty()) {
			Log.print("You don't have any TAKE TWO or WILD TAKE FOUR cards!");
			return null;
		}
		int input;
		while (true) {
			Log.print("Choose a play: ");
			input = in.nextInt();
			
			if (input == -1) {
				return null;
			}
			if (0 <= input && input < takeCards.size()) {
				return takeCards.get(input);
			}
			else {
				Log.print("Invalid play!");
			}
		}
	}

	@Override
	public String requestWildColor() {
		Log.print("Choose your wild color from these:");
		for (int i = 0; i < Card.colors.length; i++)
			Log.print(i + ": " + Card.colors[i]);
		
		int input;
		while (true) {
			Log.print("Choose wild color: ");
			input = in.nextInt();
			
			if (0 <= input && input < Card.colors.length) {
				return Card.colors[input];
			}
			else {
				Log.print("Invalid color!");
			}
		}
	}
}
