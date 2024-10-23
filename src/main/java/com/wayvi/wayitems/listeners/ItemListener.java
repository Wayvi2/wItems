package com.wayvi.wayitems.listeners;

import com.wayvi.wayitems.managers.ItemManager;
import com.wayvi.wayitems.managers.VaultManager;
import com.wayvi.wayitems.items.SpecialItem;
import com.wayvi.wayitems.items.ThiefHoe;
import com.wayvi.wayitems.managers.CooldownManager;
import de.tr7zw.nbtapi.NBTItem;
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
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Optional;

public class ItemListener implements Listener {

    private final CooldownManager cooldownManager;
    private final VaultManager vaultManager;
    private JavaPlugin plugin;
    private ItemManager itemManager;

    public ItemListener(CooldownManager cooldownManager, VaultManager vaultManager, ItemManager itemManager) {
        this.cooldownManager = cooldownManager;
        this.vaultManager = vaultManager;
        this.itemManager = itemManager;
    }


    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerHitEntity(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player) {

            Player player = (Player) event.getDamager();
            Entity entity = event.getEntity();
            ItemStack item = player.getInventory().getItemInHand();

            Optional<SpecialItem> specialItem = this.itemManager.getSpecialItemByItemStack(item);
            specialItem.ifPresent(value -> value.onHit(event, player, entity));

        }
    }


    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInHand();

        Optional<SpecialItem> specialItem = this.itemManager.getSpecialItemByItemStack(item);
        specialItem.ifPresent(value -> value.onBlockBreak(player, event.getBlock()));
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInHand();

        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Optional<SpecialItem> specialItem = this.itemManager.getSpecialItemByItemStack(item);
            specialItem.ifPresent(value -> value.onRightClick(player));
        }
    }



}