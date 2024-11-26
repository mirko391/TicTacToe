import java.util.Arrays;
import java.util.Scanner;

public class Main {
    // method to check for a remis (remis == 9turns && no win)
    public static boolean checkRemis(int r) {
        return r >= 9;
    }

    // method to print out the template for the game, showing the numbers you need to input for each field
    public static void template() {
        System.out.println("Use the shown numbers to claim the field you want.");
        System.out.println("You can access the template with \"0\".");
        System.out.println("1" + " | " + "2" + " | " + "3");
        System.out.println("---------");
        System.out.println("4" + " | " + "5" + " | " + "6");
        System.out.println("---------");
        System.out.println("7" + " | " + "8" + " | " + "9");
    }

    // method to print out the actual gaming field with taken and empty fields, no numbers
    public static void printGame(String[] op) {
        System.out.println(op[0] + " | " + op[1] + " | " + op[2]);
        System.out.println("---------");
        System.out.println(op[3] + " | " + op[4] + " | " + op[5]);
        System.out.println("---------");
        System.out.println(op[6] + " | " + op[7] + " | " + op[8]);
    }

    //method to check for win, using input to determine what player to check for
    public static boolean hasWon(String[] coordS, String sign) {
        // goes through all possible 8 win conditions one by one
        if (coordS[0].equals(sign) && coordS[1].equals(sign) && coordS[2].equals(sign)) {
            return true;
        } else if (coordS[3].equals(sign) && coordS[4].equals(sign) && coordS[5].equals(sign)) {
            return true;
        } else if (coordS[6].equals(sign) && coordS[7].equals(sign) && coordS[8].equals(sign)) {
            return true;
        } else if (coordS[0].equals(sign) && coordS[3].equals(sign) && coordS[6].equals(sign)) {
            return true;
        } else if (coordS[1].equals(sign) && coordS[4].equals(sign) && coordS[7].equals(sign)) {
            return true;
        } else if (coordS[2].equals(sign) && coordS[5].equals(sign) && coordS[8].equals(sign)) {
            return true;
        } else if (coordS[1].equals(sign) && coordS[4].equals(sign) && coordS[8].equals(sign)) {
            return true;
        } else return coordS[6].equals(sign) && coordS[4].equals(sign) && coordS[2].equals(sign);
    }

    //method to check input (field number) and ask for a new one if invalid
    public static int handleInput() {
        int goodIput;
        while (true) { // loop until accepted input
            try { //catch exceptions, like string input
                Scanner inputS = new Scanner(System.in);
                int iput = inputS.nextInt();
                if (iput >= 0 && iput <= 9) { //check for num1-9
                    goodIput = iput;
                    break;
                } else {
                    System.out.println("Please input a number between 1 and 9:");
                }
            } catch (Exception e) {
                System.out.println("Please input a number between 1 and 9:");
            }

        }
        return goodIput;
    }

    //method to check input (1 or 2) and ask for new one if invalid
    public static int handleNewGame() {
        int goodIput;
        while (true) { //loop until accepted
            try { //catch exceptions and invalid inputs
                Scanner inputS = new Scanner(System.in);
                int iput = inputS.nextInt();
                if (iput == 1 || iput == 2) { //checks for 1 or 2
                    goodIput = iput;
                    break;
                } else {
                    System.out.println("Please input 1 to exit or 2 to continue:");
                }
            } catch (Exception e) {
                System.out.println("Please input 1 to exit or 2 to continue:");
            }

        }
        return goodIput;
    }

    public static void main(String[] args) {
        //declaring all variables needed
        int playerInput; //player 1 input
        int turns; //needed to check for remis
        boolean remis = false; //used to break the gameloop if remis=true in a trackable way
        boolean win = false; //*
        boolean exit = false; // used to end the programm if desired
        String[] coordS = new String[9]; //array to display all the fields
        Arrays.fill(coordS, " "); // filling the array for format purposes

        int player = 0; //used to determine player, always 0 or 1
        String[] playerSign = {"o", "x"}; //stores the corresponding sign for the players
        String[] playerName = new String[2]; //used to store player names


        System.out.println("Player 1, please enter your name:");
        Scanner p1 = new Scanner(System.in);
        playerName[0] = p1.nextLine(); //determining 1st player name
        System.out.println("Player 2, please enter your name:");
        Scanner p2 = new Scanner(System.in);
        playerName[1] = p2.nextLine(); //determining 2nd player name


        while (!exit) { //loop for the game, begin of the actual game
            template(); //prints out the template for the game, showing the numbers needed for each field at game start
            turns = 0;
            while (true) { //loop for the players turns
                System.out.println(playerName[player] + "'s turn, please choose your field:");
                while (true) {// loop to prevent undesired inputs
                    playerInput = handleInput();//checks input
                    if(playerInput == 0){
                        template();
                    } else if (coordS[playerInput - 1].equals("o") || coordS[playerInput - 1].equals("x")) { //check if field taken
                        System.out.println("Please input a free field, this one is taken already.");
                    } else {
                        break;
                    }
                }
                coordS[playerInput - 1] = playerSign[player]; // overwrites an empty field with player sign
                printGame(coordS); //prints updated version of playing field
                win = hasWon(coordS, playerSign[player]); //checks for win
                if (win) { //prints out winner
                    System.out.println(playerName[player] + " wins!");
                    break;
                }
                turns++; // counts the turns
                remis = checkRemis(turns); //checks if turn is below 9, since there are 9 fields
                if (remis) {
                    System.out.println("The game ends in a draw.");
                    break;
                }

                // alternates player's turn
                if (player == 0) {
                    player = 1;
                } else {
                    player = 0;
                }
            }
            // choice for continuation of the game
            System.out.println("Do you want to play another round? \n Input 1 to exit, 2 to continue.");
            int newGame = handleNewGame(); //checks for 1 or 2
            if (newGame == 1) { //if 1 then game is finished, else loop starts again
                System.out.println("Thank you for playing.");
                exit = true;
            } else {
                win = false;
                coordS = new String[9];
                Arrays.fill(coordS, " "); //resetting the game
            }
        }
    }
}