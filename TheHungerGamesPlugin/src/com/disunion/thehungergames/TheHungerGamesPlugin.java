package com.disunion.thehungergames;

import com.disunion.thehungergames.commands.ChangeGameState;
import com.disunion.thehungergames.listeners.GracePeriodHandler;
import com.disunion.thehungergames.listeners.OnBlockBreak;
import com.disunion.thehungergames.listeners.PlayerListener;
import com.disunion.thehungergames.manager.GameManager;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class TheHungerGamesPlugin extends JavaPlugin{

    //-- Instance Variables
    private GameManager gameManager;

    @Override
    public void onEnable() {
        super.onEnable();

        // Instance Variables
        this.gameManager = new GameManager(this);

        // Initiate Listeners
        getServer().getPluginManager().registerEvents(new OnBlockBreak(), this);
        getServer().getPluginManager().registerEvents(new GracePeriodHandler(gameManager), this);
        getServer().getPluginManager().registerEvents(new PlayerListener(gameManager), this);

        // Initiate Commands
        getCommand("setgamestate").setExecutor(new ChangeGameState(gameManager));
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "The Hunger Games Plugin is enabled!");

    }

    @Override
    public void onDisable() {
        super.onDisable();

        getServer().getConsoleSender().sendMessage(ChatColor.RED + "The Hunger Games Plugin is enabled!");
    }
}
