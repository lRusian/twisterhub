package com.pepsidev.twisthub.commands.reload;

import com.pepsidev.twisthub.Hub;
import com.pepsidev.twisthub.commands.reload.impl.*;
import com.pepsidev.twisthub.utils.commands.ArgumentExecutor;

public class PepsiCommand extends ArgumentExecutor {

    public PepsiCommand(Hub plugin) {
        super("pepsi");

        this.addArgument(new AllReloadSubCommand());
        this.addArgument(new ConfigReloadSubCommand());
        this.addArgument(new TabListReloadSubCommand());
        this.addArgument(new DispatchReloadSubCommand());
        this.addArgument(new ScoreboardReloadSubCommand());

        plugin.getCommand("pepsi").setExecutor(this);
    }
}
