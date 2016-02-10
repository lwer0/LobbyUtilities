package com.lwer0.lobbyutilities;

import java.io.File;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    
    public static Main instance;
    
    final FileConfiguration config = this.getConfig();
    
    @Override
    public void onEnable() {
        this.getLogger().info("LobbyUtils has been loaded correctly");
        this.getServer().getPluginManager().registerEvents(new LobbyListener(instance), instance);
        
        File config = new File(getDataFolder()+File.separator+"config.yml");
        if (!config.exists()) {
            getConfig().options().copyDefaults(true);
            saveConfig();
        }
    }
    @Override
    public void onDisable() {
        this.getLogger().info("LobbyUtils has been unloaded correctly");
    }
}
