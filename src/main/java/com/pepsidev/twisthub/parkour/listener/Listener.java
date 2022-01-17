package com.pepsidev.twisthub.parkour.listener;

import com.pepsidev.twisthub.Hub;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class Listener implements org.bukkit.event.Listener {

	@EventHandler
	public void onCrack(PlayerInteractEvent event) {
		if (Hub.getInstance().getConfig().getString("License").equalsIgnoreCase("")) {
			Bukkit.broadcastMessage("I think this is cracked?");
		}
	}
	@EventHandler
	public void onCrack(PlayerMoveEvent event) {
		if (Hub.getInstance().getConfig().getString("License").equalsIgnoreCase("")) {
			Bukkit.broadcastMessage("I think this is cracked?");
		}
	}
}
