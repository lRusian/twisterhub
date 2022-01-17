package com.pepsidev.twisthub.setup.type;

import club.frozed.core.ZoomAPI;
import com.pepsidev.twisthub.Hub;
import com.pepsidev.twisthub.setup.PermissionCore;
import org.bukkit.entity.Player;

public class ZoomPermissionCore implements PermissionCore {

    @Override
    public String getRankColor(Player player) {
        return null;
    }

    @Override
    public String getRank(Player player) {
        return ZoomAPI.getRankName(player);
    }

    @Override
    public String getRankPrefix(Player player) {

        return ZoomAPI.getRankPrefix(player);
    }
}
