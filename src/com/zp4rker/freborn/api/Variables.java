package com.zp4rker.freborn.api;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Variables {

    public HashMap<OfflinePlayer, Map<String, String>> makingFaction = new HashMap<>();
    public List<Player> inBase = new ArrayList<>();
    public List<Player> factionChat = new ArrayList<>();

}