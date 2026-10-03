package bm.minecraft.more.exp;

import java.util.Locale;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public final class BmMinecraftMoreExpListener implements Listener {
    private final BmMinecraftMoreExpPlugin plugin;

    public BmMinecraftMoreExpListener(BmMinecraftMoreExpPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlace(BlockPlaceEvent event) {
        if (plugin.getConfig().getBoolean("track-player-placed-blocks", true)) {
            plugin.placedBlocks().add(event.getBlockPlaced().getLocation());
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onBreak(BlockBreakEvent event) {
        if (!plugin.getConfig().getBoolean("enabled", true)
                || !event.getBlock().getType().isSolid()
                || !isAllowedGameMode(event.getPlayer().getGameMode())) {
            return;
        }
        if (plugin.getConfig().getBoolean("require-receive-permission", false)
                && !event.getPlayer().hasPermission("bm-minecraft-more-exp.receive")) {
            return;
        }
        boolean placed = plugin.placedBlocks().remove(event.getBlock().getLocation());
        if (placed || !plugin.getConfig().getBoolean("untracked-blocks-count-as-natural", true)) {
            return;
        }
        int amount = Math.max(0, plugin.getConfig().getInt("experience-per-block", 1));
        if (amount > 0) {
            event.setExpToDrop(event.getExpToDrop() + amount);
        }
    }

    private boolean isAllowedGameMode(GameMode gameMode) {
        return plugin.getConfig().getStringList("allowed-game-modes").stream()
                .anyMatch(value -> value.equalsIgnoreCase(gameMode.name()));
    }
}
