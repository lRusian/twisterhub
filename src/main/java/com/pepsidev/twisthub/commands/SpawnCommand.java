package com.pepsidev.twisthub.commands;

import com.pepsidev.twisthub.Hub;
import com.pepsidev.twisthub.utils.CC;
import com.pepsidev.twisthub.utils.Util;
import com.pepsidev.twisthub.utils.files.ConfigFile;
import com.pepsidev.twisthub.utils.files.DispatchFile;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpawnCommand implements CommandExecutor {

    public SpawnCommand() {
        Hub.getInstance().getCommand("spawn").setExecutor(this);
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player)sender;
        if (!player.hasPermission("hub.spawn")) {
            for (String string : DispatchFile.getConfig().getStringList("dispatch.no-perms")) {
                CC.sender(player, string);
            }
            return false;
        } else {
            player.teleport(Util.stringToLocation(ConfigFile.getConfig().getString("settings.spawn-loc")));

            for (String string : DispatchFile.getConfig().getStringList("dispatch.spawn.tp")) {
                CC.sender(player, string);
            }
            return false;
        }
    }
}
