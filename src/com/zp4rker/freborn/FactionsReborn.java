package com.zp4rker.freborn;

import com.zp4rker.core.Config;
import com.zp4rker.core.ConfigManager;
import com.zp4rker.freborn.listeners.BlockPlaceListener;
import com.zp4rker.freborn.listeners.DamageByEntityListener;
import com.zp4rker.freborn.listeners.PlayerChatListener;
import org.bukkit.plugin.java.JavaPlugin;

import com.zp4rker.freborn.api.Methods;
import com.zp4rker.freborn.api.Variables;
import com.zp4rker.freborn.commands.zExecutor;
import com.zp4rker.freborn.listeners.PlayerMoveListener;

import java.util.HashMap;

public class FactionsReborn extends JavaPlugin {

    public Methods m = new Methods(this);
    public Variables v = new Variables();
    private static FactionsReborn plugin;
    private static HashMap<String, Config> configs = new HashMap<>();
    private ConfigManager manager = new ConfigManager(this);
    public ClassLoader classLoader = getClassLoader();

    // Code to be run when the plugin is enabled
    public void onEnable() {

        this.plugin = this;

        // Initialise Config files
        initConfigs();

        // Save config defaults
        getConfig("config").saveDefaults();

        // Register the events
        getServer().getPluginManager().registerEvents(new BlockPlaceListener(this), this);
        getServer().getPluginManager().registerEvents(new DamageByEntityListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerMoveListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerChatListener(this), this);
        // Register the command
        getCommand("factions").setExecutor(new zExecutor(this));
        // Load the default config
        saveDefaultConfig();

        // Plugin enabled message
        getLogger().info("FactionsReborn v" + getDescription().getVersion() + " enabled!");

    }

    private void initConfigs() {
        // config.yml
        configs.put("config", manager.getNewConfig("config.yml"));
        // factions.yml
        configs.put("factions", manager.getNewConfig("factions.yml"));
    }

    public static FactionsReborn getPlugin() {

        return FactionsReborn.plugin;

    }

    @Override
    public void saveDefaultConfig() {
        getConfig("config").saveDefaults();
    }

    public static Config getConfig(String name) {
        return configs.get(name);
    }

}
