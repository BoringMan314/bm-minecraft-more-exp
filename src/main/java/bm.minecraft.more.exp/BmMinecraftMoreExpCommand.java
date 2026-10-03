package bm.minecraft.more.exp;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

public final class BmMinecraftMoreExpCommand implements CommandExecutor, TabCompleter {
    private final BmMinecraftMoreExpPlugin plugin;

    public BmMinecraftMoreExpCommand(BmMinecraftMoreExpPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("bm-minecraft-more-exp.admin")) {
            plugin.language().send(sender, "no-permission");
            return true;
        }
        if (args.length == 0) {
            sendHelp(sender);
            return true;
        }

        switch (args[0].toLowerCase(Locale.ROOT)) {
            case "0" -> setEnabled(sender, false);
            case "1" -> setEnabled(sender, true);
            case "reload" -> {
                plugin.reloadPluginConfig();
                plugin.language().send(sender, "reloaded");
            }
            case "info" -> plugin.language().send(sender, "info-version", Map.of("version", "26.3_0.0.1"));
            case "status" -> sendStatus(sender);
            case "set" -> setExperience(sender, args);
            default -> {
                plugin.language().send(sender, "unknown-command");
                plugin.language().send(sender, "usage-main");
            }
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length != 1) {
            return List.of();
        }
        String prefix = args[0].toLowerCase(Locale.ROOT);
        return List.of("0", "1", "info", "status", "reload", "set").stream()
                .filter(option -> option.startsWith(prefix))
                .toList();
    }

    private void sendHelp(CommandSender sender) {
        for (String key : List.of("help-header", "help-toggle", "help-reload", "help-info", "help-status", "help-set", "help-footer")) {
            plugin.language().send(sender, key);
        }
    }

    private void setEnabled(CommandSender sender, boolean enabled) {
        plugin.getConfig().set("enabled", enabled);
        plugin.saveConfig();
        plugin.language().send(sender, enabled ? "enabled" : "disabled");
    }

    private void setExperience(CommandSender sender, String[] args) {
        if (args.length != 2) {
            plugin.language().send(sender, "usage-set");
            return;
        }
        try {
            int experience = Integer.parseInt(args[1]);
            if (experience < 0) {
                throw new NumberFormatException();
            }
            plugin.getConfig().set("experience-per-block", experience);
            plugin.saveConfig();
            plugin.language().send(sender, "experience-set", Map.of("amount", String.valueOf(experience)));
        } catch (NumberFormatException exception) {
            plugin.language().send(sender, "invalid-number");
        }
    }

    private void sendStatus(CommandSender sender) {
        plugin.language().send(sender, "status", Map.of(
                "enabled", String.valueOf(plugin.getConfig().getBoolean("enabled")),
                "amount", String.valueOf(plugin.getConfig().getInt("experience-per-block")),
                "tracked", String.valueOf(plugin.placedBlocks().size())
        ));
    }
}
