package com.disunion.thehungergames.commands;

import com.disunion.thehungergames.manager.GameManager;
import com.disunion.thehungergames.enums.GameState;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ChangeGameState implements CommandExecutor {

    //-- Instance variables
    private GameManager gameManager;

    public ChangeGameState(GameManager gameManager){
        this.gameManager = gameManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {

        if (!(sender instanceof Player)){return false;}

        Player player = (Player) sender;
        GameState gameState;

        // Setting the game state
        if (cmd.getName().equalsIgnoreCase("setgamestate")){

            // Check if the player entered the command correctly
            if (args.length >= 1) {
                try {
                    gameState = GameState.valueOf(args[0]);
                    gameManager.setGameState(gameState);

                } catch (IllegalArgumentException e) {
                    player.sendMessage("§e§l§c" + args[0] + " is not a valid gamestate");
                }
            } else {
                player.sendMessage("§e§l§c/setgamestate <gamestate>");
            }
        }

        return false;
    }
}
