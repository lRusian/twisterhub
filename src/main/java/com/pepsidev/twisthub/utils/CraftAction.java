package com.pepsidev.twisthub.utils;

import org.bukkit.entity.*;
import org.bukkit.potion.*;
import java.util.*;

import org.bukkit.*;

public class CraftAction
{

    public static void executeActions(final Player player, String action) {
        action = action.replace("<player>", player.getName());
        action = ChatColor.translateAlternateColorCodes('&', action);
        if (action.startsWith("[console]")) {
            action = action.replace("[console] ", "");
            Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), action);
        }
        if (action.startsWith("[player]")) {
            action = action.replace("[player] ", "");
            player.performCommand(action);
        }
        if (action.startsWith("[op]")) {
            action = action.replace("[op] ", "");
            final List<Player> op = new ArrayList<Player>();
            if (player.isOp()) {
                op.add(player);
            }
            player.setOp(true);
            player.performCommand(action);
            player.setOp(false);
            if (op.contains(player)) {
                player.setOp(true);
                op.remove(player);
            }
        }
        if (action.startsWith("[message]")) {
            action = action.replace("[message] ", "");
            player.sendMessage(action);
        }

        if (action.startsWith("[broadcast]")) {
            action = action.replace("[broadcast] ", "");
            Bukkit.getServer().broadcastMessage(action);
        }
        if (action.startsWith("[sound]")) {
            action = action.replace("[sound] ", "");
            final String[] temp = action.split(";");
            final String sound = temp[0];
            final int volume = Integer.valueOf(temp[1]);
            final int pitch = Integer.valueOf(temp[2]);
            player.playSound(player.getLocation(), Sound.valueOf(sound.toUpperCase()), (float)volume, (float)pitch);
        }
        if (action.startsWith("[soundbc]")) {
            action = action.replace("[soundbc] ", "");
            final String[] temp = action.split(";");
            final String sound = temp[0];
            final int volume = Integer.valueOf(temp[1]);
            final int pitch = Integer.valueOf(temp[2]);
            for (final Player pls : Bukkit.getServer().getOnlinePlayers()) {
                pls.playSound(pls.getLocation(), Sound.valueOf(sound.toUpperCase()), (float)volume, (float)pitch);
            }
        }
        if (action.startsWith("[effect]")) {
            action = action.replace("[effect] ", "");
            final String[] temp = action.split(";");
            final String effect = temp[0];
            final int data = Integer.valueOf(temp[1]);
            player.playEffect(player.getLocation(), Effect.valueOf(effect.toUpperCase()), data);
        }
        if (action.startsWith("[effectbc]")) {
            action = action.replace("[effectbc] ", "");
            final String[] temp = action.split(";");
            final String effect = temp[0];
            final int data = Integer.valueOf(temp[1]);
            for (final Player pls2 : Bukkit.getServer().getOnlinePlayers()) {
                pls2.playEffect(pls2.getLocation(), Effect.valueOf(effect.toUpperCase()), data);
            }
        }
        if (action.startsWith("[teleport]")) {
            action = action.replace("[teleport] ", "");
            final String[] temp = action.split(";");
            final World w = Bukkit.getServer().getWorld(temp[0]);
            final int x = Integer.valueOf(temp[1]);
            final int y = Integer.valueOf(temp[2]);
            final int z = Integer.valueOf(temp[3]);
            final Location l = new Location(w, (double)x, (double)y, (double)z);
            player.teleport(l);
        }
        if (action.startsWith("[xp]")) {
            action = action.replace("[xp] ", "");
            final int orbs = Integer.valueOf(action);
            player.giveExp(orbs);
        }
        if (action.startsWith("[potion]")) {
            action = action.replace("[potion] ", "");
            final String[] temp = action.split(";");
            final String effect = temp[0];
            final int duration = 20 * Integer.valueOf(temp[1]);
            final int amplifier = Integer.valueOf(temp[2]);
            player.addPotionEffect(new PotionEffect(PotionEffectType.getByName(effect), duration, amplifier));
        }

    }
}