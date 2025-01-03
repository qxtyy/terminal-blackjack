import java.util.Scanner;

public class Main {

    public static char input;

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        logic game = new logic();

        // char input;
        int bet;

        System.out.println("h = hit, s = stand, d = double down (not working yet), q = quit, c = continue, e = enable betting, b = bet, m = check money\ndealer stands on 17");
        game.roll();
        do {
            System.out.print("\nEnter a character: ");
            input = in.next().charAt(0);

            switch (input) {
                case 'h':
                    if(game.getState()) {
                        game.hit();
                        break;
                    } else {
                        System.out.println("You can't do this!");
                        break;
                    }
                case 's':
                    if(game.getState()) {
                        game.stand();
                        break;
                    } else {
                        System.out.println("You can't do this!");
                        break;
                    }
                case 'd':
                    if(game.getState()) {
                        game.doubleDown();
                        break;
                    } else {
                        System.out.println("You can't do this!");
                        break;
                    }
                case 'q':
                    System.out.println("bye");
                    break;
                case 'c':
                    if(!game.getState()) {
                        game.roll();
                        break;
                    } else {
                        System.out.println("You can't do this!");
                        break;
                    }
                case 'b':
                    if(!game.getState() && game.getBetState()) { //only bets if there is no ongoing game and betting is enabled
                        System.out.println("How much would you like to bet?");
                        bet = in.nextInt();
                        game.bet(bet);
                        game.roll();
                        break;
                    } else {
                        System.out.println("You can't do this!");
                        break;
                    }
                case 'e':
                    if(!game.getState()) {
                        game.toggleBetting();
                        break;
                    } else {
                        System.out.println("You can't do this!");
                        break;
                    }
                case 'm':
                    System.out.println("Current balance: " + game.getMoney() + "₪");
                    break;
                default:
                    System.out.println("Please enter 'h' (hit), 's' (stand), 'd' (double down), 'q' (quit), 'c' (continue), or 'b' (bet)");
            }

        } while (input != 'q');

        in.close();
    }
}