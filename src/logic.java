import java.util.Arrays;
import java.util.HashMap;
/*
to do:
make actual scalable deck chances, maybe use arraylist to simulate drawing a card then removing that from the pool that'd be really easy. just (int)(math.random *53) then remove that index and keep playing. maybe even going further as to using the unicode card characters that'd be cool
and possible the option to select how many decks you want, and procedurally generate them based on deck size input
double down function w error prevention
add betting.


definitely have an end state otherwise you could just keep playing but focus on that later

make sure to have good printing convention so that scanner doesn't have to take empty newlines
make METHODS for repeated actions yeah condense the code

make betting toggleable
make choice for deck size chance

fix that you can hit and stand before starting a game. would be fixed by an end state
lol you can just keep hitting sobsob

maybe make a tester method that assigns the dealer the two inputted chars maybe
 */
public class logic {
    private static final HashMap<Character, Integer> map = new HashMap<>();

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

    private char[] hand = {'0', '0', '0', '0', '0'};
    private char[] dHand = {'0', '0', '0', '0', '0'}; // dealer hand
    public static final char[] cardList = {'A', '2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K'};
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
        hand[0] = cardGeneration.cardGen(); //change these for real chance later
        hand[1] = cardGeneration.cardGen();
        if(hand[0] == 'A' && hand[1] == 0) { // if dealt two aces, change one to little a
            hand[1] = 'a';
        }
        System.out.println("You:");
        for (int i = 0; i < cardCount; i++) {
            total += map.get(hand[i]);
            System.out.print(hand[i] + " (" + map.get(hand[i]) + ") | ");
        }
        System.out.print("Total: " + total);
        //dealer
        dCardCount = 2;
        dTotal = 0;
        Arrays.fill(dHand, '0');
        dHand[0] = cardGeneration.cardGen();
        dHand[1] = cardGeneration.cardGen();
        if(dHand[0] == 'A' && dHand[1] == 0) { // if dealt two aces, change one to little a
            dHand[1] = 'a';
        }
        System.out.println("\nDealer:");
        dTotal = map.get(dHand[0]) + map.get(dHand[1]);
        if(dTotal != 21) { //this replaces the for loop because the dealer doesnt show their hand !!!!!
            System.out.print(dHand[0] + " (" + map.get(dHand[0]) + ") | " + "\uD83C\uDCA0 (?) | ");
            System.out.println("Total: ??");
        } else { //if dealer hits a blackjack, it reveals the hidden card immediately
            System.out.print(dHand[0] + " (" + map.get(dHand[0]) + ") | " + dHand[1] + " (" + map.get(dHand[1]) + ") | ");
            System.out.println("Total: " + dTotal + "\nDealer Blackjack!");
        }
        //instant blackjack stuff
        if (total == 21 && dTotal == 21) {
            push();
        } else if (dTotal == 21) {
            lose();
        } else if (total == 21) {
            System.out.print("Blackjack!");
            win();
        }
    }

    public void hit() {
        cardCount++;
        hand[cardCount - 1] = cardGeneration.cardGen();
        total += map.get(hand[cardCount - 1]);
        if (total > 21) { //checks for ace
            for (int i = 0; i < cardCount; i++) {
                if (hand[i] == 'A') {
                    hand[i] = 'a';
                    break; //only changes one if there is more than ace :|
                }
            }
        }
        total = 0;
        for (int i = 0; i < cardCount; i++) { // if total was over 21 then it sets it to what it is w/o the high ace
            total += map.get(hand[i]); //!!!!!!!!! probably the worst possible solution to this problem, optimize later :D maybe make a temp hasAce boolean instead of just recounting it
        }
        System.out.println("You:");
        for (int i = 0; i < cardCount; i++) {
            System.out.print(hand[i] + " (" + map.get(hand[i]) + ") | ");
        }
        System.out.println("Total: " + total);
        if (total > 21) { //checks if the player goes over 21 and thus loses
            System.out.print("Bust!");
            lose();
        } else if (cardCount == 5) { //checks if the player has five cards (five card charlie rule) that all > 21 and thus wins
            System.out.print("\nYou have five cards!");
            win();
        } else if (total == 21) {
            stand();
        } else {
            prompt();
        }
    }

    public void stand() {
        if(dTotal < 17) {
            while (dTotal < 17) { // hits and stands on 17
                dCardCount++;
                dHand[dCardCount - 1] = cardGeneration.cardGen();;
                dTotal += map.get(dHand[dCardCount - 1]);
                if (dTotal > 21) { //checks for ace
                    for (int i = 0; i < dCardCount; i++) {
                        if (dHand[i] == 'A') {
                            dHand[i] = 'a';
                            break;
                        }
                    }
                }
                dTotal = 0;
                for (int i = 0; i < dCardCount; i++) {
                    dTotal += map.get(dHand[i]); //!!!!!!!!! fix
                }
                System.out.println("\nDealer:");
                for (int i = 0; i < dCardCount; i++) {
                    System.out.print(dHand[i] + " (" + map.get(dHand[i]) + ") | ");
                }
                System.out.println("Total: " + dTotal);
            }
        } else { //reveals dealer hand if over 17
            System.out.println("\nDealer:");
            for (int i = 0; i < dCardCount; i++) {
                System.out.print(dHand[i] + " (" + map.get(dHand[i]) + ") | ");
            }
            System.out.println("Total: " + dTotal);
        }
        //win lose or push
        if (dTotal > 21 || dTotal < total) {
            win();
        } else if (dTotal > total) {
            lose();
        } else {
            push();
        }
    }

    /*
    private char cardGen() { // make good later
        return cardList[(int) (Math.random() * 13)];
    }
    */

    public void doubleDown() {
        // yeah make it so you can only double down if two of same card, print "u cant" otherwise
    }

    public void win() {
        // make an 'end' state so you can't play, just q or c
        System.out.println("\nYou won! Would you like to quit or continue?");
    }

    public void lose() {
        // make an 'end' state so you can't play, just q or c
        System.out.println("\nYou lost! Would you like to quit or continue?");
    }

    public void push() {
        // make sure for all of the win lose or push it does something with the bet
        System.out.println("\nYou tied! Would you like to quit or continue?");
    }

    public void prompt() {
        System.out.print("\nHit, stand, or double down?");
    }
}