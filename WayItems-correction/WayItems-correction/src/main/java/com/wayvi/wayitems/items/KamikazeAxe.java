package com.wayvi.wayitems.items;

import com.wayvi.wayitems.WayItems;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.entity.Entity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class KamikazeAxe extends SpecialItem {


    public KamikazeAxe(WayItems plugin) {
        super(plugin);
    }

    @Override
    public void onBlockBreak(Player player, Block block) {
        block.getWorld().createExplosion(block.getLocation(), 4F, false);
    }



    @Override
    public void onHit(EntityDamageByEntityEvent event, Player player, Entity entity) {
        // Pas de logique
    }

    @Override
    public void onRightClick(Player player) {
        // Pas de logique
    }

    public ItemStack getItemStack() {
        ItemStack axe = new ItemStack(Material.DIAMOND_AXE);
        ItemMeta meta = axe.getItemMeta();
        if (meta != null) {
            meta.setDisplayName("§4Kamikaze Axe");
            meta.setLore(Arrays.asList("§7An axe with a powerful explosion!"));
            axe.setItemMeta(meta);
        }
        return axe;
    }

    @Override
    public String getName() {
        return "kamikaze_axe";
    }
}
