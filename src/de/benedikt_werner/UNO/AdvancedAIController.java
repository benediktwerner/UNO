package de.benedikt_werner.UNO;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class AdvancedAIController extends PlayerController {
    public AdvancedAIController(Player player) {
        super(player);
    }

    @Override
    public Card requestCard() {
        for (Card card : player.getHand()) {
            if (player.isPlayable(card)) {
                return card;
            }
        }
        return null;
    }

    @Override
    public Card requestTakeCards(int count) {
        for (Card card : player.getHand())
            if (card.number == Card.TAKE_TWO)
                return card;
        for (Card card : player.getHand())
            if (card.number == Card.WILD_FOUR)
                return card;
        return null;
    }

    @Override
    public String requestWildColor() {
        Map<String, Integer> colorCount = new HashMap<>(Card.colors.length);
        for (Card card : player.getHand())
            colorCount.put(card.color, colorCount.getOrDefault(card.color, 0) + 1);

        int maxCount = 0;
        String maxColor = "";
        for (String color : Card.colors) {
            int count = colorCount.getOrDefault(color, 0);
            if (count > maxCount) {
                maxCount = count;
                maxColor = color;
            }
        }
        if (maxCount == 0)
            return Card.colors[new Random().nextInt(Card.colors.length)];
        return maxColor;
    }
}
