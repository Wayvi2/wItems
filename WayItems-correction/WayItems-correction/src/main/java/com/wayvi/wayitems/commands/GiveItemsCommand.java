package com.wayvi.wayitems.commands;

import com.wayvi.wayitems.WayItems;
import com.wayvi.wayitems.items.SpecialItem;
import com.wayvi.wayitems.managers.VaultManager;
import com.wayvi.wayitems.items.HeroSword;
import com.wayvi.wayitems.items.KamikazeAxe;
import com.wayvi.wayitems.items.ThiefHoe;
import com.wayvi.wayitems.managers.CooldownManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Optional;

public class GiveItemsCommand implements CommandExecutor {

    private WayItems plugin;
    private CooldownManager cooldownManager;
    private VaultManager vaultManager;

    public GiveItemsCommand(WayItems plugin, VaultManager vaultManager) {
        this.plugin = plugin;
        this.vaultManager = vaultManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Cette commande doit être utilisée par un joueur.");
            return false;
        }

        Player player = (Player) sender;


        if (args.length < 3 || !args[0].equalsIgnoreCase("give")) {
            return false;
        }


        Player target = plugin.getServer().getPlayer(args[2]);
        if (target == null) {
            player.sendMessage("Le joueur spécifié est introuvable.");
            return false;
        }

        for (int i = 1; i < args.length - 1; i++) {
            String itemName = args[i].toLowerCase();
            ItemStack item = getItemFromName(itemName);
            if (item != null) {
                target.getInventory().addItem(item);
                player.sendMessage("Vous avez donné " + itemName + " à " + target.getName());
            } else {
                player.sendMessage("L'item " + itemName + " est introuvable.");
            }
        }
        return true;
    }

    // Méthode pour récupérer l'item correspondant au nom passé en argument
    private ItemStack getItemFromName(String itemName) {
        Optional<SpecialItem> item = this.plugin.getItemManager().getSpecialItem(itemName);
        return item.map(SpecialItem::getItemStack).orElse(null);
    }

}

