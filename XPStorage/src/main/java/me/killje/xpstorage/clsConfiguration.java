package me.killje.xpstorage;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.logging.Level;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class clsConfiguration {

    private final JavaPlugin plugin;
    private FileConfiguration conf = null;
    private File file = null;
    private String fname = null;

    public clsConfiguration(JavaPlugin plugin, String filename) {
        this.plugin = plugin;
        fname = filename;
    }

    //Load the conf file and search for configs in the jar memory
    public void ReloadConfig() {
        if (file == null) {
            file = new File(plugin.getDataFolder(), fname);
        }
        conf = YamlConfiguration.loadConfiguration(file);

        //Look for defaults in the jar
        InputStream isDefaults = plugin.getResource(fname);
        if (isDefaults != null) {
            Reader reader = new InputStreamReader(isDefaults);
            YamlConfiguration confDefault = YamlConfiguration.loadConfiguration(reader);
            conf.setDefaults(confDefault);
        }
    }

    public FileConfiguration GetConfig() {
        if (conf == null) {
            ReloadConfig();	//If it's null, try reloading the configs
        }
        return conf;
    }

    public void SaveConfig() {
        if (conf == null || file == null) {
            return;
        }
        try {
            conf.save(file);	//Save the memory configurations to the config file
        } catch (IOException ex) {
            plugin.getLogger().log(Level.SEVERE, "IOException: Error saving configuration file '{0}'!", fname);
            plugin.getLogger().log(Level.SEVERE, ex.toString());
        }
    }
}
