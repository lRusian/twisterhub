package com.pepsidev.twisthub.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class Item {
    private ItemStack itemStack;

    public Item setGlow(boolean type, int durability) {
        if (type) {
            ItemMeta meta = this.itemStack.getItemMeta();
            meta.addEnchant(Enchantment.DURABILITY, durability, true);
            this.itemStack.setItemMeta(meta);
        }

        return this;
    }

    public Item(String name) {
        this.itemStack = new ItemStack(Material.valueOf(name), 1);
    }

    public Item setGlow(boolean type) {
        if (type) {
            ItemMeta meta = this.itemStack.getItemMeta();
            meta.addEnchant(Enchantment.DURABILITY, 1, true);
            this.itemStack.setItemMeta(meta);
        }
        return this;
    }

    public Item setName(String name) {
        if (name != null) {
            name = ChatColor.translateAlternateColorCodes('&', name);
            ItemMeta meta = this.itemStack.getItemMeta();
            meta.setDisplayName(name);
            this.itemStack.setItemMeta(meta);
        }

        return this;
    }

    public Item setLore(String... lore) {
        if (lore != null) {
            ItemMeta meta = this.itemStack.getItemMeta();
            meta.setLore(CC.lore(Arrays.asList(lore)));
            this.itemStack.setItemMeta(meta);
        }

        return this;
    }

    public Item setDurability(int durability) {
        this.itemStack.setDurability((short)durability);
        return this;
    }

    public Item(ItemStack stack) {
        this.itemStack = stack.clone();
    }

    public Item setLore(List<String> lore) {
        if (lore != null) {
            List<String> list = new ArrayList();
            lore.forEach((s) -> {
                list.add(ChatColor.translateAlternateColorCodes('&', s));
            });
            ItemMeta meta = this.itemStack.getItemMeta();
            meta.setLore(list);
            this.itemStack.setItemMeta(meta);
        }

        return this;
    }

    public ItemStack get() {
        return this.itemStack;
    }

    public Item setDurability(short durability) {
        this.itemStack.setDurability(durability);
        return this;
    }

    public Item setOwner(String playerName) {
        if (this.itemStack.getType() == Material.SKULL_ITEM) {
            SkullMeta meta = (SkullMeta)this.itemStack.getItemMeta();
            meta.setOwner(playerName);
            this.itemStack.setItemMeta(meta);
            return this;
        } else {
            throw new IllegalArgumentException("setOwner() only applicable for Skull Item");
        }
    }

    public Item(Material material, int data) {
        this.itemStack = new ItemStack(material, 1, (short)data);
    }

    public Item(Material material) {
        this.itemStack = new ItemStack(material, 1);
    }

    public Item setArmorColor(Color color) {
        try {
            LeatherArmorMeta meta = (LeatherArmorMeta)this.itemStack.getItemMeta();
            meta.setColor(color);
            this.itemStack.setItemMeta(meta);
        } catch (Exception e) {
            Bukkit.getConsoleSender().sendMessage("Error set armor color.");
        }

        return this;
    }

    public Item setAmount(int mmount) {
        this.itemStack.setAmount(mmount);
        return this;
    }

    public Item(Material material, int ammount, int data) {
        this.itemStack = new ItemStack(material, ammount, (short)data);
    }
}
