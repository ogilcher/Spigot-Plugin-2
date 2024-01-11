package com.disunion.thehungergames.manager;

import com.disunion.thehungergames.TheHungerGamesPlugin;
import com.disunion.thehungergames.enums.GameState;
import com.disunion.thehungergames.listeners.GracePeriodHandler;
import com.disunion.thehungergames.tasks.GameCountdownTask;
import com.disunion.thehungergames.tasks.GracePeriodCountdownTask;
import org.bukkit.*;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.inventory.ItemStack;
import java.util.Random;

/*
Developer notes:
    Game State Hierarchy - Lobby, Starting, Grace Period, Active, Deathmatch, Won, Restarting
    Some jumps in game states can occur:
        Lobby -> Starting : If the game fills up with max queued players
        Active -> : Won if there's only 1 remaining player before the game has moved to deathmatch


 */

public class GameManager{

    //-- Instance Variables
    private final TheHungerGamesPlugin plugin;
    private int playersInQueue;
    private int playersAlive;
    private GameState gameState;

    private PlayerManager playerManager;
    private GameCountdownTask gameCountdownTask;
    private GracePeriodCountdownTask gracePeriodCountdownTask;

    //-- Constructor
    public GameManager(TheHungerGamesPlugin plugin){

        this.plugin = plugin;
        this.playersInQueue = 0;
        this.gameState = GameState.LOBBY;
        this.playerManager = new PlayerManager(this);
    }

    public void setGameState(GameState gameState){

        // Prevent possible bugs that could occur with current game states
        if (this.gameState == GameState.ACTIVE && gameState == GameState.STARTING) return;
        if (this.gameState == gameState) return;

        switch (gameState) {
            case LOBBY:
                Bukkit.broadcastMessage("You are now in the lobby");
                break;
            case STARTING:
                Bukkit.broadcastMessage("Game is now starting");

                // Start a broadcasted countdown
                this.gameCountdownTask = new GameCountdownTask(this, 10);
                this.gameCountdownTask.runTaskTimer(plugin, 0, 20);

                regenerateChests();

                break;
            case GRACE_PERIOD:
                Bukkit.broadcastMessage("You are now in the grace period");

                // Cancel the previous countdown
                if (this.gameCountdownTask != null) {
                    this.gameCountdownTask.cancel();
                }

                // Start a new broadcasted countdown
                this.gracePeriodCountdownTask = new GracePeriodCountdownTask(this, 10);
                this.gracePeriodCountdownTask.runTaskTimer(plugin, 0, 20);

                break;
            case ACTIVE:
                Bukkit.broadcastMessage("The game is now active");

                // Stop the timer for the grace period countdown
                if (this.gracePeriodCountdownTask != null) {
                    this.gracePeriodCountdownTask.cancel();
                }

                // broadcast to the players that they can the grace period has ended
                Bukkit.broadcastMessage(Color.AQUA + "Grace period has ended! You may now fight!");

                // Give kits, etc...


                // Start the game countdown to control when the game goes into deathmatch
                this.gameCountdownTask = new GameCountdownTask(this, 120);
                this.gameCountdownTask.runTaskTimer(plugin, 0, 20);

                break;
            case DEATHMATCH:
                Bukkit.broadcastMessage("The game is now in deathmatch");

                // Here is where we would normally teleport the player to a deathmatch location and add a broadcast
                // Since this plugin and map includes neither, we do nothing
                // in this case i'm going to broadcast exactly that for the purpose of the demonstration video
                Bukkit.broadcastMessage("*Players get teleported to deathmatch location*");
                Bukkit.broadcastMessage("*Players get broadcast message*");


                break;
            case WON:
                Bukkit.broadcastMessage("A player has won!");
                break;
            case RESTARTING:
                Bukkit.broadcastMessage("Now restarting the game");
            default:
                Bukkit.broadcastMessage(ChatColor.RED + "Something went wrong.");
        }
    }

    //-- Refilling Chests Method
    public void regenerateChests() {

        // Get an instance of the world
        World world = Bukkit.getServer().getWorld("The Survival Game V.1.3");

        // Go through the loaded chunks inside the world
        for (Chunk chunks : world.getLoadedChunks()) {
            for (BlockState chests : chunks.getTileEntities()) {

                // Conditional - if the blocks found are chests
                if ((chests instanceof Chest)) {

                    // Variables
                    Chest chest = (Chest)chests;

                    // Create random "slots" for the items to go
                    Random random = new Random();
                    int slot1 = random.nextInt(26);
                    int slot2 = random.nextInt(26);
                    int slot3 = random.nextInt(26);

                    // List of items
                    Material[] items = {
                            Material.WOOD_SWORD, (Material.WOOD_AXE), (Material.STONE_AXE), (Material.STONE_SWORD),
                            (Material.LEATHER_HELMET),(Material.LEATHER_BOOTS),(Material.IRON_CHESTPLATE),
                            (Material.IRON_LEGGINGS)
                    };

                    // Add random items from the list to the random slots
                    chest.getInventory().clear();
                    chest.getInventory().setItem(slot1, new ItemStack(items[random.nextInt(items.length)]));
                    chest.getInventory().setItem(slot2, new ItemStack(items[random.nextInt(items.length)]));
                    chest.getInventory().setItem(slot3, new ItemStack(items[random.nextInt(items.length)]));
                }
            }
        }

        // Notify the players that the chests have been restocked
        Bukkit.getServer().broadcastMessage(ChatColor.GREEN + "The chests have been restocked!");
    }

    //-- Getters & Setters
    public int getPlayersInQueue() {
        return playersInQueue;
    }

    public GameState getGameState(){
        return this.gameState;
    }

    public void setPlayersInQueue(int playersInQueue) {
        this.playersInQueue = playersInQueue;
    }

    public int getPlayersAlive() {
        return playersAlive;
    }

    public void setPlayersAlive(int playersAlive) {
        this.playersAlive = playersAlive;
    }
}