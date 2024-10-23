package com.wayvi.wayitems.managers;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class VaultManager {

    private Economy economy;
    private Permission permissions;
    private Chat chat;
    private final JavaPlugin plugin;

    public VaultManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public boolean setupEconomy() {
        if (plugin.getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = plugin.getServer().getServicesManager().getRegistration(Economy.class);
        economy = rsp != null ? rsp.getProvider() : null;
        return economy != null;
    }

    public boolean setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = plugin.getServer().getServicesManager().getRegistration(Permission.class);
        permissions = rsp != null ? rsp.getProvider() : null;
        return permissions != null;
    }

    public boolean setupChat() {
        RegisteredServiceProvider<Chat> rsp = plugin.getServer().getServicesManager().getRegistration(Chat.class);
        chat = rsp != null ? rsp.getProvider() : null;
        return chat != null;
    }

    public Economy getEconomy() {
        return economy;
    }

    public Permission getPermissions() {
        return permissions;
    }

    public Chat getChat() {
        return chat;
    }
}

