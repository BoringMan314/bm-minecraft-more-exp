package bm.minecraft.more.exp;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Map;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;

public final class BmMinecraftMoreExpLanguageManager {
    private static final String FALLBACK_LANGUAGE = "zh_TW";

    private final BmMinecraftMoreExpPlugin plugin;
    private YamlConfiguration messages = new YamlConfiguration();

    public BmMinecraftMoreExpLanguageManager(BmMinecraftMoreExpPlugin plugin) {
        this.plugin = plugin;
    }

    public void reload() {
        plugin.saveResource("active-language.yml", true);
        String locale = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), "active-language.yml"))
                .getString("language", FALLBACK_LANGUAGE);
        String resourcePath = "lang/" + locale + ".yml";
        if (plugin.getResource(resourcePath) == null) {
            YamlConfiguration fallback = loadBundledConfiguration("lang/" + FALLBACK_LANGUAGE + ".yml");
            plugin.getLogger().warning(console(fallback, "unknown-language", Map.of(
                    "language", locale,
                    "fallback", FALLBACK_LANGUAGE)));
            resourcePath = "lang/" + FALLBACK_LANGUAGE + ".yml";
        }
        YamlConfiguration bundled = loadBundledConfiguration(resourcePath);
        File languageFile = new File(plugin.getDataFolder(), resourcePath);
        if (!languageFile.isFile()) {
            plugin.saveResource(resourcePath, false);
        } else if (!upgradeOutdatedLanguageFile(languageFile, resourcePath, bundled)) {
            messages = bundled;
            return;
        }
        messages = YamlConfiguration.loadConfiguration(languageFile);
        messages.setDefaults(bundled);
        saveMissingDefaults(languageFile);
        messages = YamlConfiguration.loadConfiguration(languageFile);
    }

    public String get(String key) {
        return ChatColor.translateAlternateColorCodes('&', messages.getString("messages." + key, key));
    }

    public String getConsole(String key) {
        return messages.getString("console." + key, key);
    }

    private YamlConfiguration loadBundledConfiguration(String resourcePath) {
        try (InputStream input = plugin.getResource(resourcePath)) {
            if (input != null) {
                return YamlConfiguration.loadConfiguration(new InputStreamReader(input, StandardCharsets.UTF_8));
            }
        } catch (Exception exception) {
            plugin.getLogger().warning(getConsole("bundled-defaults-load-failed")
                    .replace("{resource}", resourcePath));
        }
        return new YamlConfiguration();
    }

    private boolean upgradeOutdatedLanguageFile(
            File languageFile, String resourcePath, YamlConfiguration bundled) {
        int localVersion = YamlConfiguration.loadConfiguration(languageFile)
                .getInt("language-format-version", 0);
        int bundledVersion = bundled.getInt("language-format-version", 0);
        if (localVersion >= bundledVersion) {
            return true;
        }

        File backupFile = new File(
                languageFile.getParentFile(),
                languageFile.getName() + ".pre-v" + bundledVersion + ".bak");
        File temporaryFile = new File(languageFile.getParentFile(), languageFile.getName() + ".upgrade.tmp");
        try (InputStream input = plugin.getResource(resourcePath)) {
            if (input == null) {
                throw new IOException("Bundled resource is unavailable");
            }
            Files.copy(input, temporaryFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            Files.copy(languageFile.toPath(), backupFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            try {
                Files.move(temporaryFile.toPath(), languageFile.toPath(),
                        StandardCopyOption.ATOMIC_MOVE, StandardCopyOption.REPLACE_EXISTING);
            } catch (AtomicMoveNotSupportedException exception) {
                Files.move(temporaryFile.toPath(), languageFile.toPath(),
                        StandardCopyOption.REPLACE_EXISTING);
            }
            return true;
        } catch (Exception exception) {
            try {
                Files.deleteIfExists(temporaryFile.toPath());
            } catch (IOException ignored) {
                // The original language file is still intact.
            }
            plugin.getLogger().warning(console(bundled, "language-upgrade-failed", Map.of(
                    "file", languageFile.getName(),
                    "error", exception.getMessage() == null ? exception.getClass().getSimpleName() : exception.getMessage())));
            return false;
        }
    }

    private void saveMissingDefaults(File languageFile) {
        messages.options().copyDefaults(true);
        try {
            messages.save(languageFile);
        } catch (Exception exception) {
            plugin.getLogger().warning(getConsole("language-update-failed")
                    .replace("{file}", languageFile.getName()));
        }
    }

    public void send(CommandSender sender, String key) {
        sender.sendMessage(get(key));
    }

    public void send(CommandSender sender, String key, Map<String, String> replacements) {
        sender.sendMessage(replace(get(key), replacements));
    }

    private String console(YamlConfiguration configuration, String key, Map<String, String> replacements) {
        return replace(configuration.getString("console." + key, key), replacements);
    }

    private String replace(String message, Map<String, String> replacements) {
        for (Map.Entry<String, String> entry : replacements.entrySet()) {
            message = message.replace("{" + entry.getKey() + "}", entry.getValue());
        }
        return message;
    }
}
