import java.util.HashMap;
/*
to do:
make actual scalable deck chances, maybe use arraylist to simulate drawing a card then removing that from the pool that'd be really easy. just (int)(math.random *53) then remove that index and keep playing. maybe even going further as to using the unicode card characters that'd be cool
and possible the option to select how many decks you want, and procedurally generate them based on deck size input
double down function w error prevention


make METHODS for repeated actions yeah condense the code and make it MORE READABLE damn

make choice for deck size chance

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
    private int total; //player card value total
    private int cardCount; //player card count
    private int dTotal; //dealer total
    private int dCardCount; // dealer card count

    /*split totals
    private int totalOne;
    private int totalTwo;
    */

    private static int money = 100; //starting money 100? idk
    private static int currentBet;

    private int deckSize;

    private boolean state; //false will be end state, true will be ongoing state

    logic() {

    } // i mean technically i dont need a constructor because it defaults to nothing

    public void roll() {
        cardCount = 2; //resets card count to 2
        total = 0; //resets total to 0
        hand[0] = cardGeneration.cardGen(); //change these for real chance later
        hand[1] = cardGeneration.cardGen();
        if(hand[0] == 'A' && hand[1] == 'A') { // if dealt two aces, change one to little a (1)
            hand[1] = 'a';
        }
        total = map.get(hand[0]) + map.get(hand[1]);
        System.out.println("You:");
        System.out.print(hand[0] + " (" + map.get(hand[0]) + ") | " + hand[1] + " (" + map.get(hand[1]) + ") | ");
        System.out.print("Total: " + total); // prints total
        //dealer
        dCardCount = 2;
        dTotal = 0;
        dHand[0] = cardGeneration.cardGen();
        dHand[1] = cardGeneration.cardGen();
        if(dHand[0] == 'A' && dHand[1] == 'A') {
            dHand[1] = 'a';
        }
        dTotal = map.get(dHand[0]) + map.get(dHand[1]); //adds total
        System.out.println("\nDealer:");
        if(dTotal != 21) { // if the dealer doesn't blackjack, then it leaves the card hidden
            System.out.print(dHand[0] + " (" + map.get(dHand[0]) + ") | " + "\uD83C\uDCA0 (?) | ");
            System.out.println("Total: ??");
        } else { //if dealer hits a blackjack, it reveals the hidden card immediately
            System.out.print(dHand[0] + " (" + map.get(dHand[0]) + ") | " + dHand[1] + " (" + map.get(dHand[1]) + ") | ");
            System.out.println("Total: " + dTotal + "\nDealer Blackjack!");
        }
        //instant blackjack stuff
        if (total == 21 && dTotal == 21) { //if they both blackjack, then tie
            push();
        } else if (dTotal == 21) { // if dealer blackjack, then lose
            lose();
        } else if (total == 21) { //if player blackjack then win
            System.out.print("Blackjack!");
            currentBet = (int)(currentBet * 1.5); //blackjack now pays 3 to 2, no cents
            win();
        } else { //if no blackjacks, continue
            prompt();
        }
    }

    public void hit() {
        boolean hasAce = false;
        int aceIndex = 0;

        cardCount++;
        hand[cardCount - 1] = cardGeneration.cardGen(); //initializes that card
        total += map.get(hand[cardCount - 1]); //adds the new card value to the total

        for (int i = 0; i < cardCount; i++) {
            if (hand[i] == 'A') {
                aceIndex = i; // marks the first occurence of an ace (11)
                hasAce = true; //checks hasAce
                break;
            }
        }
        if (total > 21 && hasAce) { //if the card has an ace and is over 21 then it sets the first occurence of an ace to a little ace then sets the total to what it would be without a high ace
            hand[aceIndex] = 'a';
            total -= 10;
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
        } else if (total == 21) { //if player reaches 21, dealer hits
            stand();
        } else { //if nothing keep playing
            prompt();
        }
    }

    public void stand() {
        //wont hit if dealer is over or at 17
        while (dTotal < 17) { // hits and stands on 17
            boolean hasAce = false;
            int aceIndex = 0;
            dCardCount++;
            dHand[dCardCount - 1] = cardGeneration.cardGen();
            dTotal += map.get(dHand[dCardCount - 1]);

            for (int i = 0; i < dCardCount; i++) {
                if (dHand[i] == 'A') {
                    aceIndex = i;
                    hasAce = true;
                    break;
                }
            }
            if (dTotal > 21 && hasAce) {
                dHand[aceIndex] = 'a';
                dTotal -= 10;
            }
        } //reveals dealer hand if over 17
        System.out.println("\nDealer:");
        for (int i = 0; i < dCardCount; i++) {
            System.out.print(dHand[i] + " (" + map.get(dHand[i]) + ") | ");
        }
        System.out.println("Total: " + dTotal);

        //win lose or push
        if (dTotal > 21 || dTotal < total) { //if the dealer busts or if the player has a higher card value total than the dealer, win
            win();
        } else if (dTotal > total) { //if dealer has higher value card total, lose. note that if they went over 21 then they already lost so no need to check for it again
            lose();
        } else { //if the card values are the same, it is a tie
            push();
        }
    }

    public void doubleDown() {
        if (cardCount == 2 && money >= currentBet*2) {
            hit();
            currentBet *= 2;
        } else {
            System.out.println("You can't double down!");
        }
    }
    /* maybe hold off on this idk it'd be very difficult to work with and around
       also splitting is when you have two of the same card and play two separate hands w two bets
    public void split() {
        if (cardCount == 2 && map.get(hand[0]) == map.get(hand[1]) && money >= currentBet*2) {

        } else {
            System.out.println("You can't split!");
        }
    }
     */

    private void win() {
        state = false; //toggles state to off, no game ongoing
        money += currentBet; //adds the bet to the money, effectively doubles the money input
        currentBet = 0; //resets the current bet
        System.out.println("\nYou won! Would you like to quit or continue?");
    }

    private void lose() {
        state = false;
        money -= currentBet; //subtracts the current bet from the total money if lost
        currentBet = 0;
        if(money < 1) { //if the player has no money, they can't bet and it closes :)
            System.out.println("\nyou lost all ur money. ur broke kid");
            Main.input = 'q';
        } else { //but if they do have money they can keep playing
            System.out.println("\nYou lost! Would you like to quit or continue?");
        }
    }

    private void push() {
        state = false; // it also doesn't need the conditional if betting is enabled because it's basically nothing
        currentBet = 0; //nothing is done to the money because it is a push, just the bet is reset
        System.out.println("\nYou tied! Would you like to quit or continue?");
    }

    private void prompt() { //continue!
        state = true;
        System.out.print("\nHit, stand, or double down?");
    }

    public boolean getState() {
        return state; //false will be end state, true will be ongoing state
    }

    public void bet(int x) {
        currentBet = x;
        if (money-currentBet < 0 || currentBet < 1) {
            System.out.println("You can't bet this much money!");
        } //kinda want to make it so if you enter an invalid bet amount it automatically reprompts and gets a valid bet or maybe not idk
    }
    public int getMoney() {
        return money - currentBet; //returns the effective money balance
    }
}