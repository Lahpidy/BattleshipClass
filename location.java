public class Location
{
    // Static guess constants
    public static final int UNGUESSED = 0;
    public static final int HIT = 1;
    public static final int MISSED = 2;

    private int status;
    private boolean hasShip;
    private Ship ship;

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
        ship.takeHit();
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
    // Adds the ship located here to this Location
    public void setShip(boolean val, Ship ship)
    {
        hasShip = val;
        this.ship = ship;
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
    
    // Get the Ship at this Location.
    // Null if there is no ship.
    public Ship getShip()
    {
        return ship;
    }
}
