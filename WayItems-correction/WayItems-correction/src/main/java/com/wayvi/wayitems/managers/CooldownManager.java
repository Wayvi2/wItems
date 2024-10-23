package com.wayvi.wayitems.managers;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class CooldownManager {
    private final Map<String, Long> hoeCooldowns = new HashMap<>();
    private final Map<UUID, Long> playerCooldowns = new HashMap<>();
    private final Set<String> hoesInCooldown = new HashSet<>(); // Set pour suivre les hoes en cooldown

    // Génère une clé unique pour chaque hoe spécifique
    private String generateHoeKey(String hoeUUID) {
        return hoeUUID; // Utiliser uniquement l'UUID de la hoe pour les cooldowns
    }

    // Vérifie si une hoe spécifique est en cooldown
    public boolean isHoeOnCooldown(String hoeUUID) {
        return hoesInCooldown.contains(hoeUUID) || (hoeCooldowns.containsKey(hoeUUID) && hoeCooldowns.get(hoeUUID) > System.currentTimeMillis());
    }

    // Récupère le temps restant pour la hoe spécifique
    public long getHoeCooldownLeft(String hoeUUID) {
        return (hoeCooldowns.getOrDefault(hoeUUID, 0L) - System.currentTimeMillis()) / 1000;
    }

    // Applique un cooldown à la hoe spécifique
    public void setHoeCooldown(String hoeUUID, int seconds) {
        hoeCooldowns.put(hoeUUID, System.currentTimeMillis() + seconds * 1000L);
        hoesInCooldown.add(hoeUUID); // Ajoute la hoe à la liste des hoes en cooldown
    }

    // Vérifie si le cooldown de la hoe est expiré
    public void checkCooldowns() {
        Set<String> toRemove = new HashSet<>();
        for (String hoeUUID : hoesInCooldown) {
            if (hoeCooldowns.get(hoeUUID) <= System.currentTimeMillis()) {
                toRemove.add(hoeUUID);
            }
        }
        hoesInCooldown.removeAll(toRemove);
    }

    // Vérifie si le joueur est en cooldown global
    public boolean isPlayerOnCooldown(UUID playerId) {
        return playerCooldowns.containsKey(playerId) && playerCooldowns.get(playerId) > System.currentTimeMillis();
    }

    // Récupère le temps restant pour le cooldown global du joueur
    public long getPlayerCooldownLeft(UUID playerId) {
        return (playerCooldowns.getOrDefault(playerId, 0L) - System.currentTimeMillis()) / 1000;
    }

    // Applique un cooldown global au joueur
    public void setPlayerCooldown(UUID playerId, int seconds) {
        playerCooldowns.put(playerId, System.currentTimeMillis() + seconds * 1000L);
    }
}


