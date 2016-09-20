package com.zp4rker.freborn.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.zp4rker.freborn.api.Faction;

public class JoinFactionEvent extends Event implements Cancellable {

    private HandlerList handlers = new HandlerList();
    private boolean cancelled = false;

    private Player player;
    private Faction faction;
    private String playerMessage;
    private String factionMessage;

    public JoinFactionEvent(Player player, Faction faction) {
        this.player = player;
        this.faction = faction;
        this.playerMessage = "§6You joined the faction §5" + faction.getName();
        this.factionMessage = "§9" + player.getName() + " §6joined the faction!";
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

    public Player getPlayer() {
		return player;
	}

	public String getPlayerMessage() {
        return playerMessage;
    }

    public String getFactionMessage() {
        return factionMessage;
    }

    public void setPlayerMessage(String playerMessage) {
        this.playerMessage = playerMessage;
    }

    public void setFactionMessage(String factionMessage) {
        this.factionMessage = factionMessage;
    }

	public Faction getFaction() {
		return faction;
	}

}
