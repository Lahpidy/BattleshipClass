LOCATION.java

public class Location
{
    // Copy your code for the Location class into here
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
__________________________________________________________________
GRID.java

public class Grid
{
    // Copy over your Grid class into here
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
________________________________________________________________________________
SHIP.java

public class Ship
{
    // Copy your code for the Ship class into here
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
_______________________________________________________________________________________________
GRIDTESTER.java

public class GridTester extends ConsoleProgram
{
    public void run()
    {
        // Start here!
        Grid grid = new Grid();
        Ship s = new Ship(3);
        
        s.setLocation(3, 3);
        s.setDirection(1);
        grid.addShip(s);
        
        grid.printShips();
    }
}
