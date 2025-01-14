import java.util.HashMap;
import java.util.ArrayList;


public class deck {
    public static final HashMap<Character, Integer> map = new HashMap<>();

    static {
        map.put('K', 10);
        map.put('Q', 10);
        map.put('J', 10);
        map.put('T', 10);
        map.put('9', 9);
        map.put('8', 8);
        map.put('7', 7);
        map.put('6', 6);
        map.put('5', 5);
        map.put('4', 4);
        map.put('3', 3);
        map.put('2', 2);
        map.put('A', 11);
        map.put('a', 1);
        map.put('0', 0);
    }

    private static int totalCardCount;

    public static final String[] cardFaces = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "T", "J", "Q", "K"};
    public static final String[] cardSuits = {"♠","♥","♦","♣"};

    public static ArrayList<String> cards = new ArrayList<>();

    public static void shuffleDeck() {
        totalCardCount = logic.deckSize * 52;
        for (int i = 0; i < logic.deckSize; ++i) {
            for (int k = 0; k < 13; ++k) {
                for (int j = 0; j < 4; ++j) {
                    cards.add(cardFaces[k] + cardSuits[j]);
                }
            }
        }
    }

    public static int getValue(String card) { //just returns the value
        return map.get(card.charAt(0));
    }

    public static String pullCard() { //pulls a card randomly, then deletes it from the arraylist
        int rand = (int)(Math.random() * totalCardCount);
        String newCard = cards.get(rand);
        cards.remove(rand);
        --totalCardCount;
        return newCard;
    }
}
