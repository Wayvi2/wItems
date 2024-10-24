package com.wayvi.wayitems.items;

import com.wayvi.wayitems.WayItems;
import com.wayvi.wayitems.managers.CooldownManager;
import com.wayvi.wayitems.managers.VaultManager;
import com.wayvi.wayitems.modules.interfaces.RightClickBehavior;
import com.wayvi.wayitems.utils.PlayerUtils;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class ThiefHoe extends SpecialItem implements RightClickBehavior {

    private final CooldownManager cooldownManager;
    private final VaultManager vaultManager;

    public ThiefHoe(WayItems plugin) {
        super(plugin);
        this.cooldownManager = plugin.getCooldownManager();
        this.vaultManager = plugin.getVaultManager();
    }



    @Override
    public void onRightClick(Player player) {
        // Vérifiez d'abord le cooldown global du joueur
        if (cooldownManager.isPlayerOnCooldown(player.getUniqueId())) {
            long playerCooldownLeft = cooldownManager.getPlayerCooldownLeft(player.getUniqueId());
            player.sendMessage("Vous devez attendre " + playerCooldownLeft + " secondes avant de réutiliser une houe.");
            return;
        }

        ItemStack itemInHand = player.getInventory().getItemInHand();
        NBTItem nbtItem = new NBTItem(itemInHand);

        // Vérifie si l'item est bien une Thief Hoe
        if (!nbtItem.hasKey("thief_hoe_id")) {
            return;
        }

        String hoeId = nbtItem.getString("thief_hoe_id");

        // Vérifie si cette hoe spécifique est en cooldown
        if (cooldownManager.isHoeOnCooldown(hoeId)) {
            long hoeCooldownLeft = cooldownManager.getHoeCooldownLeft(hoeId);
            player.sendMessage("Vous devez attendre " + hoeCooldownLeft + " secondes avant de réutiliser cette houe.");
            return;
        }

        // Récupérer le joueur le plus riche à proximité
        List<Player> nearbyPlayers = player.getNearbyEntities(50, 50, 50).stream()
                .filter(entity -> entity instanceof Player)
                .map(entity -> (Player) entity)
                .toList();

        Player richestPlayer = PlayerUtils.findRichestPlayerNearby(player, nearbyPlayers, vaultManager);

        if (richestPlayer != null) {
            double balance = vaultManager.getEconomy().getBalance(richestPlayer);
            player.sendMessage("Le joueur le plus riche est : " + richestPlayer.getName() +
                    " avec " + balance + " unités.");

            // Afficher les coordonnées du joueur le plus riche
            PlayerUtils.sendRichestPlayerCoordinates(player, richestPlayer);
        } else {
            player.sendMessage("Aucun joueur riche à proximité.");
        }

        // Appliquer le cooldown pour cette hoe spécifique
        cooldownManager.setHoeCooldown(hoeId, 10 * 60); // 10 minutes

        // Appliquer le cooldown global pour ce joueur (10 minutes de cooldown)
        cooldownManager.setPlayerCooldown(player.getUniqueId(), 10 * 60);
    }

    public ItemStack getItemStack() {
        ItemStack hoe = new ItemStack(Material.DIAMOND_HOE);
        ItemMeta meta = hoe.getItemMeta();
        if (meta != null) {
            meta.setDisplayName("§7Thief Hoe");
            meta.setLore(List.of("§7A hoe for stealing crops."));
            hoe.setItemMeta(meta);
        }

        NBTItem nbtItem = new NBTItem(hoe);
        UUID uniqueID = UUID.randomUUID();
        nbtItem.setString("thief_hoe_id", uniqueID.toString());


        nbtItem.setString("type", this.getName());

        return nbtItem.getItem();
    }

    @Override
    public String getName() {
        return "thief_hoe";
    }

}
