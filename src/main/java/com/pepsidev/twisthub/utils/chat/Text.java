package com.pepsidev.twisthub.utils.chat;

import net.minecraft.server.v1_7_R4.*;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class Text extends ChatComponentText
{

    public Text(final String string) {
        super(string);
    }

    public void send(final CommandSender sender) {
        ChatUtil.send(sender, this);
    }


    public Text setColor(final ChatColor color) {
        this.getChatModifier().setColor(EnumChatFormat.valueOf(color.name()));
        return this;
    }
    public Text setClick(final ClickAction action, final String value) {
        this.getChatModifier().setChatClickable(new ChatClickable(action.getNMS(), value));
        return this;
    }

    public IChatBaseComponent f() {
        return this.h();
    }


}
