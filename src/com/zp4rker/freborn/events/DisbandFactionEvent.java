package com.zp4rker.freborn.events;

import org.bukkit.command.CommandSender;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.zp4rker.freborn.api.Faction;

public class DisbandFactionEvent extends Event implements Cancellable {

    private boolean cancelled = false;
    private HandlerList handlerList = new HandlerList();

    private CommandSender sender;
    private Faction faction;
    private String playerMessage = "§6You disbanded §1" + faction.getName() + "§6!";
    private String factionMessage = "§6Your faction was disbanded by §1" + sender.getName() + "§!";
    private String serverMessage = "§1" + faction.getName() + " §6was disbanded!";

    public DisbandFactionEvent(CommandSender sender, Faction faction) {
        this.sender = sender;
        this.faction = faction;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean b) {
        this.cancelled = b;
    }

    public HandlerList getHandlers() {
        return handlerList;
    }

    public String getFactionMessage() {
        return factionMessage;
    }

    public void setFactionMessage(String factionMessage) {
        this.factionMessage = factionMessage;
    }

    public String getSenderMessage() {
        return playerMessage;
    }

    public void seSenderMessage(String senderMessage) {
        this.playerMessage = senderMessage;
    }

    public String getServerMessage() {
        return serverMessage;
    }

    public void setServerMessage(String serverMessage) {
        this.serverMessage = serverMessage;
    }

    public Faction getFaction() {
        return faction;
    }

    public CommandSender getSender() {
        return sender;
    }

}
