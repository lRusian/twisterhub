package com.pepsidev.twisthub.commands.media;

import com.pepsidev.twisthub.Hub;
import com.pepsidev.twisthub.utils.CC;
import com.pepsidev.twisthub.utils.files.ConfigFile;
import com.pepsidev.twisthub.utils.files.DispatchFile;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TeamspeakCommand implements CommandExecutor {

    public TeamspeakCommand() {
        Hub.getInstance().getCommand("teamspeak").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player target = (Player) sender;
        if (DispatchFile.getConfig().getBoolean("dispatch.teamspeak.enabled")) {
            for (String string : DispatchFile.getConfig().getStringList("dispatch.teamspeak.lines")) {
                CC.sender(target, string);
            }
        }
        return true;
    }
}
