package com.wayvi.wayitems.utils;

import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Method;

public class VersionUtils {

    private static Class<?> craftItemStackClass;
    private static Method craftItemStackAsNMSCopyMethod;
    private static Method craftItemStackAsBukkitCopyMethod;
    private static String version;

    static {
        try {
            version = getVersion();
            // Dynamically load CraftItemStack based on the server version
            craftItemStackClass = Class.forName("org.bukkit.craftbukkit." + version + ".inventory.CraftItemStack");
            craftItemStackAsNMSCopyMethod = craftItemStackClass.getMethod("asNMSCopy", ItemStack.class);
            craftItemStackAsBukkitCopyMethod = craftItemStackClass.getMethod("asBukkitCopy", Class.forName("net.minecraft.server." + version + ".ItemStack"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getVersion() {
        return Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
    }

    public static Object getNMSItemStack(ItemStack item) {
        try {
            return craftItemStackAsNMSCopyMethod.invoke(null, item);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ItemStack getBukkitItemStack(Object nmsItem) {
        try {
            return (ItemStack) craftItemStackAsBukkitCopyMethod.invoke(null, nmsItem);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
