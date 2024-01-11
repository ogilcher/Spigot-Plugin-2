package com.disunion.thehungergames.tasks;

import com.disunion.thehungergames.enums.GameState;
import com.disunion.thehungergames.manager.GameManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

public class ActiveGameCountdownTask extends BukkitRunnable {

    //-- Instance Variables
    final GameManager gameManager;
    private int timeLeft;

    //-- Constructor
    public ActiveGameCountdownTask(GameManager gameManager, int timeLeft){
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

        // broadcast when the game is about to go to deathmatch
        if (timeLeft == 60 || timeLeft == 30 || timeLeft < 10) {
            Bukkit.broadcastMessage(ChatColor.RED + (timeLeft + "s until deathmatch!"));
        }
    }
}
