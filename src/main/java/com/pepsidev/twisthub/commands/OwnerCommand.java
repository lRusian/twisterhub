package com.pepsidev.twisthub.commands;

import com.google.common.collect.Sets;
import com.pepsidev.twisthub.Hub;
import com.pepsidev.twisthub.utils.CC;
import com.pepsidev.twisthub.utils.files.DispatchFile;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Set;
import java.util.UUID;

public class OwnerCommand implements CommandExecutor {

    private static OwnerCommand instance;
    private final Set<UUID> buildMode = Sets.newHashSet();
    public static OwnerCommand getInstance() {
        return instance;
    }
    public Set<UUID> getBuildMode() {
        return this.buildMode;
    }

    public OwnerCommand() {
        instance = this;
        Hub.getInstance().getCommand("owner").setExecutor(this);
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player) sender;
        if (!player.hasPermission("hub.ownermode")) {
            for (String string : DispatchFile.getConfig().getStringList("dispatch.no-perms")) {
                CC.sender(player, string);
            }
            return false;
        } else {
            if (this.buildMode.contains(player.getUniqueId())) {
                this.buildMode.remove(player.getUniqueId());
                for (String string : DispatchFile.getConfig().getStringList("dispatch.owner.remove")) {
                    CC.sender(player, string);
                }
            } else {
                this.buildMode.add(player.getUniqueId());
                for (String string : DispatchFile.getConfig().getStringList("dispatch.owner.active")) {
                    CC.sender(player, string);
                }
            }

            return true;
        }
    }
}