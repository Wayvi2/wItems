package com.wayvi.wayitems.modules.interfaces;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public interface HitBehavior {
    void onHit(EntityDamageByEntityEvent event, Player player, Entity entity);
}