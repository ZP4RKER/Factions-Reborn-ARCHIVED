package com.zp4rker.freborn.commands;

import com.zp4rker.freborn.FactionsReborn;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class zExecutor implements CommandExecutor {

    private FactionsReborn plugin;

    public zExecutor(FactionsReborn plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (cmd.getName().equalsIgnoreCase("factions")) {

            if (args.length > 0) {

                if (args[0].equalsIgnoreCase("create")) {

                    if (sender instanceof Player) {

                        return Create.command((Player) sender, args, plugin);

                    } else {

                        sender.sendMessage("§4 You must be a player!");
                        return true;

                    }

                } else if (args[0].equalsIgnoreCase("join")) {

                    if (sender instanceof Player) {

                        return Join.command((Player) sender, args, plugin);

                    } else {

                        sender.sendMessage("§4You must be a player!");
                        return true;

                    }

                } else if (args[0].equalsIgnoreCase("leave")) {

                    if (sender instanceof Player) {

                        return Leave.command((Player) sender, args, plugin);

                    } else {

                        sender.sendMessage("§4You must be a player!");
                        return true;

                    }

                } else if (args[0].equalsIgnoreCase("near")) {

                    if (sender instanceof Player) {

                        return Near.command((Player) sender, args, plugin);

                    } else {

                        sender.sendMessage("§4You must be a player!");
                        return true;

                    }

                } else if (args[0].equalsIgnoreCase("disband")) {

                    if (sender instanceof Player) {

                        return Disband.command((Player) sender, args, plugin);

                    } else {

                        sender.sendMessage("§4You must be a player!");
                        return true;

                    }

                } else if (args[0].equalsIgnoreCase("chat")) {

                    if (sender instanceof Player) {

                        return Chat.command((Player) sender, args, plugin);

                    } else {

                        sender.sendMessage("§4You must be a player!");
                        return true;

                    }

                } else if (args[0].equalsIgnoreCase("broadcast")) {

                    if (sender instanceof Player) {

                        return Broadcast.command((Player) sender, args, plugin);

                    } else {

                        sender.sendMessage("§4You must be a player!");
                        return true;

                    }

                } else if (args[0].equalsIgnoreCase("invite")) {
                	
                	if (sender instanceof Player) {
                		
                		return Invite.command((Player) sender, args, plugin);
                		
                	} else {
                		
                		sender.sendMessage("§4You must be a player!");
                        return true;
                		
                	}
                	
                } else if (args[0].equalsIgnoreCase("accept")) {
                	
                	if (sender instanceof Player) {
                		
                		return Accept.command((Player) sender, args, plugin);
                		
                	} else {
                		
                		sender.sendMessage("§4You must be a player!");
                        return true;
                		
                	}
                	
                } else if (args[0].equalsIgnoreCase("promote")) {
                	
                	if (sender instanceof Player) {
                		
                		return Promote.command((Player) sender, args, plugin);
                		
                	} else {
                		
                		sender.sendMessage("§4You must be a player!");
                        return true;
                		
                	}
                	
                } else if (args[0].equalsIgnoreCase("demote")) {
                	
                	if (sender instanceof Player) {
                		
                		return Demote.command((Player) sender, args, plugin);
                		
                	} else {
                		
                		sender.sendMessage("§4You must be a player!");
                        return true;
                		
                	}
                	
                } else if (args[0].equalsIgnoreCase("kick")) {
                	
                	if (sender instanceof Player) {
                		
                		return Kick.command((Player) sender, args, plugin);
                		
                	} else {
                		
                		sender.sendMessage("§4You must be a player!");
                        return true;
                		
                	}
                	
                } else if (args[0].equalsIgnoreCase("open")) {
                	
                	if (sender instanceof Player) {
                		
                		return Open.command((Player) sender, args, plugin);
                		
                	} else {
                		
                		sender.sendMessage("§4You must be a player!");
                        return true;
                		
                	}
                	
                } else if (args[0].equalsIgnoreCase("tag")) {

                    if (sender instanceof Player) {

                        return SetTag.command((Player) sender, args, plugin);

                    } else {

                        sender.sendMessage("§4You must be a player!");
                        return true;

                    }

                } else if (args[0].equalsIgnoreCase("info")) {

                    return Info.command(sender, args, plugin);

                } else if (args[0].equalsIgnoreCase("reload")) {

                    return Reload.command(sender, args, plugin);

                } else if (args[0].equalsIgnoreCase("list")) {
                	
                	return List.command(sender, args, plugin);
                	
                } else if (args[0].equalsIgnoreCase("destroy")) {
                	
                	return Destroy.command(sender, args, plugin);
                	
                }

            }

        }

        return false;

    }

}