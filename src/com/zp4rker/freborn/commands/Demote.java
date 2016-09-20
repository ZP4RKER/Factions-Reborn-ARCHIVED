package com.zp4rker.freborn.commands;

import com.zp4rker.freborn.FactionsReborn;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import com.zp4rker.freborn.api.Faction;

public class Demote {
	
	public static boolean command(Player player, String[] arguments, FactionsReborn plugin) {
		
		String[] args = new String[arguments.length - 1];

        for (int i = 1; i < arguments.length; i++) {
            args[i - 1] = arguments[i];
        }
        
        if (args.length != 1) {
        	return invalidArgs(player);
        }
        
        if (!(player.hasPermission("freborn.demote"))) {
        	return plugin.m.invalidPerms(player);
        }
        
        if (!(plugin.m.isOwner(player))) {
        	player.sendMessage("§4You need to be the owner of the faction!");
        	return true;
        }
        
        @SuppressWarnings("deprecation")
		OfflinePlayer op = Bukkit.getOfflinePlayer(args[0]);
        
        if (op != null) {
        	
        	Faction faction = plugin.m.getFaction(player);
        	
        	if (faction.getPlayers().contains(op.getUniqueId().toString())) {
        		
        		if (faction.getStaff().contains(op.getUniqueId().toString())) {

        			faction.removeStaff(op);
        			FactionsReborn.getConfig("factions").set(faction.getName() + ".staff", faction.getStaff());
					FactionsReborn.getConfig("factions").saveConfig();
        			
        			player.sendMessage("§6You demoted §1" + op.getName() + "§6!");
        			
        			if (op.getPlayer().isOnline()) {
        				
        				op.getPlayer().sendMessage("§6You were demoted by §1" + player.getName() + "§!");
        				
        			}
        			
        			return true;
        			
        		} else {
        			
        			player.sendMessage("§4That player is not a staff member!");
        			
        			return true;
        			
        		}
        		
        	} else {
        		
        		player.sendMessage("§4That player is not in the faction!");
        		
        		return true;
        		
        	}
        	
        } else {
        	
        	player.sendMessage("§4That player does not exist!");
        	
        	return true;
        	
        }
		
	}
	
	private static boolean invalidArgs(Player player) {
        player.sendMessage("§4Invalid Arguments!");
        player.sendMessage("§2/factions demote <PLAYER>");
        return true;
    }

}