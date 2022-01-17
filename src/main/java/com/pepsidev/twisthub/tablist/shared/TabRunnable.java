package com.pepsidev.twisthub.tablist.shared;

import com.pepsidev.twisthub.Hub;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class TabRunnable extends BukkitRunnable {

    private final TabHandler handler;

    /**
     * Constructor to make a new TabThread
     *
     * @param handler the handler to register it to
     */
    public TabRunnable(TabHandler handler) {
        this.handler = handler;
    }

    @Override
    public void run() {
        for (Player player : Hub.getInstance().getOnlinePlayers()) {
            this.handler.sendUpdate(player);
        }
    }
}