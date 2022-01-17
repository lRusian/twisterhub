package com.pepsidev.twisthub.commands.media;

import com.pepsidev.twisthub.Hub;
import com.pepsidev.twisthub.utils.CC;
import com.pepsidev.twisthub.utils.files.ConfigFile;
import com.pepsidev.twisthub.utils.files.DispatchFile;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class TwitterCommand implements CommandExecutor {

    public TwitterCommand() {
        Hub.getInstance().getCommand("twitter").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player target = (Player) sender;
        if (DispatchFile.getConfig().getBoolean("dispatch.twitter.enabled")) {
            for (String string : DispatchFile.getConfig().getStringList("dispatch.twitter.lines")) {
                CC.sender(target, string);
            }
        }
        return true;
    }
}
