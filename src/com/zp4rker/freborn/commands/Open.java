package com.zp4rker.freborn.commands;

import com.zp4rker.freborn.FactionsReborn;
import org.bukkit.entity.Player;

import com.zp4rker.freborn.api.Faction;

public class Open {
	
	public static boolean command(Player player, String[] arguments, FactionsReborn plugin) {
		
		String[] args = new String[arguments.length - 1];

        for (int i = 1; i < arguments.length; i++) {
            args[i - 1] = arguments[i];
        }
        
        if (args.length != 0) {
        	return invalidArgs(player);
        }
        
        if (player.hasPermission("freborn.open")) {
        	return plugin.m.invalidPerms(player);
        }
        
        if (!plugin.m.isInFaction(player)) {
        	player.sendMessage("§4You must be in a faction!");
        	return true;
        }
        
        if (!plugin.m.isOwner(player)) {
        	player.sendMessage("§4You must be the owner of the faction!");
        	return true;
        }

        Faction faction = plugin.m.getFaction(player);
        boolean open = faction.isOpen();
        
        faction.setOpen(open ? false : true);
        
        FactionsReborn.getConfig("factions").set(faction.getName() + ".open", faction.isOpen());
        FactionsReborn.getConfig("factions").saveConfig();
        
        player.sendMessage("§6Your faction is now §1" + (open ? "closed" : "open") + "§6!");
		
		return true;
		
	}
	
	private static boolean invalidArgs(Player player) {
		player.sendMessage("§4Invalid Arguments!");
		player.sendMessage("§2/factions open");
		return true;
	}

}
