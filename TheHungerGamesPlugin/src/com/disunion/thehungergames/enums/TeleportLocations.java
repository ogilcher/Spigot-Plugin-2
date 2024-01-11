package com.disunion.thehungergames.enums;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public enum TeleportLocations {

    //-- List of enums
    Spawn (-1576.5, 90, -610.5),
    Spawn1 (-1576, 60, -635),
    Spawn2 (-1582, 60, -634),
    Spawn3 (-1588, 60, -632),
    Spawn4 (-1594, 60, -628),
    Spawn5 (-1598, 60, -622);
    // Spawn6
    // Spawn7
    // ...

    //-- Instance Variables
    private final double x;
    private final double y;
    private final double z;

    //-- Constuctor
    TeleportLocations(double x, double y, double z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    //-- Getters & Setters
    public Location getLocation(){
        return new Location(Bukkit.getWorlds().get(0), x, y, z);
    }
}
