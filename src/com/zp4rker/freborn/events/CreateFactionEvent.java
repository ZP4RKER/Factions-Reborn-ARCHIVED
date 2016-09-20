package com.zp4rker.freborn.events;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class CreateFactionEvent extends Event implements Cancellable {

    private static HandlerList handlerList = new HandlerList();
    private boolean cancelled = false;

    private String name;
    private String tag;
    private Player player;
    private Location location;
    private String message;

    public CreateFactionEvent(String name, String tag, Player player, Location location) {
        this.name = name;
        this.tag = tag;
        this.player = player;
        this.location = location;
        this.message = "§6Successfully created faction §5" + this.getName();
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    public HandlerList getHandlers() {
        return handlerList;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public String getName() {
        return name;
    }

    public String getTag() {
        return tag;
    }

    public Player getPlayer() {
        return player;
    }

    public Location getLocation() {
        return location;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
