package net.swiftcodeapi;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * SwiftCodeAPI - The Toolkit Plugin
 * Provides 300+ static utility methods via the Swift class.
 * Does NOT run scripts — that's SwiftCodeEngine's job.
 */
public class SwiftCodeAPIPlugin extends JavaPlugin {

    private static SwiftCodeAPIPlugin instance;

    @Override
    public void onEnable() {
        instance = this;

        // Initialize the Swift API with this plugin as the host
        Swift.init(this);

        // Generate API documentation file
        DocGenerator.generate(this);

        // ASCII Art
        printLogo();

        getLogger().info("SwiftCode API v" + getDescription().getVersion() + " Enabled!");
        getLogger().info("All Swift.* methods are now available for scripts.");
    }

    @Override
    public void onDisable() {
        getLogger().info("SwiftCode API Disabled.");
    }

    public static SwiftCodeAPIPlugin getInstance() {
        return instance;
    }

    private void printLogo() {
        getLogger().info(ChatColor.AQUA + "  ____          _  __ _      _    ____ ___ ");
        getLogger().info(ChatColor.AQUA + " / ___|__      __(_)/ _| |_   / \\  |  _ \\_ _|");
        getLogger().info(ChatColor.AQUA + " \\___ \\\\ \\ /\\ / /| | |_| __| / _ \\ | |_) | | ");
        getLogger().info(ChatColor.AQUA + "  ___) |\\ V  V / | |  _| |_ / ___ \\|  __/| | ");
        getLogger().info(ChatColor.AQUA + " |____/  \\_/\\_/  |_|_|  \\__/_/   \\_\\_|  |___|");
        getLogger().info(ChatColor.GREEN + "         The Toolkit v1.0 (300+ Methods)     ");
    }
}
