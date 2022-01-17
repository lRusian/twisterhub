package com.pepsidev.twisthub.commands.reload.impl;

import com.pepsidev.twisthub.utils.CC;
import com.pepsidev.twisthub.utils.commands.CommandArgument;
import com.pepsidev.twisthub.utils.files.ConfigFile;
import com.pepsidev.twisthub.utils.files.DispatchFile;
import com.pepsidev.twisthub.utils.files.ScoreboardFile;
import com.pepsidev.twisthub.utils.files.TablistFile;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TabListReloadSubCommand extends CommandArgument {

    public TabListReloadSubCommand() {
        super("tablist", "Reload tablist configuration");
    }

    @Override
    public String getUsage(String label) {
        return "/" + label + " " + this.getName();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;

        TablistFile.getConfig().reload();

        for (String string : DispatchFile.getConfig().getStringList("dispatch.reload.TABLIST")) {
            CC.sender(player, string);
        }
        return true;
    }
}
