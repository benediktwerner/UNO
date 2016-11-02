package de.benedikt_werner.UNO;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Game {
	private CardStack stack;
	private CardStack discardStack;
	private Player[] players;
	private int activePlayer;
	private boolean gameFinished;
	private boolean dirClockwise;
	private int takeCount;
	private String wildColor;
	
	private List<GameOverListener> gameOverListeners;
	
	public interface GameOverListener {
		public void onGameOver(int winner);
	}
	
	public void addGameOverListener(GameOverListener listener) {
		gameOverListeners.add(listener);
	}
	
	public Game(Player[] ps) {
		players = ps;
		for (Player p : players)
			p.setGame(this);
		init();
	}
	
	public void init() {
		activePlayer = 0;
		gameFinished = false;
		dirClockwise = true;
		takeCount = 0;
		wildColor = "";
		
		gameOverListeners = new LinkedList<>();
		
		discardStack = new CardStack();
		stack = CardStack.getFullStack();
		stack.shuffle();
		
		for (Player p : players) {
			p.setHand(new CardStack(stack.drawCards(7)));
		}
		discardStack.addCard(stack.drawCard());
	}
	
	public void start() {
		int playerIndex = 0;
		while (!gameFinished) {
			playerIndex = activePlayer;
			Player player = players[activePlayer];
			Log.debug(player.getName() + "'s turn with " + getTopCard() + " on the stack and " + player.getHand().size() + " cards in hand");
			player.requestPlay();
		}
		for (GameOverListener listener : gameOverListeners)
			listener.onGameOver(playerIndex);
	}
	
	public void playCard(Card card, Player player) {
		if (isPlayable(card)) {
			discardStack.addCard(card);
			Log.debug(player.getName() + " played: " + card);
			
			// Apply card effect (if any)
			if (card.number == Card.SWAP_DIR)
				dirClockwise = !dirClockwise;
			else if (card.number == Card.TAKE_TWO)
				takeCount += 2;
			else if (card.number == Card.JUMP)
				nextPlayer();
			else if (card.isWild()) {
				wildColor = player.requestWildColor();
				if (!Arrays.stream(Card.colors).anyMatch(x -> x.equals(wildColor)))
					throw new IllegalStateException(wildColor + "is not a valid wild color");
				Log.debug(player.getName() + " choose " + wildColor);
				if (card.number == Card.WILD_FOUR)
					takeCount += 4;
			}
			
			if (player.hasWon()) {
				Log.info(player.getName() + " won!");
				gameFinished = true;
			}
			nextPlayer();
		}
		else {
			throw new IllegalArgumentException("Invalid card: " + card + " with " + getTopCard() + " on stack");
		}
	}
	
	public void nextPlayer() {
		if (dirClockwise)
			activePlayer += (activePlayer < players.length - 1 ? 1 : -activePlayer);
		else
			activePlayer += (activePlayer > 0 ? -1 : players.length - 1);
	}
	
	public boolean isPlayable(Card card) {
		Card top = getTopCard();
		if (top.isWild())
			return (top.number == card.number || wildColor.equals(card.color) || wildColor.equals("")) && !card.isWild();
		else if (card.isWild())
			return true;
		else
			return (top.number == card.number || top.color.equals(card.color));
	}
	
	public Card getTopCard() {
		return discardStack.peak();
	}
	
	public List<Card> takeCards() {
		List<Card> takeCards = new LinkedList<>();
		for (; takeCount > 0; takeCount--)
			takeCards.add(drawCard());
		return takeCards;
	}
	
	public int getTakeCount() {
		return takeCount;
	}
	
	public Card drawCard() {
		if (stack.isEmpty()) {
			stack = discardStack;
			discardStack = new CardStack();
			discardStack.addCard(stack.drawCard());
			stack.shuffle();
		}
		return stack.drawCard();
	}
}
