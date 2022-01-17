package com.pepsidev.twisthub.utils.chat;

import net.minecraft.server.v1_7_R4.EnumClickAction;

public enum ClickAction
{

    SUGGEST_COMMAND(EnumClickAction.SUGGEST_COMMAND);
    
    private final EnumClickAction clickAction;
    
    private ClickAction(final EnumClickAction action) {
        this.clickAction = action;
    }
    
    public EnumClickAction getNMS() {
        return this.clickAction;
    }
}
