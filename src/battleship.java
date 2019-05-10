import java.util.*;

public class battleship {

    // define scanner
    static Scanner INPUT = new Scanner(System.in);
    static Random RAND = new Random();

    public static void main(String[] args) {
        // main function
        // call intro
        String[][] ocean = introduction();
        ocean = gatherInput(ocean);
        System.out.println("Placing computer ships:");
        placeComputerShips(ocean, 5);
        printOcean(ocean);
        System.out.println("Prepare for battle!");
        // define lives
        int playerLives = 5;
        int computerLives = 5;
        playerTurn(ocean, playerLives, computerLives);


    }

    public static String[][] introduction() {

        // say hello
        System.out.println("**** Welcome to Battle Ships game ****");
        System.out.println("Right now, the sea is empty.");


        String[][] ocean = {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
        };

        printOcean(ocean);

        return ocean;
    }

    public static void printOcean(String[][] ocean) {

        for (int i=0; i<ocean.length; i++) {
            if (i==0) {
                System.out.println("  0123456789");
            }

            // print line pre-cursor
            System.out.print(i + "|");

            for (int j=0; j<ocean[i].length; j++) {

                // print cell value
                if (ocean[i][j] == null) {
                    System.out.print(" ");
                } else if (ocean[i][j] == "1") {
                    System.out.print("@");
                } else if (ocean[i][j] == "2" || ocean[i][j] == "+") {
                    System.out.print(" ");
                } else {
                    System.out.print(ocean[i][j]);
                }
            }

            // print line post-cursor
            System.out.println("|" + i);

            // print end
            if (i==9) {
                System.out.println("  0123456789");
            }
        }
    }

    public static String[][] gatherInput(String[][] ocean) {

        System.out.println("Place your 5 ships:");
        // ask user to place ships
        for (int i=0; i<5; i++) {
            System.out.print("Enter X coordinate for your ship: ");
            int x = INPUT.nextInt();
            System.out.print("Enter Y coordinate for your ship: ");
            int y = INPUT.nextInt();

            if ((x < 0 || x > 9) || (y < 0 || y > 9)) {
                i--;
                System.out.println("Coordinates not valid. Try again.");
            } else if (ocean[y][x] == "1") {
                i--;
                System.out.println("Ship already there. Try again.");
            } else {
                ocean[y][x] = "1";
            }
        }

        return ocean;
    }

    public static String[][] placeComputerShips(String[][] ocean, int count) {

        if (count > 0) {
            int x = RAND.nextInt(9);
            int y = RAND.nextInt(9);

            if (ocean[y][x]=="1" || ocean[y][x]=="2") {
                ocean = placeComputerShips(ocean, count);
            } else {
                ocean[y][x] = "2";
                ocean = placeComputerShips(ocean, count - 1);
            }
        }

        return ocean;
    }

    public static void playerTurn(String[][] ocean, int playerLives, int computerLives) {

        System.out.println("YOUR TURN:");

        boolean turnComplete = false;

        while (!turnComplete) {
            // gather user inputs
            System.out.print("Enter X coordinate: ");
            int x = INPUT.nextInt();
            System.out.print("Enter Y coordinate: ");
            int y = INPUT.nextInt();

            if ((x < 0 || x > 9) || (y < 0 || y > 9)) {
                System.out.println("Coordinates not valid. Try again.");
            } else if (ocean[y][x] == "-") {
                System.out.println("Already tried there. Try again.");
            } else if (ocean[y][x] == "x" || ocean[y][x] == "!") {
                System.out.println("Already sunk that ship. Try again.");
            } else if (ocean[y][x] == "2") {
                System.out.println("Boom! You sunk the ship!");
                ocean[y][x] = "!";
                computerLives--;
                turnComplete = true;
            } else if (ocean[y][x] == "1") {
                System.out.println("Oh no, you sunk your own ship :(");
                ocean[y][x] = "x";
                playerLives--;
                turnComplete = true;
            } else {
                System.out.println("Sorry, you missed!");
                ocean[y][x] = "-";
                turnComplete = true;
            }
        }
        if (computerLives == 0) {
            printOcean(ocean);
            System.out.println("Your ships: " + playerLives + " | Computer ships: " + computerLives);
            System.out.println("Game over! You win!");
        } else if (playerLives == 0) {
            printOcean(ocean);
            System.out.println("Your ships: " + playerLives + " | Computer ships: " + computerLives);
            System.out.println("Game over! You lose!");
        } else {
            computerTurn(ocean, playerLives, computerLives);
        }

    }

    public static void computerTurn(String[][] ocean, int playerLives, int computerLives) {
        System.out.println("Computer's Turn:");

        boolean turnComplete = false;

        while (!turnComplete) {
            // computer inputs
            int x = RAND.nextInt(9);
            int y = RAND.nextInt(9);

            if ((x < 0 || x > 9) || (y < 0 || y > 9)) {
                turnComplete = false;
            } else if (ocean[y][x] == "+") {
                turnComplete = false;
            } else if (ocean[y][x] == "x" || ocean[y][x] == "!") {
                turnComplete = false;
            } else if (ocean[y][x] == "2") {
                System.out.println("Boom! Computer sunk its own ship!");
                ocean[y][x] = "!";
                computerLives--;
                turnComplete = true;
            } else if (ocean[y][x] == "1") {
                System.out.println("Oh no, computer sunk your ship :(");
                ocean[y][x] = "x";
                playerLives--;
                turnComplete = true;
            } else {
                System.out.println("Computer missed!");
                ocean[y][x] = "+";
                turnComplete = true;
            }
        }
        if (computerLives == 0) {
            printOcean(ocean);
            System.out.println("Your ships: " + playerLives + " | Computer ships: " + computerLives);
            System.out.println("Game over! You win!");
        } else if (playerLives == 0) {
            printOcean(ocean);
            System.out.println("Your ships: " + playerLives + " | Computer ships: " + computerLives);
            System.out.println("Game over! You lose!");
        } else {
            printOcean(ocean);
            System.out.print("Your ships: " + playerLives + " | Computer ships: " + computerLives);
            playerTurn(ocean, playerLives, computerLives);
        }
    }
}
