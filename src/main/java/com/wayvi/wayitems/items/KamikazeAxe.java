package com.wayvi.wayitems.items;

import com.wayvi.wayitems.WayItems;
import com.wayvi.wayitems.modules.interfaces.BlockBreakBehavior;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


import java.util.List;

public class KamikazeAxe extends SpecialItem implements BlockBreakBehavior {


    public KamikazeAxe(WayItems plugin) {
        super(plugin);
    }

    @Override
    public void onBlockBreak(Player player, Block block) {
        block.getWorld().createExplosion(block.getLocation(), 4F, false);
    }

    public ItemStack getItemStack() {
        ItemStack axe = new ItemStack(Material.DIAMOND_AXE);
        NBTItem nbtItem = new NBTItem(axe);
        nbtItem.setString("type", "kamikaze_axe");
        ItemMeta meta = axe.getItemMeta();
        if (meta != null) {
            meta.setDisplayName("§4Kamikaze Axe");
            meta.setLore(List.of("§7An axe with a powerful explosion!"));
            axe.setItemMeta(meta);
        }
        return nbtItem.getItem();
    }


    @Override
    public String getName() {
        return "kamikaze_axe";
    }
}
