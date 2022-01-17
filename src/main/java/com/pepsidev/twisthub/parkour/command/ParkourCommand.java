package com.pepsidev.twisthub.parkour.command;

import com.pepsidev.twisthub.Hub;
import com.pepsidev.twisthub.utils.CC;
import com.pepsidev.twisthub.utils.files.DispatchFile;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ParkourCommand implements CommandExecutor {
	@SneakyThrows
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (Hub.getInstance().getConfig().getString("License").equalsIgnoreCase("")) {
			Bukkit.broadcastMessage("I think this is cracked?");
			return true;
		}
		if (!sender.hasPermission("xhub.command.parkour.admin")) {
			sender.sendMessage(CC.translate(DispatchFile.getConfig().getString("No-Perm")));
			return true;
		}

		if (!(sender instanceof Player)) {
			sender.sendMessage(CC.translate("&cPlayers only"));
			return true;
		}
		if (args.length == 1) {
			if (args[0].equalsIgnoreCase("setspawn")) {
				Player player = (Player) sender;
				sender.sendMessage(CC.translate("&aSet the parkour spawn!"));
				Hub.getInstance().getConfig().set("Parkour.Start-Location.X", player.getLocation().getX());
				Hub.getInstance().getConfig().set("Parkour.Start-Location.Y", player.getLocation().getY());
				Hub.getInstance().getConfig().set("Parkour.Start-Location.Z", player.getLocation().getZ());
				Hub.getInstance().getConfig().set("Parkour.Start-Location.Yaw", player.getLocation().getYaw());
				Hub.getInstance().getConfig().set("Parkour.Start-Location.Pitch", player.getLocation().getPitch());
				Hub.getInstance().getConfig().save("config.yml");
				return true;
			} else {
				for (String list : DispatchFile.getConfig().getStringList("Parkour-Help")) {
					sender.sendMessage(CC.translate(list));
				}
				return true;
			}
		} else {
			for (String list : DispatchFile.getConfig().getStringList("Parkour-Help")) {
				sender.sendMessage(CC.translate(list));
			}
		}
		return false;
	}
}
