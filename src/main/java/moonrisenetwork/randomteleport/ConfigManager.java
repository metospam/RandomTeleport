package moonrisenetwork.randomteleport;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;


public class ConfigManager {

    private String path;
    private RandomTeleport instance;
    File file;
    private FileConfiguration config;


    public ConfigManager(String path){
        this.instance = RandomTeleport.getInstance();
        this.path = path;
        this.file = new File(instance.getDataFolder(), path);

        if (!file.exists()) {
            file.getParentFile().mkdirs();
            instance.saveResource(path, false);
        }

        config = new YamlConfiguration();

        try{
            config.load(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void save(){
        try {
            config.save(new File(instance.getDataFolder(), path));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public FileConfiguration getConfig(){
        return this.config;
    }
}
