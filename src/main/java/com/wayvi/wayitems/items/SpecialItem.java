package com.wayvi.wayitems.items;

import com.wayvi.wayitems.WayItems;
import com.wayvi.wayitems.utils.VersionUtils;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.lang.reflect.Method;

public abstract class SpecialItem {

    protected final WayItems plugin;

    //pas besoin de mettre le material ici car tu as deja le getItemStack
    public SpecialItem(WayItems plugin) {
       this.plugin = plugin;
    }

    public abstract void onBlockBreak(Player player, Block block);

    public abstract void onHit(EntityDamageByEntityEvent event, Player player, Entity entity);

    public abstract void onRightClick(Player player);

    public abstract ItemStack getItemStack();

    public abstract String getName();

    // Reflection for NBT handling
    private static Class<?> nmsItemStackClass;
    private static Class<?> nbtTagCompoundClass;
    private static Method nmsItemStackGetTag;
    private static Method nmsItemStackSetTag;
    private static Method nbtTagCompoundSetString;
    private static Method nbtTagCompoundHasKey;
    private static Method nbtTagCompoundGetString;

    static {
        try {
            // Load NMS
            String version = VersionUtils.getVersion();
            nmsItemStackClass = Class.forName("net.minecraft.server." + version + ".ItemStack");
            nbtTagCompoundClass = Class.forName("net.minecraft.server." + version + ".NBTTagCompound");

            // initialisation pour la reflection
            nmsItemStackGetTag = nmsItemStackClass.getMethod("getTag");
            nmsItemStackSetTag = nmsItemStackClass.getMethod("setTag", nbtTagCompoundClass);
            nbtTagCompoundSetString = nbtTagCompoundClass.getMethod("setString", String.class, String.class);
            nbtTagCompoundHasKey = nbtTagCompoundClass.getMethod("hasKey", String.class);
            nbtTagCompoundGetString = nbtTagCompoundClass.getMethod("getString", String.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    protected ItemStack setNBTData(ItemStack item, String key, String value) {
        try {
            Object nmsItem = VersionUtils.getNMSItemStack(item);
            Object tag = nmsItemStackGetTag.invoke(nmsItem);

            if (tag == null) {
                tag = nbtTagCompoundClass.newInstance();
            }

            nbtTagCompoundSetString.invoke(tag, key, value);
            nmsItemStackSetTag.invoke(nmsItem, tag);
            return VersionUtils.getBukkitItemStack(nmsItem);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return item;
    }


    protected String getNBTData(ItemStack item, String key) {
        try {
            Object nmsItem = VersionUtils.getNMSItemStack(item);
            Object tag = nmsItemStackGetTag.invoke(nmsItem);

            if (tag != null && (boolean) nbtTagCompoundHasKey.invoke(tag, key)) {
                return (String) nbtTagCompoundGetString.invoke(tag, key);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
