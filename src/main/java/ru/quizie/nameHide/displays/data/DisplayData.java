package ru.quizie.nameHide.displays.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.bukkit.entity.Player;
import org.bukkit.entity.TextDisplay;

@Data @AllArgsConstructor
public class DisplayData {
    private final TextDisplay textDisplay;
    private final Player player;
}
