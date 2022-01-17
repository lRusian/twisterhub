package com.pepsidev.twisthub.setup.type;

import com.pepsidev.twisthub.Hub;
import com.pepsidev.twisthub.setup.PermissionCore;
import me.activated.core.plugin.AquaCoreAPI;
import org.bukkit.entity.Player;

public class AquaCorePermissionCore implements PermissionCore {

    @Override
    public String getRankColor(Player player) {
        return AquaCoreAPI.INSTANCE.getPlayerRank(player.getUniqueId()).getDisplayName();
    }

    @Override
    public String getRank(Player player) {
        return AquaCoreAPI.INSTANCE.getPlayerRank(player.getUniqueId()).getName();
    }

    @Override
    public String getRankPrefix(Player player) {

        return AquaCoreAPI.INSTANCE.getPlayerRank(player.getUniqueId()).getPrefix();
    }
}
