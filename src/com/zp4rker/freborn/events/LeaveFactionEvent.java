package com.zp4rker.freborn.events;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.zp4rker.freborn.api.Faction;

public class LeaveFactionEvent extends Event implements Cancellable {

    private HandlerList handlers = new HandlerList();
    private boolean cancelled = false;

    private OfflinePlayer player;
    private Faction faction;
    private String playerMessage;
    private String factionMessage;

    public LeaveFactionEvent(OfflinePlayer player, Faction faction, boolean kicked) {
        this.player = player;
        this.faction = faction;
        if (!kicked) {
        	this.playerMessage = "§6You left the faction §5" + faction.getName();
            this.factionMessage = "§9" + player.getName() + " §6left the faction!";
        } else {
        	this.playerMessage = "§6You were kicked from the faction §5" + faction.getName();
        	this.factionMessage = "§9" + player.getName() + " §6was kicked from the faction!";
        }
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean b) {
        this.cancelled = b;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public OfflinePlayer getPlayer() {
		return player;
	}
    
    public boolean playerIsOnline() {
    	Player player = Bukkit.getPlayer(this.player.getUniqueId());
    	if (player != null) {
    		return true;
    	} else {
    		return false;
    	}
    }

	public Faction getFaction() {
		return faction;
	}

	public String getPlayerMessage() {
        return playerMessage;
    }

    public void setPlayerMessage(String playerMessage) {
        this.playerMessage = playerMessage;
    }

    public String getFactionMessage() {
        return factionMessage;
    }

    public void setFactionMessage(String factionMessage) {
        this.factionMessage = factionMessage;
    }

}
