package bm.minecraft.more.exp;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

public final class BmMinecraftMoreExpPlayerPlacedBlockRegistry {
    private final BmMinecraftMoreExpPlugin plugin;
    private final Set<String> blocks = new HashSet<>();
    private final File file;

    public BmMinecraftMoreExpPlayerPlacedBlockRegistry(BmMinecraftMoreExpPlugin plugin) {
        this.plugin = plugin;
        file = new File(plugin.getDataFolder(), "placed-blocks.yml");
    }

    public void add(Location location) {
        blocks.add(locationKey(location));
    }

    public boolean remove(Location location) {
        return blocks.remove(locationKey(location));
    }

    public int size() {
        return blocks.size();
    }

    public void load() {
        if (file.exists()) {
            blocks.addAll(YamlConfiguration.loadConfiguration(file).getStringList("blocks"));
        }
    }

    public void save() {
        YamlConfiguration configuration = new YamlConfiguration();
        configuration.set("blocks", blocks.stream().sorted().toList());
        try {
            configuration.save(file);
        } catch (IOException exception) {
            plugin.getLogger().warning(plugin.language().getConsole("placed-blocks-save-failed")
                    .replace("{error}", exception.getMessage()));
        }
    }

    private String locationKey(Location location) {
        return location.getWorld().getUID() + ":"
                + location.getBlockX() + ":"
                + location.getBlockY() + ":"
                + location.getBlockZ();
    }
}
