import java.util.Scanner;

public class Main {

    public static char input;

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        logic game = new logic();

        int bet;

        System.out.println("General: c = continue / start new game, q = quit program, D = deck size (default is one)");
        System.out.println("Betting: b = bet (optional, you can only bet before starting a game), m = check money balance (NO CENTS.)");
        System.out.println("Game: h = hit, s = stand, d = double down, p = split (wip) ");
        System.out.println("Dealer stands on 17, Blackjack pays 3 to 2");
        do {
            System.out.print("\nEnter a character: ");
            input = in.next().charAt(0);

            switch (input) {
                case 'h':
                    if(game.getState()) {
                        game.hit();
                    } else {
                        System.out.println("You can't hit without starting a game!");
                    }
                    break;
                case 's':
                    if(game.getState()) {
                        game.stand();
                    } else {
                        System.out.println("You can't stand without starting a game!");
                    }
                    break;
                case 'd':
                    if(game.getState()) {
                        game.doubleDown();
                    } else {
                        System.out.println("You can't double down without starting a game!");
                    }
                    break;
                case 'q':
                    System.out.println("bye");
                    break;
                case 'c':
                    if(!game.getState()) {
                        game.roll();
                    } else {
                        System.out.println("You can't start a new game while in a game!");
                    }
                    break;
                case 'b':
                    if(!game.getState()) { //only bets if there is no ongoing game
                        boolean validInput = false;
                        while (!validInput) { //only takes valid input
                            System.out.println("How much would you like to bet?");
                            if (in.hasNextInt()) {
                                bet = in.nextInt();
                                validInput = true; // ends loop.
                                game.bet(bet);
                                game.roll();
                                break;
                            } else {
                                System.out.println("Invalid bet. Please enter a valid bet. ");
                                in.next(); //takes next line
                            }
                        }
                        break; // i dont know how the problem arose but a break here felt necessary and it fixed it somehow
                    } else {
                        System.out.println("You can't bet during a game!");
                        break;
                    }
                case 'D':
                    if(!game.getState()) { //same logic for bets i mean works just the same
                        boolean validInput = false;
                        while (!validInput) {
                            System.out.println("How many decks do you want? (1-8)");
                            if (in.hasNextInt()) {
                                int temp = in.nextInt();
                                if (temp > 8) {
                                    temp = 8;
                                    System.out.println("Your selection has exceeded the maximum.");
                                } else if (temp < 1) {
                                    temp = 1;
                                    System.out.println("Your selection has subceeded the minimum.");
                                }
                                logic.deckSize = temp;
                                validInput = true;
                                System.out.println("Deck size has been set to " + temp + " decks.");
                                game.roll();
                                break;
                            } else {
                                System.out.println("Please enter a number 1-8. ");
                                in.next();
                            }
                        }
                        break;
                    } else {
                        System.out.println("You can't alter the amount of decks during a game!");
                        break;
                    }
                /*
                case 'p':
                    if (game.getState())  {
                        game.split();
                    } else {
                        sout("You cant split)
                        break;
                 */
                case 'm':
                    System.out.println("Current balance: " + game.getMoney() + "₪");
                    break;
                default:
                    System.out.println("Please enter a valid character, refer to beginning.");
            }

        } while (input != 'q');

        in.close();
    }
}