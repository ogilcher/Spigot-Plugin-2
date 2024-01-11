package com.disunion.thehungergames.manager;

import com.disunion.thehungergames.listeners.GracePeriodHandler;

public class PlayerManager {

    //-- Instance Variables
    private GameManager gameManager;
    private boolean isInGame;
    private GracePeriodHandler gracePeriodhandler;

    //-- Constructor
    public PlayerManager(GameManager gameManager){
        this.gameManager = gameManager;
        this.isInGame = false;
    }

    // Remove Player's ability to move during grace period
    public void SetGracePeriodHandler(boolean state){
        this.gracePeriodhandler.setGracePeriod(state);
    }



    //-- Getters & Setters
    public boolean isInGame() {return isInGame;}
    public void setInGame(boolean inGame) {isInGame = inGame;}
}
