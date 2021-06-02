public class Battleship extends ConsoleProgram
{
    private static final int MAX_COL = 10;
    private static final char MAX_ROW = 'J';

    private static final int HORIZONTAL = 0;
    private static final int VERTICAL = 1;

    public void run()
    {
        System.out.println("=======================\nWelcome to Battle Ship\n=======================");

        Player human = new Player();
        Player computer = new Player();

        setUpShips(human, computer);

        readLine("Hit enter to start guessing.");

        boolean gameOver = false;

        while(!gameOver)
        {
            gameOver = playRound(human, computer);
        }

        if(human.hasWon())
        {
            System.out.println("You won!");
        }
        else
        {
            System.out.println("You lost!");
        }

        System.out.println("Thanks for playing!");
    }

    private void setUpShips(Player human, Player computer)
    {
        System.out.println("Time to place your ships.");
        initializeShipsFromInput(human);

        readLine("Hit enter for the enemy to choose their ship locations.");

        computer.initializeShipsRandomly();

        System.out.println("The enemy has placed their ships.");
        computer.printMyShips();
    }

    public void initializeShipsFromInput(Player player)
    {
        for(int i = 0; i < player.NUM_SHIPS; i++)
        {
            readLine("Hit enter to place the next ship.");
            System.out.println("Your current grid of ships.");
            player.printMyShips();

            int length = player.SHIP_LENGTHS[i];
            System.out.println("Now you need to place a ship of length " + length);
            
            Ship ship = new Ship(length);
            
            while(true)
            {
                int row = readRow();
                int col = readCol();
                int dir = readDirection();
                ship.setLocation(row, col);
                ship.setDirection(dir);
                if(player.addShip(ship))
                {
                    break;
                }
                System.out.println("Invalid ship placement. Please try again.");
            }
        }
        System.out.println("Your current grid of ships.");
        player.printMyShips();
    }
    
    

    private int readDirection()
    {
        while(true)
        {
            String dir = readLine("Horizontal or vertical? ");
            dir = dir.toUpperCase();

            if(dir.length() > 0)
            {
                if(dir.charAt(0) == 'H')
                {
                    return HORIZONTAL;
                }
                else if(dir.charAt(0) == 'V')
                {
                    return VERTICAL;
                }
            }
            System.out.println("Invalid direction, please try again.");
        }
    }

    private int readCol()
    {
        while(true)
        {
            int col = readInt("Which column? (1-" + MAX_COL + ") ");
            if(col >= 1 && col <= MAX_COL)
            {
                return col-1;
            }
            System.out.println("Invalid column, please try again.");
        }
    }

    private int readRow()
    {
        while(true)
        {
            String row = readLine("Which row? (A-" + MAX_ROW + ") ");
            row = row.toUpperCase();
            if(row.length() > 0)
            {
                char ch = row.charAt(0);
                if(ch >= 'A' && ch <= MAX_ROW)
                {
                    return ch - 'A';
                }
            }
            System.out.println("Invalid row, please try again.");
        }
    }

    // Plays a round of battle ship, returns true if the game
    // is over, false otherwise.
    private boolean playRound(Player human, Player computer)
    {
        readLine("Hit enter for your turn.");
        humanTurn(human, computer);

        if(human.hasWon())
        {
            return true;
        }

        readLine("Hit enter for the computer turn.");
        computerTurn(human, computer);

        return computer.hasWon();
    }

    private void computerTurn(Player human, Player computer)
    {
        int row = computer.getRandomRowGuess();
        int col = computer.getRandomColGuess();
        System.out.println("Computer player guesses row " + (row + 1) + " and column " + (col + 1));

        boolean hit = computer.makeGuess(row, col, human);

        if(hit)
        {
            System.out.println("Computer hit!");
        }
        else
        {
            System.out.println("Computer missed.");
        }

        computer.printMyGuesses();
        computer.printHitsDelivered();
    }

    private void humanTurn(Player human, Player computer)
    {
        System.out.println("Enemy grid");
        human.printMyGuesses();
        System.out.println("It's your turn to guess.");
        int row = readRow();
        int col = readCol();

        boolean hit = human.makeGuess(row, col, computer);

        if(hit)
        {
            System.out.println("You got a hit!");
        }
        else
        {
            System.out.println("Nope, that was a miss.");
        }

        human.printMyGuesses();
        human.printHitsDelivered();
    }
}
