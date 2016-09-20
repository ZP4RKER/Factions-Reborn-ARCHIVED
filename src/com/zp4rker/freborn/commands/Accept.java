package com.zp4rker.freborn.commands;

import com.zp4rker.freborn.FactionsReborn;
import com.zp4rker.freborn.api.Faction;
import org.bukkit.entity.Player;

public class Accept {
	
	public static boolean command(Player player, String[] arguments, FactionsReborn plugin) {
		
		String[] args = new String[arguments.length - 1];

        for (int i = 1; i < arguments.length; i++) {
            args[i - 1] = arguments[i];
        }
        
        if (args.length != 1) {
        	return invalidArgs(player);
        }
        
        if (!player.hasPermission("freborn.accept")) {
        	return plugin.m.invalidPerms(player);
        }
        
        if (plugin.m.isInFaction(player)) {
        	player.sendMessage("§4You are already in a faction!");
        	return true;
        }
        
        Faction faction = plugin.m.getFaction(args[0]);
        
        if (faction != null) {
        	
        	if (faction.getInvited().contains(player.getUniqueId().toString())) {

        		faction.addPlayer(player);
        		
        		return true;
        		
        	} else {
        		
        		player.sendMessage("§4You weren't invited to that faction!");
        		
        		return true;
        		
        	}
        	
        } else {
        	
        	player.sendMessage("§4That faction does not exist!");
        	
        	return true;
        	
        }
		
	}
	
	private static boolean invalidArgs(Player player) {
        player.sendMessage("§4Invalid Arguments!");
        player.sendMessage("§2/factions accept <FACTION>");
        return true;
    }

}