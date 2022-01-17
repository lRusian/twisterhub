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

public class ScoreboardReloadSubCommand extends CommandArgument {

    public ScoreboardReloadSubCommand() {
        super("scoreboard", "Reload scoreboard configuration");
    }

    @Override
    public String getUsage(String label) {
        return "/" + label + " " + this.getName();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;

        ScoreboardFile.getConfig().reload();

        for (String string : DispatchFile.getConfig().getStringList("dispatch.reload.SCOREBOARD")) {
            CC.sender(player, string);
        }
        return true;
    }
}
