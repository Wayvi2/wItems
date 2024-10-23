package com.wayvi.wayitems.modules.interfaces;

import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public interface ItemBehavior {

    //pas besoin de ça pour le moment je vais te faire un autre commit
    void onBlockBreak(Player player, Block block);
    void onHit(Player player, Entity entity);
    void onRightClick(Player player);
}
