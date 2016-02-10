package com.lwer0.lobbyutilities;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;

class LobbyListener implements Listener {
    
    private final Main plugin;
    String sound;

    public LobbyListener(Main instance) {
        plugin = instance;
    }
    
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPJoin(PlayerJoinEvent event) {
        Player p = (Player) event.getPlayer();
        if (p.isOp()) {
            if (plugin.getConfig().getBoolean("DisableThis")) {
                p.sendMessage("Remember to config LobbyUtilities in /plugins/LobbyUtilities");
            }
        }
        if (plugin.config.getBoolean("LobbyUtils.JoinExtraMSG")) {
            p.sendMessage(plugin.config.getString("LobbyUtils.Message.MessageExtra"));
            if (plugin.config.getBoolean("LobbyUtils.SoundOnJoin")) {
                sound = plugin.config.getString("LobbyUtils.Sounds.sound");
                Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "playsound"+ " " + sound + p);
            }
        }
    }
    
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDrop(PlayerDropItemEvent event) {
        Player p = event.getPlayer();
        if (!p.hasPermission("lobbyutils.nodrop.bypass")) {
            event.setCancelled(true);
        }
    }
    
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInvClick(InventoryClickEvent event) {
        Player p = (Player) event.getWhoClicked();
        if (!p.hasPermission("lobbyutils.nomoveinv.bypass")) {
            event.setCancelled(true);
        }
    }
    
    @EventHandler(priority = EventPriority.HIGHEST)
    public void isFlying(PlayerToggleFlightEvent event) {
        Player p = (Player) event.getPlayer();
        if (!p.hasPermission("lobbyutils.flykick.bypass")) {
            if (p.isFlying()) {
                p.kickPlayer(plugin.getConfig().getString("LobbyUtils.PlayerKickMessage"));
            }
        }
    }
    
    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        Player p = (Player) event.getPlayer();
        if (!p.hasPermission("lobbyutils.build")) {
            event.setCancelled(true);
        }
    }
    
}
