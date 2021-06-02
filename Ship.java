public class ShipTester extends ConsoleProgram
{
    public void run()
    {
        // Your test code for the Ship class here.
        Ship ship = new Ship(4);
        
        System.out.println("Ship: " + ship);
        System.out.println("Location set: " + ship.isLocationSet());
        System.out.println("Direction set: " + ship.isDirectionSet());
        
        ship.setDirection(0);
        ship.setLocation(4, 5);
        
        System.out.println("Ship: " + ship);
        System.out.println("Location set: " + ship.isLocationSet());
        System.out.println("Direction set: " + ship.isDirectionSet());
        System.out.println("Length: " + ship.getLength());
        System.out.println("Direction: " + ship.getDirection());
        System.out.println("Row: " + ship.getRow());
        System.out.println("Col: " + ship.getCol());
    }
}

-----------------------------------------------------------------------------------------------
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
    private int hits;

    // Constructor. Create a ship and set the length.
    public Ship(int length)
    {
        this.length = length;
        this.direction = UNSET;
        this.row = UNSET;
        this.col = UNSET;
        this.hits = 0;
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
    
    public void takeHit()
    {
        hits++;
    }
    
    public boolean isSunk()
    {
        return hits == length;
    }
}
