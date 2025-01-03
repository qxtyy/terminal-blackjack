import java.util.Arrays;
import java.util.HashMap;
/*
to do:
make actual scalable deck chances, maybe use arraylist to simulate drawing a card then removing that from the pool that'd be really easy. just (int)(math.random *53) then remove that index and keep playing. maybe even going further as to using the unicode card characters that'd be cool
and possible the option to select how many decks you want, and procedurally generate them based on deck size input
double down function w error prevention
add betting.

make METHODS for repeated actions yeah condense the code and make it MORE READABLE damn

make betting toggleable
make choice for deck size chance

maybe make a tester method that assigns the dealer the two inputted chars maybe

maybe make an interface before immediately starting a game ? so you can enable betting before
the game starts
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

    private static int money = 100; //starting money 100? idk
    private static int currentBet;

    private boolean bettingEnabled;
    private int deckSize;

    private boolean state; //false will be end state, true will be ongoing state

    logic() {

    } // i mean technically i dont need a constructor because it defaults to nothing

    public void roll() {
        cardCount = 2; //resets card count to 2
        total = 0; //resets total to 0
        Arrays.fill(hand, '0'); //clears hand entirely
        hand[0] = cardGeneration.cardGen(); //change these for real chance later
        hand[1] = cardGeneration.cardGen();
        if(hand[0] == 'A' && hand[1] == 0) { // if dealt two aces, change one to little a (1)
            hand[1] = 'a';
        }
        System.out.println("You:");
        for (int i = 0; i < cardCount; i++) {
            total += map.get(hand[i]); //adds up the cards for total
            System.out.print(hand[i] + " (" + map.get(hand[i]) + ") | "); //prints out card faces and their respective values
        }
        System.out.print("Total: " + total); // prints total
        //dealer
        dCardCount = 2;
        dTotal = 0;
        Arrays.fill(dHand, '0');
        dHand[0] = cardGeneration.cardGen();
        dHand[1] = cardGeneration.cardGen();
        if(dHand[0] == 'A' && dHand[1] == 0) {
            dHand[1] = 'a';
        }
        System.out.println("\nDealer:");
        dTotal = map.get(dHand[0]) + map.get(dHand[1]); //adds total
        if(dTotal != 21) { //this replaces the for loop because the dealer doesnt show their hand !!!!!
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
        cardCount++;
        hand[cardCount - 1] = cardGeneration.cardGen(); //initializes that card
        total += map.get(hand[cardCount - 1]); //adds the new card value to the total
        if (total > 21) { //checks for ace
            for (int i = 0; i < cardCount; i++) {
                if (hand[i] == 'A') {
                    hand[i] = 'a';
                    break; //only changes one if there is more than ace :|
                }
            }
        }
        total = 0; //resets total to then recount it just in the case that there was an ace needing to be changed
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
        } else if (total == 21) { //if player reaches 21, dealer hits
            stand();
        } else { //if nothing keep playing
            prompt();
        }
    }

    public void stand() {
        if(dTotal < 17) { //wont hit if dealer is over or at 17
            while (dTotal < 17) { // hits and stands on 17
                dCardCount++;
                dHand[dCardCount - 1] = cardGeneration.cardGen();
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
        if (dTotal > 21 || dTotal < total) { //if the dealer busts or if the player has a higher card value total than the dealer, win
            win();
        } else if (dTotal > total) { //if dealer has higher value card total, lose. note that if they went over 21 then they already lost so no need to check for it again
            lose();
        } else { //if the card values are the same, it is a tie
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
        if(bettingEnabled) {
            state = false; //toggles state to off, no game ongoing
            money += currentBet; //adds the bet to the money, effectively doubles the money input
            currentBet = 0; //resets the current bet
            System.out.println("\nYou won! Would you like to quit or continue?");
        } else {
            state = false;
            System.out.println("\nYou won! Would you like to quit or continue?");
        }
    }

    public void lose() {
        if(bettingEnabled) {
            state = false;
            money -= currentBet; //subtracts the current bet from the total money if lost
            currentBet = 0;
            if(money < 1) { //if the player has no money, they can't bet and it closes :)
                System.out.println("\nyou lost all ur money. ur broke kid");
                Main.input = 'q';
            } else { //but if they do have money they can keep playing
                System.out.println("\nYou lost! Would you like to quit or continue?");
            }
        } else {
            state = false;
            System.out.println("\nYou lost! Would you like to quit or continue?");
        }
    }

    public void push() {
        state = false; // it also doesn't need the conditional if betting is enabled because it's basically nothing
        currentBet = 0; //nothing is done to the money because it is a push, just the bet is reset
        System.out.println("\nYou tied! Would you like to quit or continue?");
    }

    public void prompt() { //continue!
        state = true;
        System.out.print("\nHit, stand, or double down?");
    }

    public boolean getState() {
        return state; //false will be end state, true will be ongoing state
    }

    public void bet(int x) {
        currentBet = x;
        if(money-currentBet < 0 || currentBet < 1) {
            System.out.println("You can't bet this much money!");
        } //kinda want to make it so if you enter an invalid bet amount it automatically reprompts and gets a valid bet or maybe not idk
    }
    public int getMoney() {
        return money - currentBet; //returns the effective money balance
    }
    /*
    public void checkMoney() {
        if(money < 1) {
            System.out.println(" you lost all ur money. ur broke kid");
            Main.input = 'q';
        }
    }
    */

    public void toggleBetting() {
        if(bettingEnabled) {
            bettingEnabled = false;
            System.out.println("Betting has been disabled."); //disables betting if it's enabled
        } else {
            bettingEnabled = true;
            System.out.println("Betting has been enabled."); //enables betting if it's disabled
        }
    }
    public boolean getBetState() {
        return bettingEnabled;
    }
}