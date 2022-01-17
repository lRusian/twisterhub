package com.pepsidev.twisthub.listeners;

import com.pepsidev.canaligy.PepsiDev;
import com.pepsidev.lanaligy.Analigy;
import com.pepsidev.twisthub.Hub;
import com.pepsidev.twisthub.utils.CC;
import com.pepsidev.twisthub.utils.CraftAction;
import com.pepsidev.twisthub.utils.Util;
import com.pepsidev.twisthub.utils.files.ConfigFile;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class PlayerListener  implements Listener {

    HubManager hubManager;

    public PlayerListener() {
        Bukkit.getServer().getPluginManager().registerEvents(this, Hub.getInstance());
        this.hubManager = new HubManager();
    }

    @EventHandler
    private void onJoin(PlayerJoinEvent event) {
        event.setJoinMessage(null);
        Player player = event.getPlayer();
        player.setHealth(20.0D);
        player.setFoodLevel(20);
        player.setGameMode(GameMode.ADVENTURE);
        player.teleport(Util.stringToLocation(ConfigFile.getConfig().getString("settings.spawn-loc")));

        if (ConfigFile.getConfig().getBoolean("welcome-player.enable")) {
            if (Bukkit.getServer().getPluginManager().isPluginEnabled("PlaceholderAPI")) {
                ConfigFile.getConfig().getStringList("welcome-player.message").forEach((s) -> {
                    String s1 = CC.t(player, s);
                    String dataStorageChoice = Hub.getInstance().getConfig().getString("system.analigy");
                    if (dataStorageChoice.equalsIgnoreCase("LANALIGY")) {
                        player.sendMessage(CC.set(PlaceholderAPI.setPlaceholders(player, s1.replaceAll("<coins>", String.valueOf(Analigy.get().getDatabaseManager().getCoins(Analigy.get().getMySQLInfo().getConnection(), player.getUniqueId()))))));
                    } else if (dataStorageChoice.equalsIgnoreCase("CANALIGY")) {
                        player.sendMessage(CC.set(PlaceholderAPI.setPlaceholders(player, s1.replaceAll("<coins>", String.valueOf(PepsiDev.get().getDatabaseManager().getCoins(PepsiDev.get().getMySQLInfo().getConnection(), player.getUniqueId()))))));
                    }
                    else if (dataStorageChoice.equalsIgnoreCase("NONE")){
                        player.sendMessage(CC.set(PlaceholderAPI.setPlaceholders(player, s1)));
                    }
                });
            } else {
                ConfigFile.getConfig().getStringList("welcome-player.message").forEach((s) -> {
                    String s1 = CC.t(player, s);

                    String dataStorageChoice = Hub.getInstance().getConfig().getString("system.analigy");
                    if (dataStorageChoice.equalsIgnoreCase("LANALIGY")) {
                        player.sendMessage(CC.set(s1.replaceAll("<coins>", String.valueOf(Analigy.get().getDatabaseManager().getCoins(Analigy.get().getMySQLInfo().getConnection(), player.getUniqueId())))));
                    } else if (dataStorageChoice.equalsIgnoreCase("CANALIGY")) {
                        player.sendMessage(CC.set(s1.replaceAll("<coins>", String.valueOf(PepsiDev.get().getDatabaseManager().getCoins(PepsiDev.get().getMySQLInfo().getConnection(), player.getUniqueId())))));
                    }
                    else if (dataStorageChoice.equalsIgnoreCase("NONE")){
                        player.sendMessage(CC.set(s1));
                    }
                });
            }
        }

        this.hubManager.selector(player);
        this.hubManager.hub(player);
        HubManager.setJoinItems(player);

    }

    @EventHandler
    public void onEnderButt(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack stack = player.getItemInHand();
        if ((event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR) && stack != null && stack.isSimilar(hubManager.getEnderButt(player))) {
            event.setCancelled(true);
            player.setVelocity(player.getLocation().getDirection().normalize().setY(2.0F));
            player.setVelocity(player.getLocation().getDirection().normalize().multiply(2.0F));
            player.updateInventory();
            player.playSound(player.getLocation(), Sound.ORB_PICKUP, 1.0F, 1.0F);
        }

    }

    @EventHandler
    public void onChat(final AsyncPlayerChatEvent event) {
        final boolean enabled = ConfigFile.getConfig().getBoolean("settings.chat.enable");
        if (!enabled) {
            event.setCancelled(true);
        }
        else if (event.getPlayer().hasPermission("chat.bypass")) {
            event.setCancelled(false);
        }
    }

    @EventHandler
    public void onSpawn(final EntitySpawnEvent event) {
        final boolean enabled = ConfigFile.getConfig().getBoolean("settings.mobs-spawn");
        if (!enabled) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onRightClick(final PlayerInteractEvent event) {
        final Player player = event.getPlayer();
        final Inventory inventory = Bukkit.createInventory(player,
                ConfigFile.getConfig().getInt("Inventory.Selector.Slots"),
                CC.translate(CC.t(player, PlaceholderAPI.setPlaceholders(player, ConfigFile.getConfig().getString("Inventory.Selector.Title")))));
        if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if (player.getItemInHand() == null || player.getItemInHand().getItemMeta() == null || player.getItemInHand().getItemMeta().getDisplayName() == null) {
                return;
            }
            if (player.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(CC.translate(ConfigFile.getConfig().getString("Selector.Name")))) {
                for (String id : ConfigFile.getConfig().getConfigurationSection("Inventory.Selector.Items").getKeys(false)) {

                    ItemStack items = new ItemStack(Material.valueOf(ConfigFile.getConfig().getString("Inventory.Selector.Items." + id + ".Item")));
                    ItemMeta itemsmeta = items.getItemMeta();

                    int slot = ConfigFile.getConfig().getInt("Inventory.Selector.Items." + id + ".Slot");

                    itemsmeta.setDisplayName(CC.translate(CC.t(player, PlaceholderAPI.setPlaceholders(player, ConfigFile.getConfig().getString("Inventory.Selector.Items." + id + ".Name")))));
                    ArrayList<String> lore = new ArrayList<String>();

                    if (Bukkit.getServer().getPluginManager().isPluginEnabled("PlaceholderAPI")) {
                        for (String loremessage : ConfigFile.getConfig().getStringList("Inventory.Selector.Items." + id + ".Lore")) {
                            lore.add(CC.translate(CC.t(player, PlaceholderAPI.setPlaceholders(player, loremessage))));
                        }
                    } else {
                        for (String loremessage : ConfigFile.getConfig().getStringList("Inventory.Selector.Items." + id + ".Lore")) {
                            lore.add(CC.translate(CC.t(player, loremessage)));
                        }
                    }

                    itemsmeta.setLore(lore);
                    items.setItemMeta(itemsmeta);
                    inventory.setItem(slot, items);
                }
                player.openInventory(inventory);
            }
        }
    }

    @EventHandler
    public void onRightClick2(final PlayerInteractEvent event) {
        final Player player = event.getPlayer();



        final Inventory inventory = Bukkit.createInventory(player,
                ConfigFile.getConfig().getInt("Inventory.Hub.Slots"),
                CC.translate(CC.t(player, PlaceholderAPI.setPlaceholders(player, ConfigFile.getConfig().getString("Inventory.Hub.Title")))));


        if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if (player.getItemInHand() == null || player.getItemInHand().getItemMeta() == null || player.getItemInHand().getItemMeta().getDisplayName() == null) {
                return;
            }
            if (player.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(CC.translate(ConfigFile.getConfig().getString("Hub.Name")))) {
                for (final String id : ConfigFile.getConfig().getConfigurationSection("Inventory.Hub.Items").getKeys(false)) {
                    final int slot = ConfigFile.getConfig().getInt("Inventory.Hub.Items." + id + ".Slot");
                    final ItemStack items = new ItemStack(Material.valueOf(ConfigFile.getConfig().getString("Inventory.Hub.Items." + id + ".Item")));
                    final ItemMeta itemsmeta = items.getItemMeta();
                    itemsmeta.setDisplayName(CC.translate(CC.t(player, PlaceholderAPI.setPlaceholders(player, ConfigFile.getConfig().getString("Inventory.Hub.Items." + id + ".Name")))));
                    final ArrayList<String> lore = new ArrayList<String>();

                    if (Bukkit.getServer().getPluginManager().isPluginEnabled("PlaceholderAPI")) {
                        for (final String loremessage : ConfigFile.getConfig().getStringList("Inventory.Hub.Items." + id + ".Lore")) {
                            lore.add(CC.translate(CC.t(player, PlaceholderAPI.setPlaceholders(player, loremessage))));
                        }
                    } else {
                        for (final String loremessage : ConfigFile.getConfig().getStringList("Inventory.Hub.Items." + id + ".Lore")) {
                            lore.add(CC.translate(CC.t(player, loremessage)));
                        }
                    }



                    itemsmeta.setLore(lore);
                    items.setItemMeta(itemsmeta);
                    inventory.setItem(slot, items);
                }
                player.openInventory(inventory);
            }
        }
    }

    @EventHandler
    public void onClick(final InventoryClickEvent event) {
        final Player player = (Player)event.getWhoClicked();
        if (event.getClickedInventory() == null || event.getClickedInventory().getTitle() == null) {
            return;
        }

        if (event.getClickedInventory().getTitle().equalsIgnoreCase(CC.translate(CC.t(player, PlaceholderAPI.setPlaceholders(player, ConfigFile.getConfig().getString("Inventory.Selector.Title")))))) {
            event.setCancelled(true);
        }

        for (final String id : ConfigFile.getConfig().getConfigurationSection("Inventory.Selector.Items").getKeys(false)) {
            if (event.getCurrentItem() == null || event.getCurrentItem().getItemMeta() == null || event.getCurrentItem().getItemMeta().getDisplayName() == null) {
                return;
            }
            if (!event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(CC.translate(ConfigFile.getConfig().getString("Inventory.Selector.Items." + id + ".Name")))) {
                continue;
            }

            List<String> commands = ConfigFile.getConfig().getStringList("Inventory.Selector.Items." + id + ".Commands");
            commands.forEach(action -> {
                action = action;
                CraftAction.executeActions(player, action);
                return;
            });

            if (ConfigFile.getConfig().getBoolean("Inventory.Selector.Items." + id + ".Autoqueue")) {
                Hub.getInstance().getQueueManager().sendPlayer(player, ConfigFile.getConfig().getString("Inventory.Selector.Items." + id + ".Server"));
            }

        }
        if (event.getClickedInventory() == null || event.getClickedInventory().getTitle() == null) {
            return;
        }
        if (Bukkit.getServer().getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            if (event.getClickedInventory().getTitle().equalsIgnoreCase(CC.translate(CC.t(player, PlaceholderAPI.setPlaceholders(player, ConfigFile.getConfig().getString("Inventory.Hub.Title")))))) {
                event.setCancelled(true);
            }
        } else {
            if (event.getClickedInventory().getTitle().equalsIgnoreCase(CC.translate(CC.t(player, ConfigFile.getConfig().getString("Inventory.Hub.Title"))))) {
                event.setCancelled(true);
            }
        }
        for (final String id : ConfigFile.getConfig().getConfigurationSection("Inventory.Hub.Items").getKeys(false)) {
            if (event.getCurrentItem() == null || event.getCurrentItem().getItemMeta() == null || event.getCurrentItem().getItemMeta().getDisplayName() == null) {
                return;
            }
            if (!event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(CC.translate(ConfigFile.getConfig().getString("Inventory.Hub.Items." + id + ".Name")))) {
                continue;
            }

            List<String> commands = ConfigFile.getConfig().getStringList("Inventory.Hub.Items." + id + ".Commands");
            commands.forEach(action -> {
                action = action;
                CraftAction.executeActions(player, action);
                return;
            });
        }
    }

    @EventHandler
    public void onChat2(final AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if (player.hasPermission("hub.staff")) {
            event.setFormat(CC.translate(CC.t(player, ConfigFile.getConfig().getString("settings.chat.staff")).replace("<message>", event.getMessage())));
        } else if (player.hasPermission("hub.donator")) {
            event.setFormat(CC.translate(CC.t(player, ConfigFile.getConfig().getString("settings.chat.donator")).replace("<message>", event.getMessage())));
        } else {
            event.setFormat(CC.translate(CC.t(player, ConfigFile.getConfig().getString("settings.chat.format")).replace("<message>", event.getMessage())));

        }

    }

}
