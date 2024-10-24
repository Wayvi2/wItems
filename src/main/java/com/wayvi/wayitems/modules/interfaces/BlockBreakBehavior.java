package com.wayvi.wayitems.modules.interfaces;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public interface BlockBreakBehavior {
    void onBlockBreak(Player player, Block block);
}


