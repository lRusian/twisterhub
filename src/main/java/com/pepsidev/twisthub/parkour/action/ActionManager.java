package com.pepsidev.twisthub.parkour.action;

import com.pepsidev.twisthub.Hub;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class ActionManager {


    public ActionManager() {
    }

    public void performAction(String action, Player player) {
        if (action.equalsIgnoreCase("[LAST-CHECKPOINT]")) {
            lastCheck(player);
        } else if (action.equalsIgnoreCase("[LEAVE-PARKOUR]")) {
            leaveParkour(player);
        } else if (action.equalsIgnoreCase("[ACTIVATE-PARKOUR]")) {
            startParkour(player);
        } else {
            System.out.println(player.getName() + " tried to execute " + action + ", but we could not find a function for it!");
        }
    }

    public void lastCheck(Player player) {
        if (Hub.getInstance().getParkourManager().checkPointLoc.containsKey(player)) {
            player.teleport(Hub.getInstance().getParkourManager().checkPointLoc.get(player));
            return;
        }
        double x = Hub.getInstance().getConfig().getDouble("Parkour.Start-Location.X");
        double y = Hub.getInstance().getConfig().getDouble("Parkour.Start-Location.Y");
        double z = Hub.getInstance().getConfig().getDouble("Parkour.Start-Location.Z");
        float pitch = Hub.getInstance().getConfig().getInt("Parkour.Start-Location.Pitch");
        float yaw = Hub.getInstance().getConfig().getInt("Parkour.Start-Location.Yaw");
        Location location = new Location(player.getWorld(), x, y, z);
        location.setPitch(pitch);
        location.setYaw(yaw);
        player.teleport(location);
    }

    public void leaveParkour(Player player) {
        Hub.getInstance().getParkourManager().deactivateParkour(player);
    }

    public void startParkour(Player player) {
        double x = Hub.getInstance().getConfig().getDouble("Parkour.Start-Location.X");
        double y = Hub.getInstance().getConfig().getDouble("Parkour.Start-Location.Y");
        double z = Hub.getInstance().getConfig().getDouble("Parkour.Start-Location.Z");
        float pitch = Hub.getInstance().getConfig().getInt("Parkour.Start-Location.Pitch");
        float yaw = Hub.getInstance().getConfig().getInt("Parkour.Start-Location.Yaw");
        Location location = new Location(player.getWorld(), x, y, z);
        location.setPitch(pitch);
        location.setYaw(yaw);
        Hub.getInstance().getParkourManager().activateParkour(player, location);
    }

}
