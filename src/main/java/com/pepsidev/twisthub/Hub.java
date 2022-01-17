package com.pepsidev.twisthub;

import com.pepsidev.twisthub.commands.OwnerCommand;
import com.pepsidev.twisthub.commands.ReloadCommand;
import com.pepsidev.twisthub.commands.SpawnCommand;
import com.pepsidev.twisthub.commands.media.*;
import com.pepsidev.twisthub.commands.reload.PepsiCommand;
import com.pepsidev.twisthub.listeners.DeveloperListener;
import com.pepsidev.twisthub.listeners.JumpListener;
import com.pepsidev.twisthub.listeners.PlayerListener;
import com.pepsidev.twisthub.listeners.WorldListerner;
import com.pepsidev.twisthub.parkour.ParkourManager;
import com.pepsidev.twisthub.scoreboard.Assemble;
import com.pepsidev.twisthub.scoreboard.AssembleStyle;
import com.pepsidev.twisthub.scoreboard.ScoreboardProvider;
import com.pepsidev.twisthub.setup.PermissionCore;
import com.pepsidev.twisthub.setup.type.*;
import com.pepsidev.twisthub.utils.CC;
import com.pepsidev.twisthub.utils.FileLicense;
import com.pepsidev.twisthub.utils.files.ConfigFile;
import com.pepsidev.twisthub.utils.files.DispatchFile;
import lombok.Getter;
import com.pepsidev.twisthub.commands.SetSpawnCommand;
import com.pepsidev.twisthub.queues.QueueManager;
import com.pepsidev.twisthub.tablist.TablistProvider;
import com.pepsidev.twisthub.tablist.shared.TabHandler;
import com.pepsidev.twisthub.tablist.v1_7_R4.v1_7_R4TabAdapter;
import com.pepsidev.twisthub.tablist.v1_8_R3.v1_8_R3TabAdapter;
import com.pepsidev.twisthub.utils.files.ScoreboardFile;
import com.pepsidev.twisthub.utils.files.TablistFile;
import net.milkbowl.vault.chat.Chat;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Collection;

@Getter
public final class Hub extends JavaPlugin {

    private QueueManager queueManager;
    private Chat chat;
    private PermissionCore permissionCore;
    private ParkourManager parkourManager;

    @Override
    public void onEnable() {
        this.commands();
        CC.log("&aCommand connecting!");
        this.listeners();
        CC.log("&aListeners connecting!");
        this.permissions();
        CC.log("&aPermissions connecting!");
        this.bungee();
        CC.log("&aBungeecord connecting!");
        this.scoreboard();
        CC.log("&aScoreboard connecting!");
        this.tablist();
        CC.log("&aTablist connecting!");
        queueManager = new QueueManager();
        CC.log("&aQueue connecting!");
        DispatchFile.getConfig().reload();
        this.managers();


        if (!new FileLicense(this.getConfig().getString("server-information.license"), "http://licensekeyphcf.000webhostapp.com/verify.php", this).register()) {
            Bukkit.getPluginManager().disablePlugin(this);
            Bukkit.getScheduler().cancelTasks(this);
            return;
        }
    }

    private void permissions() {
        String system = ConfigFile.getConfig().getString("system.rank");
        switch (system) {
            case "AquaCore":
                permissionCore = new AquaCorePermissionCore();
                break;
            case "Vault":
                permissionCore = new VaultPermissionCore();
                break;
            case "Mizu":
                permissionCore = new MizuPermissionCore();
                break;
            case "Hestia":
                permissionCore = new HestiaPermissionCore();
                break;
            case "Zoom":
                permissionCore = new ZoomPermissionCore();
                break;
            case "Zoot":
                permissionCore = new ZootPermissionCore();
                break;
        }
    }

    private void commands() {
        new DiscordCommand();
        new StoreCommand();
        new TeamspeakCommand();
        new TwitterCommand();
        new OwnerCommand();
        new WebsiteCommand();
        new SetSpawnCommand();
        new ReloadCommand();
        new SpawnCommand();
        new PepsiCommand(this);
    }

    private void listeners() {
        new PlayerListener();
        new DeveloperListener();
        new JumpListener();
        new WorldListerner();
    }

    private void managers() {
        parkourManager = new ParkourManager(this);
    }

    private void bungee() {
    }

    private void scoreboard() {
        if (ScoreboardFile.getConfig().getBoolean("scoreboard.enabled")) {
            Assemble assemble = new Assemble(this, new ScoreboardProvider());
            assemble.setTicks(2);
            assemble.setAssembleStyle(AssembleStyle.VIPER);
        }
    }

    private void tablist() {
        if (TablistFile.getConfig().getBoolean("tablist.enabled")) {
            if (Bukkit.getVersion().contains("1.7")) {
                new TabHandler(new v1_7_R4TabAdapter(), new TablistProvider(), this, 20L);
            }
            if (Bukkit.getVersion().contains("1.8")) {
                new TabHandler(new v1_8_R3TabAdapter(), new TablistProvider(), this, 20L);
            }
        }
    }

    @Override
    public void onDisable() {
        ConfigFile.getConfig().save();
        DispatchFile.getConfig().save();
        ScoreboardFile.getConfig().save();
        TablistFile.getConfig().save();

    }

    public static Hub getInstance() {
        return Hub.getPlugin(Hub.class);
    }

    public Collection<? extends Player> getOnlinePlayers() {
        Collection<Player> collection = new ArrayList<>();
        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            collection.add(player);
        }
        return collection;
    }
}
