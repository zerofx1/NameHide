package ru.quizie.nameHide.displays;

import lombok.experimental.UtilityClass;
import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.entity.Display;
import org.bukkit.entity.Player;
import org.bukkit.entity.TextDisplay;
import org.bukkit.util.Transformation;
import org.joml.AxisAngle4f;
import org.joml.Vector3f;
import ru.quizie.nameHide.Config;
import ru.quizie.nameHide.NameHide;
import ru.quizie.nameHide.displays.data.DisplayData;
import ru.quizie.nameHide.utils.Colorizer;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;

@UtilityClass
public class DisplayManager {

    private final List<DisplayData> displays = new ArrayList<>();

    public void create(Player player, NameHide nameHide) {
        remove(player);

        final TextDisplay textDisplay = getBestTextDisplay(player.getLocation(), player);
        if (Config.hideYourName) player.hideEntity(nameHide, textDisplay);

        player.addPassenger(textDisplay);

        final DisplayData data = new DisplayData(textDisplay, player);
        displays.add(data);
    }

    public void remove(Player player) {
        final List<DisplayData> toRemove = new ArrayList<>();

        for (DisplayData data : displays) {
            if (data.getPlayer().getName().equals(player.getName())) {
                data.getTextDisplay().remove();
                toRemove.add(data);
            }
        }

        displays.removeAll(toRemove);
    }

    public void removeAllDisplays() {
        final List<DisplayData> toRemove = new ArrayList<>(displays);

        for (DisplayData data : toRemove) {
            data.getTextDisplay().remove();
        }

        displays.clear();
    }

    public DisplayData getDispalyData(Player player) {
        for (final DisplayData data : displays) {
            if (data.getPlayer().getName().equals(player.getName())) {
                return data;
            }
        }
        return null;
    }

    public void updateDisplay(Player player) {
        final DisplayData displayData = getDispalyData(player);
        if (displayData == null) return;

        final TextDisplay display = displayData.getTextDisplay();
        final Component newText = Component.text(PlaceholderAPI.setPlaceholders(player, Colorizer.use(Config.display)));

        if (!display.text().equals(newText)) display.text(newText);
    }


    public TextDisplay getBestTextDisplay(Location location, Player player) {
        final TextDisplay textDisplay = location.getWorld().spawn(location, TextDisplay.class);

        textDisplay.setText(PlaceholderAPI.setPlaceholders(player, Colorizer.use(Config.display)));
        textDisplay.setBillboard(Display.Billboard.CENTER);

        textDisplay.setTransformation(
                new Transformation(
                        new Vector3f(Config.VECTOR.x, Config.VECTOR.y, Config.VECTOR.z),
                        new AxisAngle4f(),
                        new Vector3f(Config.VECTOR2.x, Config.VECTOR2.y, Config.VECTOR2.z),
                        new AxisAngle4f()
                )
        );

        return textDisplay;
    }
}
