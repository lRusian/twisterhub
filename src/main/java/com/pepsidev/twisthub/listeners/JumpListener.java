package com.pepsidev.twisthub.listeners;

import com.pepsidev.twisthub.Hub;
import com.pepsidev.twisthub.utils.files.ConfigFile;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.player.*;
import org.bukkit.*;

public class JumpListener implements Listener {

    public JumpListener() {
        Bukkit.getServer().getPluginManager().registerEvents(this, Hub.getInstance());
    }

    @EventHandler
    public void onJump(PlayerToggleFlightEvent event) {
        Player player = event.getPlayer();
        if (ConfigFile.getConfig().getBoolean("jump.enabled"))
            if (player.getGameMode() == GameMode.CREATIVE) {
                return;
            }
        event.setCancelled(true);
        player.setAllowFlight(false);
        player.setFlying(false);
        player.setVelocity(player.getLocation().getDirection().multiply(ConfigFile.getConfig().getDouble("jump.velocity")).setY(1));
        player.playSound(player.getLocation(), Sound.valueOf(ConfigFile.getConfig().getString("jump.sound").toUpperCase()), 1.0F, 1.0F);
    }

    @EventHandler
    public void onPlayerGround(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (player.getGameMode() == GameMode.CREATIVE) {
            return;
        }
        if (player.getLocation().getBlock().getRelative(BlockFace.DOWN).getType() != Material.AIR) {
            player.setAllowFlight(true);
        }
    }
}
