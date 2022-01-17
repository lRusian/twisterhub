package com.pepsidev.twisthub.commands;

import com.pepsidev.twisthub.Hub;
import com.pepsidev.twisthub.utils.CC;
import com.pepsidev.twisthub.utils.Util;
import com.pepsidev.twisthub.utils.files.ConfigFile;
import com.pepsidev.twisthub.utils.files.DispatchFile;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetSpawnCommand implements CommandExecutor {

   public SetSpawnCommand() {
      Hub.getInstance().getCommand("setspawn").setExecutor(this);
   }

   public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
      Player player = (Player)sender;
      if (!player.hasPermission("hub.setspawn")) {
         for (String string : DispatchFile.getConfig().getStringList("dispatch.no-perms")) {
            CC.sender(player, string);
         }
         return false;
      } else {
         ConfigFile.getConfig().set("settings.spawn-loc", Util.locationToString(player.getLocation()));
         ConfigFile.getConfig().save();

         for (String string : DispatchFile.getConfig().getStringList("dispatch.spawn.set")) {
            CC.sender(player, string);
         }
         return false;
      }
   }
}
