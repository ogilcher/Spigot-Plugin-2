package com.disunion.thehungergames.tasks;

import com.disunion.thehungergames.manager.GameManager;
import com.disunion.thehungergames.enums.GameState;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

public class GameCountdownTask extends BukkitRunnable {

    //-- Instance Variables
    final GameManager gameManager;
    private int timeLeft;

    //-- Constructor
    public GameCountdownTask(GameManager gameManager, int timeLeft){
        this.gameManager = gameManager;
        this.timeLeft = timeLeft;
    }

    @Override
    public void run(){

        // game start countdown
        timeLeft--;
        if (timeLeft <= 0){
            cancel();
            gameManager.setGameState(GameState.DEATHMATCH);
            return;
        }

        // grace period countdown

        // game end countdown



        Bukkit.broadcastMessage(ChatColor.GOLD + (timeLeft + "s until game starts!"));
    }
}
