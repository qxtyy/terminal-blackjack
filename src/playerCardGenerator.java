import java.util.HashMap;

public class playerCardGenerator {
    private static final HashMap<Character,Integer> map = new HashMap<>();
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
    // maybe use an arraylist for cards? or just use five cards ohh but arraylist would be so easy to print with a counter
    private char[] hand = {'0','0','0','0','0'};
    private final char[] cardList = {'A','2','3','4','5','6','7','8','9','T','J','Q','K'};
    //gotta make the option to make ace a 1 or 11
    private int total = 0;
    private int cardCount = 2;

    playerCardGenerator() {

    } // i mean technically i dont need a constructor because it defaults to nothing

    public void roll() {
        cardCount = 2;
        total = 0;
        for (int i = 0; i < hand.length; i++) {
            hand[i] = '0';
        }
        hand[0] = cardList[(int) (Math.random() * 14)];
        hand[1] = cardList[(int) (Math.random() * 14)];
        for(int i = 0; i < cardCount; ++i) {
            total+= map.get(hand[i]);
        }
        for(int i = 0; i < cardCount; ++i) {
            System.out.print(hand[i] + " (" + map.get(hand[i]) + ") ");
        } // make sure to make an exception for aces idk implement later

    }
    public void hit() {
        cardCount++;
        checkAce();


    }
    public void stand() {

    }
    public void checkAce(){
        if(total > 21) {
            for(int i = 0; i < cardCount; ++i) {
                if(hand[i] == 'A') {
                    hand[i] = 'a';
                }
            }
        }
    }



    // if (object.getTotal > 21 && (.get cardOne == 'A' || .get cardTwo == 'A') {
    // gotta make it so that you can stand on 11 + other card without busting
    // no no this is all wrong just default it to 11 and if total > 21 then go to 1
}
