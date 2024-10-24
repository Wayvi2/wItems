package com.wayvi.wayitems.listeners;

import com.wayvi.wayitems.managers.ItemManager;
import com.wayvi.wayitems.managers.VaultManager;
import com.wayvi.wayitems.items.SpecialItem;
import com.wayvi.wayitems.managers.CooldownManager;
import com.wayvi.wayitems.modules.interfaces.BlockBreakBehavior;
import com.wayvi.wayitems.modules.interfaces.HitBehavior;
import com.wayvi.wayitems.modules.interfaces.RightClickBehavior;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

public class ItemListener implements Listener {

    private final CooldownManager cooldownManager;
    private final VaultManager vaultManager;
    private final ItemManager itemManager;

    public ItemListener(CooldownManager cooldownManager, VaultManager vaultManager, ItemManager itemManager) {
        this.cooldownManager = cooldownManager;
        this.vaultManager = vaultManager;
        this.itemManager = itemManager;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerHitEntity(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player player) {
            Entity entity = event.getEntity();
            ItemStack item = player.getInventory().getItemInHand();

            Optional<HitBehavior> hitBehavior = this.itemManager.getSpecialItemByItemStack(item, HitBehavior.class);

            hitBehavior.ifPresent(behavior -> behavior.onHit(event, player, entity));
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInHand();


        Optional<BlockBreakBehavior> blockBreakBehavior = this.itemManager.getSpecialItemByItemStack(item, BlockBreakBehavior.class);

        blockBreakBehavior.ifPresent(behavior -> behavior.onBlockBreak(player, event.getBlock()));
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInHand();

        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {

            Optional<RightClickBehavior> rightClickBehavior = this.itemManager.getSpecialItemByItemStack(item, RightClickBehavior.class);

            rightClickBehavior.ifPresent(behavior -> behavior.onRightClick(player));
        }
    }

}
