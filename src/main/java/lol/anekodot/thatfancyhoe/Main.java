package lol.anekodot.thatfancyhoe;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        // Plugin startup logic
        this.getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @EventHandler
    private void onCropTrample(PlayerInteractEvent event)
    {
        if (event.getAction() != Action.PHYSICAL) return;

        Block block = event.getClickedBlock();
        if (block == null || block.getType() != Material.FARMLAND) return;

        event.setCancelled(true);
    }


    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Block block = event.getBlock();

        if (event.getPlayer().getInventory().getItemInMainHand().getType().toString().endsWith("_HOE")) {
            if (block.getType() == Material.WHEAT) {
                Ageable ageable = (Ageable) block.getBlockData();

                    if (ageable.getAge() == ageable.getMaximumAge()) {
                        this.getServer().getScheduler().runTaskLater(this, () -> block.setType(Material.WHEAT), 1L);
                    } else {
                        event.setCancelled(true);
                    }
                }
        }
    }
}
