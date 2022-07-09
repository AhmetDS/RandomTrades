package de.amin.bingo.utils;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import de.amin.bingo.BingoPlugin;

public class Config {

    static FileConfiguration config = BingoPlugin.INSTANCE.getConfig();
    private static final double CURRENT_CONFIG_VERSION = 1.0;

    public static double CONFIG_VERSION = config.getDouble("configVersion");
    public static String DEFAULT_LOCALE = config.getString("defaultLocale");
    public static int BOARD_SIZE = 25;

    public static boolean isDeprecated() {
        YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(new File(BingoPlugin.INSTANCE.getDataFolder(), "config.yml"));
        return CURRENT_CONFIG_VERSION>yamlConfiguration.getDouble("configVersion");
    }

}
