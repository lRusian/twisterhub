package com.pepsidev.twisthub.scoreboard;

import com.pepsidev.twisthub.Hub;
import com.pepsidev.twisthub.commands.OwnerCommand;
import com.pepsidev.twisthub.queues.QueueManager;
import com.pepsidev.twisthub.utils.CC;
import com.pepsidev.twisthub.utils.files.ScoreboardFile;
import me.clip.placeholderapi.PlaceholderAPI;
import java.util.*;
import org.bukkit.entity.Player;

public class ScoreboardProvider implements AssembleAdapter {

    @Override
    public String getTitle(Player player) {
        return CC.translate(ScoreboardFile.getConfig().getString("scoreboard.title").replace("|", "\u2503"));
    }
    @Override
    public List<String> getLines(Player player) {
        List<String> toReturn = new ArrayList<>();

        if (OwnerCommand.getInstance().getBuildMode().contains(player.getUniqueId())) {
            ScoreboardFile.getConfig().getStringList("scoreboard.owner-mode").stream()
                    .map(CC::translate)
                    .map(line -> CC.t(player, PlaceholderAPI.setPlaceholders(player, line)))
                    .forEach(toReturn::add);
        } else if (Hub.getInstance().getQueueManager().inQueue(player)) {
            ScoreboardFile.getConfig().getStringList("scoreboard.in-queue").stream()
                    .map(CC::translate)
                    .map(line -> CC.t(player, PlaceholderAPI.setPlaceholders(player, line)))
                    .map(line -> line.replace("<server>", String.valueOf(Hub.getInstance().getQueueManager().getQueueIn(player))))
                    .map(line -> line.replace("<position>", String.valueOf(Hub.getInstance().getQueueManager().getPosition(player))))
                    .map(line -> line.replace("<size>", String.valueOf(Hub.getInstance().getQueueManager().getInQueue(Hub.getInstance().getQueueManager().getQueueIn(player)))))
                    .forEach(toReturn::add);
        } else {
            ScoreboardFile.getConfig().getStringList("scoreboard.default").stream()
                    .map(CC::translate)
                    .map(line -> CC.t(player, PlaceholderAPI.setPlaceholders(player, line)))
                    .forEach(toReturn::add);
        }

        return toReturn;
    }


}