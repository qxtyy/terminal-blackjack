import java.util.Arrays;
import java.util.HashMap;
/*
to do:
make actual scalable deck chances, maybe use arraylist to simulate drawing a card then removing that from the pool that'd be really easy. just (int)(math.random *53) then remove that index and keep playing. maybe even going further as to using the unicode card characters that'd be cool
and possible the option to select how many decks you want, and procedurally generate them based on deck size input
double down function w error prevention
add betting.
dealer logic too, make it stand on 17

do make it so that you can input q or c for continuing or quitting
definitely have an end state otherwise you could just keep playing but focus on that later

make sure to have good printing convention so that scanner doesn't have to take empty newlines
 */
public class logic {
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

    private char[] hand = {'0','0','0','0','0'};
    private char[] dHand = {'0','0','0','0','0'}; // dealer hand
    private final char[] cardList = {'A','2','3','4','5','6','7','8','9','T','J','Q','K'};
    private int total;
    private int cardCount;
    private int dTotal; //dealer total
    private int dCardCount; // dealer card count

    /*double down totals (worry abt later)
    private int totalOne;
    private int totalTwo;
    */

    logic() {

    } // i mean technically i dont need a constructor because it defaults to nothing

    public void roll() {
        cardCount = 2;
        total = 0;
        Arrays.fill(hand, '0'); //clears hand entirely
        hand[0] = cardList[(int) (Math.random() * 13)]; //change this for real chance later
        hand[1] = cardList[(int) (Math.random() * 13)];
        System.out.println("You:\n");
        for(int i = 0; i < cardCount; i++) {
            total+= map.get(hand[i]);
            System.out.print(hand[i] + " (" + map.get(hand[i]) + ") ");
        }
        //dealer
        dCardCount = 2;
        dTotal = 0;
        Arrays.fill(dHand, '0');
        dHand[0] = cardList[(int) (Math.random() * 13)];
        dHand[1] = cardList[(int) (Math.random() * 13)];
        System.out.println("\nDealer:\n");
        for(int i = 0; i < dCardCount; i++) {
            dTotal+= map.get(dHand[i]);
            System.out.print(dHand[i] + " (" + map.get(dHand[i]) + ") ");
        }
        if(total == 21 && dTotal == 21) {
            push();
        } else if (dTotal == 21) {
            lose();
        } else {
            win();
        }
    }
    public void hit() {
        cardCount++;
        hand[cardCount-1] = cardList[(int) (Math.random() * 13)];
        total+= map.get(hand[cardCount-1]);
        if(total > 21) {
            for(int i = 0; i < cardCount; i++) {
                if(hand[i] == 'A') {
                    hand[i] = 'a';
                }
            }
        }
        total+= map.get(hand[cardCount-1]); // if total was over 21 then it sets it to what it is w/o the high ace
        System.out.println("You:\n");
        for(int i = 0; i < cardCount; i++) {
            System.out.print(hand[i] + " (" + map.get(hand[i]) + ") ");
        }
        if(total > 21) { //checks if the player goes over 21 and thus loses
            System.out.println("You bust!");
            lose();
        }
        if(cardCount == 5 && total <= 21) { //checks if the player has five cards (five card charlie rule) that all > 21 and thus wins
            win();
        }
    }
    public void stand() { //this is where the majority of the dealer logic will reside, just hit and stand on 17 or bust, then check if hand > dHand or hand < dHand else it's a tie (push for betting)
        while(dCardCount < 17) { //stands on 17
            dCardCount++;
            dHand[cardCount-1] = cardList[(int) (Math.random() * 13)];
            dTotal+= map.get(dHand[dCardCount-1]);
            if(dTotal > 21) {
                for(int i = 0; i < dCardCount; i++) {
                    if(dHand[i] == 'A') {
                        dHand[i] = 'a';
                    }
                }
            }
            dTotal+= map.get(dHand[dCardCount-1]);
            System.out.println("\nDealer:\n");
            for(int i = 0; i < dCardCount; i++) {
                System.out.print(dHand[i] + " (" + map.get(dHand[i]) + ") ");
            }
        }
        if(dCardCount > 21 ) {
            win();
        }

    }


    public void doubleDown() {
        // yeah make it so you can only double down if two of same card, print "u cant" otherwise
    }
    public void win() {
        // make an 'end' state so you can't play, just q or c
        System.out.println("You won! Would you like to quit or continue?");
    }
    public void lose() {
        // make an 'end' state so you can't play, just q or c
        System.out.println("You lose! Would you like to quit or continue?");
    }
    public void push() {
        System.out.println("Tie. Would you like to quit or continue?");
    }
}
