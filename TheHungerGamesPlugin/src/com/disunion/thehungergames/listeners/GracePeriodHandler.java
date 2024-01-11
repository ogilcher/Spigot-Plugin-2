package com.disunion.thehungergames.listeners;

import com.disunion.thehungergames.enums.GameState;
import com.disunion.thehungergames.manager.GameManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class GracePeriodHandler implements Listener {

    //-- Instance Variables
    private GameManager gameManager;
    private boolean isGracePeriod;

    //-- Constructor
    public GracePeriodHandler(GameManager gameManager){
        this.gameManager = gameManager;
    }

    @EventHandler
    public void PlayerMovement(PlayerMoveEvent e){

        // variables
        Player player = e.getPlayer();

        // conditional that will make sure the players can only not move during grace period
        if (gameManager.getGameState() == GameState.GRACE_PERIOD){
            e.setCancelled(true);
        }
    }

    //-- Getters & Setters
    public void setGracePeriod(boolean gracePeriod) {
        this.isGracePeriod = gracePeriod;
    }
}
