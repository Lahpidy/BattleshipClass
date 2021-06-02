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
_________________________________________________________________
(PLAYER)
   public class Player
{
    // Static constants for the Player class
    public static final int[] SHIP_LENGTHS = {2, 3, 3, 4, 5};
    public static final int NUM_SHIPS = 5;
    private static final int MAX_HITS = computeMaxHits();

    // Direction constants
    private static final int UNSET = -1;
    private static final int HORIZONTAL = 0;
    private static final int VERTICAL = 1;

    private Grid myGrid;
    private Grid opponentGrid;
    private Ship[] myShips;
    private int numShips;
    private int totalHitsTaken;
    private int totalHitsDelivered;

    public Player()
    {
        myGrid = new Grid();
        opponentGrid = new Grid();
        myShips = new Ship[NUM_SHIPS];
        numShips = 0;
        totalHitsTaken = 0;
        totalHitsDelivered = 0;
    }

    private static int computeMaxHits()
    {
        int sum = 0;
        for(int i = 0; i < SHIP_LENGTHS.length; i++)
        {
            sum += SHIP_LENGTHS[i];
        }
        return sum;
    }

    public void initializeShipsRandomly()
    {
        for(int i = 0; i < SHIP_LENGTHS.length; i++)
        {
            int length = SHIP_LENGTHS[i];
            int dir;
            int row;
            int col;
            Ship ship = new Ship(length);

            while(true)
            {
                dir = Randomizer.nextInt(HORIZONTAL, VERTICAL);
                row = Randomizer.nextInt(0, myGrid.numRows() - 1);
                col = Randomizer.nextInt(0, myGrid.numCols() - 1);
                ship.setDirection(dir);
                ship.setLocation(row, col);
                if(addShip(ship))
                {
                    break;
                }
            }
        }
    }

    // Adds a ship if it's a legal placement
    // Returns whether the ship was successfully added
    public boolean addShip(Ship s)
    {
        if(myGrid.canPlaceShip(s))
        {
            myGrid.addShip(s);
            myShips[numShips] = s;
            numShips++;
            return true;
        }
        return false;
    }

    public int getRandomRowGuess()
    {
        return Randomizer.nextInt(0, opponentGrid.numRows()-1);
    }

    public int getRandomColGuess()
    {
        return Randomizer.nextInt(0, opponentGrid.numCols()-1);
    }

    public boolean makeGuess(int row, int col, Player other)
    {
        if(opponentGrid.alreadyGuessed(row, col))
        {
            return false;
        }

        boolean hit = other.recordOpponentGuess(row, col);
        markGuess(row, col, hit);
        return hit;
    }

    private void markGuess(int row, int col, boolean val)
    {
        opponentGrid.setShip(row, col, val);
        if(val)
        {
            opponentGrid.markHit(row, col);
            totalHitsDelivered++;
        }
        else
        {
            opponentGrid.markMiss(row, col);
        }
    }

    /*
     * Takes in an oppnent guess for a row, col location.
     * Records the guess, and returns a boolean indicating
     * whether the guess was a hit.
     */
    private boolean recordOpponentGuess(int row, int col)
    {
        if(myGrid.alreadyGuessed(row, col))
        {
            return false;
        }
        if(myGrid.hasShip(row, col))
        {
            myGrid.markHit(row, col);
            totalHitsTaken++;
        }
        else
        {
            myGrid.markMiss(row, col);
        }
        return myGrid.hasShip(row, col);
    }

    public void printMyShips()
    {
        myGrid.printShips();
    }

    public void printOpponentGuesses()
    {
        myGrid.printStatus();
    }

    public void printMyGuesses()
    {
        opponentGrid.printStatus();
    }

    public void printHitsDelivered()
    {
        System.out.println("Total Hits = " + totalHitsDelivered + " out of " + MAX_HITS);
    }

    public boolean hasWon()
    {
        return totalHitsDelivered == MAX_HITS;
    }

    public boolean hasLost()
    {
        return totalHitsTaken == MAX_HITS;
    }

    public void chooseShipLocation(Ship s, int row, int col, int direction)
    {
        s.setLocation(row, col);
        s.setDirection(direction);
        myGrid.addShip(s);
        myShips[numShips] = s;
        numShips++;
    }
}
______________________________________________________________________________________________
(GRID)
   public class Grid
{
     // Static constants for the grid class
    public static final int NUM_ROWS = 10;
    public static final int NUM_COLS = 10;

