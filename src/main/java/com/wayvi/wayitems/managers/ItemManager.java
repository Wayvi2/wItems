package com.wayvi.wayitems.managers;

import com.wayvi.wayitems.WayItems;
import com.wayvi.wayitems.items.SpecialItem;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.reflections.Reflections;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class ItemManager implements Listener {

    private final WayItems plugin;
    private final CooldownManager cooldownManager;
    private final VaultManager vaultManager;
    private final Map<String, SpecialItem> specialItems;

    public ItemManager(WayItems plugin, CooldownManager cooldownManager, VaultManager vaultManager) {
        this.plugin = plugin;
        this.cooldownManager = cooldownManager;
        this.vaultManager = vaultManager;
        this.specialItems = new HashMap<>();
    }


    public void loadItems() {
        Reflections reflections = new Reflections("com.wayvi.wayitems.items");
        Set<Class<? extends SpecialItem>> itemClasses = reflections.getSubTypesOf(SpecialItem.class);


        for (Class<? extends SpecialItem> itemClass : itemClasses) {
            try {
                SpecialItem item = createSpecialItem(itemClass);
                specialItems.put(item.getName(), item); // Ici pas besoin de != null car ta methode throw une erreur s'il y a un un soucis et ne renvoie jamais null
                plugin.getLogger().info(item.getName() + " has been loaded.");
            } catch (Exception e) {
                plugin.getLogger().warning("Failed to load item: " + e.getMessage());
            }
        }
    }


    public Optional<SpecialItem> getSpecialItemByItemStack(ItemStack item) {
        if (item == null || item.getType() == Material.AIR) return Optional.empty();

        NBTItem nbtItem = new NBTItem(item);
        String type = nbtItem.getString("type"); // Récupérez le type de l'item

        // Si le type est présent, on récupére l'item spécial basé sur le type
        if (!type.isEmpty()) {
            return Optional.ofNullable(specialItems.get(type));
        }

        return Optional.empty();
    }


    private SpecialItem createSpecialItem(Class<? extends SpecialItem> itemClass) throws Exception {
        try {
            return itemClass.getConstructor(WayItems.class).newInstance(this.plugin);
        } catch (NoSuchMethodException e1) {
           throw new RuntimeException("No constructor with WayItems parameter found for " + itemClass.getSimpleName());
        }
    }

    public Optional<SpecialItem> getSpecialItem(String name) {
        return Optional.ofNullable(specialItems.get(name));
    }

}
