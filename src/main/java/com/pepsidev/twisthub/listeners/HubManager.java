package com.pepsidev.twisthub.listeners;

import com.pepsidev.twisthub.utils.CC;
import com.pepsidev.twisthub.utils.Item;
import com.pepsidev.twisthub.utils.files.ConfigFile;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class HubManager {

    public void selector(final Player player) {
        final int slot = ConfigFile.getConfig().getInt("Selector.Slot");
        int amount = ConfigFile.getConfig().getInt("Selector.Amount");
        final boolean enabled = ConfigFile.getConfig().getBoolean("Selector.Enabled");
        final ItemStack selector = new ItemStack(Material.valueOf(ConfigFile.getConfig().getString("Selector.Item")));
        final ItemMeta selectormeta = selector.getItemMeta();
        selectormeta.setDisplayName(CC.translate(CC.t(player, PlaceholderAPI.setPlaceholders(player, ConfigFile.getConfig().getString("Selector.Name")))));
        final ArrayList<String> lore = new ArrayList<String>();
        for (final String selectorlore : ConfigFile.getConfig().getStringList("Selector.Lore")) {
            lore.add(CC.translate(CC.t(player, PlaceholderAPI.setPlaceholders(player, selectorlore))));
        }
        selectormeta.setLore(lore);
        selector.setAmount(amount);
        selector.setItemMeta(selectormeta);
        if (enabled) {
            player.getInventory().setItem(slot, selector);
        }
    }


    public void hub(final Player player) {
        final int slot = ConfigFile.getConfig().getInt("Hub.Slot");
        int amount = ConfigFile.getConfig().getInt("Hub.Amount");
        final boolean enabled = ConfigFile.getConfig().getBoolean("Hub.Enabled");
        final ItemStack hub = new ItemStack(Material.valueOf(ConfigFile.getConfig().getString("Hub.Item")));
        final ItemMeta hubmeta = hub.getItemMeta();
        hubmeta.setDisplayName(CC.translate(CC.t(player, PlaceholderAPI.setPlaceholders(player, ConfigFile.getConfig().getString("Hub.Name")))));
        final ArrayList<String> lore = new ArrayList<String>();
        for (final String hublore : ConfigFile.getConfig().getStringList("Hub.Lore")) {
            lore.add(CC.translate(CC.t(player, PlaceholderAPI.setPlaceholders(player, hublore))));
        }
        hubmeta.setLore(lore);
        hub.setItemMeta(hubmeta);
        hub.setAmount(amount);
        if (enabled) {
            player.getInventory().setItem(slot, hub);
        }
    }

    public static void setJoinItems(Player player) {
        if (ConfigFile.getConfig().getBoolean("EnderButt.Enabled")) {
            player.getInventory().setItem(ConfigFile.getConfig().getInt("EnderButt.Slot") - 1, getEnderButt(player));
        }
    }

    public static ItemStack getEnderButt(Player player) {
        return (new Item(ConfigFile.getConfig().getString("EnderButt.Item")))
                .setName(CC.t(player, PlaceholderAPI.setPlaceholders(player, ConfigFile.getConfig().getString("EnderButt.Name"))))
                .setLore(ConfigFile.getConfig().getStringList("EnderButt.Lore").stream()
                        .map(map -> CC.t(player, PlaceholderAPI.setPlaceholders(player, map))
                        ).collect(Collectors.toList()))
                .setAmount(ConfigFile.getConfig().getInt("EnderButt.Amount"))
                .get();
    }


}