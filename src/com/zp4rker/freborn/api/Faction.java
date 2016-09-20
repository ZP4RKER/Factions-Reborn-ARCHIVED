package com.zp4rker.freborn.api;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.zp4rker.core.Config;
import com.zp4rker.freborn.FactionsReborn;
import com.zp4rker.freborn.events.DisbandFactionEvent;
import com.zp4rker.freborn.events.JoinFactionEvent;
import com.zp4rker.freborn.events.LeaveFactionEvent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Faction {

    private String name;
    private String tag;
    private List<String> players = new ArrayList<>();
    private List<String> staff = new ArrayList<>();
    private List<String> invited = new ArrayList<>();
    private Base base;
    private boolean open;
    private double balance;

    public Faction(String name, String tag, List<String> players, Base base, List<String> staff, boolean open) {

        this.name = name;
        this.tag = tag;
        this.players = players;
        this.base = base;
        this.open = open;
        this.staff = staff;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public List<String> getPlayers() {
        return players;
    }

    public List<OfflinePlayer> getBukkitPlayers() {
        List<OfflinePlayer> players = new ArrayList<>();
        for (String uuid : getPlayers()) {
            players.add(Bukkit.getPlayer(UUID.fromString(uuid)));
        }
        return players;
    }

    public int getPlayerCount() {
        return players.size();
    }

    public void addPlayer(Player player) {
        // Make Event
        JoinFactionEvent event = new JoinFactionEvent(player, this);
        // Call Event
        Bukkit.getPluginManager().callEvent(event);
        // Run Default Code
        if (!event.isCancelled()) {

            // Add Player
            players.add(player.getUniqueId().toString());

            // Send Message to Player
            player.sendMessage(event.getPlayerMessage());

            // Send Message to Faction
            for (String uuid : this.players) {
                Player p = Bukkit.getPlayer(UUID.fromString(uuid));
                if (p != null) {
                    p.sendMessage(event.getFactionMessage());
                }
            }

            // Add New Outline
            for (int i = 0; i < this.base.getOutline(players.size()).size(); i++) {
                final int I = i;
                Bukkit.getScheduler().scheduleSyncDelayedTask(FactionsReborn.getPlugin(), new Runnable() {
                    public void run() {
                        base.getOutline(players.size()).get(I).getBlock().setType(Material.STONE);
                    }
                }, i);
            }

            // Save to file
            Config factions = FactionsReborn.getConfig("factions");
            String uuid = player.getUniqueId().toString();
            List<String> list = factions.getStringList(name + ".players");
            list.add(uuid);
            factions.set(name + ".players", list);
            factions.saveConfig();

        }
    }

    public void removePlayer(OfflinePlayer offlinePlayer, boolean kicked) {
        // Create Event
        LeaveFactionEvent event = new LeaveFactionEvent(offlinePlayer, this, kicked);
        // Call Event
        Bukkit.getPluginManager().callEvent(event);
        // Run Default Code
        if (!event.isCancelled()) {

            // Remove player
            players.remove(offlinePlayer.getUniqueId().toString());

            if (event.playerIsOnline()) {
                // Send Message to Player
                Player player = Bukkit.getPlayer(event.getPlayer().getUniqueId());
                player.sendMessage(event.getPlayerMessage());
            }

            // Send Message to Faction
            for (String uuid : this.players) {
                Player p = Bukkit.getPlayer(UUID.fromString(uuid));
                if (p != null) {
                    p.sendMessage(event.getFactionMessage());
                }
            }

            // Add New Outline
            for (int i = 0; i < this.base.getOutline(this.players.size()).size(); i++) {
                final int I = i;
                Bukkit.getScheduler().scheduleSyncDelayedTask(FactionsReborn.getPlugin(), new Runnable() {
                    public void run() {
                        base.getOutline(players.size()).get(I).getBlock().setType(Material.STONE);
                    }
                }, i);
            }

            // Save to file
            Config factions = FactionsReborn.getConfig("factions");
            String uuid = offlinePlayer.getUniqueId().toString();
            List<String> list = factions.getStringList(name + ".players");
            list.remove(uuid);
            factions.set(name + ".players", list);
            factions.saveConfig();

        }
    }

    public Base getBase() {
        return base;
    }

    public Vault getVault() {
        return base.getVault();
    }

    public Nexus getNexus() {
        return base.getVault().getNexus();
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public OfflinePlayer getOwner() {
        return Bukkit.getPlayer(UUID.fromString(players.get(0)));
    }

    public List<String> getStaff() {
        return staff;
    }

    public void addStaff(OfflinePlayer player) {
        staff.add(player.getUniqueId().toString());
    }

    public void removeStaff(OfflinePlayer player) {
        staff.remove(player.getUniqueId().toString());
    }

    public void disband(CommandSender sender) {
        // Make Event
        DisbandFactionEvent event = new DisbandFactionEvent(sender, this);
        // Call Event
        Bukkit.getPluginManager().callEvent(event);
        // Run Default Code
        if (!event.isCancelled()) {
            // Get Database Entry
            Faction faction = FactionsReborn.getPlugin().m.getFaction(this.name);
            // Send the player a message
            sender.sendMessage(event.getSenderMessage());
            for (OfflinePlayer offlinePlayer : faction.getBukkitPlayers()) {
                // Send all other online faction members a message
                Player factionPlayer = Bukkit.getPlayer(offlinePlayer.getUniqueId());
                if (factionPlayer != null) {
                    factionPlayer.sendMessage(event.getFactionMessage());
                }
            }
            // Send the server a message
            Bukkit.broadcastMessage(event.getServerMessage());
        }
    }

    public List<String> getInvited() {
        return invited;
    }

    public void addInvited(OfflinePlayer player) {
        invited.add(player.getUniqueId().toString());
    }

    public void removeInvited(OfflinePlayer player) {
        invited.remove(player.getUniqueId().toString());
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void addMoney(double amount) {
        balance += amount;
    }

    public void subMoney(double amount) {
        balance -= amount;
    }

}
