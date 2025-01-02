import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        logic game = new logic();
        char input;
        System.out.println("h = hit, s = stand, d = double down (not working yet), q = quit, c = continue (and start), b = bet\ndealer stands on 17");

        do {
            //game.roll(); // this runs anyway even if a random character is input but ig thats ok
            System.out.print("\nEnter a character: ");
            input = in.next().charAt(0);
            // bet = in.nextInt(); // make this work somehow by making a bet method w/ parameter

            switch (input) {
                case 'h':
                    game.hit();
                    break;
                case 's':
                    game.stand();
                    break;
                case 'd':
                    game.doubleDown();
                    break;
                case 'q':
                    System.out.println("bye");
                    break;
                case 'c':
                    game.roll();
                    break;
                case 'b':
                    System.out.println("How much would you like to bet?");
                default:
                    System.out.println("Please enter 'h' (hit), 's' (stand), 'd' (double down), 'q' (quit), 'c' (continue), or 'b' (bet)");
            }

        } while (input != 'q');

        in.close();
    }
}