package com.disunion.thehungergames.listeners;

import com.disunion.thehungergames.enums.GameState;
import com.disunion.thehungergames.manager.GameManager;
import com.disunion.thehungergames.enums.TeleportLocations;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {

    //-- Instance Variables
    final GameManager gameManager;

    //-- Constructor
    public PlayerListener(GameManager gameManager){
        this.gameManager = gameManager;
    }

    @EventHandler
    public void OnPlayerJoin(PlayerJoinEvent e){

        // Variables
        Player player = e.getPlayer();

        // First we want to teleport the player and make sure they are in the correct state
        player.teleport(TeleportLocations.Spawn.getLocation());
        player.getInventory().clear();
        player.setGameMode(GameMode.SURVIVAL);
        e.setJoinMessage("");

        // Update the number of players present in the game then broadcast that they join
        int totalPlayers = (gameManager.getPlayersInQueue() + 1);
        gameManager.setPlayersInQueue(totalPlayers);
        Bukkit.getServer().broadcastMessage(ChatColor.GOLD + (player.getName() + " has joined. " + totalPlayers + " / 23"));

        // put the game in a starting state once the min player requirement is fulfilled 
        if (totalPlayers == 7){
            gameManager.setGameState(GameState.STARTING);
        }

    }
    @EventHandler
    public void OnPlayerDeath(PlayerDeathEvent e){

        // Variables
        Player player = (Player) e.getEntity();

        // Remove player for total alive players
        int alivePlayers = (gameManager.getPlayersAlive() -1);
        gameManager.setPlayersAlive(alivePlayers);
        Bukkit.getServer().broadcastMessage(ChatColor.RED + (player.getName() + " has died!" + alivePlayers + " left!"));
    }

    @EventHandler
    public void OnPlayerQuit(PlayerQuitEvent e){

        // Variables
        Player player = e.getPlayer();

        // Remove Player from the queue
        int totalPlayers = (gameManager.getPlayersInQueue() - 1);
        gameManager.setPlayersInQueue(totalPlayers);
        Bukkit.getServer().broadcastMessage(ChatColor.GOLD + (player.getName() + " has left. " + totalPlayers + " / 23"));
    }


}
