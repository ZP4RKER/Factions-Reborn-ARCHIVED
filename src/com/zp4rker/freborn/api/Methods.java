package com.zp4rker.freborn.api;

import com.zp4rker.core.Config;
import com.zp4rker.freborn.FactionsReborn;
import org.bukkit.*;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class Methods {

    private FactionsReborn plugin;

    public Methods(FactionsReborn plugin) {
        this.plugin = plugin;
    }

    public void giveNexus(Player player) {
        ItemStack itemStack = new ItemStack(Material.EMERALD_BLOCK, 1);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName("§5Nexus");
        itemStack.setItemMeta(itemMeta);

        if (player.getItemInHand().getType() != Material.AIR) {
            player.sendMessage("§4Make sure there is nothing in your hand!");
        } else {
            player.getInventory().addItem(itemStack);
            player.sendMessage("§3Place this block to make your faction!");
        }

    }

    public void createFaction(Faction faction) {

        Config factions = FactionsReborn.getConfig("factions");
        Location nexusLoc = faction.getNexus().getLocation();

        factions.set(faction.getName() + ".tag", faction.getTag());
        factions.set(faction.getName() + ".players", faction.getPlayers());
        factions.set(faction.getName() + ".nexusX", nexusLoc.getBlockX());
        factions.set(faction.getName() + ".nexusY", nexusLoc.getBlockY());
        factions.set(faction.getName() + ".nexusZ", nexusLoc.getBlockZ());
        factions.set(faction.getName() + ".staff", faction.getPlayers());
        factions.set(faction.getName() + ".open", faction.isOpen());

        factions.saveConfig();

    }

    public boolean factionExists(String name) {

        Config factions = FactionsReborn.getConfig("factions");

        for (String key : factions.getKeys()) {
            if (key == name) {
                return true;
            }
        }

        return false;

    }

    public boolean invalidPerms(CommandSender sender) {
        sender.sendMessage("§4You do not permission to do that!");
        return true;
    }

    public boolean inFactionBase(Location location) {

        Config factions = FactionsReborn.getConfig("factions");

        for (String key : factions.getKeys()) {
            if (location.getBlockX() >= factions.getInt(key + ".xOne") && location.getBlockX() <= factions.getInt(key + ".xTwo")) {
                if (location.getBlockY() >= factions.getInt(key + ".yOne") && location.getBlockY() <= factions.getInt(key + ".yTwo")) {
                    if (location.getBlockZ() >= factions.getInt(key + ".zOne") &&
                            location.getBlockZ() <= factions.getInt(key + ".zTwo")) {
                        return true;
                    }
                }
            }
        }

        return false;

    }

    public Faction getFactionAtLocation(Location location) {

        Config factions = FactionsReborn.getConfig("factions");

        for (String key : factions.getKeys()) {
            if (location.getBlockX() >= factions.getInt(key + ".xOne") && location.getBlockX() <= factions.getInt(key + ".xTwo")) {
                if (location.getBlockY() >= factions.getInt(key + ".yOne") && location.getBlockY() <= factions.getInt(key + ".yTwo")) {
                    if (location.getBlockZ() >= factions.getInt(key + ".zOne")
                            && location.getBlockZ() <= factions.getInt(key + ".zOne")) {
                        World world = location.getWorld();
                        Nexus nexus = new Nexus(
                                new Location(world, factions.getInt(key + ".nexusX"),
                                        factions.getInt(key + ".nexusY"),
                                        factions.getInt(key + ".nexusZ")), true);
                        Vault vault = new Vault(nexus);
                        Base base = new Base(vault, factions.getStringList(key + ".players").size());
                        Faction faction = new Faction(
                                key, factions.getString(key + ".tag"),
                                factions.getStringList(key + ".players"),
                                base, factions.getStringList(key + ".staff"),
                                factions.getBoolean(key + ".open"));
                        return faction;
                    }
                }
            }
        }

        return null;

    }

    public boolean isInFaction(Player player) {

        String UUID = player.getUniqueId().toString();
        Config factions = FactionsReborn.getConfig("factions");

        for (String key : factions.getKeys()) {
            List<String> uuids = factions.getStringList(key + ".players");
            if (uuids.contains(UUID)) {
                return true;
            }
        }

        return false;

    }

    public Faction getFaction(OfflinePlayer player) {

        Config factions = FactionsReborn.getConfig("factions");
        String uuid = player.getUniqueId().toString();

        for (String key : factions.getKeys()) {
            if (factions.getStringList(key + ".players").contains(uuid)) {
                World world = Bukkit.getWorld(factions.getString(key + ".world"));
                Location loc = new Location(world, factions.getInt(key + ".nexusX"),
                        factions.getInt(key + ".nexusY"), factions.getInt(key + ".nexusZ"));
                Nexus nexus = new Nexus(loc, true);
                Vault vault = new Vault(nexus);
                Base base = new Base(vault, factions.getStringList(key + ".players").size());
                Faction faction = new Faction(key, factions.getString(key + ".tag"), factions.getStringList(key + ".players"),
                        base, factions.getStringList(key + ".staff"), factions.getBoolean(key + ".open"));
                return faction;
            }
        }

        return null;

    }

    public Faction getFaction(String name) {

        Config factions = FactionsReborn.getConfig("factions");

        if (factions.getConfigurationSection(name).getKeys(false).size() != 0) {
            World world = Bukkit.getWorld(factions.getString(name + ".world"));
            Location loc = new Location(world, factions.getInt(name + ".nexusX"),
                    factions.getInt(name + ".nexusY"), factions.getInt(name + ".nexusZ"));
            Nexus nexus = new Nexus(loc, true);
            Vault vault = new Vault(nexus);
            Base base = new Base(vault, factions.getStringList(name + ".players").size());
            Faction faction = new Faction(name, factions.getString(name + ".tag"), factions.getStringList(name + ".players"),
                    base, factions.getStringList(name + ".staff"), factions.getBoolean(name + ".open"));
            return faction;
        }

        return null;

    }

    public boolean isOwner(Player player) {
        Faction faction = getFaction(player);
        if (faction.getOwner().getUniqueId() == player.getUniqueId()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isStaff(Player player) {
        Faction faction = getFaction(player);
        if (faction.getStaff().contains(player)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isNearFaction(Player player, double range) {

        Config factions = FactionsReborn.getConfig("factions");

        for (String key : factions.getKeys()) {

            double nearestAmount = getNearestAmount(key, player);

            if (nearestAmount <= range) {

                return true;

            }

        }

        return false;

    }

    public String getNearestDimension(String factionName, Player player) {

        Config factions = FactionsReborn.getConfig("factions");
        String nearestDimension = null;
        double nearestAmount = -1;

        if (player.getLocation().getX() > factions.getInt(factionName + ".xOne")) {

            if (nearestAmount == -1) {

                nearestAmount = player.getLocation().getX() - factions.getInt(factionName + ".xOne");
                nearestDimension = "X-";

            }

            if (nearestAmount > player.getLocation().getX() - factions.getInt(factionName + ".xOne")) {

                nearestAmount = player.getLocation().getX() - factions.getInt(factionName + ".xOne");
                nearestDimension = "X-";

            }

        } else if (player.getLocation().getX() < factions.getInt(factionName + ".xTwo")) {

            if (nearestAmount == -1) {

                nearestAmount = factions.getInt(factionName + ".xTwo") - player.getLocation().getX();
                nearestDimension = "X+";

            }

            if (nearestAmount > factions.getInt(factionName + ".xTwo") - player.getLocation().getX()) {

                nearestAmount = factions.getInt(factionName + ".xTwo") - player.getLocation().getX();
                nearestDimension = "X+";

            }

        }

        if (player.getLocation().getY() > factions.getInt(factionName + ".yOne")) {

            if (nearestAmount == -1) {

                nearestAmount = player.getLocation().getY() - factions.getInt(factionName + ".yOne");
                nearestDimension = "Y-";

            }

            if (nearestAmount > player.getLocation().getY() - factions.getInt(factionName + ".yOne")) {

                nearestAmount = player.getLocation().getY() - factions.getInt(factionName + ".yOne");
                nearestDimension = "Y-";

            }

        } else if (player.getLocation().getY() < factions.getInt(factionName + ".yTwo")) {

            if (nearestAmount == -1) {

                nearestAmount = factions.getInt(factionName + ".yTwo") - player.getLocation().getY();
                nearestDimension = "Y+";

            }

            if (nearestAmount > factions.getInt(factionName + ".yTwo") - player.getLocation().getY()) {

                nearestAmount = factions.getInt(factionName + ".yTwo") - player.getLocation().getY();
                nearestDimension = "Y+";

            }

        }

        if (player.getLocation().getZ() > factions.getInt(factionName + ".zOne")) {

            if (nearestAmount == -1) {

                nearestAmount = player.getLocation().getZ() - factions.getInt(factionName + ".zOne");
                nearestDimension = "Z-";

            }

            if (nearestAmount > player.getLocation().getZ() - factions.getInt(factionName + ".zOne")) {

                nearestAmount = player.getLocation().getZ() - factions.getInt(factionName + ".zOne");
                nearestDimension = "Z-";

            }

        } else if (player.getLocation().getZ() < factions.getInt(factionName + ".zTwo")) {

            if (nearestAmount == -1) {

                nearestAmount = factions.getInt(factionName + ".zTwo") - player.getLocation().getZ();
                nearestDimension = "Z+";

            }

            if (nearestAmount > factions.getInt(factionName + ".zTwo") - player.getLocation().getZ()) {

                nearestAmount = factions.getInt(factionName + ".zTwo") - player.getLocation().getZ();
                nearestDimension = "Z+";

            }

        }

        return nearestDimension;

    }

    public double getNearestAmount(String factionName, Player player) {

        Config factions = FactionsReborn.getConfig("factions");
        double nearestAmount = -1;

        if (player.getLocation().getX() > factions.getInt(factionName + ".xOne")) {

            if (nearestAmount == -1) {

                nearestAmount = player.getLocation().getX() - factions.getInt(factionName + ".xOne");

            }

            if (nearestAmount > player.getLocation().getX() - factions.getInt(factionName + ".xOne")) {

                nearestAmount = player.getLocation().getX() - factions.getInt(factionName + ".xOne");

            }

        } else if (player.getLocation().getX() < factions.getInt(factionName + ".xTwo")) {

            if (nearestAmount == -1) {

                nearestAmount = factions.getInt(factionName + ".xTwo") - player.getLocation().getX();

            }

            if (nearestAmount > factions.getInt(factionName + ".xTwo") - player.getLocation().getX()) {

                nearestAmount = factions.getInt(factionName + ".xTwo") - player.getLocation().getX();

            }

        }

        if (player.getLocation().getY() > factions.getInt(factionName + ".yOne")) {

            if (nearestAmount == -1) {

                nearestAmount = player.getLocation().getY() - factions.getInt(factionName + ".yOne");

            }

            if (nearestAmount > player.getLocation().getY() - factions.getInt(factionName + ".yOne")) {

                nearestAmount = player.getLocation().getY() - factions.getInt(factionName + ".yOne");

            }

        } else if (player.getLocation().getY() < factions.getInt(factionName + ".yTwo")) {

            if (nearestAmount == -1) {

                nearestAmount = factions.getInt(factionName + ".yTwo") - player.getLocation().getY();

            }

            if (nearestAmount > factions.getInt(factionName + ".yTwo") - player.getLocation().getY()) {

                nearestAmount = factions.getInt(factionName + ".yTwo") - player.getLocation().getY();

            }

        }

        if (player.getLocation().getZ() > factions.getInt(factionName + ".zOne")) {

            if (nearestAmount == -1) {

                nearestAmount = player.getLocation().getZ() - factions.getInt(factionName + ".zOne");

            }

            if (nearestAmount > player.getLocation().getZ() - factions.getInt(factionName + ".zOne")) {

                nearestAmount = player.getLocation().getZ() - factions.getInt(factionName + ".zOne");

            }

        } else if (player.getLocation().getZ() < factions.getInt(factionName + ".zTwo")) {

            if (nearestAmount == -1) {

                nearestAmount = factions.getInt(factionName + ".zTwo") - player.getLocation().getZ();

            }

            if (nearestAmount > factions.getInt(factionName + ".zTwo") - player.getLocation().getZ()) {

                nearestAmount = factions.getInt(factionName + ".zTwo") - player.getLocation().getZ();

            }

        }

        return nearestAmount;

    }

    public String getNearestFaction(Player player, double range) {

        List<String> list = new ArrayList<>();
        Config factions = FactionsReborn.getConfig("factions");

        for (String key : factions.getKeys()) {

            if (player.getLocation().getX() > factions.getInt(key + ".xOne")) {

                if (player.getLocation().getX() - factions.getInt(key + ".xOne") <= range) {

                    if (player.getLocation().getY() > factions.getInt(key + ".yOne")) {

                        if (player.getLocation().getY() - factions.getInt(key + ".yOne") <= range) {

                            if (player.getLocation().getZ() > factions.getInt(key + ".zOne")) {

                                if (player.getLocation().getZ() - factions.getInt(key + ".zOne") <= range) {

                                    list.add(key);

                                }
                            } else if (player.getLocation().getZ() > factions.getInt(key + ".zTwo")) {

                                // IGNORE

                            } else {

                                if (factions.getInt(key + ".zTwo") - player.getLocation().getZ() <= range) {

                                    list.add(key);

                                }

                            }

                        }

                    } else if (player.getLocation().getY() > factions.getInt(key + ".yTwo")) {

                        // IGNORE

                    } else {

                        if (factions.getInt(key + ".yTwo") - player.getLocation().getY() <= range) {

                            if (player.getLocation().getZ() > factions.getInt(key + ".zOne")) {

                                if (player.getLocation().getZ() - factions.getInt(key + ".zOne") <= range) {

                                    list.add(key);

                                }
                            } else if (player.getLocation().getZ() > factions.getInt(key + ".zTwo")) {

                                // IGNORE

                            } else {

                                if (factions.getInt(key + ".zTwo") - player.getLocation().getZ() <= range) {

                                    list.add(key);

                                }

                            }

                        }

                    }

                }

            } else if (player.getLocation().getX() > factions.getInt(key + ".xTwo")) {

                // IGNORE

            } else {

                if (factions.getInt(key + ".xTwo") - player.getLocation().getX() <= range) {

                    if (player.getLocation().getY() > factions.getInt(key + ".yOne")) {

                        if (player.getLocation().getY() - factions.getInt(key + ".yOne") <= range) {

                            if (player.getLocation().getZ() > factions.getInt(key + ".zOne")) {

                                if (player.getLocation().getZ() - factions.getInt(key + ".zOne") <= range) {

                                    list.add(key);

                                }
                            } else if (player.getLocation().getZ() > factions.getInt(key + ".zTwo")) {

                                // IGNORE

                            } else {

                                if (factions.getInt(key + ".zTwo") - player.getLocation().getZ() <= range) {

                                    list.add(key);

                                }

                            }

                        }

                    } else if (player.getLocation().getY() > factions.getInt(key + ".yTwo")) {

                        // IGNORE

                    } else {

                        if (factions.getInt(key + ".yTwo") - player.getLocation().getY() <= range) {

                            if (player.getLocation().getZ() > factions.getInt(key + ".zOne")) {

                                if (player.getLocation().getZ() - factions.getInt(key + ".zOne") <= range) {

                                    list.add(key);

                                }
                            } else if (player.getLocation().getZ() > factions.getInt(key + ".zTwo")) {

                                // IGNORE

                            } else {

                                if (factions.getInt(key + ".zTwo") - player.getLocation().getZ() <= range) {

                                    list.add(key);

                                }

                            }

                        }

                    }

                }

            }

        }

        plugin.getLogger().info("Size: " + list.size());

        String nearest = null;

        if (list.size() == 1) {

            return list.get(0);

        }

        double nearestAmount = -1;

        for (String key : list) {

            if (nearest == null) {

                nearest = key;

                nearestAmount = getNearestAmount(nearest, player);

            } else {

                if (getNearestAmount(key, player) < nearestAmount) {

                    nearestAmount = getNearestAmount(key, player);
                    nearest = key;

                }

            }

        }

        return nearest;

    }

    public int getPages() {

        Config factions = FactionsReborn.getConfig("factions");

        if (factions.getKeys().size() % 5 == 0) {
            return factions.getKeys().size() / 5;
        } else {
            return (factions.getKeys().size() / 5) + 1;
        }

    }

    public int getWholePages() {
        return FactionsReborn.getConfig("factions").getKeys().size() / 5;
    }

    public List<Faction> getPage(int index) {
        List<Faction> list = new ArrayList<>();
        Config factions = FactionsReborn.getConfig("factions");
        int count = 0;
        for (String key : factions.getKeys()) {
            count++;
            if (count >= ((index * 5) + 1) && count <= (((index * 5) + 1) + 5)) {
                list.add(getFaction(key));
            }
        }
        return list;
    }

    public boolean isExpandable(List<Location> blocks, Faction faction) {
        for (Location block : blocks) {
            if (getFactionAtLocation(block) != null) {
                if (getFactionAtLocation(block).getName() != faction.getName()) {
                    return false;
                }
            }
        }
        return true;
    }

}