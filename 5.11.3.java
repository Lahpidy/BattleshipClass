public class LocationTester extends ConsoleProgram
{
    public void run()
    {
        // Test out your Location class here!
        Location loc = new Location(); 
        
        //Unguessed
        System.out.println("UNGUESSED LOCATION");
        System.out.println("Hit? " + loc.checkHit());
        System.out.println("Miss? " + loc.checkMiss());
        System.out.println("Unguessed? " + loc.isUnguessed());
        System.out.println("Ship? " + loc.hasShip());
        System.out.println("Status: " + loc.getStatus());
        System.out.println();
        
        //Hit with ship
        Location hit = new Location();
        hit.markHit();
        hit.setShip(true);
        
        System.out.println("HIT LOCATION");
        System.out.println("Hit? " + hit.checkHit());
        System.out.println("Miss? " + hit.checkMiss());
        System.out.println("Unguessed? " + hit.isUnguessed());
        System.out.println("Ship? " + hit.hasShip());
        System.out.println("Status: " + hit.getStatus());
        System.out.println();
        
        // Set the status
        hit.setStatus(0);
        System.out.println("SET STATUS TO 0");
        System.out.println("Hit? " + hit.checkHit());
        System.out.println("Miss? " + hit.checkMiss());
        System.out.println("Unguessed? " + hit.isUnguessed());
        System.out.println("Ship? " + hit.hasShip());
        System.out.println("Status: " + hit.getStatus());
    }
}

------------------------------------------------------------------------------------
public class Location
{
    //Implement the Location class here
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
