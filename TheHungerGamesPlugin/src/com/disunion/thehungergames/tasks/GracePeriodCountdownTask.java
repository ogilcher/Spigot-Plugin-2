package com.disunion.thehungergames.tasks;

import com.disunion.thehungergames.enums.GameState;
import com.disunion.thehungergames.manager.GameManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

public class GracePeriodCountdownTask extends BukkitRunnable {

    //-- Instance Variables
    final GameManager gameManager;
    private int timeLeft;

    //-- Constructor
    public GracePeriodCountdownTask(GameManager gameManager, int timeLeft){
        this.gameManager = gameManager;
        this.timeLeft = timeLeft;
    }

    @Override
    public void run(){

        // game start countdown
        timeLeft--;
        if (timeLeft <= 0){
            cancel();
            gameManager.setGameState(GameState.ACTIVE);
            return;
        }

        Bukkit.broadcastMessage(ChatColor.GOLD + (timeLeft + "s until you are released!"));
    }
}
