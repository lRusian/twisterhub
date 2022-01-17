package com.pepsidev.twisthub.setup.type;

import com.minexd.zoot.profile.Profile;
import com.pepsidev.twisthub.setup.PermissionCore;
import org.bukkit.entity.Player;

public class ZootPermissionCore implements PermissionCore {

    @Override
    public String getRankColor(Player player) {
        return Profile.getByUuid(player.getUniqueId()).getActiveGrant().getRank().getColor().name();
    }

    @Override
    public String getRank(Player p) {
        return Profile.getByUuid(p.getUniqueId()).getActiveGrant().getRank().getDisplayName();
    }

    @Override
    public String getRankPrefix(Player p) {
        return Profile.getByUuid(p.getUniqueId()).getActiveGrant().getRank().getPrefix();
    }

}
