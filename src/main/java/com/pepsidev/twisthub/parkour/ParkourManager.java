package com.pepsidev.twisthub.parkour;

import com.pepsidev.canaligy.utils.item.ItemBuilder;
import com.pepsidev.twisthub.Hub;
import com.pepsidev.twisthub.parkour.action.ActionManager;
import com.pepsidev.twisthub.parkour.command.ParkourCommand;
import com.pepsidev.twisthub.parkour.listener.Listener;
import com.pepsidev.twisthub.parkour.listener.ParkourListener;
import com.pepsidev.twisthub.utils.CC;
import com.pepsidev.twisthub.utils.files.DispatchFile;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class ParkourManager {

	private Hub hub;

	public HashSet<Player> parkour;
	public HashMap<Player, Location> checkPointLoc;
	public HashSet<Player> clickedPlate;

	public ParkourManager(Hub Hub) {
		this.hub = Hub;
		parkour = new HashSet<>();
		checkPointLoc = new HashMap<>();
		clickedPlate = new HashSet<>();
		Bukkit.getPluginManager().registerEvents(new ParkourListener(), hub);
		Bukkit.getPluginManager().registerEvents(new Listener(), hub);
		hub.getCommand("parkour").setExecutor(new ParkourCommand());
	}

	public void saveCheckPoint(Player player) {
		if (parkour.contains(player)) {
			checkPointLoc.putIfAbsent(player, player.getLocation());
			if (!checkPointLoc.containsKey(player)) {
				for (String message : DispatchFile.getConfig().getStringList("CheckPoint-Set")) {
					player.sendMessage(CC.translate(message));
				}
			}
			if (checkPointLoc.get(player).equals(player.getLocation())) {
				for (String message : DispatchFile.getConfig().getStringList("CheckPoint-Already-Set")) {
					player.sendMessage(CC.translate(message));
				}
				return;
			}
			if (!checkPointLoc.get(player).equals(player.getLocation())) {
				for (String message : DispatchFile.getConfig().getStringList("CheckPoint-Set")) {
					player.sendMessage(CC.translate(message));
				}
				checkPointLoc.put(player, player.getLocation());
			}
		}
	}

	public void activateParkour(Player player, Location location) {
		for (PotionEffect pot : player.getActivePotionEffects())
			player.removePotionEffect(pot.getType());
		for (String section : Hub.getInstance().getConfig().getConfigurationSection("Parkour.Effects").getKeys(false)) {
			player.addPotionEffect(new PotionEffect(PotionEffectType.getByName(section), Integer.MAX_VALUE, Hub.getInstance().getConfig().getInt("Parkour.Effects." + section + ".Amplifier") - 1));
		}
		player.getInventory().clear();
		parkour.add(player);
		giveItems(player);
		player.teleport(location);
		for (String message : DispatchFile.getConfig().getStringList("Parkour-Started")) {
			player.sendMessage(CC.translate(message));
		}
		String sound = hub.getConfig().getString("Parkour.Start-Sound.Sound");
		boolean enabled = hub.getConfig().getBoolean("Parkour.Start-Sound.Enabled");
		if (enabled) {
			player.playSound(player.getLocation(), Sound.valueOf(sound), 1.0f, 1.0f);
		}
	}

	public void finishParkour(Player player) {
		for (PotionEffect effect : player.getActivePotionEffects())
			player.removePotionEffect(effect.getType());
		if (Hub.getInstance().getConfig().getBoolean("Walk-Speed.Enabled")) {
			player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, Hub.getInstance().getConfig().getInt("Walk-Speed.Speed") - 1, true));
		}
		player.getInventory().clear();
		parkour.remove(player);
		teleportToSpawn(player);
		if (Hub.getInstance().getConfig().getBoolean("Parkour.Broadcast.Enabled")) {
			for (String message : Hub.getInstance().getConfig().getStringList("Parkour.Broadcast.Message")) {
				Bukkit.broadcastMessage(CC.translate(message).replaceAll("%player%", player.getName()));
			}
		}
		for (Player online : Bukkit.getOnlinePlayers()) {
			String sound = hub.getConfig().getString("Parkour.Finish-Sound.Sound");
			boolean enabled = hub.getConfig().getBoolean("Parkour.Finish-Sound.Enabled");
			if (enabled) {
				online.playSound(online.getLocation(), Sound.valueOf(sound), 1.0f, 1.0f);
			}
		}
		checkPointLoc.remove(player);
		//new ActionManager().giveJoinItems(player);

	}

	public void deactivateParkour(Player player) {
		for (PotionEffect effect : player.getActivePotionEffects())
			player.removePotionEffect(effect.getType());
		if (Hub.getInstance().getConfig().getBoolean("Walk-Speed.Enabled")) {
			player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, Hub.getInstance().getConfig().getInt("Walk-Speed.Speed") - 1, true));
		}
		player.getInventory().clear();
		parkour.remove(player);
		teleportToSpawn(player);
		//new ActionManager().giveJoinItems(player);
		for (String message : DispatchFile.getConfig().getStringList("Parkour-Left")) {
			player.sendMessage(CC.translate(message));
		}
		String sound = hub.getConfig().getString("Parkour.Left-Sound.Sound");
		boolean enabled = hub.getConfig().getBoolean("Parkour.Left-Sound.Enabled");
		if (enabled) {
			player.playSound(player.getLocation(), Sound.valueOf(sound), 1.0f, 1.0f);
		}
		checkPointLoc.remove(player);
	}


	private void teleportToSpawn(Player player) {
		Location location = player.getLocation();
		float yaw = Hub.getInstance().getConfig().getInt("Spawn.Yaw");
		float pitch = Hub.getInstance().getConfig().getInt("Spawn.Pitch");
		double x = Hub.getInstance().getConfig().getDouble("Spawn.X");
		double y = Hub.getInstance().getConfig().getDouble("Spawn.Y");
		double z = Hub.getInstance().getConfig().getDouble("Spawn.Z");

		location.setYaw(yaw);
		location.setPitch(pitch);
		location.setY(y);
		location.setX(x);
		location.setZ(z);

		player.teleport(location);

	}

	public void giveItems(Player player) {
		player.getInventory().clear();
		for (String sect : Hub.getInstance().getConfig().getConfigurationSection("Parkour-Inventory").getKeys(false)) {
			String mat = Hub.getInstance().getConfig().getString("Parkour-Inventory." + sect + ".Material");
			String name = CC.translate(Hub.getInstance().getConfig().getString("Parkour-Inventory." + sect + ".Name"));
			List<String> lore = CC.translate(Hub.getInstance().getConfig().getStringList("Parkour-Inventory." + sect + ".Lore"));
			int data = Hub.getInstance().getConfig().getInt("Parkour-Inventory." + sect + ".Data");
			int slot = Hub.getInstance().getConfig().getInt("Parkour-Inventory." + sect + ".Slot");
			int amount = Hub.getInstance().getConfig().getInt("Parkour-Inventory." + sect + ".Amount");
			ItemStack stack = new ItemBuilder(Material.valueOf(mat), amount).displayName(name).data((short) data).lore(lore).build();
			player.getInventory().setItem(slot - 1, stack);
		}
		player.updateInventory();
	}

}
