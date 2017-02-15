package de.benedikt_werner.UNO;

import java.lang.reflect.InvocationTargetException;

public class Player {
    private Game game;
    private String name;
    private CardStack hand;
    private PlayerController controller;

    public Player(String playerName, Class<? extends PlayerController> controllerClass) {
        name = playerName;
        try {
            controller = controllerClass.getDeclaredConstructor(new Class[]{Player.class}).newInstance(this);
        } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Could not create new instance of given controllerClass");
        }
    }

    public void requestPlay() {
        Card card;

        if (game.getTakeCount() > 0) {
            if (game.getTopCard().isWild())
                card = null;
            else
                card = controller.requestTakeCards(game.getTakeCount());

            if (card != null) {
                if (card.number == Card.TAKE_TWO || card.number == Card.WILD_FOUR) {
                    hand.remove(card);
                    game.playCard(card, this);
                    return;
                }
                else
                    throw new IllegalArgumentException("Can not play " + card + " on " + game.getTopCard() + " instead of taking cards");
            }
            else {
                Log.debug(name + " takes " + game.getTakeCount() + " cards");
                hand.addCards(game.takeCards());
            }
        }

        if (canPlay()) {
            card = controller.requestCard();
            hand.remove(card);
            game.playCard(card, this);
        }
        else {
            card = game.drawCard();
            Log.debug("Drew " + card);
            if (game.isPlayable(card)) {
                game.playCard(card, this);
            }
            else {
                hand.addCard(card);
                game.nextPlayer();
            }
        }
    }

    public String requestWildColor() {
        return controller.requestWildColor();
    }

    public boolean canPlay() {
        for (Card card : hand) {
            if (game.isPlayable(card))
                return true;
        }
        return false;
    }

    public boolean isPlayable(Card card) {
        return game.isPlayable(card);
    }

    public boolean hasWon() {
        return hand.isEmpty();
    }

    public String getName() {
        return name;
    }

    public CardStack getHand() {
        return hand;
    }

    public void setHand(CardStack newHand) {
        hand = newHand;
    }

    public Card getTopCard() {
        return game.getTopCard();
    }

    public void setGame(Game newGame) {
        game = newGame;
    }
}
