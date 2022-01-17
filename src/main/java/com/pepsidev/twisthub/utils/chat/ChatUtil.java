package com.pepsidev.twisthub.utils.chat;

import net.minecraft.server.v1_7_R4.*;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class ChatUtil
{
    public static String getName(final ItemStack stack) {
        final NBTTagCompound nbttagcompound;
        if (stack.tag != null && stack.tag.hasKeyOfType("display", 10) && (nbttagcompound = stack.tag.getCompound("display")).hasKeyOfType("Name", 8)) {
            return nbttagcompound.getString("Name");
        }
        return stack.getItem().a(stack) + ".name";
    }


    public static void send(final CommandSender sender, final IChatBaseComponent text) {
        if (sender instanceof Player) {
            final Player player = (Player)sender;
            final PacketPlayOutChat packet = new PacketPlayOutChat(text, true);
            final EntityPlayer entityPlayer = ((CraftPlayer)player).getHandle();
            entityPlayer.playerConnection.sendPacket((Packet)packet);
        }
        else {
            sender.sendMessage(text.c());
        }
    }
}
