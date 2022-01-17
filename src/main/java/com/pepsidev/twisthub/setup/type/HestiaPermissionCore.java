package com.pepsidev.twisthub.setup.type;

import com.pepsidev.twisthub.Hub;
import com.pepsidev.twisthub.setup.PermissionCore;
import me.quartz.hestia.HestiaAPI;
import org.bukkit.entity.Player;

public class HestiaPermissionCore implements PermissionCore {

    @Override
    public String getRankColor(Player player) {
        return HestiaAPI.instance.getRankColor(player.getUniqueId()).toString();
    }

    @Override
    public String getRank(Player player) {
        return HestiaAPI.instance.getRank(player.getUniqueId());
    }

    @Override
    public String getRankPrefix(Player player) {

        return HestiaAPI.instance.getRankPrefix(player.getUniqueId());
    }
}