    private static final String HEADER_COLS = "  1 2 3 4 5 6 7 8 9 10";
    private static final String HEADER_ROWS = "ABCDEFGHIJ";

    private static final String[] STATUS_STRINGS = {"-", "X", "O"};

    // Direction constants
    private static final int UNSET = -1;
    private static final int HORIZONTAL = 0;
    private static final int VERTICAL = 1;

    private Location[][] grid;

    // Create a new Grid. Initialize each Location in the grid
    // to be a new Location object.
    public Grid()
    {
        grid = new Location[NUM_ROWS][NUM_COLS];
        initGridValues();
    }

    // Initialize each Location in the grid to be a new Location object
    private void initGridValues()
    {
        for(int row = 0; row < NUM_ROWS; row++)
        {
            for(int col = 0; col < NUM_COLS; col++)
            {
                grid[row][col] = new Location();
            }
        }
    }

    /**
     * This method can be called on your own grid. To add a ship
     * we will go at the ships location and mark a true value
     * in every location that the ship takes up.
     */
    public void addShip(Ship s)
    {
        int row = s.getRow();
        int col = s.getCol();

        if(s.getDirection() == VERTICAL)
        {
            for(int i = row; i < row + s.getLength(); i++)
            {
                setShip(i, col, true);
            }
        }
        else
        {
            for(int i = col; i < col + s.getLength(); i++)
            {
                setShip(row, i, true);
            }
        }
    }

    public boolean inBounds(int row, int col)
    {
        return row >= 0 && row < numRows() && col >= 0 && col < numCols();
    }

    public boolean canPlaceShip(Ship s)
    {
        int row = s.getRow();
        int col = s.getCol();

        if(s.getDirection() == VERTICAL)
        {
            for(int i = row; i < row + s.getLength(); i++)
            {
                if(!inBounds(i, col) || hasShip(i, col))
                {
                    return false;
                }
            }
        }
        else
        {
            for(int i = col; i < col + s.getLength(); i++)
            {
                if(!inBounds(row, i) || hasShip(row, i))
                {
                    return false;
                }
            }
        }
        return true;
    }

    // Mark a hit in this location by calling the markHit method
    // on the Location object.
    public void markHit(int row, int col)
    {
        grid[row][col].markHit();
    }

    // Mark a miss on this location.
    public void markMiss(int row, int col)
    {
        grid[row][col].markMiss();
    }

    // Set the status of this location object.
    public void setStatus(int row, int col, int status)
    {
        grid[row][col].setStatus(status);
    }

    // Get the status of this location in the grid
    public int getStatus(int row, int col)
    {
        return grid[row][col].getStatus();
    }

    // Return whether or not this Location has already been guessed.
    public boolean alreadyGuessed(int row, int col)
    {
        return !grid[row][col].isUnguessed();
    }

    // Set whether or not there is a ship at this location to the val
    public void setShip(int row, int col, boolean val)
    {
        grid[row][col].setShip(val);
    }

    // Return whether or not there is a ship here
    public boolean hasShip(int row, int col)
    {
        return grid[row][col].hasShip();
    }

    // Get the Location object at this row and column position
    public Location get(int row, int col)
    {
        return grid[row][col];
    }

    // Return the number of rows in the Grid
    public int numRows()
    {
        return grid.length;
    }

    // Return the number of columns in the grid
    public int numCols()
    {
        return grid[0].length;
    }

    // Print the Grid status including a header at the top
    // that shows the columns 1-10 as well as letters across
    // the side for A-J
    // If there is no guess print a -
    // If it was a miss print a O
    // If it was a hit, print an X
    // A sample print out would look something like this:
    //
    //   1 2 3 4 5 6 7 8 9 10
    // A - - - - - - - - - -
    // B - - - - - - - - - -
    // C - - - O - - - - - -
    // D - O - - - - - - - -
    // E - X - - - - - - - -
    // F - X - - - - - - - -
    // G - X - - - - - - - -
    // H - O - - - - - - - -
    // I - - - - - - - - - -
    // J - - - - - - - - - -
    public void printStatus()
    {
        System.out.println(HEADER_COLS);
        for(int row = 0; row < NUM_ROWS; row++)
        {
            System.out.print(HEADER_ROWS.charAt(row) + " ");

            for(int col = 0; col < NUM_COLS; col++)
            {
                System.out.print(STATUS_STRINGS[getStatus(row, col)] + " ");
            }
            System.out.println();
        }
    }

