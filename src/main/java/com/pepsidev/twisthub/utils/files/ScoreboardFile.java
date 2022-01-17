package com.pepsidev.twisthub.utils.files;

import com.pepsidev.twisthub.Hub;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ScoreboardFile extends YamlConfiguration {

    private File file;
    private static ScoreboardFile config;

    public ScoreboardFile() {
        file = new File(Hub.getInstance().getDataFolder(), "scoreboard.yml");
        if(!file.exists()) Hub.getInstance().saveResource("scoreboard.yml", false);
        this.reload();
    }

    public static ScoreboardFile getConfig() {
        if(config == null) {
            config = new ScoreboardFile();
        }
        return config;
    }

    public void save() {
        try {
            super.save(this.file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reload() {
        try {
            super.load(this.file);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }
}
