package com.pepsidev.twisthub.setup;

import org.bukkit.entity.Player;

public interface PermissionCore {

    String getRankColor(Player player);
    
    String getRank(Player player);

    String getRankPrefix(Player player);
}
