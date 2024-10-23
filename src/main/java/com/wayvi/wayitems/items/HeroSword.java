package com.wayvi.wayitems.items;

import com.wayvi.wayitems.WayItems;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

public class HeroSword extends SpecialItem {

    public HeroSword(WayItems plugin) {
        super(plugin);
    }

    @Override
    public void onBlockBreak(Player player, Block block) {
        // Pas de logique pour casser des blocs
    }

    public boolean isOwner(Player player) {
        ItemStack item = player.getInventory().getItemInHand();
        String owner = getNBTData(item, "owner");
        return owner != null && owner.equals(player.getName());
    }

    @Override
    public void onHit(EntityDamageByEntityEvent event, Player player, Entity entity) {
        ItemStack item = player.getInventory().getItemInHand();
        NBTItem nbtItem = new NBTItem(item);
        String owner = nbtItem.getString("owner");


        if (owner == null || owner.isEmpty()) {
            nbtItem.setString("owner", player.getName());
            player.getInventory().setItemInHand(nbtItem.getItem());
            player.sendMessage("L'épée est maintenant liée à vous.");
        }


        if (!this.isOwner(player)) {
            event.setCancelled(true);
            player.sendMessage("Cette épée appartient à " + owner + "!");
        }
    }



    @Override
    public void onRightClick(Player player) {
        // Pas de logique pour clic droit
    }

    public ItemStack getItemStack() {
        ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);
        ItemMeta meta = sword.getItemMeta();
        if (meta != null) {
            meta.setDisplayName("§6Hero Sword");
            meta.setLore(List.of("§7A powerful sword for heroes!"));
            sword.setItemMeta(meta);
        }

        // Ajoutez le type NBT
        NBTItem nbtItem = new NBTItem(sword);
        nbtItem.setString("type", "hero_sword");
        return nbtItem.getItem();
    }


    @Override
    public String getName() {
        return "hero_sword";
    }
}
