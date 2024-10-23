package com.wayvi.wayitems;

import com.wayvi.wayitems.commands.GiveItemsCommand;
import com.wayvi.wayitems.listeners.ItemListener;
import com.wayvi.wayitems.managers.CooldownManager;
import com.wayvi.wayitems.managers.ItemManager;
import com.wayvi.wayitems.managers.VaultManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class WayItems extends JavaPlugin {

    private ItemManager itemManager;
    private CooldownManager cooldownManager;
    private ItemListener itemListener;
    private VaultManager vaultManager;

    @Override
    public void onEnable() {
        // Initialisation du VaultManager pour gérer l'économie
        this.vaultManager = new VaultManager(this);

        // Vérification de la configuration de l'économie Vault
        if (!vaultManager.setupEconomy()) {
            getLogger().severe("Vault economy could not be initialized!");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        // Initialisation des autres gestionnaires
        this.cooldownManager = new CooldownManager();
        this.itemManager = new ItemManager(this, cooldownManager, vaultManager);
        this.itemListener = new ItemListener(cooldownManager, vaultManager, itemManager);

        // Chargement des items
        itemManager.loadItems();

        // Enregistrement des événements
        getServer().getPluginManager().registerEvents(itemListener, this);

        // Enregistrement des commandes
        getCommand("wayitems").setExecutor(new GiveItemsCommand(this, vaultManager));

        // Log d'activation
        getLogger().info("WayItems is enabled!");
    }

    @Override
    public void onDisable() {
        // Log de désactivation
        getLogger().info("WayItems is disabled.");
    }

    public CooldownManager getCooldownManager() {
        return cooldownManager;
    }

    public ItemManager getItemManager() {
        return itemManager;
    }

    public VaultManager getVaultManager() {
        return vaultManager;
    }
}
