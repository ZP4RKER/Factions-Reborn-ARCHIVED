package com.zp4rker.freborn.commands;

import com.zp4rker.freborn.FactionsReborn;
import com.zp4rker.freborn.api.Faction;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Invite {
	
	public static boolean command(Player player, String[] arguments, FactionsReborn plugin) {
		
		String[] args = new String[arguments.length - 1];

        for (int i = 1; i < arguments.length; i++) {
            args[i - 1] = arguments[i];
        }
        
        if (args.length != 1) {
        	return invalidArgs(player);
        }
        
        if (!player.hasPermission("freborn.invite")) {
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
        
        Faction faction = plugin.m.getFaction(player);
        Player target = Bukkit.getPlayer(args[0]);
        
        if (target != null) {
        	
        	if (!plugin.m.isInFaction(player)) {
        		
        		if (plugin.m.getFaction(target) != faction) {
            		
            		faction.addInvited(target);
            		FactionsReborn.getConfig("factions").set(faction.getName() + ".invited", faction.getInvited());
					FactionsReborn.getConfig("factions").saveConfig();
            		
            		player.sendMessage("§6You invited §1" + target.getName() + " §6to your faction!");
            		target.sendMessage("§6You were invited to join §1" + faction.getName() + "§6!");
            		
            		return true;
            		
            	} else {
            		
            		player.sendMessage("§4That player is already in the faction!");
            		
            		return true;
            		
            	}
        		
        	} else {
        		
        		player.sendMessage("§4That player is already in a faction!");
        		
        		return true;
        		
        	}
        	
        } else {
        	
        	player.sendMessage("§4That player is not online!");
        	
        	return true;
        	
        }
		
	}
	
	private static boolean invalidArgs(Player player) {
        player.sendMessage("§4Invalid Arguments!");
        player.sendMessage("§2/factions invite <NAME>");
        return true;
    }

}