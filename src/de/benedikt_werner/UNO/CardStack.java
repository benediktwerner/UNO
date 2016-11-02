package de.benedikt_werner.UNO;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class CardStack implements Iterable<Card> {
	private LinkedList<Card> stack;
	
	public CardStack(List<Card> cards) {
		stack = new LinkedList<>(cards);
	}
	
	public CardStack() {
		stack = new LinkedList<>();
	}
	
	public void shuffle() {
		Random random = new Random();
		for (int i = stack.size() - 1; i >= 0; i--) {
			int rand = random.nextInt(i + 1);
			Card c = stack.get(i);
			stack.set(i, stack.get(rand));
			stack.set(rand, c);
		}
	}
	
	public Card peak() {
		return stack.getLast();
	}
	
	public Card get(int index) {
		return stack.get(index);
	}
	
	public Card remove(int index) {
		return stack.remove(index);
	}
	
	public void remove(Card card) {
		stack.remove(card);
	}
	
	public Card drawCard() {
		return stack.removeLast();
	}
	
	public LinkedList<Card> drawCards(int count) {
		LinkedList<Card> cards = new LinkedList<>();
		for (int i = 0; i < count; i++)
			cards.add(drawCard());
		return cards;
	}
	
	public void addCards(List<Card> cards) {
		stack.addAll(cards);
	}
	
	public void addCard(Card card) {
		stack.addLast(card);
	}
	
	public int size() {
		return stack.size();
	}
	
	public boolean isEmpty() {
		return stack.isEmpty();
	}
	
	public String toString() {
		String string = "";
		for (int i = 0; i < stack.size(); i++) {
			string += i + ": " + stack.get(i) + (i != stack.size()-1 ? "\n" : "");			
		}
		return string;
	}
	
	/**
	 * Returns a new CardStack with all cards in the game.
	 */
	public static CardStack getFullStack() {
		LinkedList<Card> cards = new LinkedList<>();
		
		for (int num = -3; num < 10; num++)
			for (String col : Card.colors)
				cards.add(new Card(num, col));
		for (int num = 1; num < 10; num++)
			for (String col : Card.colors)
				cards.add(new Card(num, col));
		for (int num = -3; num < 0; num++)
			for (String col : Card.colors)
				cards.add(new Card(num, col));
		for (int num = 0; num < 4; num++) {
			cards.add(new Card(Card.WILD, Card.BLACK));
			cards.add(new Card(Card.WILD_FOUR, Card.BLACK));
		}
		return new CardStack(cards);
	}

	@Override
	public Iterator<Card> iterator() {
		return stack.iterator();
	}
}
