package com.pepsidev.twisthub.setup.type;

import com.broustudio.MizuAPI.MizuAPI;
import com.pepsidev.twisthub.Hub;
import com.pepsidev.twisthub.setup.PermissionCore;
import org.bukkit.entity.Player;


public class MizuPermissionCore implements PermissionCore {
    @Override
    public String getRankColor(Player player) {
        return MizuAPI.getAPI().getRankColor(MizuAPI.getAPI().getRank(player.getUniqueId()));
    }

    @Override
    public String getRank(Player player) {
        return MizuAPI.getAPI().getRank(player.getUniqueId());
    }

    @Override
    public String getRankPrefix(Player player) {

        return MizuAPI.getAPI().getRankPrefix(String.valueOf(player.getUniqueId()));
    }

}
