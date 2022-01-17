package com.pepsidev.twisthub.commands;

import com.pepsidev.twisthub.Hub;
import com.pepsidev.twisthub.utils.CC;
import com.pepsidev.twisthub.utils.files.ConfigFile;
import com.pepsidev.twisthub.utils.files.DispatchFile;
import com.pepsidev.twisthub.utils.files.ScoreboardFile;
import com.pepsidev.twisthub.utils.files.TablistFile;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReloadCommand implements CommandExecutor {

    public ReloadCommand() {
        Hub.getInstance().getCommand("treload").setExecutor(this);
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player)sender;
        if (!player.hasPermission("hub.reload")) {
            for (String string : DispatchFile.getConfig().getStringList("dispatch.no-perms")) {
                CC.sender(player, string);
            }
            return false;
        } else {
            ScoreboardFile.getConfig().reload();
            ConfigFile.getConfig().reload();
            TablistFile.getConfig().reload();
            for (String string : DispatchFile.getConfig().getStringList("dispatch.reload")) {
                CC.sender(player, string);
            }
            return false;
        }
    }
}
