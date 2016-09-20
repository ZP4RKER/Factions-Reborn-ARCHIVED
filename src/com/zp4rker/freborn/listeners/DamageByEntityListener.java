package com.zp4rker.freborn.listeners;

import com.zp4rker.freborn.api.Faction;
import com.zp4rker.freborn.FactionsReborn;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class DamageByEntityListener implements Listener {

    private FactionsReborn plugin;

    public DamageByEntityListener() {
        this.plugin = FactionsReborn.getPlugin();
    }

    @EventHandler
    public void onDamageByEntity(EntityDamageByEntityEvent event) {

        // Check if hit entity is an ender crystal
        if (event.getEntity().getType() == EntityType.ENDER_CRYSTAL) {

            // Disable explosion
            event.setCancelled(true);

            // Get the nexus' faction
            Faction dFaction = plugin.m.getFactionAtLocation(event.getEntity().getLocation());

            // Make sure the attacker is a player
            if (event.getEntity() instanceof Player) {

                // Get the attacker
                Player attacker = (Player) event.getEntity();

                // Get faction of attacker
                Faction aFaction = plugin.m.getFaction(attacker);

                // Make sure the attacker is in a faction
                if (aFaction != null) {

                    // Check if the attacker is in the defending faction
                    if (aFaction == dFaction) {

                        // Check if faction owner
                        if (aFaction.getOwner().getUniqueId() == attacker.getUniqueId()) {

                            // Nexus destroy process

                        } else {

                            // Tell attacker need to be owner
                            attacker.sendMessage("§4You must be the owner of the faction!");

                        }

                    } else {

                        // Nexus destroy process

                    }

                }

            }

        }

    }

}