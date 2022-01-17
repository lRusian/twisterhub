package com.pepsidev.twisthub.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class Util {

    public static Location stringToLocation(String string) {
        String[] locationString = string.split(",");
        return new Location(Bukkit.getWorld(locationString[0]), Double.parseDouble(locationString[1]), Double.parseDouble(locationString[2]), Double.parseDouble(locationString[3]), Float.parseFloat(locationString[4]), Float.parseFloat(locationString[5]));
    }

    public static String locationToString(Location location) {
        return location.getWorld().getName() + "," + location.getX() + "," + location.getY() + "," + location.getZ() + "," + Math.round(location.getYaw()) + "," + Math.round(location.getPitch());
    }
}
