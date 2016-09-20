package com.zp4rker.freborn.commands;

import java.util.UUID;

import com.zp4rker.freborn.FactionsReborn;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import com.zp4rker.freborn.api.Faction;

public class Kick {
	
	public static boolean command(Player player, String[] arguments, FactionsReborn plugin) {
		
		String[] args = new String[arguments.length - 1];

        for (int i = 1; i < arguments.length; i++) {
            args[i - 1] = arguments[i];
        }
        
        if (args.length != 1) {
        	return invalidArgs(player);
        }
        
        if (player.hasPermission("freborn.kick")) {
        	return plugin.m.invalidPerms(player);
        }
        
        if (!plugin.m.isInFaction(player)) {
        	player.sendMessage("§4You must be in a faction!");
        	return true;
        }
        
        if (!plugin.m.isStaff(player)) {
        	player.sendMessage("§4You must be staff in the faction!");
        	return true;
        }
        
        @SuppressWarnings("deprecation")
		OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
        
        if (target != null) {
        	
        	Faction faction = plugin.m.getFaction(player);
        	
        	if (!faction.getPlayers().contains(target.getUniqueId())) {
        		
        		player.sendMessage("§4That player is not in the faction!");
        		
        	}

        	faction.removePlayer(target, true);

			FactionsReborn.getConfig("factions").set(faction.getName() + ".players", faction.getPlayers());
        	
        	Player targetPlayer = Bukkit.getPlayer(target.getUniqueId());
        	
        	if (targetPlayer != null) {
        		
        		targetPlayer.sendMessage("§1You were kicked from your faction!");
        		
        	}
        	
        	player.sendMessage("§1You kicked §6" + target.getName() + " §1 from the faction!");
        	
        	for (String uuid : faction.getPlayers()) {
        		
        		Player theTarget = Bukkit.getPlayer(UUID.fromString(uuid));
        		
        		if (theTarget != null) {
        			
        			theTarget.sendMessage("§6" + target.getName() + " §1was kicked from the faction!");
        			
        		}
        		
        	}
        	
        	return true;
        	
        } else {
        	
        	player.sendMessage("§4That player doesn't exist!");
        	
        }
		
		return true;
		
	}
	
	private static boolean invalidArgs(Player player) {
        player.sendMessage("§4Invalid Arguments!");
        player.sendMessage("§2/factions kick <NAME>");
        return true;
    }

}
