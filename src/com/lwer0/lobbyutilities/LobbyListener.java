/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lwer0.lobbyutilities;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;

/**
 *
 * @author Joel
 */
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
        if (plugin.config.getBoolean("JoinExtraMSG")) {
            p.sendMessage(plugin.config.getString("MessageExtra"));
            if (plugin.config.getBoolean("SoundOnJoin")) {
                sound = plugin.config.getString("sound");
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
                p.kickPlayer(plugin.getConfig().getString("PlayerKickMessage"));
            }
        }
    }
    
}
