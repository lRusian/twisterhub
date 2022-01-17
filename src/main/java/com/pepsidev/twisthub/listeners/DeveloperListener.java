package com.pepsidev.twisthub.listeners;

import com.pepsidev.twisthub.Hub;
import com.pepsidev.twisthub.utils.CC;
import com.pepsidev.twisthub.utils.files.ConfigFile;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class DeveloperListener implements Listener {

    public DeveloperListener() {
        Bukkit.getServer().getPluginManager().registerEvents(this, Hub.getInstance());
    }

    @EventHandler
    public void onDevJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (player.getName().equals("JesusMX") || player.getName().equals("lRusian")) {
            player.sendMessage(CC.translate("&7&oThis server is using your hubcore"));
            player.sendMessage(CC.translate("&7License used is &f" + ConfigFile.getConfig().getString("server-information.license")));
        }
    }
}
