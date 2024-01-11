package com.disunion.thehungergames.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class OnBlockBreak implements Listener {

    @EventHandler
    public void OnBlockBreakEvent(BlockBreakEvent e){

        // Grab the player that triggered the event, and cancel it if they aren't opped
        Player player = e.getPlayer();
        if (!player.isOp()){
            e.setCancelled(true);
        }
    }
}
