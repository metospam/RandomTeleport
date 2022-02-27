package moonrisenetwork.randomteleport;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class RandomTeleport extends JavaPlugin {
    public static RandomTeleport instance;


    @Override
    public void onEnable() {
        instance = this;
        new RTCommands();
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();
    }

    @Override
    public void onDisable() {}

    public static RandomTeleport getInstance() {
        return instance;
    }

}
