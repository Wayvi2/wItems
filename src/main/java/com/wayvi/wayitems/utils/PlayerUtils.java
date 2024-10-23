package com.wayvi.wayitems.utils;

import com.wayvi.wayitems.managers.VaultManager;
import org.bukkit.entity.Player;

import java.util.List;

public class PlayerUtils {

    // Méthode pour trouver le joueur le plus riche à proximité
    public static Player findRichestPlayerNearby(Player player, List<Player> nearbyPlayers, VaultManager vaultManager) {
        Player richestPlayer = null;
        double highestBalance = 0.0;

        for (Player nearbyPlayer : nearbyPlayers) {
            double balance = vaultManager.getEconomy().getBalance(nearbyPlayer);
            if (balance > highestBalance) {
                highestBalance = balance;
                richestPlayer = nearbyPlayer;
            }
        }

        return richestPlayer;
    }

    // Méthode pour envoyer les coordonnées du joueur le plus riche
    public static void sendRichestPlayerCoordinates(Player player, Player richestPlayer) {
        double x = richestPlayer.getLocation().getX();
        double y = richestPlayer.getLocation().getY();
        double z = richestPlayer.getLocation().getZ();

        player.sendMessage("Coordonnées du joueur le plus riche (" + richestPlayer.getName() + ") :");
        player.sendMessage("X: " + x + ", Y: " + y + ", Z: " + z);
    }
}