    // Print the grid and whether there is a ship at each location.
    // If there is no ship, you will print a - and if there is a
    // ship you will print a X. You can find out if there was a ship
    // by calling the hasShip method.
    //
    //   1 2 3 4 5 6 7 8 9 10
    // A - - - - - - - - - -
    // B - X - - - - - - - -
    // C - X - - - - - - - -
    // D - - - - - - - - - -
    // E X X X - - - - - - -
    // F - - - - - - - - - -
    // G - - - - - - - - - -
    // H - - - X X X X - X -
    // I - - - - - - - - X -
    // J - - - - - - - - X -
    public void printShips()
    {
        System.out.println(HEADER_COLS);
        for(int row = 0; row < NUM_ROWS; row++)
        {
            System.out.print(HEADER_ROWS.charAt(row) + " ");

            for(int col = 0; col < NUM_COLS; col++)
            {
                if(hasShip(row, col))
                {
                    System.out.print("X ");
                }
                else
                {
                    System.out.print("- ");
                }
            }
            System.out.println();
        }
    }
}
_____________________________________________________________________________
(SHIP)
    
  public class Ship
{
   // Direction constants
    private static final int UNSET = -1;
    private static final int HORIZONTAL = 0;
    private static final int VERTICAL = 1;

    private static final String[] directions = {"unset direction", "horizontal", "vertical"};

    // Instance variables
    private int row;
    private int col;
    private int length;
    private int direction;

    // Constructor. Create a ship and set the length.
    public Ship(int length)
    {
        this.length = length;
        this.direction = UNSET;
        this.row = UNSET;
        this.col = UNSET;
    }

    // Has the location been initialized
    public boolean isLocationSet()
    {
        return row != UNSET && col != UNSET;
    }

    // Has the direction been initialized
    public boolean isDirectionSet()
    {
        return direction != UNSET;
    }

    // Set the location of the ship
    public void setLocation(int row, int col)
    {
        this.row = row;
        this.col = col;
    }

    // Set the direction of the ship
    public void setDirection(int direction)
    {
        this.direction = direction;
    }

    // Getter for the row value
    public int getRow()
    {
        return row;
    }

    // Getter for the column value
    public int getCol()
    {
        return col;
    }

    // Getter for the length of the ship
    public int getLength()
    {
        return length;
    }

    // Getter for the direction
    public int getDirection()
    {
        return direction;
    }

    // Helper method to get a string value from the direction
    private String directionToString()
    {
        return directions[direction + 1];
    }

    // Helper method to get a (row, col) string value from the location
    private String locationToString()
    {
        if(isLocationSet())
        {
            return "(" + row + ", " + col + ")";
        }
        else
        {
            return "(unset location)";
        }
    }

    // toString value for this Ship
    public String toString()
    {
        return directionToString() + " ship of length " + length + " at " + locationToString();
    }
}
_______________________________________________________________________________________________________________
(LOCATION)
   public class Location
{
    // Static guess constants
    public static final int UNGUESSED = 0;
    public static final int HIT = 1;
    public static final int MISSED = 2;

    private int status;
    private boolean hasShip;

    // Location constructor.
    public Location()
    {
        status = UNGUESSED;
        hasShip = false;
    }

    // Was this Location a hit?
    public boolean checkHit()
    {
        return status == HIT;
    }

    // Was this location a miss?
    public boolean checkMiss()
    {
        return status == MISSED;
    }

    // Was this location unguessed?
    public boolean isUnguessed()
    {
        return status == UNGUESSED;
    }

    // Mark this location a hit.
    public void markHit()
    {
        status = HIT;
    }

    // Mark this location a miss.
    public void markMiss()
    {
        status = MISSED;
    }

    // Return whether or not this location has a ship.
    public boolean hasShip()
    {
        return hasShip;
    }

    // Set the value of whether this location has a ship.
    public void setShip(boolean val)
    {
        hasShip = val;
    }

    // Set the status of this Location.
    public void setStatus(int status)
    {
        this.status = status;
    }

    // Get the status of this Location.
    public int getStatus()
    {
        return status;
    }
}

