package com.pepsidev.twisthub.utils;

import com.pepsidev.twisthub.Hub;
import com.pepsidev.twisthub.utils.files.ConfigFile;
import com.sun.org.apache.xerces.internal.xs.StringList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class CC {

    public static String translate(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }
    public static List<String> translate(List<String> input) {
        return input.stream().map(CC::translate).collect(Collectors.toList());
    }

    public static List<String> lore(List<String> input) {
        return (List)input.stream().map(CC::set).collect(Collectors.toList());
    }

    public static String NO_CONSOLE = CC.translate("&cNo Console!");
    public static String set(String input) {
        return ChatColor.translateAlternateColorCodes('&', input);
    }
    public static void log(String in) {
        Bukkit.getConsoleSender().sendMessage(translate(in));
    }



    public static void sender(CommandSender sender, String in) {
        Player player = (Player) sender;
        double tps = Bukkit.spigot().getTPS()[0];
        tps = Math.min(tps, 20.0);
        double finalTps = tps;
        ChatColor colour = (tps >= 18.0) ? ChatColor.GREEN : ((tps >= 13.0) ? ChatColor.YELLOW : ChatColor.RED);
        if (ConfigFile.getConfig().getBoolean("settings.prefix-rank")) {
            sender.sendMessage(translate(in.replace("<player>", player.getName())
                    .replace("<discord>", ConfigFile.getConfig().getString("server-information.discord"))
                    .replace("<store>", ConfigFile.getConfig().getString("server-information.store"))
                    .replace("<teamspeak>", ConfigFile.getConfig().getString("server-information.teamspeak"))
                    .replace("<twitter>", ConfigFile.getConfig().getString("server-information.twitter"))
                    .replace("<website>", ConfigFile.getConfig().getString("server-information.website"))
                    .replace("<rank>", Hub.getInstance().getPermissionCore().getRank(player))
                    .replace("<crank>", Hub.getInstance().getPermissionCore().getRankColor(player))
                    .replace("<tps>", colour + String.format(Locale.ROOT, "%.2f", finalTps))
                    .replace("<online>", String.valueOf(Bukkit.getServer().getOnlinePlayers().size()))
                    .replace("<gamemode>", player.getGameMode().name())
                    .replace("<prefix-rank>", Hub.getInstance().getPermissionCore().getRankPrefix(player)
                            .replace("[", "").replace("]", "")
                            .replace("(", "").replace(")", ""))));
        } else if (!ConfigFile.getConfig().getBoolean("settings.prefix-rank")) {
            sender.sendMessage(translate(in.replace("<player>", player.getName())
                    .replace("<discord>", ConfigFile.getConfig().getString("server-information.discord"))
                    .replace("<store>", ConfigFile.getConfig().getString("server-information.store"))
                    .replace("<teamspeak>", ConfigFile.getConfig().getString("server-information.teamspeak"))
                    .replace("<twitter>", ConfigFile.getConfig().getString("server-information.twitter"))
                    .replace("<website>", ConfigFile.getConfig().getString("server-information.website"))
                    .replace("<rank>", Hub.getInstance().getPermissionCore().getRank(player))
                    .replace("<crank>", Hub.getInstance().getPermissionCore().getRankColor(player))
                    .replace("<tps>", colour + String.format(Locale.ROOT, "%.2f", finalTps))
                    .replace("<online>", String.valueOf(Bukkit.getServer().getOnlinePlayers().size()))
                    .replace("<gamemode>", player.getGameMode().name())
                    .replace("<prefix-rank>", Hub.getInstance().getPermissionCore().getRankPrefix(player))));
        }
    }

    public static String t(Player player, String input) {
        double tps = Bukkit.spigot().getTPS()[0];
        tps = Math.min(tps, 20.0);
        double finalTps = tps;
        ChatColor colour = (tps >= 18.0) ? ChatColor.GREEN : ((tps >= 13.0) ? ChatColor.YELLOW : ChatColor.RED);

        if (ConfigFile.getConfig().getBoolean("settings.prefix-rank")) {
            return input.replace("<player>", player.getName())
                    .replace("<discord>", ConfigFile.getConfig().getString("server-information.discord"))
                    .replace("<store>", ConfigFile.getConfig().getString("server-information.store"))
                    .replace("<teamspeak>", ConfigFile.getConfig().getString("server-information.teamspeak"))
                    .replace("<twitter>", ConfigFile.getConfig().getString("server-information.twitter"))
                    .replace("<website>", ConfigFile.getConfig().getString("server-information.website"))
                    .replace("<rank>", Hub.getInstance().getPermissionCore().getRank(player))
                    .replace("<color-rank>", Hub.getInstance().getPermissionCore().getRankColor(player))
                    .replace("<tps>", colour + String.format(Locale.ROOT, "%.2f", finalTps))
                    .replace("<online>", String.valueOf(Bukkit.getServer().getOnlinePlayers().size()))
                    .replace("<gamemode>", player.getGameMode().name())
                    .replace("<prefix-rank>", Hub.getInstance().getPermissionCore().getRankPrefix(player)
                            .replace("[", "").replace("]", "")
                            .replace("(", "").replace(")", ""));

        } else if (!ConfigFile.getConfig().getBoolean("settings.prefix-rank")) {
            return input.replace("<player>", player.getName())
                    .replace("<discord>", ConfigFile.getConfig().getString("server-information.discord"))
                    .replace("<store>", ConfigFile.getConfig().getString("server-information.store"))
                    .replace("<teamspeak>", ConfigFile.getConfig().getString("server-information.teamspeak"))
                    .replace("<twitter>", ConfigFile.getConfig().getString("server-information.twitter"))
                    .replace("<website>", ConfigFile.getConfig().getString("server-information.website"))
                    .replace("<rank>", Hub.getInstance().getPermissionCore().getRank(player))
                    .replace("<color-rank>", Hub.getInstance().getPermissionCore().getRankColor(player))
                    .replace("<tps>", colour + String.format(Locale.ROOT, "%.2f", finalTps))
                    .replace("<online>", String.valueOf(Bukkit.getServer().getOnlinePlayers().size()))
                    .replace("<gamemode>", player.getGameMode().name())
                    .replace("<prefix-rank>", Hub.getInstance().getPermissionCore().getRankPrefix(player));

        }

        return input;
    }

}
