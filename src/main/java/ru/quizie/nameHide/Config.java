package ru.quizie.nameHide;

import lombok.experimental.UtilityClass;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

@UtilityClass
public class Config {

    private FileConfiguration config;

    public void load(FileConfiguration file) {
        config = file;

        radius = config.getInt("radius");
        repeated = config.getInt("repeated");
        display = config.getString("display");
        hideYourName = config.getBoolean("hide_your_name");

        parseVector();
        parseVector2();
    }

    private void parseVector() {
        final ConfigurationSection section = config.getConfigurationSection("vector");

        VECTOR.x = Float.parseFloat(section.getString("x"));
        VECTOR.y = Float.parseFloat(section.getString("y"));
        VECTOR.z = Float.parseFloat(section.getString("z"));
    }

    private void parseVector2() {
        final ConfigurationSection section = config.getConfigurationSection("vector-2");

        VECTOR2.x = Float.parseFloat(section.getString("x"));
        VECTOR2.y = Float.parseFloat(section.getString("y"));
        VECTOR2.z = Float.parseFloat(section.getString("z"));
    }


    public int radius, repeated;
    public String display;
    public boolean hideYourName;

    @UtilityClass
    public class VECTOR {
        public float x, y, z;
    }

    @UtilityClass
    public class VECTOR2 {
        public float x, y, z;
    }
}
