package bm.minecraft.more.exp;

import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

/** Awards experience for eligible naturally generated blocks. */
public final class BmMinecraftMoreExpPlugin extends JavaPlugin {
    private static final String COMMAND_NAME = "bm-minecraft-more-exp";

    private BmMinecraftMoreExpLanguageManager languageManager;
    private BmMinecraftMoreExpPlayerPlacedBlockRegistry placedBlockRegistry;
    private BukkitTask registrySaveTask;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        languageManager = new BmMinecraftMoreExpLanguageManager(this);
        languageManager.reload();
        placedBlockRegistry = new BmMinecraftMoreExpPlayerPlacedBlockRegistry(this);
        placedBlockRegistry.load();
        scheduleRegistrySaves();
        getServer().getPluginManager().registerEvents(new BmMinecraftMoreExpListener(this), this);
        registerCommand();
        getLogger().info(languageManager.getConsole("enabled").replace("{version}", getPluginMeta().getVersion()));
    }

    @Override
    public void onDisable() {
        if (registrySaveTask != null) {
            registrySaveTask.cancel();
        }
        if (placedBlockRegistry != null) {
            placedBlockRegistry.save();
        }
        if (languageManager != null) {
            getLogger().info(languageManager.getConsole("disabled"));
        }
    }

    public void reloadPluginConfig() {
        reloadConfig();
        languageManager.reload();
    }

    public BmMinecraftMoreExpLanguageManager language() {
        return languageManager;
    }

    public BmMinecraftMoreExpPlayerPlacedBlockRegistry placedBlocks() {
        return placedBlockRegistry;
    }

    private void registerCommand() {
        PluginCommand command = getCommand(COMMAND_NAME);
        if (command == null) {
            throw new IllegalStateException(languageManager.getConsole("missing-command")
                    .replace("{command}", COMMAND_NAME));
        }
        BmMinecraftMoreExpCommand commandHandler = new BmMinecraftMoreExpCommand(this);
        command.setExecutor(commandHandler);
        command.setTabCompleter(commandHandler);
    }

    private void scheduleRegistrySaves() {
        int intervalSeconds = getConfig().getInt("placement-registry-save-interval-seconds", 300);
        if (intervalSeconds <= 0) {
            return;
        }
        long intervalTicks = intervalSeconds * 20L;
        registrySaveTask = getServer().getScheduler().runTaskTimer(
                this,
                placedBlockRegistry::save,
                intervalTicks,
                intervalTicks
        );
    }
}
